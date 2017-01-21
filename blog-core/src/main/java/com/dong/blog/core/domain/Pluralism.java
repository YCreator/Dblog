package com.dong.blog.core.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dong.blog.core.AbstractEntity;

@Entity
@Table(name="t_pluralism")
public class Pluralism extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(length=80)
	private String title;
	@Column(length=200)
	private String description;
	@Column
	private String path;
	@Column(name="img_url", length=100)
	private String imgUrl;
	@Column
	private Integer pv;
	@Column
	private Integer sort;
	@JoinColumn(name = "plur_type_id", referencedColumnName = "id")
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	private PlurType plurType;
	@Column(name = "release_date", nullable = false)
	private Date releaseDate; // 发布日期
	@Column
	private Boolean display;
	
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
	public PlurType getPlurType() {
		return plurType;
	}
	public void setPlurType(PlurType plurType) {
		this.plurType = plurType;
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
	
	
}
