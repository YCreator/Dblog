package com.dong.blog.facade.impl;

import java.math.BigInteger;
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
import com.dong.blog.facade.dto.BlogTypeDTO;
import com.dong.blog.facade.dto.CategoryDTO;
import com.dong.blog.facade.impl.assembler.BlogAssembler;
import com.dong.blog.facade.impl.assembler.BlogTypeAssembler;

@Named
@Transactional(rollbackFor = Exception.class)
public class BlogFacadeImpl implements BlogFacade {

	@Inject
	private BlogApplication application;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogDTO get(Long pk) {
		Blog blog = application.loadEntity(Blog.class, pk);
		return new BlogAssembler().toDto(blog);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogDTO> findAll() {
		List<Blog> blogs = application.findAll(Blog.class);
		return new BlogAssembler().toDtos(blogs);
	}

	public void save(BlogDTO t) {
		Blog blog = new BlogAssembler().toEntity(t);
		BlogType blogType = new BlogTypeAssembler()
				.toEntity(t.getBlogTypeDTO());
		blog = application.save(blog, blogType);
		t.setId(blog.getId());
	}

	public boolean update(BlogDTO t) {
		boolean isSuccess = false;
		Blog blog = application.getEntity(Blog.class, t.getId());
		try {
			blog.setTitle(t.getTitle());
			blog.setPicPath(t.getPicPath());
			blog.setKeyWord(t.getKeyWord());
			blog.setContent(t.getContent());
			blog.setSummary(t.getSummary());
			blog.setBlogType(BlogType.load(BlogType.class, t.getBlogTypeDTO()
					.getId()));
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
		for (Long pk : pks)
			application.remove(pk);
	}

	public BigInteger getTotal(Map<String, Object> params) {
		return application.getTotal(params);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogDTO getLastBlog(Long id) {
		Blog blog = application.getLastBlog(id);
		return new BlogAssembler().toDto(blog);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogDTO getNextBlog(Long id) {
		Blog blog = application.getNextBlog(id);
		return new BlogAssembler().toDto(blog);
	}

	public Integer upClickHit(Long id) {
		// TODO Auto-generated method stub
		Blog blog = application.getEntity(Blog.class, id);
		application.upClickHit(blog);
		return blog.getClickHit();
	}

	public Integer upReplyHit(Long id) {
		// TODO Auto-generated method stub
		Blog blog = application.getEntity(Blog.class, id);
		application.upReplyHit(blog);
		return blog.getReplyHit();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BlogDTO> pageQueryByCate(CategoryDTO dto, int currentPage,
			int pageSize) {
		Page<Blog> pages = application.pageQueryByCate(dto.getId(),
				currentPage, pageSize);
		List<BlogDTO> dtos = new BlogAssembler().toDtos(pages.getData());
		return new Page<BlogDTO>(pages.getStart(), pages.getResultCount(),
				pageSize, dtos);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BlogDTO> pageQuery(Map<String, Object> map, int currentPage,
			int pageSize) {
		Page<Blog> pages = application.pageQuery(map, currentPage, pageSize);
		List<Blog> list = pages.getData();
		List<BlogDTO> dtos = new BlogAssembler().toDtos(list);
		return new Page<BlogDTO>(pages.getStart(), pages.getResultCount(),
				pageSize, dtos);
	}

	public boolean blogInBlogTypeExist(BlogTypeDTO dto) {
		// TODO Auto-generated method stub
		return false;
	}
}
