package com.dong.blog.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.dayatang.utils.Page;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dong.blog.application.BlogApplication;
import com.dong.blog.application.dto.BlogDTO;
import com.dong.blog.application.dto.BlogTypeDTO;
import com.dong.blog.application.dto.PageBean;
import com.dong.blog.web.util.PageUtil;
import com.dong.blog.util.StringUtil;

/**
 * 主页Controller
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@Inject
	private BlogApplication blogApplication;
		
	/**
	 * 请求主页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/myindex")
	public ModelAndView index(@RequestParam(value="page",required=false)String page,@RequestParam(value="typeId",required=false)String typeId,@RequestParam(value="releaseDateStr",required=false)String releaseDateStr,HttpServletRequest request)throws Exception{
		ModelAndView mav=new ModelAndView();
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),10);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("typeId", typeId);
		map.put("releaseDateStr", releaseDateStr);
		//List<Blog> blogList=blogService.list(map);
		BlogDTO dto = new BlogDTO();
		Page<BlogDTO> blogList=blogApplication.pageQuery(dto, pageBean.getStart(), pageBean.getPageSize());
		for (BlogDTO d : blogList.getData()) {
			List<String> imagesList=d.getImagesList();
			String blogInfo=d.getContent();
			Document doc=Jsoup.parse(blogInfo);
			Elements jpgs=doc.select("img[src$=.jpg]"); //　查找扩展名是jpg的图片
			for(int i=0;i<jpgs.size();i++){
				Element jpg=jpgs.get(i);
				imagesList.add(jpg.toString());
				if(i==2){
					break;
				}
			}
		}
		/*for(Blog blog:blogList){
			List<String> imagesList=blog.getImagesList();
			String blogInfo=blog.getContent();
			Document doc=Jsoup.parse(blogInfo);
			Elements jpgs=doc.select("img[src$=.jpg]"); //　查找扩展名是jpg的图片
			for(int i=0;i<jpgs.size();i++){
				Element jpg=jpgs.get(i);
				imagesList.add(jpg.toString());
				if(i==2){
					break;
				}
			}
		}*/
		mav.addObject("blogList", blogList.getData());
		StringBuffer param=new StringBuffer(); // 查询参数
		if(StringUtil.isNotEmpty(typeId)){
			param.append("typeId="+typeId+"&");
		}
		if(StringUtil.isNotEmpty(releaseDateStr)){
			param.append("releaseDateStr="+releaseDateStr+"&");
		}
		mav.addObject("test","hello");
		//mav.addObject("pageCode",PageUtil.genPagination(request.getContextPath()+"/index.html", blogService.getTotal(map), Integer.parseInt(page), 10, param.toString()));
		mav.addObject("mainPage", "foreground/blog/list.jsp");
		mav.addObject("pageTitle","Java开源博客系统");
		mav.setViewName("mainTemp");
		return mav;
	}
	
	/**
	 * 源码下载
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/download")
	public ModelAndView download()throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("mainPage", "foreground/system/download.jsp");
		mav.addObject("pageTitle","本站源码下载页面_Java开源博客系统");
		mav.setViewName("mainTemp");
		return mav;
	}
	
	/**
	 * 请求主页
	 * @param page
	 * @param typeId
	 * @param releaseDateStr
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public ModelAndView staticIndex(@RequestParam(value="page",required=false)String page,@RequestParam(value="typeId",required=false)String typeId,@RequestParam(value="releaseDateStr",required=false)String releaseDateStr,HttpServletRequest request) throws Exception {
		ModelAndView mav=new ModelAndView();
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		PageBean pageBean=new PageBean(Integer.parseInt(page),10);
		BlogDTO dto = new BlogDTO();
		if (StringUtil.isNotEmpty(typeId)) {
			BlogTypeDTO typeDTO = new BlogTypeDTO();
			typeDTO.setId(Long.valueOf(typeId));
			dto.setBlogTypeDTO(typeDTO);
		}
		Page<BlogDTO> blogList=blogApplication.pageQuery(dto, pageBean.getPage(), pageBean.getPageSize());
		mav.addObject("blogList", blogList.getData());
		
		StringBuffer param=new StringBuffer(); // 查询参数
		if(StringUtil.isNotEmpty(typeId)){
			param.append("typeId="+typeId+"&");
		}
		if(StringUtil.isNotEmpty(releaseDateStr)){
			param.append("releaseDateStr="+releaseDateStr+"&");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		if (typeId != null && !typeId.equals("")) {
			params.put("typeId", typeId);
		}
		mav.addObject("pageCode",PageUtil.genPagination(request.getContextPath()+"/index.html",blogApplication
				.getTotal(params).longValue(), Integer.parseInt(page), 10, param.toString()));
		params.clear();
		params.put("orderType", "clickHit");
		List<BlogDTO> clickBlogs = blogApplication.pageQuery(params, 0, 6).getData(); //查询点击量最高的前六篇博客
		mav.addObject("clickBlogs", clickBlogs);
		params.put("orderType", "releaseDate");
		List<BlogDTO> dateBlogs = blogApplication.pageQuery(params, 0, 6).getData(); //查询最新的六篇博客
		mav.addObject("dateBlogs", dateBlogs);
		
		mav.addObject("pageTitle","Dong博客系统");
		mav.addObject("mainPage", "foreground/myblog/home.jsp");
		mav.addObject("listPage", "list.jsp");
		mav.setViewName("index");
		return mav;
	}
}
