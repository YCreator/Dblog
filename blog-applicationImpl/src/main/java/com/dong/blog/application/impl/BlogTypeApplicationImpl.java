package com.dong.blog.application.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.BlogTypeApplication;
import com.dong.blog.application.dto.BlogTypeDTO;
import com.dong.blog.core.domain.BlogType;
import com.dong.blog.util.BeanCopierUtil;


@Named
@Transactional(rollbackFor=Exception.class)
public class BlogTypeApplicationImpl extends BaseApplicationImpl implements BlogTypeApplication {

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogTypeDTO get(Long pk) {
		BlogType blogType = BlogType.load(BlogType.class, pk);
		return transformBeanData(blogType);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogTypeDTO> findAll() {
		List<BlogType> list = BlogType.findAll(BlogType.class);
		return transformBeanDatas(list);
	}

	public BlogTypeDTO save(BlogTypeDTO t) {
		BlogType bt = new BlogType();
		try {
			BeanCopierUtil.copyProperties(t, bt);
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
			BeanCopierUtil.copyProperties(t, bt);
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
		List<BlogTypeDTO> dtos = transformBeanDatas(list);
		return new Page<BlogTypeDTO>(page.getStart(), page.getResultCount(), pageSize, dtos);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Long getTotal() {
		return (Long) this.getQueryChannelService().createJpqlQuery("select count(*) from BlogType _blogType").singleResult() ;
	}
	
	/**
	 * 转换数组对象
	 * @param source
	 * @return
	 */
	private List<BlogTypeDTO> transformBeanDatas(List<BlogType> source) {
		List<BlogTypeDTO> list = new ArrayList<BlogTypeDTO>();
		for (BlogType blogType : source) {
			list.add(transformBeanData(blogType));
		}
		return list;
	}
	
	/**
	 * 转换对象
	 * @param source
	 * @return
	 */
	private BlogTypeDTO transformBeanData(BlogType source) {
		BlogTypeDTO blogTypeDTO = new BlogTypeDTO();
		try {
			BeanCopierUtil.copyProperties(source, blogTypeDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blogTypeDTO;
	}

}
