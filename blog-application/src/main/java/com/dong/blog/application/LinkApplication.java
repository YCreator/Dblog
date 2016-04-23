package com.dong.blog.application;

import org.dayatang.utils.Page;

import com.dong.blog.application.dto.LinkDTO;

public interface LinkApplication extends BaseApplication<LinkDTO, Long> {
	
	Page<LinkDTO> getPage(int currentPage, int pageSize);
	
	Long getTotal();

}
