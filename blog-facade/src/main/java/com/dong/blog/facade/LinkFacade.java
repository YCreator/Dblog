package com.dong.blog.facade;

import org.dayatang.utils.Page;

import com.dong.blog.facade.dto.LinkDTO;

public interface LinkFacade extends BaseFacade<LinkDTO, Long> {
	
	Page<LinkDTO> getPage(int currentPage, int pageSize);
	
	Long getTotal();

}
