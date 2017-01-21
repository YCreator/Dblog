package com.dong.blog.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dong.blog.facade.BloggerFacade;
import com.dong.blog.facade.dto.BloggerDTO;
import com.dong.blog.web.util.CryptographyUtil;

/**
 * 博主Controller层
 * 
 * @author java1234_小锋
 *
 */
@Controller
@RequestMapping("/blogger")
public class BloggerController {

	@Inject
	private BloggerFacade bloggerFacade;

	/**
	 * 用户登录
	 * 
	 * @param blogger
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(BloggerDTO blogger, HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(
				blogger.getUsername(), CryptographyUtil.md5(
						blogger.getPassword(), "java1234"));
		try {
			subject.login(token); // 登录验证
			return "redirect:/admin/main.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("blogger", blogger);
			request.setAttribute("errorInfo", "用户名或密码错误！");
			return "login";
		}
	}

	/**
	 * 查找博主信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/aboutMe")
	public ModelAndView aboutMe() throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("blogger", bloggerFacade.getBlogger());
		mav.addObject("mainPage", "foreground/blogger/info.jsp");
		mav.addObject("pageTitle", "关于博主_Java开源博客系统");
		mav.setViewName("mainTemp");
		return mav;
	}
}
