package com.dong.blog.web.controller.admin;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dong.blog.application.BlogApplication;
import com.dong.blog.application.BlogTypeApplication;
import com.dong.blog.application.dto.BlogDTO;
import com.dong.blog.application.dto.PageBean;
import com.dong.blog.lucene.BlogIndex;
import com.dong.blog.util.StringUtil;
import com.google.common.io.Files;
import com.google.gson.Gson;


/**
 * 管理员博客Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {

	@Inject
	private BlogApplication blogApplication;
	
	@Inject
	private BlogTypeApplication blogTypeApplication;
	
	// 博客索引
	private BlogIndex blogIndex = new BlogIndex();
	
	/**
	 * 添加或者修改博客信息
	 * @param blog
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Map<String, Object> save(BlogDTO blog) throws Exception {
		
		boolean isUpdateSuccess = false;
		
		if (blog.getId()==null) {
			blog = blogApplication.save(blog);
			blogIndex.addIndex(blog); // 添加博客索引
		} else {
			blog.setBlogTypeDTO(blogTypeApplication.get(blog.getBlogTypeDTO().getId()));
			isUpdateSuccess = blogApplication.update(blog);
			blogIndex.updateIndex(blog); // 更新博客索引
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", blog.getId() != null || isUpdateSuccess);
		return result;
		
	}
	
	/**
	 * 分页查询博客信息
	 * @param page
	 * @param rows
	 * @param s_customer
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
	public JSONObject list(
			@RequestParam(value="page",required=false)String page,
			@RequestParam(value="rows",required=false)String rows,
			BlogDTO s_blog) throws Exception{
		
		Map<String,Object> map = new HashMap<String,Object>();
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		map.put("title", StringUtil.formatLike(s_blog.getTitle()));
		List<BlogDTO> blogList=blogApplication.pageQuery(map, pageBean.getPage(), pageBean.getPageSize()).getData();
		Long total=blogApplication.getTotal(map).longValue();
		
		JSONObject result=new JSONObject();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray=JSONArray.fromObject(blogList,jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", total);
		return result;
		
	}
	
	/**
	 * 删除博客信息
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> delete(@RequestParam(value="ids")String ids) throws Exception{
		String []idsStr=ids.split(",");
		
		for(int i=0;i<idsStr.length;i++){
			blogApplication.remove(Long.valueOf(idsStr[i]));
			blogIndex.deleteIndex(idsStr[i]); // 删除对应博客的索引
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return result;
	}
	
	/**
	 * 通过ID查找实体
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findById")
	public String findById(@RequestParam(value="id")String id)throws Exception{
		BlogDTO blog = blogApplication.get(Long.valueOf(id));
		return new Gson().toJson(blog);
	}
	
	/**
	 * 上传博客图片
	 * @param imageFile
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/uploadImg")
	public Map<String, Object> uploadImg(
			@RequestParam(value="imageFile") MultipartFile imageFile,
			HttpServletRequest request) throws Exception{
		
		Map<String, Object> result = new HashMap<String, Object>();
		if (imageFile != null && !imageFile.isEmpty()) {
			
			String imageName = imageFile.getOriginalFilename();
			String imgForm = imageName.substring(imageName.lastIndexOf(".") + 1, imageName.length());
			String path = String.format("/resources/images/%s.%s", System.currentTimeMillis(), imgForm);
			File targetFile = new File(request.getSession().getServletContext().getRealPath(path));
			File sourceFile = new File(String.format("D:/workplace/blog/blog-web/src/main/webapp%s",path));
			imageFile.transferTo(sourceFile);
			Files.copy(sourceFile, targetFile);
			
			result.put("success", true);
			result.put("imgPath", path);
		} else {
			
			result.put("success", false);
		}
		return result;
	}
	
}
