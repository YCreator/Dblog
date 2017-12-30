package com.dong.blog.web.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.dayatang.domain.InstanceFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dong.blog.facade.BlogTypeFacade;
import com.dong.blog.facade.BloggerFacade;
import com.dong.blog.facade.CategoryFacade;
import com.dong.blog.facade.LinkFacade;
import com.dong.blog.facade.dto.BlogTypeDTO;
import com.dong.blog.facade.dto.BloggerDTO;
import com.dong.blog.facade.dto.CategoryDTO;
import com.dong.blog.facade.dto.LinkDTO;
import com.dong.blog.lucene.BlogIndex;
import com.dong.blog.web.util.ConfigUtil;
import com.dong.blog.web.util.Configuration;

/**
 * 初始化组件 把博主信息 根据博客类别分类信息 根据日期归档分类信息 存放到application中，用以提供页面请求性能
 * 
 * @author Administrator
 *
 */
@Component
public class InitComponent implements ServletContextListener,
		ApplicationContextAware {

	private LinkFacade linkFacade;
	private BlogTypeFacade blogTypeFacade;
	private BloggerFacade bloggerFacade;
	private CategoryFacade categoryFacade;

	private LinkFacade getLinkFacade() {
		if (linkFacade == null) {
			linkFacade = InstanceFactory
					.getInstance(LinkFacade.class);
		}
		return linkFacade;
	}

	private BlogTypeFacade getBlogTypeFacade() {
		if (blogTypeFacade == null) {
			blogTypeFacade = InstanceFactory
					.getInstance(BlogTypeFacade.class);
		}
		return blogTypeFacade;
	}

	private BloggerFacade getBloggerFacade() {
		if (bloggerFacade == null) {
			bloggerFacade = InstanceFactory
					.getInstance(BloggerFacade.class);
		}
		return bloggerFacade;
	}

	private CategoryFacade getCategoryFacade() {
		if (categoryFacade == null) {
			categoryFacade = InstanceFactory
					.getInstance(CategoryFacade.class);
		}
		return categoryFacade;
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext application = servletContextEvent.getServletContext();

		List<LinkDTO> linkList = getLinkFacade().findAll(); // 查询所有的友情链接信息
		application.setAttribute("linkList", linkList);

		List<BlogTypeDTO> typeList = getBlogTypeFacade().findAll(); // 查询博客类别以及博客的数量
		application.setAttribute("blogTypeCountList", typeList);

		BloggerDTO blogger = getBloggerFacade().getBlogger();
		blogger.setPassword(null);
		application.setAttribute("blogger", blogger);

		List<CategoryDTO> categoryDTOs = getCategoryFacade().findAllBySort(); // 获取主分类
		application.setAttribute("categorys", categoryDTOs);
		
		ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(application);
		BlogIndex mBlogIndex = applicationContext.getBean("blogIndex", BlogIndex.class);
		BlogIndex.setInstance(mBlogIndex);		//获取搜索引擎
		
		System.out.println("================="+ ConfigUtil.SOCKET_CLIENT_HOST+"=======================");
		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {

	}

}
