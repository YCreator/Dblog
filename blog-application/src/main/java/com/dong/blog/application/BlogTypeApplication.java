package com.dong.blog.application;

import java.util.Map;

import org.dayatang.utils.Page;

import com.dong.blog.core.domain.BlogType;

public interface BlogTypeApplication extends BaseApplication<BlogType, Long> {
	
	Page<BlogType> getPage(Map<String, Object> params, int currentPage, int pageSize);
	
	Long getTotal();
	
}
