package com.dong.blog.application.mapper;

import javax.inject.Inject;
import javax.inject.Named;

import com.dong.blog.application.dto.CommentDTO;
import com.dong.blog.core.domain.Blog;
import com.dong.blog.core.domain.Comment;
import com.dong.blog.util.BeanCopierUtil;

@Named
public class CommentMapper extends AbstractMapper<Comment, CommentDTO> {
	
	@Inject
	private BlogMapper blogMapper;

	public CommentDTO transformBeanData(Comment source) throws Exception {
		CommentDTO dto = new CommentDTO();
		BeanCopierUtil.copyProperties(source, dto);
		if (source.getBlog() != null) {
			dto.setBlogDTO(blogMapper.transformBeanData(source.getBlog()));
		}
		return dto;
	}

	public Comment transformEntityData(CommentDTO source) throws Exception {
		Comment comment = new Comment();
		BeanCopierUtil.copyProperties(source, comment);
		Blog blog = Blog.load(Blog.class, source.getBlogDTO().getId());
		comment.setBlog(blog);
		return comment;
	}

	@Override
	public Comment transformEntityData(Comment target, CommentDTO source)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
