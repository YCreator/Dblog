package com.dong.blog.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.CommentApplication;
import com.dong.blog.core.domain.Blog;
import com.dong.blog.core.domain.Comment;
import com.dong.blog.facade.CommentFacade;
import com.dong.blog.facade.dto.BlogDTO;
import com.dong.blog.facade.dto.CommentDTO;
import com.dong.blog.facade.impl.assembler.BlogMapper;
import com.dong.blog.facade.impl.assembler.CommentMapper;

@Named
@Transactional(rollbackFor=Exception.class)
public class CommentFacadeImpl implements CommentFacade {
	
	@Inject
	CommentApplication application;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommentDTO get(Long pk) {
		CommentDTO commentDTO = null;
		try {
			commentDTO = new CommentMapper().transformBeanData(application.load(pk));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return commentDTO;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommentDTO> findAll() {
		List<CommentDTO> list = null;
		 try {
			list = (List<CommentDTO>) new CommentMapper().transformBeanDatas(application.findAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public CommentDTO save(CommentDTO t) {
		try {
			Comment comment = new CommentMapper().transformEntityData(t);
			BlogDTO blogDTO = t.getBlogDTO();
			if (blogDTO != null) {
				Blog blog = new BlogMapper().transformEntityData(blogDTO);
				comment.setBlog(blog);
			}
			comment = application.save(comment);
			t.setId(comment.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
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
		for (Long pk : pks) {
			remove(pk);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Long getTotal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return application.getTotal(params);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CommentDTO> getPage(CommentDTO dto, int currentPage,
			int pageSize) {
		List<CommentDTO> list = new ArrayList<CommentDTO>();
		Page<Comment> pages = null;
		try {
			CommentMapper mapper = new CommentMapper();
			Comment comment = mapper.transformEntityData(dto);
			BlogDTO blogDTO = dto.getBlogDTO();
			if (blogDTO != null) {
				Blog blog = new BlogMapper().transformEntityData(blogDTO);
				comment.setBlog(blog);
			}
			pages = application.getPage(comment, currentPage, pageSize);
			list = (List<CommentDTO>) mapper.transformBeanDatas(pages.getData());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Page<CommentDTO>(pages.getStart(), pages.getResultCount(), pageSize, list);
	}

}
