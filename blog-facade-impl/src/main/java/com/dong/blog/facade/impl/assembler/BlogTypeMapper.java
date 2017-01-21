package com.dong.blog.facade.impl.assembler;

import com.dong.blog.core.domain.BlogType;
import com.dong.blog.facade.dto.BlogTypeDTO;
import com.dong.blog.facade.impl.exception.AssemblerException;
import com.dong.blog.util.BeanCopierUtil;

public class BlogTypeMapper extends AbstractMapper<BlogType, BlogTypeDTO> {

	public BlogTypeDTO transformBeanData(BlogType source) throws AssemblerException {
		// TODO Auto-generated method stub
		try {
			BlogTypeDTO dto = new BlogTypeDTO();
			BeanCopierUtil.copyProperties(source, dto);
			return dto;
		} catch(Exception e) {
			throw new AssemblerException("BlogTypeDTO属性转换异常");
		}	
		
	}

	public BlogType transformEntityData(BlogTypeDTO source) throws AssemblerException {
		// TODO Auto-generated method stub
		BlogType blogType = new BlogType();
		BeanCopierUtil.copyProperties(source, blogType);
		return blogType;
	}

	@Override
	public BlogType transformEntityData(BlogType target, BlogTypeDTO source)
			throws AssemblerException {
		BeanCopierUtil.copyProperties(source, target);
		return target;
	}

}
