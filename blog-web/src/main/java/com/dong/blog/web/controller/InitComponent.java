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

import com.dong.blog.application.BlogTypeApplication;
import com.dong.blog.application.BloggerApplication;
import com.dong.blog.application.CategoryApplication;
import com.dong.blog.application.LinkApplication;
import com.dong.blog.application.dto.BlogTypeDTO;
import com.dong.blog.application.dto.BloggerDTO;
import com.dong.blog.application.dto.CategoryDTO;
import com.dong.blog.application.dto.LinkDTO;

/**
 * 初始化组件 把博主信息 根据博客类别分类信息 根据日期归档分类信息 存放到application中，用以提供页面请求性能
 * 
 * @author Administrator
 *
 */
@Component
public class InitComponent implements ServletContextListener,
		ApplicationContextAware {

	private LinkApplication linkApplication;
	private BlogTypeApplication blogTypeApplication;
	private BloggerApplication bloggerApplication;
	private CategoryApplication categoryApplication;

	private LinkApplication getLinkApplication() {
		if (linkApplication == null) {
			linkApplication = InstanceFactory
					.getInstance(LinkApplication.class);
		}
		return linkApplication;
	}

	private BlogTypeApplication getBlogTypeApplication() {
		if (blogTypeApplication == null) {
			blogTypeApplication = InstanceFactory
					.getInstance(BlogTypeApplication.class);
		}
		return blogTypeApplication;
	}

	private BloggerApplication getBloggerApplication() {
		if (bloggerApplication == null) {
			bloggerApplication = InstanceFactory
					.getInstance(BloggerApplication.class);
		}
		return bloggerApplication;
	}

	private CategoryApplication getCategoryApplication() {
		if (categoryApplication == null) {
			categoryApplication = InstanceFactory
					.getInstance(CategoryApplication.class);
		}
		return categoryApplication;
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext application = servletContextEvent.getServletContext();

		List<LinkDTO> linkList = getLinkApplication().findAll(); // 查询所有的友情链接信息
		application.setAttribute("linkList", linkList);

		List<BlogTypeDTO> typeList = getBlogTypeApplication().findAll(); // 查询博客类别以及博客的数量
		application.setAttribute("blogTypeCountList", typeList);

		BloggerDTO blogger = getBloggerApplication().getBlogger();
		blogger.setPassword(null);
		application.setAttribute("blogger", blogger);

		List<CategoryDTO> categoryDTOs = getCategoryApplication()
				.findAllBySort(); // 获取主分类
		application.setAttribute("categorys", categoryDTOs);
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {

	}

}
