package com.dong.blog.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.dayatang.cache.memcached.MemcachedBasedCache;
import org.dayatang.utils.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dong.blog.facade.BlogFacade;
import com.dong.blog.facade.CategoryFacade;
import com.dong.blog.facade.dto.BlogDTO;
import com.dong.blog.facade.dto.CategoryDTO;
import com.dong.blog.facade.dto.PageBean;
import com.dong.blog.util.StringUtil;
import com.dong.blog.web.util.PageUtil;

/**
 * 主页Controller
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@Inject
	private BlogFacade blogFacade;

	@Inject
	private CategoryFacade categoryFacade;
	
	@Inject
	private MemcachedBasedCache memcachedBasedCache;

	/**
	 * 源码下载
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/download")
	public ModelAndView download() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("mainPage", "foreground/system/download.jsp");
		mav.addObject("pageTitle", "本站源码下载页面_Java开源博客系统");
		mav.setViewName("mainTemp");
		return mav;
	}

	/**
	 * 请求主页
	 * 
	 * @param page
	 * @param typeId
	 * @param releaseDateStr
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public ModelAndView index(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "typeId", required = false) String typeId,
			@RequestParam(value = "releaseDateStr", required = false) String releaseDateStr,
			HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		Integer intPage = Integer.parseInt(StringUtil.isEmpty(page) ? "1" : page);
		PageBean pageBean = new PageBean(intPage, 10);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderType", "releaseDate");
		if (StringUtil.isNotEmpty(typeId)) {
			params.put("blogTypeId", typeId);
		}
		Page<BlogDTO> blogList = blogFacade.pageQuery(params, pageBean.getPage(), pageBean.getPageSize());
		mav.addObject("blogList", blogList.getData());

		StringBuilder param = new StringBuilder(); // 查询参数
		if (StringUtil.isNotEmpty(typeId)) {
			param.append("typeId=" + typeId + "&");
		}
		if (StringUtil.isNotEmpty(releaseDateStr)) {
			param.append("releaseDateStr=" + releaseDateStr + "&");
		}

		mav.addObject("pageCode", PageUtil.genPagination(
				request.getContextPath() + "/index.html",
				blogFacade.getTotal(params).longValue(),
				intPage, 10, param.toString()));
		
		params.clear();
		
		@SuppressWarnings("unchecked")
		List<BlogDTO> clickBlogs = (List<BlogDTO>) memcachedBasedCache.get("clickBlogs");
		if (clickBlogs == null) {
			params.put("orderType", "clickHit");
			clickBlogs = blogFacade.pageQuery(params, 0, 6).getData(); // 查询点击量最高的前六篇博客
			memcachedBasedCache.put("clickBlogs", clickBlogs, 60 * 1000);
		}
		mav.addObject("clickBlogs", clickBlogs);
		
		@SuppressWarnings("unchecked")
		List<BlogDTO> dateBlogs = (List<BlogDTO>) memcachedBasedCache.get("dateBlogs");
		if (dateBlogs == null) {
			params.put("orderType", "releaseDate");
			dateBlogs = blogFacade.pageQuery(params, 0, 6).getData(); // 查询最新的六篇博客
			memcachedBasedCache.put("dateBlogs", dateBlogs, 60 * 1000);
		}
		mav.addObject("dateBlogs", dateBlogs);
		
		@SuppressWarnings("unchecked")
		List<BlogDTO> replyBlogs = (List<BlogDTO>) memcachedBasedCache.get("replyBlogs");
		if (replyBlogs == null) {
			params.put("orderType", "replyHit");
			replyBlogs = blogFacade.pageQuery(params, 0, 6).getData(); //查询评论最多的六篇博客
			memcachedBasedCache.put("replyBlogs", dateBlogs, 3600 * 1000);
		}
		mav.addObject("replyBlogs", replyBlogs);

		mav.addObject("categoryName", "");

		mav.addObject("pageTitle", "Dong博客系统");
		mav.addObject("mainPage", "foreground/myblog/home.jsp");
		mav.addObject("listPage", "list.jsp");
		mav.setViewName("index");
		return mav;
	}

	/**
	 * 根据分类获取博客列表
	 * 
	 * @param page
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index/{id}")
	public ModelAndView getBlogsByCate(
			@RequestParam(value = "page", required = false) String page,
			@PathVariable("id") Long id, HttpServletRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		Integer intPage = Integer.parseInt(StringUtil.isEmpty(page) ? "1" : page);
		PageBean pageBean = new PageBean(intPage, 10);
		CategoryDTO dto = new CategoryDTO();
		dto.setId(id);
		Page<BlogDTO> blogList = blogFacade.pageQueryByCate(dto,
				pageBean.getPage(), pageBean.getPageSize());
		mav.addObject("blogList", blogList.getData());

		StringBuffer param = new StringBuffer(); // 查询参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cateId", id);
		mav.addObject("pageCode", PageUtil.genPagination(
				request.getContextPath() + "/index/" + id + ".html",
				blogFacade.getTotal(params).longValue(),
				intPage, 10, param.toString()));
		params.clear();
		params.put("orderType", "clickHit");
		List<BlogDTO> clickBlogs = blogFacade.pageQuery(params, 0, 6)
				.getData(); // 查询点击量最高的前六篇博客
		mav.addObject("clickBlogs", clickBlogs);
		params.put("orderType", "releaseDate");
		List<BlogDTO> dateBlogs = blogFacade.pageQuery(params, 0, 6)
				.getData(); // 查询最新的六篇博客
		mav.addObject("dateBlogs", dateBlogs);

		String name = categoryFacade.getCateNameById(id);
		mav.addObject("categoryName", String.format("标签为“%s”的内容", name));

		mav.addObject("pageTitle", "Dong博客系统");
		mav.addObject("mainPage", "foreground/myblog/list.jsp");
		mav.setViewName("index");
		return mav;
	}
}
