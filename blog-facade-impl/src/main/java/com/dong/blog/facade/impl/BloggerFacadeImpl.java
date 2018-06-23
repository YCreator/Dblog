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
import com.dong.blog.facade.impl.assembler.BloggerAssembler;

@Named
@Transactional(rollbackFor = Exception.class)
public class BloggerFacadeImpl implements BloggerFacade {

	@Inject
	private BloggerApplication application;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BloggerDTO get(Long pk) {
		Blogger blogger = application.load(pk);
		return new BloggerAssembler().toDto(blogger);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BloggerDTO> findAll() {
		// TODO Auto-generated method stub
		List<Blogger> list = application.findAll();
		return new BloggerAssembler().toDtos(list);
	}

	public void save(BloggerDTO t) {
		Blogger blogger = new BloggerAssembler().toEntity(t);
		blogger = application.save(blogger);
		t.setId(blogger.getId());
	}

	public boolean update(BloggerDTO t) {
		boolean isSuccess = false;
		try {
			Blogger blogger = application.get(t.getId());
			blogger.setImageName(t.getImageName());
			blogger.setNickName(t.getNickName());
			blogger.setUsername(t.getUsername());
			blogger.setProFile(t.getProFile());
			blogger.setSign(t.getSign());
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
		for (Long pk : pks) remove(pk);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BloggerDTO findByUsername(String username) {
		Blogger blogger = application.findByUsername(username);
		return new BloggerAssembler().toDto(blogger);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BloggerDTO getBlogger() {
		return this.get(1L);
	}

	public boolean updatePassword(String password) {
		return Blogger.updatePassword(password) > 0;
	}

}
