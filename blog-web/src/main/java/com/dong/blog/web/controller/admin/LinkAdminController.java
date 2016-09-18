package com.dong.blog.web.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dong.blog.application.LinkApplication;
import com.dong.blog.application.dto.LinkDTO;
import com.dong.blog.application.dto.PageBean;

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
	@ResponseBody
	@RequestMapping("/list")
	public Map<String, Object> list(
			@RequestParam(value="page",required=false)String page,
			@RequestParam(value="rows",required=false)String rows) throws Exception{
		
		Map<String, Object> result = new HashMap<String, Object>();
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		List<LinkDTO> linkList = linkApplication.getPage(pageBean.getPage(), pageBean.getPageSize()).getData();
		Long total=linkApplication.getTotal().longValue();
		result.put("rows", linkList);
		result.put("total", total);
		return result;
	}
	
	/**
	 * 添加或者修改友情链接信息
	 * @param link
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Map<String, Object> save(LinkDTO link)throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		
		boolean isUpdateSuccess = false; // 操作的记录条数
		
		if(link.getId() == null) {
			link = linkApplication.save(link);
		} else {
			isUpdateSuccess = linkApplication.update(link);
		}
		
		result.put("success", link.getId() != null || isUpdateSuccess);
		return result;
	}
	
	/**
	 * 删除友情链接信息
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> delete(@RequestParam(value="ids")String ids) throws Exception{
		String[] idsStr = ids.split(",");
		
		for(int i = 0; i < idsStr.length; i++){
			linkApplication.remove(Long.parseLong(idsStr[i]));
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return result;
	}
}
