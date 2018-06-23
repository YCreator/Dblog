package com.dong.blog.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dong.blog.facade.CommentFacade;
import com.dong.blog.facade.dto.CommentDTO;

/**
 * 评论Controller层
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

	@Inject
	private CommentFacade commentFacade;

	/**
	 * 添加或者修改评论
	 * 
	 * @param comment
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Map<String, Object> save(CommentDTO comment,
			@RequestParam("imageCode") String imageCode,
			HttpServletRequest request, HttpSession session) throws Exception {

		String sRand = (String) session.getAttribute("sRand"); // 获取系统生成的验证码
		Map<String, Object> result = new HashMap<String, Object>();
		if (!imageCode.equals(sRand)) {
			result.put("success", false);
			result.put("errorInfo", "验证码填写错误！");
		} else {
			String userIp = request.getRemoteAddr(); // 获取用户IP
			comment.setUserIp(userIp);
			if (comment.getId() == null) {
				comment.setCommentDate(new Date());
				comment.setState(0);
				commentFacade.save(comment);
			}
			result.put("success", comment.getId() != null);
		}
		return result;
	}

}
