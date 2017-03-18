package com.dong.blog.application.impl;

import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.dayatang.utils.Page;

import com.dong.blog.application.CommentApplication;
import com.dong.blog.core.domain.Comment;


@Named
public class CommentApplicationImpl extends BaseApplicationImpl implements CommentApplication {

	public Comment get(Long pk) {
		return Comment.get(Comment.class, pk);
	}

	public List<Comment> findAll() {
		return Comment.findAll(Comment.class);
	}

	public Comment save(Comment t) {
		t.save();
		return t;
	}

	public boolean update(Long id, Integer state) {
		Comment comment = Comment.get(Comment.class, id);
		comment.setState(state);
		return true;
	}

	public void remove(Long pk) {
		Comment comment = Comment.load(Comment.class, pk);
		comment.remove();
	}

	public void removes(Long[] pks) {
		for ( int i = 0; i < pks.length; i++) {
			Comment comment = Comment.load(Comment.class, pks[i]);
			comment.remove();
		}
	}

	public Long getTotal(Map<String, Object> params) {
		String jpql = "select count(*) from Comment _comment";
		if (params != null && params.containsKey("state")) {
			jpql += String.format(" where _comment.state = %s", params.get("state"));
		}
		return (Long) this.getQueryChannelService().createJpqlQuery(jpql).singleResult();
	}

	@SuppressWarnings("unchecked")
	public Page<Comment> getPage(Comment dto, int currentPage,
			int pageSize) {
		StringBuilder jpql = new StringBuilder(
				"select new com.dong.blog.core.domain.Comment(_comment.id, _comment.userIp"
				+ ", _comment.content, _comment.commentDate, _comment.state"
				+ ", _comment.blog.id, _comment.blog.title, _comment.blog.replyHit) from Comment _comment where 1=1");
		if (dto.getBlog() != null && dto.getBlog().getId() != null) {
			jpql.append(" and _comment.blog.id = ").append(dto.getBlog().getId());
		}
		if (dto.getState() != null) {
			jpql.append(" and _comment.state=").append(dto.getState());
		}
		jpql.append(" order by _comment.commentDate desc");
		getLog().debug(jpql.toString()+"_"+currentPage+"_"+pageSize);
		return this.getQueryChannelService().createJpqlQuery(jpql.toString()).setPage(currentPage, pageSize).pagedList();
	}

	public boolean update(Comment t) {
		// TODO Auto-generated method stub
		return false;
	}

	public Comment load(Long id) {
		// TODO Auto-generated method stub
		return Comment.load(Comment.class, id);
	}

}
