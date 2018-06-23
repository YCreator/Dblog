package com.dong.blog.web.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dong.blog.facade.CategoryFacade;
import com.dong.blog.facade.dto.BlogTypeDTO;
import com.dong.blog.facade.dto.CategoryDTO;
import com.dong.blog.facade.dto.PageBean;
import com.dong.blog.util.StringUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/admin/category")
public class CategoryAdminController {

	@Inject
	private CategoryFacade categoryFacade;

	/**
	 * 分页查询分类信息
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Map<String, Object> list(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "rows", required = false) String rows) throws Exception {
		
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<CategoryDTO> categoryList = categoryFacade.getPage(pageBean.getPage(), pageBean.getPageSize()).getData();
		if (categoryList != null) {
			for (CategoryDTO dto : categoryList) {
				StringBuilder builder = new StringBuilder();
				for (BlogTypeDTO type : dto.getList()) {
					builder.append(type.getTypeName()).append(",");
				}
				if (builder.length() > 1) {
					builder.deleteCharAt(builder.length() - 1);
				}
				dto.setIds(builder.toString());
			}
		}
		Long total = categoryFacade.getTotal();
		map.clear();
		map.put("rows", categoryList);
		map.put("total", total);
		return map;
	}

	/**
	 * 添加或修改分类信息
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Map<String, Object> save(CategoryDTO dto) throws Exception {
		
		for (String json : dto.getBlogTypeJsons()) {
			if (StringUtil.isNotEmpty(json)) {
				BlogTypeDTO d = new Gson().fromJson(json, BlogTypeDTO.class);
				dto.getList().add(d);
			}
		}
		boolean isUpdateSuccess = false;
		if (dto.getId() == null) {
			categoryFacade.save(dto);
		} else {
			isUpdateSuccess = categoryFacade.update(dto);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", dto.getId() != null || isUpdateSuccess);
		return result;
	}
	
	/**
	 * 删除分类信息
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> delete(@RequestParam(value="ids")String ids) throws Exception {
		
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++) {
			categoryFacade.remove(Long.parseLong(idsStr[i]));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return result;
	}

}
