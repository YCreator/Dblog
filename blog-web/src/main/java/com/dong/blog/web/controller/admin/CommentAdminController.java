package com.dong.blog.web.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dong.blog.application.BlogApplication;
import com.dong.blog.application.CommentApplication;
import com.dong.blog.application.dto.BlogDTO;
import com.dong.blog.application.dto.CommentDTO;
import com.dong.blog.application.dto.PageBean;

/**
 * 管理员评论Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/comment")
public class CommentAdminController {

	@Inject
	private CommentApplication commentApplication;
	
	@Inject
	private BlogApplication blogApplication;
	
	/**
	 * 分页查询评论信息
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
	public String list(
			@RequestParam(value="page",required=false)String page,
			@RequestParam(value="rows",required=false)String rows,
			@RequestParam(value="state",required=false)String state) throws Exception{
		
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		CommentDTO dto = new CommentDTO();
		if (state == null) {
			state = "1";
		}
		dto.setState(Integer.parseInt(state));
		List<CommentDTO> commentList = commentApplication.getPage(dto, pageBean.getPage(), pageBean.getPageSize()).getData();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("state", state); // 评论状态
		Long total=commentApplication.getTotal(map);
		
		JSONObject result=new JSONObject();
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray=JSONArray.fromObject(commentList,jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", total);
		return result.toString();
	}
	
	/**
	 * 删除评论信息
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> delete(@RequestParam(value="ids")String ids) throws Exception {
		
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			commentApplication.remove(Long.parseLong(idsStr[i]));
		}
		Map<String, Object> result=new HashMap<String, Object>();
		result.put("success", true);
		return result;
	}
	
	/**
	 * 评论审核
	 * @param comment
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/review")
	public Map<String, Object> review(
			@RequestParam(value="ids")String ids,
			@RequestParam(value="state")Integer state) throws Exception {
		
		String []idsStr=ids.split(",");
		for(int i = 0; i < idsStr.length; i++){
			CommentDTO comment=new CommentDTO();
			comment.setState(state);
			comment.setId(Long.parseLong(idsStr[i]));
			commentApplication.update(comment);
			if (state == 1) {
				// 该博客的回复次数加1
				comment = commentApplication.get(Long.parseLong(idsStr[i]));
				BlogDTO blogDTO = comment.getBlogDTO();
				blogApplication.updateReplyHit(blogDTO.getId(), blogDTO.getReplyHit() + 1);
			}
		}
		Map<String, Object> result=new HashMap<String, Object>();
		result.put("success", true);
		return result;
	}
}
