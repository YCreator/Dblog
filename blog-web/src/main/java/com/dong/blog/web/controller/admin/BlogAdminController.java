package com.dong.blog.web.controller.admin;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dong.blog.application.BlogApplication;
import com.dong.blog.application.dto.BlogDTO;
import com.dong.blog.application.dto.PageBean;
import com.dong.blog.lucene.BlogIndex;
import com.dong.blog.web.util.ResponseUtil;
import com.dong.blog.util.StringUtil;


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
	
	// 博客索引
	private BlogIndex blogIndex=new BlogIndex();
	
	/**
	 * 添加或者修改博客信息
	 * @param blog
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(BlogDTO blog,HttpServletResponse response)throws Exception{
		System.out.println("save===========================>");
		boolean isUpdateSuccess = false;
		if(blog.getId()==null){
			blog = blogApplication.save(blog);
			blogIndex.addIndex(blog); // 添加博客索引
		}else{
			isUpdateSuccess = blogApplication.update(blog);
			blogIndex.updateIndex(blog); // 更新博客索引
		}
		JSONObject result=new JSONObject();
		if (blog.getId() != null || isUpdateSuccess) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
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
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,BlogDTO s_blog,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("title", StringUtil.formatLike(s_blog.getTitle()));
		List<BlogDTO> blogList=blogApplication.pageQuery(map, pageBean.getPage(), pageBean.getPageSize()).getData();
		Long total=blogApplication.getTotal(map).longValue();
		JSONObject result=new JSONObject();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray=JSONArray.fromObject(blogList,jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 删除博客信息
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="ids")String ids,HttpServletResponse response)throws Exception{
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			blogApplication.remove(Long.valueOf(idsStr[i]));
			blogIndex.deleteIndex(idsStr[i]); // 删除对应博客的索引
		}
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 通过ID查找实体
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findById")
	public String findById(@RequestParam(value="id")String id,HttpServletResponse response)throws Exception{
		System.out.println("find======================>");
		BlogDTO blog=blogApplication.get(Long.valueOf(id));
		JSONObject jsonObject=JSONObject.fromObject(blog);
		ResponseUtil.write(response, jsonObject);
		return null;
	}
	
	
	
}
