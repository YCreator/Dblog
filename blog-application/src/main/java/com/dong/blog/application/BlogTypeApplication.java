package com.dong.blog.application;

import java.math.BigInteger;

import org.dayatang.utils.Page;

import com.dong.blog.application.dto.BlogTypeDTO;

public interface BlogTypeApplication extends BaseApplication<BlogTypeDTO, Long> {
	
	Page<BlogTypeDTO> getPage(BlogTypeDTO dto, int currentPage, int pageSize);
	
	BigInteger getTotal();

}
