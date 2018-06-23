package com.dong.blog.facade.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.utils.Page;
import org.jboss.logging.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.CommentApplication;
import com.dong.blog.core.domain.Blog;
import com.dong.blog.core.domain.Comment;
import com.dong.blog.facade.CommentFacade;
import com.dong.blog.facade.dto.BlogDTO;
import com.dong.blog.facade.dto.CommentDTO;
import com.dong.blog.facade.impl.assembler.BlogAssembler;
import com.dong.blog.facade.impl.assembler.CommentAssembler;

@Named
@Transactional(rollbackFor=Exception.class)
public class CommentFacadeImpl implements CommentFacade {
	
	@Inject
	CommentApplication application;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommentDTO get(Long pk) {
		Comment comment = application.load(pk);
		return new CommentAssembler().toDto(comment);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommentDTO> findAll() {
		List<Comment> list = application.findAll();
		return new CommentAssembler().toDtos(list);
	}

	public void save(CommentDTO t) {
		Comment comment = new CommentAssembler().toEntity(t);
		BlogDTO blogDTO = t.getBlogDTO();
		Blog blog = new BlogAssembler().toEntity(blogDTO);
		comment.setBlog(blog);
		comment = application.save(comment);
		t.setId(comment.getId());
	}

	public boolean update(CommentDTO t) {
		Comment comment = application.get(t.getId());
		comment.setState(t.getState());
		return true;
	}

	public void remove(Long pk) {
		application.remove(pk);
	}

	public void removes(Long[] pks) {
		for (Long pk : pks) remove(pk);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Long getTotal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return application.getTotal(params);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CommentDTO> getPage(CommentDTO dto, int currentPage,
			int pageSize) {
		CommentAssembler mapper = new CommentAssembler();
		Comment comment = mapper.toEntity(dto);
		BlogDTO blogDTO = dto.getBlogDTO();
		if (blogDTO != null) {
			Blog blog = new BlogAssembler().toEntity(blogDTO);
			comment.setBlog(blog);
		}
		Page<Comment> pages = application.getPage(comment, currentPage, pageSize);
		Logger.getLogger(this.getClass()).debug("=================>"+pages.getData().size());
		List<CommentDTO> list = (List<CommentDTO>) mapper.toDtos(pages.getData());
		return new Page<CommentDTO>(pages.getStart(), pages.getResultCount(), pageSize, list);
	}

}
