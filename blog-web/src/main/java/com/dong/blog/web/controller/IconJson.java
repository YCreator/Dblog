package com.dong.blog.web.controller;

import java.io.Serializable;

public class IconJson implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7313471567081748364L;

	private String icon;
	private String iconUrl;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

}
