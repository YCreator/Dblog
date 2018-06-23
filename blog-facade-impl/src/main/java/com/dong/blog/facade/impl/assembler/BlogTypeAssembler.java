package com.dong.blog.facade.impl.assembler;

import com.dong.blog.core.domain.BlogType;
import com.dong.blog.facade.dto.BlogTypeDTO;
import com.dong.blog.util.BeanCopierUtil;

public class BlogTypeAssembler extends AbstractAssembler<BlogType, BlogTypeDTO> {

	public BlogTypeDTO toDto(BlogType source) {
		// TODO Auto-generated method stub
		BlogTypeDTO dto = new BlogTypeDTO();
		try {
			BeanCopierUtil.copyProperties(source, dto);
		} catch (Exception e) {

		}
		return dto;
	}

	public BlogType toEntity(BlogTypeDTO source) {
		// TODO Auto-generated method stub
		BlogType blogType = new BlogType();
		BeanCopierUtil.copyProperties(source, blogType);
		return blogType;
	}

	public BlogType transformEntityData(BlogType target, BlogTypeDTO source) {
		BeanCopierUtil.copyProperties(source, target);
		return target;
	}

}
