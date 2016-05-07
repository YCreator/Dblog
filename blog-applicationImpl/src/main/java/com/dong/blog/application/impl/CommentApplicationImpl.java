package com.dong.blog.application.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.CommentApplication;
import com.dong.blog.application.dto.BlogDTO;
import com.dong.blog.application.dto.CommentDTO;
import com.dong.blog.core.domain.Blog;
import com.dong.blog.core.domain.Comment;
import com.dong.blog.util.BeanCopierUtil;


@Named
@Transactional(rollbackFor=Exception.class)
public class CommentApplicationImpl extends BaseApplicationImpl implements CommentApplication {

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommentDTO get(Long pk) {
		Comment comment = Comment.load(Comment.class, pk);
		return transformBeanData(comment);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommentDTO> findAll() {
		List<Comment> list = Comment.findAll(Comment.class);
		return transformBeanDatas(list);
	}

	public CommentDTO save(CommentDTO t) {
		Comment comment = new Comment();
		try {
			BeanCopierUtil.copyProperties(t, comment);
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
		if (dto.getBlogDTO() != null && dto.getBlogDTO().getId() != null) {
			jpql.append(" and _comment.blog.id = ").append(dto.getBlogDTO().getId());
		}
		if (dto.getState() != null) {
			jpql.append(" and _comment.state = ").append(dto.getState());
		}
		jpql.append("order by _comment.commentDate desc");
		getLog().debug(jpql.toString());
		@SuppressWarnings("unchecked")
		Page<Comment> pages = this.getQueryChannelService().createJpqlQuery(jpql.toString()).setPage(currentPage, pageSize).pagedList();
		List<Comment> list = pages.getData();
		List<CommentDTO> dtos = transformBeanDatas(list);
		return new Page<CommentDTO>(pages.getStart(), pages.getResultCount(), pageSize, dtos);
	}
	
	private List<CommentDTO> transformBeanDatas(List<Comment> source) {
		List<CommentDTO> list = new ArrayList<CommentDTO>();
		for (Comment comment : source) {
			list.add(transformBeanData(comment));
		}
		return list;
	}
	
	private CommentDTO transformBeanData(Comment source) {
		CommentDTO commentDTO = new CommentDTO();
		try {
			BeanCopierUtil.copyProperties(source, commentDTO);
			try {
				Blog blog = source.getBlog();
				if (blog != null) {
					BlogDTO blogDTO = new BlogDTO();
					BeanCopierUtil.copyProperties(blog, blogDTO);
					commentDTO.setBlogDTO(blogDTO);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return commentDTO;
	}


}
