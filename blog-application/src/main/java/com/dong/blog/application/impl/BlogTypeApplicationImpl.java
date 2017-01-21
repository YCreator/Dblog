package com.dong.blog.application.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.dayatang.utils.Page;

import com.dong.blog.application.BlogTypeApplication;
import com.dong.blog.core.domain.BlogType;

@Named
public class BlogTypeApplicationImpl extends BaseApplicationImpl implements BlogTypeApplication {
	
	public BlogType get(Long pk) {
		BlogType blogType = BlogType.get(BlogType.class, pk);
		return blogType;
	}
	
	public BlogType load(Long id) {
		BlogType blogType = BlogType.load(BlogType.class, id);
		return blogType;
	}

	public List<BlogType> findAll() {
		List<BlogType> list = BlogType.findAll(BlogType.class);
		return list;
	}

	public BlogType save(BlogType t) {
		t.save();
		return t;
	}

	public boolean update(BlogType t) {
		return false;
	}

	public void remove(Long pk) {
		BlogType bt = BlogType.load(BlogType.class, pk);
		bt.remove();
	}

	public void removes(Long[] pks) {
		for(int i = 0; i < pks.length; i++) {
			BlogType bt = BlogType.load(BlogType.class, pks[i]);
			bt.remove();
		}
	}

	@SuppressWarnings("unchecked")
	public Page<BlogType> getPage(Map<String, Object> params, int currentPage,
			int pageSize) {
		return this.getQueryChannelService()
				.createJpqlQuery("select _blogType from BlogType _blogType")
				.setPage(currentPage, pageSize).pagedList();
	}

	public Long getTotal() {
		return BlogType.getTotal();
	}
	
}
