package com.dong.blog.application.impl;

import java.util.List;

import javax.inject.Named;

import org.dayatang.utils.Page;

import com.dong.blog.application.MenuApplication;
import com.dong.blog.core.domain.Menu;

@Named
public class MenuApplicationImpl extends BaseApplicationImpl implements
		MenuApplication {

	public Menu get(Long pk) {
		// TODO Auto-generated method stub
		return Menu.get(Menu.class, pk);
	}

	public Menu load(Long pk) {
		// TODO Auto-generated method stub
		return Menu.load(Menu.class, pk);
	}

	public List<Menu> findAll() {
		// TODO Auto-generated method stub
		return Menu.findAll(Menu.class);
	}

	public Menu save(Menu t) {
		t.save();
		return t;
	}

	public boolean update(Menu t) {
		// TODO Auto-generated method stub
		return false;
	}

	public void remove(Long pk) {
		// TODO Auto-generated method stub
		this.load(pk).remove();
	}

	public void removes(Long[] pks) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	public List<Menu> getAllParentMenu() {
		return this.getQueryChannelService()
				.createJpqlQuery("select m from Menu m where m.isParent=true order by m.createTime asc")
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<Menu> getAllChildrenMenu() {
		return this.getQueryChannelService()
				.createJpqlQuery("select m from Menu m where m.isParent=false order by m.createTime asc")
				.list();
	}

	@SuppressWarnings("unchecked")
	public Page<Menu> pageQuery(int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		return this.getQueryChannelService()
				.createJpqlQuery("select m from Menu m order by m.createTime asc")
				.setPage(currentPage, pageSize).pagedList();
	}

	public Long getCount() {
		return (Long) this.getQueryChannelService().createJpqlQuery("select count(*) from Menu m").singleResult();
	}

}
