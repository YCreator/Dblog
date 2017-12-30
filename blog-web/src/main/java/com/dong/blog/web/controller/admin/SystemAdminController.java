package com.dong.blog.web.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.dayatang.cache.memcached.MemcachedBasedCache;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import redis.clients.jedis.Jedis;

import com.dong.blog.facade.BlogTypeFacade;
import com.dong.blog.facade.BloggerFacade;
import com.dong.blog.facade.CategoryFacade;
import com.dong.blog.facade.LinkFacade;
import com.dong.blog.facade.MenuFacade;
import com.dong.blog.facade.dto.BlogTypeDTO;
import com.dong.blog.facade.dto.BloggerDTO;
import com.dong.blog.facade.dto.CategoryDTO;
import com.dong.blog.facade.dto.LinkDTO;
import com.dong.blog.facade.dto.MenuDTO;
import com.dong.blog.infra.redis.RedisUtil;

/**
 * 管理员系统Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/system")
public class SystemAdminController {

	@Inject
	private BloggerFacade bloggerFacade;
	@Inject
	private BlogTypeFacade blogTypeFacade;
	@Inject
	private LinkFacade linkFacade;
	@Inject
	private CategoryFacade categoryFacade;
	@Inject
	private MenuFacade menuFacade;
	@Inject
	private MemcachedBasedCache memcachedBasedCache;
	
	/**
	 * 刷新系统缓存
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/refreshSystem")
	public Map<String, Object> refreshSystem(
			@RequestParam(value = "id") Integer id,
			@RequestParam(value = "key") String key,
			@RequestParam(value = "method") String method,
			HttpServletRequest request) throws Exception {
		ServletContext Facade=RequestContextUtils.findWebApplicationContext(request).getServletContext();
		switch(id) {
			case 1 :
				BloggerDTO blogger = bloggerFacade.getBlogger(); // 查询博主信息
				blogger.setPassword(null);
				Facade.setAttribute("blogger", blogger);
				break;
			case 2 :
				List<BlogTypeDTO> blogTypeCountList=blogTypeFacade.findAll(); // 查询博客类别以及博客的数量
				Facade.setAttribute("blogTypeCountList", blogTypeCountList);
				break;
			case 3 :
				List<CategoryDTO> categorys = categoryFacade.findAllBySort();	//获取主分类
				Facade.setAttribute("categorys", categorys);
				break;
			case 4 :
				List<LinkDTO> linkList=linkFacade.findAll(); // 获取所有友情链接
				Facade.setAttribute("linkList", linkList);
				break;
			case 5 :
				List<MenuDTO> list = menuFacade.getMenuTree();
				JSONObject result = new JSONObject();
				JsonConfig jsonConfig = new JsonConfig();
				jsonConfig.registerJsonValueProcessor(java.util.Date.class,
						new DateJsonValueProcessor("yyyy-MM-dd"));
				JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
				result.put("rows", jsonArray);
				result.put("total", 0);
				memcachedBasedCache.put("menuTree", result);
				/*Jedis jedis = RedisUtil.getJedis();
				jedis.set("menuTree", result.toString());
				RedisUtil.returnResource(jedis);*/
				break;
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return result;
	}
	
}
