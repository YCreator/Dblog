package com.dong.blog.facade;

import java.util.Map;

import org.dayatang.utils.Page;

import com.dong.blog.facade.dto.CommentDTO;

public interface CommentFacade extends BaseFacade<CommentDTO, Long> {
	
	/**
	 * 有条件的查询记录总数
	 * @param params
	 * @return
	 */
	Long getTotal(Map<String, Object> params);
	
	Page<CommentDTO> getPage(CommentDTO dto, int currentPage, int pageSize);
	
}
