package com.dong.blog.facade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.CategoryApplication;
import com.dong.blog.core.domain.Category;
import com.dong.blog.facade.CategoryFacade;
import com.dong.blog.facade.dto.CategoryDTO;
import com.dong.blog.facade.impl.assembler.CategoryMapper;

@Named
@Transactional(rollbackFor = Exception.class)
public class CategoryFacadeImpl implements CategoryFacade {
	
	@Inject
	CategoryApplication application;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CategoryDTO get(Long pk) {
		CategoryDTO categoryDTO = null;
		try {
			categoryDTO = new CategoryMapper().transformBeanData(application.load(pk));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categoryDTO;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CategoryDTO> findAll() {
		List<CategoryDTO> list = null;
		try {
			list = (List<CategoryDTO>) new CategoryMapper().transformBeanDatas(application.findAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public CategoryDTO save(CategoryDTO t) {
		try {
			Category category = new CategoryMapper().transformEntityData(t);
			category = application.save(category);
			t.setId(category.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	public boolean update(CategoryDTO t) {
		boolean isSuccess = false;
		Category category = application.get(t.getId());
		try {
			new CategoryMapper().transformEntityData(category, t);
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
		for (Long pk : pks) {
			remove(pk);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CategoryDTO> getPage(int currentPage, int pageSize) {
		Page<Category> pages = application.getPage(currentPage, pageSize);
		List<CategoryDTO> list = new ArrayList<CategoryDTO>();
		try {
			list = (List<CategoryDTO>) new CategoryMapper().transformBeanDatas(pages.getData());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Page<CategoryDTO>(pages.getStart(), pages.getResultCount(), pageSize, list);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Long getTotal() {
		// TODO Auto-generated method stub
		return application.getTotal();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CategoryDTO> findAllBySort() {
		List<CategoryDTO> list = null;
		try {
			list = (List<CategoryDTO>) new CategoryMapper().transformBeanDatas(application.findAllBySort());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getCateNameById(Long id) {
		// TODO Auto-generated method stub
		return application.getCateNameById(id);
	}

}
