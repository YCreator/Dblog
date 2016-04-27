package com.dong.blog.core.test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.dayatang.domain.AbstractEntity;

@Entity
@Table(name="t_test")
public class Test extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2390878387198771458L;

	@Column(nullable=true)
	private String name;
	
	private String num;
	
	@Column(nullable=false)
	private Integer sort;
	
	@Column
	private String test;
	
	
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

	@Column(name="nums", nullable=false)
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
