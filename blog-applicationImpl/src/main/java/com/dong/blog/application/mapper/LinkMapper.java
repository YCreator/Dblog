package com.dong.blog.application.mapper;

import javax.inject.Named;

import com.dong.blog.application.dto.LinkDTO;
import com.dong.blog.core.domain.Link;
import com.dong.blog.util.BeanCopierUtil;

@Named
public class LinkMapper extends AbstractMapper<Link, LinkDTO> {

	public LinkDTO transformBeanData(Link source) throws Exception {
		LinkDTO dto = new LinkDTO();
		BeanCopierUtil.copyProperties(source, dto);
		return dto;
	}

	public Link transformEntityData(LinkDTO source) throws Exception {
		Link link = new Link();
		BeanCopierUtil.copyProperties(source, link);
		return link;
	}

	@Override
	public Link transformEntityData(Link target, LinkDTO source)
			throws Exception {
		BeanCopierUtil.copyProperties(source, target);
		return target;
	}

}
