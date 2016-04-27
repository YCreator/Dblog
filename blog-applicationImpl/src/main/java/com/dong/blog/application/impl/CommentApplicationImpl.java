package com.dong.blog.application.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.CommentApplication;
import com.dong.blog.application.dto.CommentDTO;
import com.dong.blog.core.domain.Comment;


@Named
@Transactional(rollbackFor=Exception.class)
public class CommentApplicationImpl extends BaseApplicationImpl implements CommentApplication {

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommentDTO get(Long pk) {
		CommentDTO commentDTO = new CommentDTO();
		Comment comment = Comment.load(Comment.class, pk);
		try {
			BeanUtils.copyProperties(commentDTO, comment);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return commentDTO;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommentDTO> findAll() {
		List<CommentDTO> dtos = new ArrayList<CommentDTO>();
		List<Comment> list = Comment.findAll(Comment.class);
		for (Comment comment : list) {
			CommentDTO dto = new CommentDTO();
			try {
				BeanUtils.copyProperties(dto, comment);	
			} catch(Exception e) {
				e.printStackTrace();
			}	
			dtos.add(dto);
		}
		return dtos;
	}

	public CommentDTO save(CommentDTO t) {
		Comment comment = new Comment();
		try {
			BeanUtils.copyProperties(comment, t);
		} catch(Exception e) {
			e.printStackTrace();
		}
		comment.setState(0);
		comment.save();
		t.setId(comment.getId());
		return t;
	}

	public boolean update(CommentDTO t) {
		Comment comment = Comment.get(Comment.class, t.getId());
		comment.setState(t.getState());
		return true;
	}

	public void remove(Long pk) {
		removes(new Long[]{ pk });
	}

	public void removes(Long[] pks) {
		for ( int i = 0; i < pks.length; i++) {
			Comment comment = Comment.load(Comment.class, pks[i]);
			comment.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Long getTotal(Map<String, Object> params) {
		String jpql = "select count(*) from Comment _comment";
		if (params != null && params.containsKey("state")) {
			jpql += String.format(" where _comment.state = %s", params.get("state"));
		}
		return (Long) this.getQueryChannelService().createJpqlQuery(jpql).singleResult();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<CommentDTO> getPage(CommentDTO dto, int currentPage,
			int pageSize) {
		StringBuilder jpql = new StringBuilder("select _comment from Comment _comment where 1=1");
		if (dto.getBlogId() != null) {
			jpql.append(" and _comment.blogId = ").append(dto.getBlogId());
		}
		if (dto.getState() != null) {
			jpql.append(" and _comment.state = ").append(dto.getState());
		}
		jpql.append("order by _comment.commentDate desc");
		@SuppressWarnings("unchecked")
		Page<Comment> pages = this.getQueryChannelService().createJpqlQuery(jpql.toString()).setPage(currentPage, pageSize).pagedList();
		List<Comment> list = pages.getData();
		List<CommentDTO> dtos = new ArrayList<CommentDTO>();
		for (Comment comment : list) {
			CommentDTO d = new CommentDTO();
			try {
				BeanUtils.copyProperties(d, comment);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			dtos.add(d);
		}
		return new Page<CommentDTO>(pages.getStart(), pages.getResultCount(), pageSize, dtos);
	}


}
