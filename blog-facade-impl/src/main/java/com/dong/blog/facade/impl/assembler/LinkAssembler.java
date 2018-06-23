package com.dong.blog.facade.impl.assembler;

import com.dong.blog.core.domain.Link;
import com.dong.blog.facade.dto.LinkDTO;
import com.dong.blog.util.BeanCopierUtil;

public class LinkAssembler extends AbstractAssembler<Link, LinkDTO> {

	public LinkDTO toDto(Link source){
		LinkDTO dto = new LinkDTO();
		try {
			BeanCopierUtil.copyProperties(source, dto);
		} catch(Exception e) {
			
		}
		return dto;
	}

	public Link toEntity(LinkDTO source){
		Link link = new Link();
		BeanCopierUtil.copyProperties(source, link);
		return link;
	}

	public Link transformEntityData(Link target, LinkDTO source) {
		BeanCopierUtil.copyProperties(source, target);
		return target;
	}

}
