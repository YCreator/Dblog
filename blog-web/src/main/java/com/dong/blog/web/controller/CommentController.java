package com.dong.blog.web.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dong.blog.application.BlogApplication;
import com.dong.blog.application.CommentApplication;
import com.dong.blog.application.dto.BlogDTO;
import com.dong.blog.application.dto.CommentDTO;
import com.dong.blog.web.util.ResponseUtil;

/**
 * 评论Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Inject
	private CommentApplication commentApplication;

	@Inject
	private BlogApplication blogApplication;
	
	/**
	 * 添加或者修改评论
	 * @param comment
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(CommentDTO comment,@RequestParam("imageCode") String imageCode,HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception{
		String sRand=(String) session.getAttribute("sRand"); // 获取系统生成的验证码
		JSONObject result=new JSONObject();
		if(!imageCode.equals(sRand)){
			result.put("success", false);
			result.put("errorInfo", "验证码填写错误！");
		}else{
			String userIp=request.getRemoteAddr(); // 获取用户IP
			comment.setUserIp(userIp);
			if(comment.getId()==null){
				comment = commentApplication.save(comment);
				// 该博客的回复次数加1
				BlogDTO blog=blogApplication.get(comment.getBlogId());
				blog.setReplyHit(blog.getReplyHit()+1);
				blogApplication.update(blog);
			}
			if(comment.getId() != null){
				result.put("success", true);
			}else{
				result.put("success", false);
			}
		}
		ResponseUtil.write(response, result);
		return null;
	}

}
