package com.dong.blog.facade.impl.assembler;

import com.dong.blog.core.domain.Blogger;
import com.dong.blog.facade.dto.BloggerDTO;
import com.dong.blog.facade.impl.exception.AssemblerException;
import com.dong.blog.util.BeanCopierUtil;

public class BloggerMapper extends AbstractMapper<Blogger, BloggerDTO> {

	public BloggerDTO transformBeanData(Blogger source)
			throws AssemblerException {
		try {
			BloggerDTO dto = new BloggerDTO();
			BeanCopierUtil.copyProperties(source, dto);
			return dto;
		} catch(Exception e) {
			throw new AssemblerException("BloggerDTO属性转换异常");
		}
		
	}

	public Blogger transformEntityData(BloggerDTO source)
			throws AssemblerException {
		try {
			Blogger blogger = new Blogger();
			BeanCopierUtil.copyProperties(source, blogger);
			return blogger;
		} catch(Exception e) {
			throw new AssemblerException("Blogger属性转换异常");
		}
		
	}

	@Override
	public Blogger transformEntityData(Blogger target, BloggerDTO source)
			throws AssemblerException {
		target.setImageName(source.getImageName());
		target.setNickName(source.getNickName());
		target.setUsername(source.getUsername());
		target.setProFile(source.getProFile());
		target.setSign(source.getSign());
		return target;
	}

}
