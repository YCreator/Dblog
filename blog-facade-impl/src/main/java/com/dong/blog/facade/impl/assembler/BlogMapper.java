package com.dong.blog.facade.impl.assembler;

import com.dong.blog.core.domain.Blog;
import com.dong.blog.core.domain.BlogType;
import com.dong.blog.facade.dto.BlogDTO;
import com.dong.blog.facade.dto.BlogTypeDTO;
import com.dong.blog.facade.impl.exception.AssemblerException;
import com.dong.blog.util.BeanCopierUtil;

public class BlogMapper extends AbstractMapper<Blog, BlogDTO> {

	public BlogDTO transformBeanData(Blog source) throws AssemblerException {
		if (source == null) {
			return null;
		}
		BlogDTO dto = new BlogDTO();
		BeanCopierUtil.copyProperties(source, dto);
		BlogType bt = source.getBlogType();
		if (bt != null) {
			BlogTypeDTO blogType = new BlogTypeMapper().transformBeanData(bt);
			dto.setBlogTypeDTO(blogType);
		}
		dto.setId((java.lang.Long) source.getId());
		return dto;
	}

	public Blog transformEntityData(BlogDTO source) throws AssemblerException {
		Blog blog = new Blog();
		BeanCopierUtil.copyProperties(source, blog);
		return blog;
	}

	@Override
	public Blog transformEntityData(Blog target, BlogDTO source)
			throws AssemblerException {
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
