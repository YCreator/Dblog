package com.dong.blog.web.controller.admin;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dong.blog.application.LinkApplication;
import com.dong.blog.application.dto.LinkDTO;
import com.dong.blog.application.dto.PageBean;
import com.dong.blog.web.util.ResponseUtil;

/**
 * 友情链接Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/link")
public class LinkAdminController {
	
	@Inject
	private LinkApplication linkApplication;
	
	/**
	 * 分页查询友情链接信息
	 * @param page
	 * @param rows
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		List<LinkDTO> linkList = linkApplication.getPage(pageBean.getPage(), pageBean.getPageSize()).getData();
		Long total=linkApplication.getTotal().longValue();
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(linkList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 添加或者修改友情链接信息
	 * @param link
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(LinkDTO link,HttpServletResponse response)throws Exception{
		boolean isUpdateSuccess=false; // 操作的记录条数
		if(link.getId()==null){
			link = linkApplication.save(link);
		}else{
			isUpdateSuccess=linkApplication.update(link);
		}
		JSONObject result=new JSONObject();
		if(link.getId() != null || isUpdateSuccess){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 删除友情链接信息
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="ids")String ids,HttpServletResponse response)throws Exception{
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			linkApplication.remove(Long.parseLong(idsStr[i]));
		}
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
}
