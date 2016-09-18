package com.dong.blog.web.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.dong.blog.application.BlogTypeApplication;
import com.dong.blog.application.BloggerApplication;
import com.dong.blog.application.CategoryApplication;
import com.dong.blog.application.LinkApplication;
import com.dong.blog.application.dto.BlogTypeDTO;
import com.dong.blog.application.dto.BloggerDTO;
import com.dong.blog.application.dto.CategoryDTO;
import com.dong.blog.application.dto.LinkDTO;

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
	private LinkApplication linkApplication;
	@Inject
	private CategoryApplication categoryApplication;
	
	/**
	 * 刷新系统缓存
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/refreshSystem")
	public Map<String, Object> refreshSystem(HttpServletRequest request) throws Exception {
		
		ServletContext application=RequestContextUtils.findWebApplicationContext(request).getServletContext();
		BloggerDTO blogger = bloggerApplication.getBlogger(); // 查询博主信息
		blogger.setPassword(null);
		application.setAttribute("blogger", blogger);

		List<BlogTypeDTO> blogTypeCountList=blogTypeApplication.findAll(); // 查询博客类别以及博客的数量
		application.setAttribute("blogTypeCountList", blogTypeCountList);
		
		List<CategoryDTO> categorys = categoryApplication.findAllBySort();	//获取主分类
		application.setAttribute("categorys", categorys);
		
		List<LinkDTO> linkList=linkApplication.findAll(); // 获取所有友情链接
		application.setAttribute("linkList", linkList);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return result;
	}
}
