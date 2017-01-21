package com.dong.blog.facade;

import org.dayatang.utils.Page;

import com.dong.blog.facade.dto.BlogTypeDTO;

public interface BlogTypeFacade extends BaseFacade<BlogTypeDTO, Long> {
	
	Page<BlogTypeDTO> getPage(BlogTypeDTO dto, int currentPage, int pageSize);
	
	Long getTotal();

}
