package com.dong.blog.facade.impl.assembler;

import com.dong.blog.core.domain.Menu;
import com.dong.blog.facade.dto.MenuDTO;
import com.dong.blog.util.BeanCopierUtil;

public class MenuAssembler extends AbstractAssembler<Menu, MenuDTO> {

	public MenuDTO toDto(Menu source) {
		MenuDTO dto = new MenuDTO();
		try {
			BeanCopierUtil.copyProperties(source, dto);
			dto.setMenuType(source.getType().getLabel());
		} catch (Exception e) {

		}
		return dto;
	}

	public Menu toEntity(MenuDTO source) {
		Menu menu = new Menu();
		BeanCopierUtil.copyProperties(source, menu);
		return menu;
	}

	public Menu transformEntityData(Menu target, MenuDTO source) {
		target.setDescription(source.getDescription());
		target.setIcon(source.getIcon());
		target.setPageUrl(source.getPageUrl());
		target.setTitle(source.getTitle());
		target.setSort(source.getSort());
		return target;
	}

}
