package com.dong.blog.application.mapper;

import javax.inject.Named;

import com.dong.blog.application.dto.BlogTypeDTO;
import com.dong.blog.core.domain.BlogType;
import com.dong.blog.util.BeanCopierUtil;

@Named
public class BlogTypeMapper extends AbstractMapper<BlogType, BlogTypeDTO> {

	public BlogTypeDTO transformBeanData(BlogType source) throws Exception {
		// TODO Auto-generated method stub
		BlogTypeDTO dto = new BlogTypeDTO();
		BeanCopierUtil.copyProperties(source, dto);
		return dto;
	}

	public BlogType transformEntityData(BlogTypeDTO source) throws Exception {
		// TODO Auto-generated method stub
		BlogType blogType = new BlogType();
		BeanCopierUtil.copyProperties(source, blogType);
		return blogType;
	}

	@Override
	public BlogType transformEntityData(BlogType target, BlogTypeDTO source)
			throws Exception {
		BeanCopierUtil.copyProperties(source, target);
		return target;
	}

}
