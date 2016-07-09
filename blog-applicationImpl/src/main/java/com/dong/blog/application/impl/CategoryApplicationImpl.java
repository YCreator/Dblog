package com.dong.blog.application.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.CategoryApplication;
import com.dong.blog.application.Mapper;
import com.dong.blog.application.dto.BlogTypeDTO;
import com.dong.blog.application.dto.CategoryDTO;
import com.dong.blog.core.domain.Category;
import com.dong.blog.util.BeanCopierUtil;

@Named
@Transactional(rollbackFor=Exception.class)
public class CategoryApplicationImpl extends BaseApplicationImpl implements CategoryApplication, Mapper<Category, CategoryDTO> {
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CategoryDTO get(Long pk) {
		Category category = Category.load(Category.class, pk);
		return transformBeanData(category);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = Category.findAll(Category.class);
		return transformBeanDatas(list);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public CategoryDTO save(CategoryDTO t) {
		Category category = new Category();
		try {
			BeanCopierUtil.copyProperties(t, category);
		} catch(Exception e) {
			e.printStackTrace();
		}
		category.save();
		t.setId(category.getId());
		return t;
	}

	public boolean update(CategoryDTO t) {
		Category category = Category.get(Category.class, t.getId());
		boolean isSuccess;
		try {
			BeanCopierUtil.copyProperties(t, category);
			isSuccess = true;
		} catch(Exception e) {
			isSuccess = false;
			e.printStackTrace();
		}
		
		return isSuccess;
	}

	public void remove(Long pk) {
		Category category = Category.get(Category.class, pk);
		category.remove();
	}

	public void removes(Long[] pks) {
		for (int i = 0; i < pks.length; i++) {
			remove(pks[i]);
		}
	}

	public CategoryDTO transformBeanData(Category source) {
		CategoryDTO dto = new CategoryDTO();
		try {
			BeanCopierUtil.copyProperties(source, dto);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public List<CategoryDTO> transformBeanDatas(List<Category> sources) {
		List<CategoryDTO> list = new ArrayList<CategoryDTO>();
		for (Category category : sources) {
			list.add(transformBeanData(category));
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CategoryDTO> getPage(int currentPage, int pageSize) {
		@SuppressWarnings("unchecked")
		Page<Category> pages = this.getQueryChannelService()
				.createJpqlQuery("select _category from Category _category").setPage(currentPage, pageSize).pagedList();
		List<Category> list = pages.getData();
		List<CategoryDTO> dtos = this.transformBeanDatas(list);
		return new Page<CategoryDTO>(pages.getStart(), pages.getResultCount(), pageSize, dtos);
	}

}
