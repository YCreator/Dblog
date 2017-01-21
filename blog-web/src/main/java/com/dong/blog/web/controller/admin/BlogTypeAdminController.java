package com.dong.blog.web.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dong.blog.facade.BlogFacade;
import com.dong.blog.facade.BlogTypeFacade;
import com.dong.blog.facade.dto.BlogDTO;
import com.dong.blog.facade.dto.BlogTypeDTO;
import com.dong.blog.facade.dto.PageBean;

/**
 * 管理员博客类别Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/blogType")
public class BlogTypeAdminController {

	@Inject
	private BlogTypeFacade blogTypeFacade;
	
	@Inject
	private BlogFacade blogFacade;
	
	/**
	 * 分页查询博客类别信息
	 * @param page
	 * @param rows
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
	public String list (
			@RequestParam(value="page",required=false)String page,
			@RequestParam(value="rows",required=false)String rows) throws Exception{
		
		PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<BlogTypeDTO> blogTypeList = blogTypeFacade.getPage(null, pageBean.getPage(), pageBean.getPageSize()).getData();
		Long total = blogTypeFacade.getTotal();
		JSONObject result = new JSONObject();
		JSONArray jsonArray  = JSONArray.fromObject(blogTypeList);
		result.put("rows", jsonArray);
		result.put("total", total);
		return result.toString();
	}
	
	/**
	 * 添加或者修改博客类别信息
	 * @param blogType
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public Map<String, Object> save(BlogTypeDTO blogType) throws Exception{
		
		boolean isUpdateSuccess = false;
		if(blogType.getId() == null){
			blogType = blogTypeFacade.save(blogType);
		}else{
			isUpdateSuccess = blogTypeFacade.update(blogType);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", blogType.getId() != null || isUpdateSuccess);
		return result;
	}
	
	/**
	 * 删除博客类别信息
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> delete(@RequestParam(value="ids")String ids)throws Exception {
		
		String []idsStr=ids.split(",");
		Map<String, Object> result=new HashMap<String, Object>();
		for(int i = 0; i < idsStr.length; i++){
			BlogTypeDTO blogTypeDTO = blogTypeFacade.get(Long.valueOf(idsStr[i]));
			List<BlogDTO> dto = blogFacade.getBlogsByProperty("blogType", blogTypeDTO);
			if(dto.size() > 0){
				result.put("exist", "博客类别下有博客，不能删除！");
				result.put("success", false);
			}else{
				blogTypeFacade.remove(Long.valueOf(idsStr[i]));	
				result.put("success", true);
			}
		}	
		return result;
	}
}
