package com.dong.blog.facade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.BlogTypeApplication;
import com.dong.blog.core.domain.BlogType;
import com.dong.blog.facade.BlogTypeFacade;
import com.dong.blog.facade.dto.BlogTypeDTO;
import com.dong.blog.facade.impl.assembler.BlogTypeMapper;

@Named
@Transactional(rollbackFor=Exception.class)
public class BlogTypeFacadeImpl implements BlogTypeFacade {
	
	@Inject
	BlogTypeApplication application;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogTypeDTO get(Long pk) {
		BlogTypeDTO blogTypeDTO = null;
		try {
			blogTypeDTO = new BlogTypeMapper().transformBeanData(application.load(pk));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blogTypeDTO;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogTypeDTO> findAll() {
		List<BlogTypeDTO> list = null;
		try {
			list = (List<BlogTypeDTO>) new BlogTypeMapper().transformBeanDatas(application.findAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public BlogTypeDTO save(BlogTypeDTO t) {
		try {
			BlogType blogType = new BlogTypeMapper().transformEntityData(t);
			blogType = application.save(blogType);
			t.setId(blogType.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	public boolean update(BlogTypeDTO t) {
		boolean isSuccess = false;
		BlogType blogType = application.get(t.getId());
		try {
			new BlogTypeMapper().transformEntityData(blogType, t);
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
	public Page<BlogTypeDTO> getPage(BlogTypeDTO dto, int currentPage,
			int pageSize) {
		Page<BlogType> pages = application.getPage(null, currentPage, pageSize);
		List<BlogTypeDTO> list = new ArrayList<BlogTypeDTO>();
		try {
			list = (List<BlogTypeDTO>) new BlogTypeMapper().transformBeanDatas(pages.getData());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  new Page<BlogTypeDTO>(pages.getStart(), pages.getResultCount(), pageSize, list);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Long getTotal() {
		// TODO Auto-generated method stub
		return application.getTotal();
	}

}
