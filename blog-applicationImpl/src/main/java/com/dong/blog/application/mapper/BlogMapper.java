package com.dong.blog.application.mapper;

import java.util.Date;

import javax.inject.Named;

import com.dong.blog.application.dto.BlogDTO;
import com.dong.blog.application.dto.BlogTypeDTO;
import com.dong.blog.core.domain.Blog;
import com.dong.blog.core.domain.BlogType;
import com.dong.blog.util.BeanCopierUtil;

@Named
public class BlogMapper extends AbstractMapper<Blog, BlogDTO> {

	public BlogDTO transformBeanData(Blog source) throws Exception {
		BlogDTO dto = new BlogDTO();
		BeanCopierUtil.copyProperties(source, dto);
		BlogTypeDTO blogTypeDTO = new BlogTypeDTO();
		BeanCopierUtil.copyProperties(source.getBlogType(), blogTypeDTO);
		dto.setBlogTypeDTO(blogTypeDTO);
		dto.setId((java.lang.Long)source.getId());
		return dto;
	}

	public Blog transformEntityData(BlogDTO source) throws Exception {
		Blog blog = new Blog();
		BeanCopierUtil.copyProperties(source, blog);
		blog.setBlogType(BlogType.load(BlogType.class, source.getBlogTypeDTO().getId()));
		blog.setReleaseDate(new Date());
		blog.setClickHit(0);
		blog.setReplyHit(0);
		return blog;
	}

	@Override
	public Blog transformEntityData(Blog target, BlogDTO source)
			throws Exception {
		target.setTitle(source.getTitle());
		target.setPicPath(source.getPicPath());
		target.setKeyWord(source.getKeyWord());
		target.setContent(source.getContent());
		target.setSummary(source.getSummary());
		target.setBlogType(BlogType.load(BlogType.class, source.getBlogTypeDTO().getId()));
		return target;
	}

}
