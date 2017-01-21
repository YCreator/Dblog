package com.dong.blog.facade.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.BloggerApplication;
import com.dong.blog.core.domain.Blogger;
import com.dong.blog.facade.BloggerFacade;
import com.dong.blog.facade.dto.BloggerDTO;
import com.dong.blog.facade.impl.assembler.BloggerMapper;

@Named
@Transactional(rollbackFor = Exception.class)
public class BloggerFacadeImpl implements BloggerFacade {

	@Inject
	BloggerApplication application;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BloggerDTO get(Long pk) {
		BloggerDTO bloggerDTO = null;
		try {
			bloggerDTO = new BloggerMapper().transformBeanData(application.load(pk));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bloggerDTO;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BloggerDTO> findAll() {
		// TODO Auto-generated method stub
		List<BloggerDTO> list = null;
		try {
			list = (List<BloggerDTO>) new BloggerMapper()
					.transformBeanDatas(application.findAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public BloggerDTO save(BloggerDTO t) {
		try {
			Blogger blogger = new BloggerMapper().transformEntityData(t);
			blogger = application.save(blogger);
			t.setId(blogger.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	public boolean update(BloggerDTO t) {
		boolean isSuccess = false;
		Blogger blogger = application.get(t.getId());
		try {
			new BloggerMapper().transformEntityData(blogger, t);
			isSuccess = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
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
	public BloggerDTO findByUsername(String username) {
		// TODO Auto-generated method stub
		BloggerDTO bloggerDTO = null;
		try {
			bloggerDTO = new BloggerMapper().transformBeanData(application
					.findByUsername(username));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bloggerDTO;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BloggerDTO getBlogger() {
		return this.get(1L);
	}

}
