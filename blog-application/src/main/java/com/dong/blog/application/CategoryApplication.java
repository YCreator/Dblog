package com.dong.blog.application;

import org.dayatang.utils.Page;

import com.dong.blog.application.dto.CategoryDTO;

public interface CategoryApplication extends BaseApplication<CategoryDTO, Long> {
	
	Page<CategoryDTO> getPage(int currentPage, int pageSize);

}
