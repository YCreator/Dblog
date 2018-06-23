package com.dong.blog.facade.impl.assembler;

import com.dong.blog.core.domain.Blogger;
import com.dong.blog.facade.dto.BloggerDTO;
import com.dong.blog.util.BeanCopierUtil;

public class BloggerAssembler extends AbstractAssembler<Blogger, BloggerDTO> {

	public BloggerDTO toDto(Blogger source) {
		BloggerDTO dto = new BloggerDTO();
		try {
			BeanCopierUtil.copyProperties(source, dto);
		} catch (Exception ignore) {

		}
		return dto;
	}

	public Blogger toEntity(BloggerDTO source) {
		Blogger blogger = new Blogger();
		BeanCopierUtil.copyProperties(source, blogger);
		return blogger;
	}

}
