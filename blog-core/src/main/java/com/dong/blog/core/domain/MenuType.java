package com.dong.blog.core.domain;

public enum MenuType {

	MENU, CONTANT, DIALOG;

	public String getLabel() {
		switch (this) {
		case MENU:
			return "菜单";
		case CONTANT:
			return "目录";
		case DIALOG:
			return "窗口";
		}
		return this.toString();
	}
	
	public static MenuType getType(String type) {
		switch(type) {
		case "菜单":
			return MenuType.MENU;
		case "目录":
			return MenuType.CONTANT;
		default:
			return MenuType.DIALOG;
		}
	}
	
	public static boolean isParent(String type) {
		return "目录".equals(type);
	}

}
