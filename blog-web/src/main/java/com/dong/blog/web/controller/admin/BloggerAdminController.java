package com.dong.blog.web.controller.admin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dong.blog.application.BloggerApplication;
import com.dong.blog.application.dto.BloggerDTO;
import com.dong.blog.web.util.CryptographyUtil;
import com.dong.blog.util.DateUtil;

/**
 * 管理员博主Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/blogger")
public class BloggerAdminController {

	@Inject
	private BloggerApplication bloggerApplication;
	
	/**
	 * 修改博主信息
	 * @param file1
	 * @param blogger
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	public String save(
			@RequestParam("imageFile") MultipartFile imageFile,
			BloggerDTO blogger,
			HttpServletRequest request) throws Exception {
		
		if(!imageFile.isEmpty()) {
			String filePath = request.getServletContext().getRealPath("/");
			String imageName = DateUtil.getCurrentDateStr() + "." + imageFile.getOriginalFilename().split("\\.")[1];
			imageFile.transferTo(new File(filePath + "static/userImages/" + imageName));
			blogger.setImageName(imageName);
		}
		boolean isUpdateSuccess =bloggerApplication.update(blogger);
		return isUpdateSuccess 
				? "<script language='javascript'>alert('修改成功！');</script>" 
						: "<script language='javascript'>alert('修改失败！');</script>";
	}
	
	/**
	 * 查询博主信息
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/find")
	public JSONObject find() throws Exception {
		BloggerDTO blogger = bloggerApplication.getBlogger();
		return JSONObject.fromObject(blogger);
	}
	
	/**
	 * 修改博主密码
	 * @param newPassword
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/modifyPassword")
	public Map<String, Object> modifyPassword(String newPassword) throws Exception {
		
		BloggerDTO blogger = new BloggerDTO();
		blogger.setPassword(CryptographyUtil.md5(newPassword, "java1234"));
		boolean isUpdateSuccess = bloggerApplication.update(blogger);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", isUpdateSuccess);
		return result;
	}
	
	/**
	 * 注销
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/logout")
	public String  logout()throws Exception{
		SecurityUtils.getSubject().logout();
		return "redirect:/login.jsp";
	}
}
