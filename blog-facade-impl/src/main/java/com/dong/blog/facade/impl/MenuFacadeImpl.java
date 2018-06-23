package com.dong.blog.facade.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.MenuApplication;
import com.dong.blog.application.TaccountabilityApplication;
import com.dong.blog.core.domain.Menu;
import com.dong.blog.core.domain.MenuType;
import com.dong.blog.core.domain.Taccountability;
import com.dong.blog.facade.MenuFacade;
import com.dong.blog.facade.dto.MenuDTO;
import com.dong.blog.facade.impl.assembler.MenuAssembler;

@Named
@Transactional(rollbackFor = Exception.class)
public class MenuFacadeImpl implements MenuFacade {

	@Inject
	MenuApplication application;
	
	@Inject
	TaccountabilityApplication tApplication;
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MenuDTO get(Long pk) {
		// TODO Auto-generated method stub
		Menu menu = application.load(pk);
		return new MenuAssembler().toDto(menu);
	}

	public List<MenuDTO> findAll() {
		List<Menu> list = application.findAll();
		return new MenuAssembler().toDtos(list);
	}

	public void save(MenuDTO t) {
		Menu menu = new MenuAssembler().toEntity(t);
		menu.setType(MenuType.getType(t.getMenuType()));
		application.save(menu);
		t.setId(menu.getId());
	}

	public boolean update(MenuDTO t) {
		boolean isSuccess = false;
		Menu menu = application.get(t.getId());
		try {
			menu.setDescription(t.getDescription());
			menu.setIcon(t.getIcon());
			menu.setPageUrl(t.getPageUrl());
			menu.setTitle(t.getTitle());
			menu.setSort(t.getSort());
			isSuccess = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuccess;
	}

	public void remove(Long pk) {
		application.remove(pk);
	}

	public void removes(Long[] pks) {
		for(Long id : pks) application.remove(id);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MenuDTO> getMenuTree() {
		List<MenuDTO> list = new ArrayList<MenuDTO>();
		List<Menu> menus = application.getAllParentMenu();
		for (Menu menu : menus) {
			List<Menu> children = tApplication.getMenuByParent(menu);
			if (children.size() > 0) {
				MenuAssembler mapper = new MenuAssembler();
				List<MenuDTO> menuDTOs = (List<MenuDTO>) mapper.toDtos(children);
				Set<MenuDTO> set = new LinkedHashSet<MenuDTO>(menuDTOs);
				MenuDTO dto = mapper.toDto(menu);
				dto.setChildren(set);
				list.add(dto);
			}
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MenuDTO> getParentMenus() {
		List<Menu> list = application.getAllParentMenu();
		return new MenuAssembler().toDtos(list);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MenuDTO> getChildMenus() {
		List<Menu> list = application.getAllChildrenMenu();
		return new MenuAssembler().toDtos(list);
	}

	public boolean addMenuRelationShip(MenuDTO parent, MenuDTO child) {
		boolean isSuccess = false;
		MenuAssembler mapper = new MenuAssembler();
		Menu p = mapper.toEntity(parent);
		Menu c = mapper.toEntity(child);
		Taccountability<Menu, Menu> t = tApplication.addChildToParent(p, c);
		isSuccess = t.getId() != null && t.getId() != 0;
		// TODO Auto-generated method stub
		return isSuccess;
	}

	public boolean removeMenuRelationByParent(MenuDTO parent) {
		boolean isSuccess = false;
		Menu p = new MenuAssembler().toEntity(parent);
		isSuccess = tApplication.delChildRelation(p);
		return isSuccess;
	}

	public boolean removeMenuRelation(MenuDTO parent, MenuDTO child) {
		boolean isSuccess = false;
		MenuAssembler mapper = new MenuAssembler();
		Menu p = mapper.toEntity(parent);
		Menu c = mapper.toEntity(child);
		isSuccess = tApplication.delChildRelationByParent(p, c);
		return isSuccess;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MenuDTO> pageQuery(int currentPage, int pageSize) {
		Page<Menu> pages = application.pageQuery(currentPage, pageSize);
		List<MenuDTO> dtos = new MenuAssembler().toDtos(pages.getData());
		return new Page<MenuDTO>(pages.getStart(), pages.getResultCount(), pageSize, dtos);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MenuDTO> getAllChildNoParent() {
		List<Menu> list = tApplication.getAllChildNoParent();
		List<MenuDTO> dtos = new MenuAssembler().toDtos(list);
		return dtos;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MenuDTO getParentMenuByChild(MenuDTO child) {
		MenuAssembler mapper = new MenuAssembler();
		Menu menu = tApplication.getParentByChild(mapper.toEntity(child));
		return mapper.toDto(menu);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasMenuRelationship(MenuDTO menu) {
		Menu m = new MenuAssembler().toEntity(menu);
		Long count = tApplication.getMenuRelationshipCount(m);
		return count != null && count > 0;
	}

	public Long getCount() {
		return application.getCount();
	}

}
