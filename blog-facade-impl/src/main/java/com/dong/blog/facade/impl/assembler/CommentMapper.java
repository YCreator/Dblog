package com.dong.blog.facade.impl.assembler;

import com.dong.blog.core.domain.Comment;
import com.dong.blog.facade.dto.CommentDTO;
import com.dong.blog.facade.impl.exception.AssemblerException;
import com.dong.blog.util.BeanCopierUtil;

public class CommentMapper extends AbstractMapper<Comment, CommentDTO> {
	
	public CommentDTO transformBeanData(Comment source) throws AssemblerException {
		CommentDTO dto = new CommentDTO();
		BeanCopierUtil.copyProperties(source, dto);
		if (source.getBlog() != null) {
			dto.setBlogDTO(new BlogMapper().transformBeanData(source.getBlog()));
		}
		return dto;
	}

	public Comment transformEntityData(CommentDTO source) throws AssemblerException {
		Comment comment = new Comment();
		BeanCopierUtil.copyProperties(source, comment);
		return comment;
	}

	@Override
	public Comment transformEntityData(Comment target, CommentDTO source)
			throws AssemblerException {
		// TODO Auto-generated method stub
		return null;
	}

}
