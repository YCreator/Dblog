package com.dong.blog.facade.dto;

import java.io.Serializable;
import java.util.Date;

public class PluralismDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String title;
	
	private String description;
	
	private String path;
	
	private String imgUrl;
	
	private Integer pv;
	
	private Integer sort;
	
	private PlurPositionDTO plurPositionDTO;

	private Date releaseDate; // 发布日期
	
	private Boolean display;
	
	private String plurType;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public PlurPositionDTO getPlurPositionDTO() {
		return plurPositionDTO;
	}

	public void setPlurPositionDTO(PlurPositionDTO plurPositionDTO) {
		this.plurPositionDTO = plurPositionDTO;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public String getPlurType() {
		return plurType;
	}

	public void setPlurType(String plurType) {
		this.plurType = plurType;
	}
	
	
	

}
