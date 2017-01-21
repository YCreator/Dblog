package com.dong.blog.application;

import java.util.Map;

import org.dayatang.utils.Page;
import com.dong.blog.core.domain.Comment;

public interface CommentApplication extends BaseApplication<Comment, Long> {
	
	/**
	 * 有条件的查询记录总数
	 * @param params
	 * @return
	 */
	Long getTotal(Map<String, Object> params);
	
	Page<Comment> getPage(Comment dto, int currentPage, int pageSize);
	
	boolean update(Long id, Integer statu);
	
}
