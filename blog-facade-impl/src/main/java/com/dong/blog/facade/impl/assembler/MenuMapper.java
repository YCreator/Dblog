package com.dong.blog.facade.impl.assembler;

import com.dong.blog.core.domain.Menu;
import com.dong.blog.facade.dto.MenuDTO;
import com.dong.blog.facade.impl.exception.AssemblerException;
import com.dong.blog.util.BeanCopierUtil;

public class MenuMapper extends AbstractMapper<Menu, MenuDTO> {

	public MenuDTO transformBeanData(Menu source) throws AssemblerException {
		MenuDTO dto = new MenuDTO();
		BeanCopierUtil.copyProperties(source, dto);
		dto.setMenuType(source.getType().getLabel());
		return dto;
	}

	public Menu transformEntityData(MenuDTO source) throws AssemblerException {
		Menu menu = new Menu();
		BeanCopierUtil.copyProperties(source, menu);
		return menu;
	}

	@Override
	public Menu transformEntityData(Menu target, MenuDTO source)
			throws AssemblerException {
		target.setDescription(source.getDescription());
		target.setIcon(source.getIcon());
		target.setPageUrl(source.getPageUrl());
		target.setTitle(source.getTitle());
		target.setSort(source.getSort());
		return target;
	}

}
