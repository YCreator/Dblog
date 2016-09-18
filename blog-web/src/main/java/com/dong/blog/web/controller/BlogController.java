package com.dong.blog.web.controller;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dong.blog.application.BlogApplication;
import com.dong.blog.application.CommentApplication;
import com.dong.blog.application.dto.BlogDTO;
import com.dong.blog.application.dto.CommentDTO;
import com.dong.blog.lucene.BlogIndex;
import com.dong.blog.util.StringUtil;

/**
 * 博客Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

	@Inject
	private BlogApplication blogApplication;
	
	@Inject
	private CommentApplication commentApplication;
		
	// 博客索引
	private BlogIndex blogIndex=new BlogIndex();
	
	/**
	 * 请求博客详细信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/articles/{id}")
	public ModelAndView details(@PathVariable("id") Long id,HttpServletRequest request)throws Exception{
		ModelAndView mav=new ModelAndView();
		BlogDTO blogDTO = blogApplication.get(id);
		String keyWords=blogDTO.getKeyWord();
		if(StringUtil.isNotEmpty(keyWords)){
			String arr[]=keyWords.split(" ");
			mav.addObject("keyWords",StringUtil.filterWhite(Arrays.asList(arr)));			
		}else{
			mav.addObject("keyWords",null);			
		}
		mav.addObject("blog", blogDTO);
		blogDTO.setClickHit(blogDTO.getClickHit() + 1);
		blogApplication.updateClickHit(blogDTO.getId(), blogDTO.getClickHit()); // 博客点击次数加1
		
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setBlogDTO(blogDTO);
		commentDTO.setState(1); // 查询审核通过的评论
		
		mav.addObject("commentList", commentApplication.getPage(commentDTO, 0, 100).getData()); 
		mav.addObject("pageCode", this.genUpAndDownPageCode(blogApplication.getLastBlog(id),blogApplication.getNextBlog(id),request.getServletContext().getContextPath()));
		mav.addObject("mainPage", "foreground/blog/view.jsp");
		mav.addObject("pageTitle",blogDTO.getTitle()+"_Dong博客系统");
		mav.setViewName("index");
		return mav;
	}
	
	/**
	 * 根据关键字查询相关博客信息
	 * @param q
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/q")
	public ModelAndView search(@RequestParam(value="q",required=false)String q,@RequestParam(value="page",required=false)String page,HttpServletRequest request)throws Exception{
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		ModelAndView mav=new ModelAndView();
		mav.addObject("mainPage", "foreground/blog/result.jsp");
		List<BlogDTO> blogList=blogIndex.searchBlog(q.trim());
		Integer toIndex=blogList.size()>=Integer.parseInt(page)*10?Integer.parseInt(page)*10:blogList.size();
		List<BlogDTO> list = new LinkedList<BlogDTO>();
		for (int i = (Integer.parseInt(page)-1)*10 ; i < toIndex; i++) {
			list.add(blogList.get(i));
		}
		//mav.addObject("blogList",blogList.subList((Integer.parseInt(page)-1)*10, toIndex));
		mav.addObject("blogList",list);
		mav.addObject("pageCode",this.genUpAndDownPageCode(Integer.parseInt(page), blogList.size(), q,10,request.getServletContext().getContextPath()));
		mav.addObject("q",q);
		mav.addObject("resultTotal",blogList.size());
		mav.addObject("pageTitle","搜索关键字'"+q+"'结果页面_Java开源博客系统");
		mav.setViewName("index");
		return mav;
	}
	
	/**
	 * 获取下一篇博客和下一篇博客代码
	 * @param lastBlog
	 * @param nextBlog
	 * @return
	 */
	private String genUpAndDownPageCode(BlogDTO lastBlog,BlogDTO nextBlog,String projectContext){
		StringBuffer pageCode=new StringBuffer();
		if(lastBlog==null || lastBlog.getId()==null){
			pageCode.append("<p>上一篇：没有了</p>");
		}else{
			pageCode.append("<p>上一篇：<a href='"+projectContext+"/blog/articles/"+lastBlog.getId()+".html'>"+lastBlog.getTitle()+"</a></p>");
		}
		if(nextBlog==null || nextBlog.getId()==null){
			pageCode.append("<p>下一篇：没有了</p>");
		}else{
			pageCode.append("<p>下一篇：<a href='"+projectContext+"/blog/articles/"+nextBlog.getId()+".html'>"+nextBlog.getTitle()+"</a></p>");
		}
		return pageCode.toString();
	}
	
	/**
	 * 获取上一页，下一页代码 查询博客用到
	 * @param page 当前页
	 * @param totalNum 总记录数
	 * @param q 查询关键字
	 * @param pageSize 每页大小
	 * @param projectContext
	 * @return
	 */
	private String genUpAndDownPageCode(Integer page,Integer totalNum,String q,Integer pageSize,String projectContext){
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode=new StringBuffer();
		if(totalPage==0){
			return "";
		}else{
			pageCode.append("<nav>");
			pageCode.append("<ul class='pager' >");
			if(page>1){
				pageCode.append("<li><a href='"+projectContext+"/blog/q.html?page="+(page-1)+"&q="+q+"'>上一页</a></li>");
			}else{
				pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
			}
			if(page<totalPage){
				pageCode.append("<li><a href='"+projectContext+"/blog/q.html?page="+(page+1)+"&q="+q+"'>下一页</a></li>");				
			}else{
				pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");				
			}
			pageCode.append("</ul>");
			pageCode.append("</nav>");
		}
		return pageCode.toString();
	}
}
