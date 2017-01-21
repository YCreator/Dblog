package com.dong.blog.application.impl;

import java.util.List;

import javax.inject.Named;

import com.dong.blog.application.BloggerApplication;
import com.dong.blog.core.domain.Blogger;

@Named
public class BloggerApplicationImpl extends BaseApplicationImpl implements BloggerApplication {
		
	public Blogger get(Long pk) {
		return Blogger.get(Blogger.class, pk);
	}

	public List<Blogger> findAll() {
		return Blogger.findAll(Blogger.class);
	}

	public Blogger save(Blogger t) {
		t.save();
		return t;
	}

	public boolean update(Blogger t) {
		return false;
	}

	public void remove(Long pk) {
		get(pk).remove();
	}

	public void removes(Long[] pks) {
		// TODO Auto-generated method stub

	}

	public Blogger findByUsername(String username) {
		return Blogger.findByUsername(username);
	}

	public Blogger load(Long pk) {
		return Blogger.load(Blogger.class, pk);
	}

}
