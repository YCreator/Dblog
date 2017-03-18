package com.dong.blog.application.impl;

import java.util.List;

import javax.inject.Named;

import com.dong.blog.application.TaccountabilityApplication;
import com.dong.blog.core.domain.Menu;
import com.dong.blog.core.domain.MenuManagement;
import com.dong.blog.core.domain.Taccountability;

@Named
public class TaccountabilityApplicationImpl extends BaseApplicationImpl implements TaccountabilityApplication {

	public Taccountability<Menu, Menu> get(Long pk) {
		// TODO Auto-generated method stub
		return null;
	}

	public Taccountability<Menu, Menu> load(Long pk) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Taccountability<Menu, Menu>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Taccountability<Menu, Menu> save(Taccountability<Menu, Menu> t) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean update(Taccountability<Menu, Menu> t) {
		// TODO Auto-generated method stub
		return false;
	}

	public void remove(Long pk) {
		// TODO Auto-generated method stub
		
	}

	public void removes(Long[] pks) {
		// TODO Auto-generated method stub
		
	}

	public Taccountability<Menu, Menu> addChildToParent(Menu parent, Menu child) {
		Taccountability<Menu, Menu> t = new MenuManagement(parent, child);
		t.save();
		return t;
	}

	public boolean delChildRelation(Menu parent) {
		List<MenuManagement> list = MenuManagement.getByCommissioner(parent);
		for(MenuManagement m : list) {
			m.remove();
		}
		return true;
	}

	public boolean delChildRelationByParent(Menu parent, Menu child) {
		MenuManagement m = MenuManagement.getByCommissionerAndResponsible(MenuManagement.class, parent, child);
		if (m != null) {
			m.remove();
			return true;
		}
		return false;
	}

	public List<Menu> getMenuByParent(Menu parent) {
		return MenuManagement.getChildrenOfMenu(parent);
	}

	/**
	 * 为什么使用父类Taccountability而不是用子类MenuManagerment呢？
	 * 因为使用MenuManagerment会报错
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> getAllChildNoParent() {
		String jpql = "select m from Menu m where m.isParent=false and m not in(select t.responsible from Taccountability t)";
		List<Menu> menus = this.getQueryChannelService().createJpqlQuery(jpql).list();
		return menus;
	}

	public Menu getParentByChild(Menu child) {
		return MenuManagement.getParentOfMenu(child);
	}

	public Long getMenuRelationshipCount(Menu menu) {
		String jpql = "select count(*) from Taccountability t where t.responsible = :menu or t.commissioner = :menu";
		Long count = (Long)this.getQueryChannelService().createJpqlQuery(jpql).addParameter("menu", menu).singleResult();
		System.out.println("关系数量：===================》"+count);
		return count;
	}

}
