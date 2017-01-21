package com.dong.blog.application.impl;

import java.util.List;

import javax.inject.Named;

import org.dayatang.utils.Page;

import com.dong.blog.application.LinkApplication;
import com.dong.blog.core.domain.Link;

@Named
public class LinkApplicationImpl extends BaseApplicationImpl implements LinkApplication {
	
	public Link get(Long pk) {
		return Link.get(Link.class, pk);
	}

	public List<Link> findAll() {
		return Link.findAll(Link.class);
	}

	public Link save(Link t) {
		t.save();
		return t;
	}

	public boolean update(Link t) {
		return false;
	}

	public void remove(Long pk) {
		Link link = Link.load(Link.class, pk);
		link.remove();
	}

	public void removes(Long[] pks) {
		for (int i = 0; i < pks.length; i ++) {
			Link link = Link.load(Link.class, pks[i]);
			link.remove();
		}
	}

	@SuppressWarnings("unchecked")
	public Page<Link> getPage(int currentPage, int pageSize) {		
		return this.getQueryChannelService()
				.createJpqlQuery("select _link from Link _link").setPage(currentPage, pageSize).pagedList();
	}

	public Long getTotal() {
		return (Long)this.getQueryChannelService().createJpqlQuery("select count(*) from Link _link").singleResult();
	}

	public Link load(Long id) {
		// TODO Auto-generated method stub
		return Link.load(Link.class, id);
	}

}
