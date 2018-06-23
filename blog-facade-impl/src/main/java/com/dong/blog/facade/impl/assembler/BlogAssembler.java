package com.dong.blog.facade.impl.assembler;

import com.dong.blog.core.domain.Blog;
import com.dong.blog.core.domain.BlogType;
import com.dong.blog.facade.dto.BlogDTO;
import com.dong.blog.util.BeanCopierUtil;

public class BlogAssembler extends AbstractAssembler<Blog, BlogDTO> {

	public BlogDTO toDto(Blog source) {
		BlogDTO dto = new BlogDTO();
		try {
			BeanCopierUtil.copyProperties(source, dto);
			BlogType bt = source.getBlogType();
			if (bt != null)
				dto.setBlogTypeDTO(new BlogTypeAssembler().toDto(bt));
			dto.setId((java.lang.Long) source.getId());
		} catch (Exception ignore) {

		}
		return dto;
	}

	public Blog toEntity(BlogDTO source) {
		Blog blog = new Blog();
		BeanCopierUtil.copyProperties(source, blog);
		return blog;
	}

	public Blog transformEntityData(Blog target, BlogDTO source) {
		target.setTitle(source.getTitle());
		target.setPicPath(source.getPicPath());
		target.setKeyWord(source.getKeyWord());
		target.setContent(source.getContent());
		target.setSummary(source.getSummary());
		target.setBlogType(BlogType.load(BlogType.class, source
				.getBlogTypeDTO().getId()));
		return target;
	}

}
