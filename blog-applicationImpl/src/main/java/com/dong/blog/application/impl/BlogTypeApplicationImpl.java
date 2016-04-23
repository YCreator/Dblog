package com.dong.blog.application.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.BlogTypeApplication;
import com.dong.blog.application.dto.BlogTypeDTO;
import com.dong.blog.core.domain.BlogType;


/*@Service("blogTypeApplication")*/
@Named
@Transactional
public class BlogTypeApplicationImpl extends BaseApplicationImpl implements BlogTypeApplication {

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogTypeDTO get(Long pk) {
		BlogTypeDTO dto = new BlogTypeDTO();
		BlogType blogType = BlogType.load(BlogType.class, pk);
		try {
			BeanUtils.copyProperties(dto, blogType);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogTypeDTO> findAll() {
		List<BlogType> list = BlogType.findAll(BlogType.class);
		List<BlogTypeDTO> dtos = new ArrayList<BlogTypeDTO>();
		for (BlogType blogType : list) {
			BlogTypeDTO dto = new BlogTypeDTO();
			try {
				BeanUtils.copyProperties(dto, blogType);
			} catch (Exception e) {
				e.printStackTrace();
			}
			dtos.add(dto);
		}
		return dtos;
	}

	public BlogTypeDTO save(BlogTypeDTO t) {
		BlogType bt = new BlogType();
		try {
			BeanUtils.copyProperties(bt, t);
		} catch(Exception e) {
			e.printStackTrace();
		}
		bt.save();
		t.setId(bt.getId());
		return t;
	}

	public boolean update(BlogTypeDTO t) {
		BlogType bt = BlogType.get(BlogType.class, t.getId());
		boolean isSuccess;
		try {
			BeanUtils.copyProperties(bt, t);
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
		List<BlogTypeDTO> dtos = new ArrayList<BlogTypeDTO>();
		@SuppressWarnings("unchecked")
		Page<BlogType> page = this.getQueryChannelService()
				.createJpqlQuery("select _blogType from BlogType _blogType").setPage(currentPage, pageSize).pagedList();
		List<BlogType> list = page.getData();
		for (BlogType type : list) {
			BlogTypeDTO d = new BlogTypeDTO();
			try {
				BeanUtils.copyProperties(d, type);
			} catch(Exception e) {
				e.printStackTrace();
			}
			dtos.add(d);
		}
		return new Page<BlogTypeDTO>(page.getStart(), page.getResultCount(), pageSize, dtos);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BigInteger getTotal() {
		return (BigInteger) this.getQueryChannelService().createJpqlQuery("select count(*) from BlogType _blogType").singleResult() ;
	}

}
