package com.dong.blog.application;

import java.util.List;

import org.dayatang.utils.Page;

import com.dong.blog.core.domain.Category;

public interface CategoryApplication extends BaseApplication<Category, Long> {
	
	Page<Category> getPage(int currentPage, int pageSize);
	
	Long getTotal();
	
	List<Category> findAllBySort();
	
	String getCateNameById(Long id);
	
}
