package com.dong.blog.core.test;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.dayatang.domain.AbstractEntity;

import com.dong.blog.core.domain.Link;

@Entity
@Table(name="t_test")
public class Test extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2390878387198771458L;

	@Column(nullable=true)
	private String name;
	
	@Column(name="num", nullable=false)
	private String num;
	
	@Column(nullable=false)
	private Integer sort;
	
	@Column
	private String test;
	
	@JoinColumn(name="linkId", referencedColumnName="id")
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},fetch=FetchType.LAZY)
	private Link link;
	
	
	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	

}
