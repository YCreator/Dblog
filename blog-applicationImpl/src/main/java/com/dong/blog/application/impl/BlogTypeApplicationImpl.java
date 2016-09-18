package com.dong.blog.application.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.BlogTypeApplication;
import com.dong.blog.application.dto.BlogTypeDTO;
import com.dong.blog.application.mapper.BlogTypeMapper;
import com.dong.blog.core.domain.BlogType;

@Named
@Transactional(rollbackFor=Exception.class)
public class BlogTypeApplicationImpl extends BaseApplicationImpl implements BlogTypeApplication {
	
	@Inject
	private BlogTypeMapper blogTypeMapper;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogTypeDTO get(Long pk) {
		BlogType blogType = BlogType.load(BlogType.class, pk);
		BlogTypeDTO dto;
		try {
			dto = blogTypeMapper.transformBeanData(blogType);
		} catch (Exception e) {
			dto = new BlogTypeDTO();
			e.printStackTrace();
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogTypeDTO> findAll() {
		List<BlogType> list = BlogType.findAll(BlogType.class);
		List<BlogTypeDTO> dtos;
		try {
			dtos = (List<BlogTypeDTO>) blogTypeMapper.transformBeanDatas(list);
		} catch (Exception e) {
			dtos = new ArrayList<BlogTypeDTO>();
			e.printStackTrace();
		}
		return dtos;
	}

	public BlogTypeDTO save(BlogTypeDTO t) {
		try {
			BlogType bt = blogTypeMapper.transformEntityData(t);
			bt.save();
			t.setId(bt.getId());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public boolean update(BlogTypeDTO t) {
		BlogType bt = BlogType.get(BlogType.class, t.getId());
		boolean isSuccess;
		try {
			blogTypeMapper.transformEntityData(bt, t);
			isSuccess = true;
		} catch(Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}	
		return isSuccess;
	}

	public void remove(Long pk) {
		removes(new Long[]{pk});
		
	}

	public void removes(Long[] pks) {
		for(int i = 0; i < pks.length; i++) {
			BlogType bt = BlogType.load(BlogType.class, pks[i]);
			bt.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BlogTypeDTO> getPage(BlogTypeDTO dto, int currentPage,
			int pageSize) {
		@SuppressWarnings("unchecked")
		Page<BlogType> page = this.getQueryChannelService()
				.createJpqlQuery("select _blogType from BlogType _blogType").setPage(currentPage, pageSize).pagedList();
		List<BlogType> list = page.getData();
		List<BlogTypeDTO> dtos;
		try {
			dtos = (List<BlogTypeDTO>) blogTypeMapper.transformBeanDatas(list);
		} catch (Exception e) {
			dtos = new ArrayList<BlogTypeDTO>();
			e.printStackTrace();
		}
		return new Page<BlogTypeDTO>(page.getStart(), page.getResultCount(), pageSize, dtos);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Long getTotal() {
		return (Long) this.getQueryChannelService().createJpqlQuery("select count(*) from BlogType _blogType").singleResult() ;
	}
	
}
