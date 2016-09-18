package com.dong.blog.application.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.CommentApplication;
import com.dong.blog.application.dto.CommentDTO;
import com.dong.blog.application.mapper.CommentMapper;
import com.dong.blog.core.domain.Comment;


@Named
@Transactional(rollbackFor=Exception.class)
public class CommentApplicationImpl extends BaseApplicationImpl implements CommentApplication {
	
	@Inject
	private CommentMapper commentMapper;
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommentDTO get(Long pk) {
		Comment comment = Comment.load(Comment.class, pk);
		CommentDTO dto;
		try {
			dto = commentMapper.transformBeanData(comment);
		} catch (Exception e) {
			dto = new CommentDTO();
			e.printStackTrace();
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommentDTO> findAll() {
		List<Comment> list = Comment.findAll(Comment.class);
		List<CommentDTO> dtos;
		try {
			dtos = (List<CommentDTO>) commentMapper.transformBeanDatas(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			dtos = new ArrayList<CommentDTO>();
			e.printStackTrace();
		}
		return dtos;
	}

	public CommentDTO save(CommentDTO t) {
		try {
			Comment comment = commentMapper.transformEntityData(t);
			comment.save();
			t.setId(comment.getId());
		} catch(Exception e) {
			e.printStackTrace();
		}
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
		StringBuilder jpql = new StringBuilder(
				"select new com.dong.blog.application.dto.CommentDTO(_comment.id, _comment.userIp"
				+ ", _comment.content, _comment.commentDate, _comment.state, _comment.blog.id"
				+ ", _comment.blog.title, _comment.blog.replyHit) from Comment _comment where 1=1");
		if (dto.getBlogDTO() != null && dto.getBlogDTO().getId() != null) {
			jpql.append(" and _comment.blog.id = ").append(dto.getBlogDTO().getId());
		}
		if (dto.getState() != null) {
			jpql.append(" and _comment.state=").append(dto.getState());
		}
		jpql.append(" order by _comment.commentDate desc");
		getLog().debug(jpql.toString());
		@SuppressWarnings("unchecked")
		Page<CommentDTO> pages = this.getQueryChannelService().createJpqlQuery(jpql.toString()).setPage(currentPage, pageSize).pagedList();
		return new Page<CommentDTO>(pages.getStart(), pages.getResultCount(), pageSize, pages.getData());
	}

}
