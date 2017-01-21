package com.dong.blog.application.impl;

import java.util.List;

import javax.inject.Named;

import org.dayatang.utils.Page;

import com.dong.blog.application.CategoryApplication;
import com.dong.blog.core.domain.Category;

@Named
public class CategoryApplicationImpl extends BaseApplicationImpl implements
		CategoryApplication {

	public Category get(Long pk) {
		return Category.get(Category.class, pk);
	}

	public List<Category> findAll() {
		return Category.findAll(Category.class);
	}

	public Category save(Category t) {
		t.save();
		return t;
	}

	public boolean update(Category t) {
		return true;
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

	@SuppressWarnings("unchecked")
	public Page<Category> getPage(int currentPage, int pageSize) {
		return this.getQueryChannelService()
				.createJpqlQuery("select _category from Category _category order by _category.sort asc")
				.setPage(currentPage, pageSize)
				.pagedList();
	}

	public Long getTotal() {
		return Category.getTotal();
	}

	public List<Category> findAllBySort() {
		List<Category> list = Category.findAllBySort();
		this.getLog().debug("cateSize============>"+ list.size());
		for (Category cate : list) {
			this.getLog().debug(cate.toString());
		}
		return list;
	}

	public String getCateNameById(Long id) {
		String jpql = "select c.categoryName from Category c where c.id=?";
		String name = (String) this.getQueryChannelService().createJpqlQuery(jpql)
				.setParameters(new Object[]{ id }).singleResult();
		return name;
	}

	public Category load(Long id) {
		// TODO Auto-generated method stub
		return Category.load(Category.class, id);
	}

}
