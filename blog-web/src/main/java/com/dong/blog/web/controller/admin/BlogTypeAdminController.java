package com.dong.blog.web.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dong.blog.application.BlogApplication;
import com.dong.blog.application.BlogTypeApplication;
import com.dong.blog.application.dto.BlogDTO;
import com.dong.blog.application.dto.BlogTypeDTO;
import com.dong.blog.application.dto.PageBean;
import com.dong.blog.web.util.ResponseUtil;

/**
 * 管理员博客类别Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/blogType")
public class BlogTypeAdminController {

	@Inject
	private BlogTypeApplication blogTypeApplication;
	
	@Inject
	private BlogApplication blogApplication;
	
	/**
	 * 分页查询博客类别信息
	 * @param page
	 * @param rows
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<BlogTypeDTO> blogTypeList=blogTypeApplication.getPage(null, pageBean.getPage(), pageBean.getPageSize()).getData();
		Long total=blogTypeApplication.getTotal().longValue();
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(blogTypeList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 添加或者修改博客类别信息
	 * @param blogType
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(BlogTypeDTO blogType,HttpServletResponse response)throws Exception{
		boolean isUpdateSuccess = false;
		if(blogType.getId() == null){
			blogType = blogTypeApplication.save(blogType);
		}else{
			isUpdateSuccess = blogTypeApplication.update(blogType);
		}
		JSONObject result=new JSONObject();
		if(blogType.getId() != null || isUpdateSuccess){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 删除博客类别信息
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="ids")String ids,HttpServletResponse response)throws Exception{
		String []idsStr=ids.split(",");
		JSONObject result=new JSONObject();
		for(int i=0;i<idsStr.length;i++){
			List<BlogDTO> dto = blogApplication.getBlogByTypeId(Long.valueOf(idsStr[i]));
			if(dto.size() > 0){
				result.put("exist", "博客类别下有博客，不能删除！");
			}else{
				blogTypeApplication.remove(Long.valueOf(idsStr[i]));				
			}
		}
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
}
