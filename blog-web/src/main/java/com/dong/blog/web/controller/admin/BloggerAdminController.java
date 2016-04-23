package com.dong.blog.web.controller.admin;

import java.io.File;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dong.blog.application.BloggerApplication;
import com.dong.blog.application.dto.BloggerDTO;
import com.dong.blog.web.util.CryptographyUtil;
import com.dong.blog.util.DateUtil;
import com.dong.blog.web.util.ResponseUtil;

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
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(@RequestParam("imageFile") MultipartFile imageFile,BloggerDTO blogger,HttpServletRequest request,HttpServletResponse response)throws Exception{
		if(!imageFile.isEmpty()){
			String filePath=request.getServletContext().getRealPath("/");
			String imageName=DateUtil.getCurrentDateStr()+"."+imageFile.getOriginalFilename().split("\\.")[1];
			imageFile.transferTo(new File(filePath+"static/userImages/"+imageName));
			blogger.setImageName(imageName);
		}
		boolean isUpdateSuccess =bloggerApplication.update(blogger);
		StringBuffer result=new StringBuffer();
		if(isUpdateSuccess){
			result.append("<script language='javascript'>alert('修改成功！');</script>");
		}else{
			result.append("<script language='javascript'>alert('修改失败！');</script>");
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 查询博主信息
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/find")
	public String find(HttpServletResponse response)throws Exception{
		BloggerDTO blogger=bloggerApplication.getBlogger();
		JSONObject jsonObject=JSONObject.fromObject(blogger);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	/**
	 * 修改博主密码
	 * @param newPassword
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyPassword")
	public String modifyPassword(String newPassword,HttpServletResponse response)throws Exception{
		BloggerDTO blogger=new BloggerDTO();
		blogger.setPassword(CryptographyUtil.md5(newPassword, "java1234"));
		boolean isUpdateSuccess=bloggerApplication.update(blogger);
		JSONObject result=new JSONObject();
		if(isUpdateSuccess){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
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
