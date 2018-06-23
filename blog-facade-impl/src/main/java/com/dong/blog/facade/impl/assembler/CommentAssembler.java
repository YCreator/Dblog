package com.dong.blog.facade.impl.assembler;

import com.dong.blog.core.domain.Comment;
import com.dong.blog.facade.dto.CommentDTO;
import com.dong.blog.util.BeanCopierUtil;

public class CommentAssembler extends AbstractAssembler<Comment, CommentDTO> {

	public CommentDTO toDto(Comment source) {
		CommentDTO dto = new CommentDTO();
		try {
			BeanCopierUtil.copyProperties(source, dto);
			if (source.getBlog() != null) {
				dto.setBlogDTO(new BlogAssembler().toDto(source.getBlog()));
			}
		} catch (Exception e) {

		}
		return dto;
	}

	public Comment toEntity(CommentDTO source) {
		Comment comment = new Comment();
		BeanCopierUtil.copyProperties(source, comment);
		return comment;
	}

}
