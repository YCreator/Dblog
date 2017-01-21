package com.dong.blog.facade;

import java.util.List;

import org.dayatang.utils.Page;

import com.dong.blog.facade.dto.CategoryDTO;

public interface CategoryFacade extends BaseFacade<CategoryDTO, Long> {
	
	Page<CategoryDTO> getPage(int currentPage, int pageSize);
	
	Long getTotal();
	
	List<CategoryDTO> findAllBySort();
	
	String getCateNameById(Long id);

}
