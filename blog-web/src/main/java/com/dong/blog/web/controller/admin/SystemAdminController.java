package com.dong.blog.web.controller.admin;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.dong.blog.application.BlogApplication;
import com.dong.blog.application.BlogTypeApplication;
import com.dong.blog.application.BloggerApplication;
import com.dong.blog.application.LinkApplication;
import com.dong.blog.application.dto.BlogDTO;
import com.dong.blog.application.dto.BlogTypeDTO;
import com.dong.blog.application.dto.BloggerDTO;
import com.dong.blog.application.dto.LinkDTO;
import com.dong.blog.web.util.ResponseUtil;

/**
 * 管理员系统Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/system")
public class SystemAdminController {

	@Inject
	private BloggerApplication bloggerApplication;
	@Inject
	private BlogTypeApplication blogTypeApplication;
	@Inject
	private BlogApplication blogApplication;
	@Inject
	private LinkApplication linkApplication;
	
	/**
	 * 刷新系统缓存
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/refreshSystem")
	public String refreshSystem(HttpServletResponse response,HttpServletRequest request)throws Exception{
		ServletContext application=RequestContextUtils.findWebApplicationContext(request).getServletContext();
		BloggerDTO blogger = bloggerApplication.getBlogger(); // 查询博主信息
		blogger.setPassword(null);
		application.setAttribute("blogger", blogger);
		
		List<BlogTypeDTO> blogTypeCountList=blogTypeApplication.findAll(); // 查询博客类别以及博客的数量
		application.setAttribute("blogTypeCountList", blogTypeCountList);
		
		//List<Blog> blogCountList=blogService.countList(); // 根据日期分组查询博客
		List<BlogDTO> blogCountList=blogApplication.findAll();
		application.setAttribute("blogCountList", blogCountList);
		
		List<LinkDTO> linkList=linkApplication.findAll(); // 获取所有友情链接
		application.setAttribute("linkList", linkList);
		
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
}
