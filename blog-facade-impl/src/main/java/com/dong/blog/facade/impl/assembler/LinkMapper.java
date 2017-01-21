package com.dong.blog.facade.impl.assembler;

import com.dong.blog.core.domain.Link;
import com.dong.blog.facade.dto.LinkDTO;
import com.dong.blog.facade.impl.exception.AssemblerException;
import com.dong.blog.util.BeanCopierUtil;

public class LinkMapper extends AbstractMapper<Link, LinkDTO> {

	public LinkDTO transformBeanData(Link source) throws AssemblerException {
		LinkDTO dto = new LinkDTO();
		BeanCopierUtil.copyProperties(source, dto);
		return dto;
	}

	public Link transformEntityData(LinkDTO source) throws AssemblerException {
		Link link = new Link();
		BeanCopierUtil.copyProperties(source, link);
		return link;
	}

	@Override
	public Link transformEntityData(Link target, LinkDTO source)
			throws AssemblerException {
		BeanCopierUtil.copyProperties(source, target);
		return target;
	}

}
