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
import com.dong.blog.facade.impl.assembler.MenuMapper;
import com.dong.blog.facade.impl.exception.AssemblerException;

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
		MenuDTO dto = null;
		try {
			Menu menu = application.load(pk);
			dto = new MenuMapper().transformBeanData(menu);
		} catch (AssemblerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
	}

	public List<MenuDTO> findAll() {
		List<MenuDTO> list = null;
		try {
			list = (List<MenuDTO>) new MenuMapper().transformBeanDatas(application.findAll());
		} catch (AssemblerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public MenuDTO save(MenuDTO t) {
		try {
			Menu menu = new MenuMapper().transformEntityData(t);
			menu.setType(MenuType.getType(t.getMenuType()));
			application.save(menu);
			t.setId(menu.getId());
		} catch (AssemblerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	public boolean update(MenuDTO t) {
		boolean isSuccess = false;
		Menu menu = application.get(t.getId());
		try {
			new MenuMapper().transformEntityData(menu, t);
			isSuccess = true;
		} catch (AssemblerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuccess;
	}

	public void remove(Long pk) {
		application.remove(pk);
	}

	public void removes(Long[] pks) {
		for(Long id : pks) {
			application.remove(id);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MenuDTO> getMenuTree() {
		List<MenuDTO> list = new ArrayList<MenuDTO>();
		List<Menu> menus = application.getAllParentMenu();
		for (Menu menu : menus) {
			List<Menu> children = tApplication.getMenuByParent(menu);
			if (children.size() > 0) {
				try {
					MenuMapper mapper = new MenuMapper();
					List<MenuDTO> menuDTOs = (List<MenuDTO>) mapper.transformBeanDatas(children);
					Set<MenuDTO> set = new LinkedHashSet<MenuDTO>(menuDTOs);
					MenuDTO dto = mapper.transformBeanData(menu);
					dto.setChildren(set);
					list.add(dto);
				} catch (AssemblerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MenuDTO> getParentMenus() {
		List<MenuDTO> list = null;
		try {
			list = (List<MenuDTO>) new MenuMapper().transformBeanDatas(application.getAllParentMenu());
		} catch (AssemblerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MenuDTO> getChildMenus() {
		List<MenuDTO> list = null;
		try {
			list = (List<MenuDTO>) new MenuMapper().transformBeanDatas(application.getAllChildrenMenu());
		} catch (AssemblerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public boolean addMenuRelationShip(MenuDTO parent, MenuDTO child) {
		boolean isSuccess = false;
		MenuMapper mapper = new MenuMapper();
		try {
			Menu p = mapper.transformEntityData(parent);
			Menu c = mapper.transformEntityData(child);
			Taccountability<Menu, Menu> t = tApplication.addChildToParent(p, c);
			isSuccess = t.getId() != null && t.getId() != 0;
		} catch (AssemblerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return isSuccess;
	}

	public boolean removeMenuRelationByParent(MenuDTO parent) {
		boolean isSuccess = false;
		try {
			Menu p = new MenuMapper().transformEntityData(parent);
			isSuccess = tApplication.delChildRelation(p);
		} catch (AssemblerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuccess;
	}

	public boolean removeMenuRelation(MenuDTO parent, MenuDTO child) {
		boolean isSuccess = false;
		MenuMapper mapper = new MenuMapper();
		try {
			Menu p = mapper.transformEntityData(parent);
			Menu c = mapper.transformEntityData(child);
			isSuccess = tApplication.delChildRelationByParent(p, c);
		} catch (AssemblerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuccess;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<MenuDTO> pageQuery(int currentPage, int pageSize) {
		Page<Menu> pages = application.pageQuery(currentPage, pageSize);
		List<MenuDTO> dtos = new ArrayList<MenuDTO>();
		try {
			dtos = (List<MenuDTO>) new MenuMapper().transformBeanDatas(pages.getData());
		} catch (AssemblerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Page<MenuDTO>(pages.getStart(), pages.getResultCount(), pageSize, dtos);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<MenuDTO> getAllChildNoParent() {
		List<Menu> list = tApplication.getAllChildNoParent();
		List<MenuDTO> dtos = new ArrayList<MenuDTO>();
		try {
			dtos = (List<MenuDTO>) new MenuMapper().transformBeanDatas(list);
		} catch (AssemblerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dtos;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public MenuDTO getParentMenuByChild(MenuDTO child) {
		MenuMapper mapper = new MenuMapper();
		MenuDTO dto = new MenuDTO();
		try {
			Menu menu = tApplication.getParentByChild(mapper.transformEntityData(child));
			dto = mapper.transformBeanData(menu);
		} catch (AssemblerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasMenuRelationship(MenuDTO menu) {
		try {
			Menu m = new MenuMapper().transformEntityData(menu);
			Long count = tApplication.getMenuRelationshipCount(m);
			return count != null && count > 0;
		} catch (AssemblerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Long getCount() {
		return application.getCount();
	}

}
