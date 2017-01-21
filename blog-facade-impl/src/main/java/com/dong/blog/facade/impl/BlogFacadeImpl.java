package com.dong.blog.facade.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.BlogApplication;
import com.dong.blog.core.domain.Blog;
import com.dong.blog.core.domain.BlogType;
import com.dong.blog.facade.BlogFacade;
import com.dong.blog.facade.dto.BlogDTO;
import com.dong.blog.facade.dto.CategoryDTO;
import com.dong.blog.facade.impl.assembler.BlogMapper;
import com.dong.blog.facade.impl.assembler.BlogTypeMapper;

@Named
@Transactional(rollbackFor = Exception.class)
public class BlogFacadeImpl implements BlogFacade {

	@Inject
	BlogApplication application;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogDTO get(Long pk) {
		BlogDTO blogDTO = null;
		try {
			Blog blog = application.load(pk);
			blogDTO = new BlogMapper().transformBeanData(blog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blogDTO;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogDTO> findAll() {
		List<BlogDTO> list = null;
		try {
			list = (List<BlogDTO>) new BlogMapper()
					.transformBeanDatas(application.findAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public BlogDTO save(BlogDTO t) {
		try {
			Blog blog = new BlogMapper().transformEntityData(t);
			BlogType blogType = new BlogTypeMapper().transformEntityData(t
					.getBlogTypeDTO());
			blog.setBlogType(blogType);
			blog = application.save(blog);
			t.setId(blog.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	public boolean update(BlogDTO t) {
		boolean isSuccess = false;
		Blog blog = application.get(t.getId());
		try {
			new BlogMapper().transformEntityData(blog, t);
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

	public BigInteger getTotal(Map<String, Object> params) {
		return application.getTotal(params);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BlogDTO> pageQuery(Map<String, Object> map, int currentPage,
			int pageSize) {
		Page<Blog> pages = application.pageQuery(map, currentPage, pageSize);
		List<Blog> list = pages.getData();
		List<BlogDTO> dtos = new ArrayList<BlogDTO>();
		try {
			dtos = (List<BlogDTO>) new BlogMapper().transformBeanDatas(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Page<BlogDTO>(pages.getStart(), pages.getResultCount(),
				pageSize, dtos);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogDTO getLastBlog(Long id) {
		BlogDTO blogDTO = null;
		try {
			blogDTO = new BlogMapper().transformBeanData(application
					.getLastBlog(id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return blogDTO;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogDTO getNextBlog(Long id) {
		BlogDTO blogDTO = null;
		try {
			blogDTO = new BlogMapper().transformBeanData(application
					.getNextBlog(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blogDTO;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogDTO> getBlogsByProperty(String propertyName,
			Object propertyValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogDTO> getBlogsByProperties(Map<String, Object> properties) {
		return null;
	}

	public boolean updateClickHit(Long id, Integer clickHit) {
		// TODO Auto-generated method stub
		return application.updateClickHit(id, clickHit);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BlogDTO> pageQueryByCate(CategoryDTO dto, int currentPage,
			int pageSize) {
		Page<Blog> pages = application.pageQueryByCate(dto.getId(),
				currentPage, pageSize);
		List<BlogDTO> dtos = new ArrayList<BlogDTO>();
		try {
			dtos = (List<BlogDTO>) new BlogMapper().transformBeanDatas(pages
					.getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Page<BlogDTO>(pages.getStart(), pages.getResultCount(),
				pageSize, dtos);
	}

	public boolean updateReplyHit(Long id, Integer replyHit) {
		// TODO Auto-generated method stub
		return application.updateReplyHit(id, replyHit);
	}

}
