package com.dong.blog.application.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.CategoryApplication;
import com.dong.blog.application.dto.BlogTypeDTO;
import com.dong.blog.application.dto.CategoryDTO;
import com.dong.blog.application.mapper.CategoryMapper;
import com.dong.blog.core.domain.BlogType;
import com.dong.blog.core.domain.Category;
import com.dong.blog.util.BeanCopierUtil;

@Named
@Transactional(rollbackFor = Exception.class)
public class CategoryApplicationImpl extends BaseApplicationImpl implements
		CategoryApplication {

	@Inject
	private CategoryMapper categoryMapper;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CategoryDTO get(Long pk) {
		Category category = Category.load(Category.class, pk);
		return categoryMapper.transformBeanData(category);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = Category.findAll(Category.class);
		return (List<CategoryDTO>) categoryMapper.transformBeanDatas(list);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public CategoryDTO save(CategoryDTO t) {
		Category category = new Category();
		try {
			BeanCopierUtil.copyProperties(t, category);
			Set<BlogType> set = new HashSet<BlogType>();
			for (BlogTypeDTO dto : t.getList()) {
				set.add(BlogType.get(BlogType.class, dto.getId()));
			}
			category.setBlogTypes(set);
		} catch (Exception e) {
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
				.createJpqlQuery("select _category from Category _category")
				.setPage(currentPage, pageSize).pagedList();
		List<Category> list = pages.getData();
		List<CategoryDTO> dtos = (List<CategoryDTO>) categoryMapper
				.transformBeanDatas(list);
		return new Page<CategoryDTO>(pages.getStart(), pages.getResultCount(),
				pageSize, dtos);
	}

}
