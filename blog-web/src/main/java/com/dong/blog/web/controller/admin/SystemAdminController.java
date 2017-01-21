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

import com.dong.blog.facade.BlogTypeFacade;
import com.dong.blog.facade.BloggerFacade;
import com.dong.blog.facade.CategoryFacade;
import com.dong.blog.facade.LinkFacade;
import com.dong.blog.facade.dto.BlogTypeDTO;
import com.dong.blog.facade.dto.BloggerDTO;
import com.dong.blog.facade.dto.CategoryDTO;
import com.dong.blog.facade.dto.LinkDTO;

/**
 * 管理员系统Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/system")
public class SystemAdminController {

	@Inject
	private BloggerFacade bloggerFacade;
	@Inject
	private BlogTypeFacade blogTypeFacade;
	@Inject
	private LinkFacade linkFacade;
	@Inject
	private CategoryFacade categoryFacade;
	
	/**
	 * 刷新系统缓存
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/refreshSystem")
	public Map<String, Object> refreshSystem(HttpServletRequest request) throws Exception {
		
		ServletContext Facade=RequestContextUtils.findWebApplicationContext(request).getServletContext();
		BloggerDTO blogger = bloggerFacade.getBlogger(); // 查询博主信息
		blogger.setPassword(null);
		Facade.setAttribute("blogger", blogger);

		List<BlogTypeDTO> blogTypeCountList=blogTypeFacade.findAll(); // 查询博客类别以及博客的数量
		Facade.setAttribute("blogTypeCountList", blogTypeCountList);
		
		List<CategoryDTO> categorys = categoryFacade.findAllBySort();	//获取主分类
		Facade.setAttribute("categorys", categorys);
		
		List<LinkDTO> linkList=linkFacade.findAll(); // 获取所有友情链接
		Facade.setAttribute("linkList", linkList);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return result;
	}
}
