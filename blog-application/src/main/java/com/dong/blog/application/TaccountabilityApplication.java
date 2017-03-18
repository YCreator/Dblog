package com.dong.blog.application;

import java.util.List;

import com.dong.blog.core.domain.Menu;
import com.dong.blog.core.domain.Taccountability;

public interface TaccountabilityApplication extends BaseApplication<Taccountability<Menu, Menu>, Long> {
	
	/**
	 * 父菜单与子菜单模块建立关系
	 * @param parent
	 * @param child
	 * @return
	 */
	public Taccountability<Menu, Menu> addChildToParent(Menu parent, Menu child);
	
	/**
	 * 根据父菜单批量解除与子菜单的对应关系
	 * @param parent
	 * @return
	 */
	public boolean delChildRelation(Menu parent);
	
	/**
	 * 解除某个父菜单和子菜单的关系
	 * @param child
	 * @return
	 */
	public boolean delChildRelationByParent(Menu parent, Menu child);

	
	/**
	 * 根据父菜单获取子菜单
	 * @param parent
	 * @return
	 */
	public List<Menu> getMenuByParent(Menu parent);
	
	/**
	 * 根据某个子菜单获取父菜单
	 * @param parent
	 * @return
	 */
	public Menu getParentByChild(Menu child);
	
	/**
	 * 获取没有建立关系的子菜单
	 * @return
	 */
	public List<Menu> getAllChildNoParent();
	
	/**
	 * 获取某个菜单存在关系的数量
	 * @param menu
	 * @return
	 */
	public Long getMenuRelationshipCount(Menu menu);
}
