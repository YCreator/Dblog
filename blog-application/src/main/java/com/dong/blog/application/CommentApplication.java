package com.dong.blog.application;

import java.math.BigInteger;
import java.util.Map;

import org.dayatang.utils.Page;

import com.dong.blog.application.dto.CommentDTO;

public interface CommentApplication extends BaseApplication<CommentDTO, Long> {
	
	/**
	 * 有条件的查询记录总数
	 * @param params
	 * @return
	 */
	BigInteger getTotal(Map<String, Object> params);
	
	Page<CommentDTO> getPage(CommentDTO dto, int currentPage, int pageSize);

}
