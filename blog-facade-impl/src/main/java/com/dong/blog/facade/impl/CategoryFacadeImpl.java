package com.dong.blog.facade.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.CategoryApplication;
import com.dong.blog.core.domain.BlogType;
import com.dong.blog.core.domain.Category;
import com.dong.blog.facade.CategoryFacade;
import com.dong.blog.facade.dto.BlogTypeDTO;
import com.dong.blog.facade.dto.CategoryDTO;
import com.dong.blog.facade.impl.assembler.CategoryAssembler;

@Named
@Transactional(rollbackFor = Exception.class)
public class CategoryFacadeImpl implements CategoryFacade {
	
	@Inject
	CategoryApplication application;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CategoryDTO get(Long pk) {
		Category category = application.load(pk);
		return new CategoryAssembler().toDto(category);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = application.findAll();
		return new CategoryAssembler().toDtos(list);
	}

	public void save(CategoryDTO t) {
		Category category = new CategoryAssembler().toEntity(t);
		category = application.save(category);
		t.setId(category.getId());
	}

	public boolean update(CategoryDTO t) {
		boolean isSuccess = false;
		Category category = application.get(t.getId());
		try {
			category.setCategoryName(t.getCategoryName());
			category.setSort(t.getSort());
			Set<BlogType> set = new HashSet<BlogType>();
			for (BlogTypeDTO dto : t.getList()) {
				set.add(BlogType.get(BlogType.class, dto.getId()));
			}
			category.setBlogTypes(set);
			isSuccess = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuccess;
	}

	public void remove(Long pk) {
		application.remove(pk);
	}

	public void removes(Long[] pks) {
		for (Long pk : pks) remove(pk);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CategoryDTO> getPage(int currentPage, int pageSize) {
		Page<Category> pages = application.getPage(currentPage, pageSize);
		List<CategoryDTO> list = new CategoryAssembler().toDtos(pages.getData());
		return new Page<CategoryDTO>(pages.getStart(), pages.getResultCount(), pageSize, list);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Long getTotal() {
		// TODO Auto-generated method stub
		return application.getTotal();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CategoryDTO> findAllBySort() {
		List<Category> list = application.findAllBySort();
		return new CategoryAssembler().toDtos(list);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getCateNameById(Long id) {
		// TODO Auto-generated method stub
		return application.getCateNameById(id);
	}

}
