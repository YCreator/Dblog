package com.dong.blog.application.mapper;

import javax.inject.Named;

import com.dong.blog.application.dto.BloggerDTO;
import com.dong.blog.core.domain.Blogger;
import com.dong.blog.util.BeanCopierUtil;

@Named
public class BloggerMapper extends AbstractMapper<Blogger, BloggerDTO> {
	

	public BloggerDTO transformBeanData(Blogger source) throws Exception {
		BloggerDTO dto = new BloggerDTO();
		BeanCopierUtil.copyProperties(source, dto);
		return dto;
	}

	public Blogger transformEntityData(BloggerDTO source) throws Exception {
		Blogger blogger = new Blogger();
		BeanCopierUtil.copyProperties(source, blogger);
		return blogger;
	}

	@Override
	public Blogger transformEntityData(Blogger target, BloggerDTO source)
			throws Exception {
		target.setImageName(source.getImageName());
		target.setNickName(source.getNickName());
		target.setUsername(source.getUsername());
		target.setProFile(source.getProFile());
		target.setSign(source.getSign());
		return target;
	}

}
