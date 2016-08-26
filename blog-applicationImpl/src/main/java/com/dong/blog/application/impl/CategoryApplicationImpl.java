package com.dong.blog.application.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.CategoryApplication;
import com.dong.blog.application.dto.CategoryDTO;
import com.dong.blog.application.mapper.CategoryMapper;
import com.dong.blog.core.domain.Category;

@Named
@Transactional(rollbackFor = Exception.class)
public class CategoryApplicationImpl extends BaseApplicationImpl implements
		CategoryApplication {

	@Inject
	private CategoryMapper categoryMapper;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CategoryDTO get(Long pk) {
		Category category = Category.load(Category.class, pk);
		CategoryDTO dto;
		try {
			dto = categoryMapper.transformBeanData(category);
		} catch (Exception e) {
			dto = new CategoryDTO();
			e.printStackTrace();
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = Category.findAll(Category.class);
		List<CategoryDTO> dtos;
		try {
			dtos = (List<CategoryDTO>) categoryMapper.transformBeanDatas(list);
		} catch (Exception e) {
			dtos = new ArrayList<CategoryDTO>();
			e.printStackTrace();
		}
		return dtos;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public CategoryDTO save(CategoryDTO t) {
		try {
			Category category = categoryMapper.transformEntityData(t);
			category.save();
			t.setId(category.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public boolean update(CategoryDTO t) {
		Category category = Category.get(Category.class, t.getId());
		boolean isSuccess;
		try {
			categoryMapper.transformEntityData(category, t);
			isSuccess = true;
		} catch (Exception e) {
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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CategoryDTO> getPage(int currentPage, int pageSize) {
		@SuppressWarnings("unchecked")
		Page<Category> pages = this.getQueryChannelService()
				.createJpqlQuery("select _category from Category _category order by _category.sort asc")
				.setPage(currentPage, pageSize).pagedList();
		List<Category> list = pages.getData();
		List<CategoryDTO> dtos;
		try {
			dtos = (List<CategoryDTO>) categoryMapper.transformBeanDatas(list);
		} catch (Exception e) {
			dtos = new ArrayList<CategoryDTO>();
			e.printStackTrace();
		}
		return new Page<CategoryDTO>(pages.getStart(), pages.getResultCount(),
				pageSize, dtos);
	}

	public Long getTotal() {
		return (Long) this.getQueryChannelService().createJpqlQuery("select count(*) from Category _category ").singleResult();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CategoryDTO> findAllBySort() {
		String jpql = "select c from Category c order by c.sort asc";
		@SuppressWarnings("unchecked")
		List<Category> list = this.getQueryChannelService().createJpqlQuery(jpql).list();
		List<CategoryDTO> dtos;
		try {
			dtos = (List<CategoryDTO>) categoryMapper.transformBeanDatas(list);
		} catch (Exception e) {
			dtos = new ArrayList<CategoryDTO>();
			e.printStackTrace();
		}
		
		return dtos;
	}

}
