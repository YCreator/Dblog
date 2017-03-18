package com.dong.blog.facade;

import java.util.List;

import org.dayatang.utils.Page;

import com.dong.blog.facade.dto.MenuDTO;

public interface MenuFacade extends BaseFacade<MenuDTO, Long> {
	
	public List<MenuDTO> getMenuTree();
	
	public List<MenuDTO> getParentMenus();
	
	public List<MenuDTO> getChildMenus();
	
	public boolean addMenuRelationShip(MenuDTO parent, MenuDTO child);
	
	public boolean removeMenuRelationByParent(MenuDTO parent);
	
	public boolean removeMenuRelation(MenuDTO parent, MenuDTO child);
	
	public Page<MenuDTO> pageQuery(int currentPage, int pageSize);
	
	public List<MenuDTO> getAllChildNoParent();
	
	public MenuDTO getParentMenuByChild(MenuDTO child);
	
	public boolean hasMenuRelationship(MenuDTO menu);
	
	public Long getCount();
	
}
