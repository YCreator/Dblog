package com.dong.blog.application.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.BloggerApplication;
import com.dong.blog.application.dto.BloggerDTO;
import com.dong.blog.application.mapper.BloggerMapper;
import com.dong.blog.core.domain.Blogger;
import com.dong.blog.util.BeanCopierUtil;

@Named
@Transactional(rollbackFor=Exception.class)
public class BloggerApplicationImpl extends BaseApplicationImpl implements BloggerApplication {
	
	@Inject
	private BloggerMapper bloggerMapper;
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BloggerDTO get(Long pk) {
		Blogger blogger = Blogger.get(Blogger.class, pk);
		BloggerDTO bloggerDTO;
		try {
			bloggerDTO = bloggerMapper.transformBeanData(blogger);
		} catch (Exception e) {
			bloggerDTO = new BloggerDTO();
			e.printStackTrace();
		}
		return bloggerDTO;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BloggerDTO> findAll() {
		return null;
	}

	public BloggerDTO save(BloggerDTO t) {
		return null;
	}

	public boolean update(BloggerDTO t) {
		Blogger blogger = Blogger.get(Blogger.class, t.getId());
		boolean isSuccess;
		try {
			bloggerMapper.transformEntityData(blogger, t);
			isSuccess = true;
		} catch(Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}

	public void remove(Long pk) {
		// TODO Auto-generated method stub

	}

	public void removes(Long[] pks) {
		// TODO Auto-generated method stub

	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BloggerDTO findByUsername(String username) {
		String jpql = "select _blogger from Blogger _blogger where _blogger.username= '" + username + "'";
		Blogger blogger = (Blogger) this.getQueryChannelService()
				.createJpqlQuery(jpql).singleResult();
		BloggerDTO bloggerDTO = new BloggerDTO();
		try {
			BeanCopierUtil.copyProperties(blogger, bloggerDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bloggerDTO;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BloggerDTO getBlogger() {
		return this.get(1L);
	}

}
