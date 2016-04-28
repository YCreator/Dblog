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

import com.dong.blog.application.CommentApplication;
import com.dong.blog.application.dto.CommentDTO;
import com.dong.blog.application.dto.PageBean;
import com.dong.blog.web.util.ResponseUtil;

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
	
	/**
	 * 分页查询评论信息
	 * @param page
	 * @param rows
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,@RequestParam(value="state",required=false)String state,HttpServletResponse response)throws Exception{
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
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 删除评论信息
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="ids")String ids,HttpServletResponse response)throws Exception{
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			commentApplication.remove(Long.parseLong(idsStr[i]));
		}
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 评论审核
	 * @param comment
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/review")
	public String review(@RequestParam(value="ids")String ids,@RequestParam(value="state")Integer state,HttpServletResponse response)throws Exception{
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			CommentDTO comment=new CommentDTO();
			comment.setState(state);
			comment.setId(Long.parseLong(idsStr[i]));
			commentApplication.update(comment);
		}
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
}
