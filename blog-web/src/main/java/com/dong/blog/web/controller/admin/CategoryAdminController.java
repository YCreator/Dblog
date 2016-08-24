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

import com.dong.blog.application.CategoryApplication;
import com.dong.blog.application.dto.BlogTypeDTO;
import com.dong.blog.application.dto.CategoryDTO;
import com.dong.blog.application.dto.PageBean;
import com.dong.blog.core.domain.BlogType;
import com.dong.blog.web.util.ResponseUtil;
import com.google.gson.Gson;

@Controller
@RequestMapping("/admin/category")
public class CategoryAdminController {

	@Inject
	private CategoryApplication categoryApplication;

	@RequestMapping("/list")
	public String list(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "rows", required = false) String rows,
			HttpServletResponse response) throws Exception {
		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<CategoryDTO> blogTypeList = categoryApplication.getPage(
				pageBean.getPage(), pageBean.getPageSize()).getData();
		if (blogTypeList != null) {
			for (CategoryDTO dto : blogTypeList) {
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
		JSONObject result = new JSONObject();
		JSONArray jsonArray = JSONArray.fromObject(blogTypeList);
		result.put("rows", jsonArray);
		ResponseUtil.write(response, result);
		return null;
	}

	@RequestMapping("/save")
	public String save(CategoryDTO dto, HttpServletResponse response)
			throws Exception {
		for (String json : dto.getBlogTypeJsons()) {
			System.out.println(json);
			//BlogTypeDTO d = new Gson().fromJson(json, BlogTypeDTO.class);
			//dto.getList().add(d);
		}
		/*boolean isUpdateSuccess = false;
		if (dto.getId() == null) {
			dto = categoryApplication.save(dto);
		} else {
			isUpdateSuccess = categoryApplication.update(dto);
		}
		JSONObject result = new JSONObject();
		if (dto.getId() != null || isUpdateSuccess) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		ResponseUtil.write(response, result);*/
		return null;
	}

}
