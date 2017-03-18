package com.dong.blog.application;

import java.util.List;

import org.dayatang.utils.Page;

import com.dong.blog.core.domain.Menu;

public interface MenuApplication extends BaseApplication<Menu, Long> {

	public List<Menu> getAllParentMenu();
	
	public List<Menu> getAllChildrenMenu();
	
	public Page<Menu> pageQuery(int currentPage, int pageSize);
	
	public Long getCount();
	
}
