package com.dong.blog.web.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;

import com.dong.blog.core.domain.MenuType;
import com.dong.blog.facade.MenuFacade;
import com.dong.blog.facade.dto.MenuDTO;
import com.dong.blog.facade.dto.PageBean;
import com.dong.blog.util.StringUtil;
import com.dong.blog.web.util.RedisUtil;

/**
 * 菜单管理层
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/menu")
public class MenuAdminController {

	@Inject
	private MenuFacade menuFacade;

	@ResponseBody
	@RequestMapping("/list")
	public JSONObject list(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "rows", required = false) String rows)
			throws Exception {
		PageBean pageBean = new PageBean(Integer.parseInt(page),
				Integer.parseInt(rows));
		List<MenuDTO> menus = menuFacade.pageQuery(pageBean.getPage(),
				pageBean.getPageSize()).getData();
		JSONObject result = new JSONObject();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray = JSONArray.fromObject(menus, jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", menuFacade.getCount());
		return result;
	}

	@ResponseBody
	@RequestMapping("/save")
	public Map<String, Object> save(MenuDTO menuDTO) throws Exception {
		boolean isSuccess = false;
		Map<String, Object> result = new HashMap<String, Object>();
		menuDTO.setIsParent(MenuType.isParent(menuDTO.getMenuType()));
		if (menuDTO.getId() == null) {
			menuDTO.setCreateTime(new Date());
			menuDTO = menuFacade.save(menuDTO);
			isSuccess = menuDTO.getId() != null;
		} else {
			isSuccess = menuFacade.update(menuDTO);
		}
		Logger.getLogger(this.getClass()).debug(menuDTO.toString());
		result.put("success", isSuccess);
		return result;
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> delete(@RequestParam(value = "ids") String ids)
			throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		String[] idsStr = ids.split(",");
		for (String id : idsStr) {
			MenuDTO dto = new MenuDTO(Long.parseLong(id));
			if (menuFacade.hasMenuRelationship(dto)) {
				result.put("success", false);
				result.put("message", "删除失败，部分菜单存在关系，请解除关系后再删除");
				return result;
			}
		}
		for (String id : idsStr) {
			menuFacade.remove(Long.parseLong(id));
		}
		result.put("success", true);
		return result;
	}

	@ResponseBody
	@RequestMapping("/getTree")
	public JSONObject getTree() throws Exception {
		List<MenuDTO> list = menuFacade.getMenuTree();
		JSONObject result = new JSONObject();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", 0);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getMenuTree")
	public JSONObject getCacheTree() throws Exception {
		Jedis jedis = RedisUtil.getJedis();
		String menuTree = jedis.get("menuTree");
		JSONObject object;
		if (!StringUtil.isEmpty(menuTree)) {
			Logger.getLogger(this.getClass()).debug(menuTree);
			object = JSONObject.fromObject(menuTree);	
		} else {
			Logger.getLogger(this.getClass()).debug("menuTree == null");
			object = getTree();
			jedis.set("menuTree", object.toString());
		}
		RedisUtil.returnResource(jedis);
		return object;
	}

	@ResponseBody
	@RequestMapping("/addRelationship")
	public Map<String, Object> addRelationship(
			@RequestParam(value = "pid") String pid,
			@RequestParam(value = "cid") String cid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		MenuDTO pDTO = new MenuDTO(Long.parseLong(pid));
		MenuDTO cDTO = new MenuDTO(Long.parseLong(cid));
		boolean isSuccess = menuFacade.addMenuRelationShip(pDTO, cDTO);
		result.put("success", isSuccess);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getAllChildNoParent")
	public Map<String, Object> getAllChildNoParent() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<MenuDTO> dtos = menuFacade.getAllChildNoParent();
		result.put("success", dtos != null);
		result.put("data", dtos);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/getParent")
	public Map<String, Object> getParent() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		List<MenuDTO> dtos = menuFacade.getParentMenus();
		result.put("success", dtos != null);
		result.put("data", dtos);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/parentRelieve")
	public Map<String, Object> relieveParent(MenuDTO dto) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		Logger.getLogger(MenuAdminController.class).debug(dto.toString());
		boolean isSuccess = menuFacade.removeMenuRelationByParent(dto);
		result.put("success", isSuccess);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/childRelieve")
	public Map<String, Object> relieveChild(
			@RequestParam(value = "pid") String pid, 
			@RequestParam(value = "cid") String cid) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		MenuDTO parent = new MenuDTO(Long.parseLong(pid));
		MenuDTO child = new MenuDTO(Long.parseLong(cid));
		boolean isSuccess = menuFacade.removeMenuRelation(parent, child);
		result.put("success", isSuccess);
		return result;
	}

}
