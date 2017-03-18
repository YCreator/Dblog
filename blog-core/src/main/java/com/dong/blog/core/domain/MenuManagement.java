package com.dong.blog.core.domain;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue("MenuManagement")
@NamedQueries({
		@NamedQuery(name = "getParentOfMenu", query = "select o.commissioner from MenuManagement o where o.responsible = :menu"),
		@NamedQuery(name = "getChildrenOfMenu", query = "select o.responsible from MenuManagement o where o.commissioner = :menu order by o.responsible.createTime asc"),
		@NamedQuery(name = "findByResponsible", query = "select o from MenuManagement o where o.responsible = :menu"),
		@NamedQuery(name = "findByCommissioner", query = "select o from MenuManagement o where o.commissioner = :menu") })
public class MenuManagement extends Taccountability<Menu, Menu> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MenuManagement() {

	}

	public MenuManagement(Menu parent, Menu child) {
		super(parent, child);
	}

	/**
	 * 获取父菜单
	 * 
	 * @param menu
	 * @return
	 */
	public static Menu getParentOfMenu(Menu menu) {
		List<Menu> menus = getRepository().createNamedQuery("getParentOfMenu")
				.addParameter("menu", menu).list();
		return menus.isEmpty() ? null : menus.get(0);
	}

	/**
	 * 获取某父菜单下的所有功能模块
	 * 
	 * @param menu
	 * @return
	 */
	public static List<Menu> getChildrenOfMenu(Menu menu) {
		return getRepository().createNamedQuery("getChildrenOfMenu")
				.addParameter("menu", menu).list();
	}

	/**
	 * 获得某个菜单作为下级菜单的菜单责任关系
	 * 
	 * @param menu
	 * @return
	 */
	public static MenuManagement getByResponsible(Menu menu) {
		List<MenuManagement> list = getRepository()
				.createNamedQuery("findByResponsible")
				.addParameter("menu", menu).list();
		return list.isEmpty() ? null : list.get(0);
	}
	
	/**
	 * 获取某个菜单作为父菜单的所有菜单责任关系
	 * @param menu
	 * @return
	 */
	public static List<MenuManagement> getByCommissioner(Menu menu) {
		return getRepository().createNamedQuery("findByCommissioner").addParameter("menu", menu).list();
	}

}
