package com.dong.blog.application;

import java.io.Serializable;
import java.util.List;

public interface BaseApplication<T, PK extends Serializable> {
	
	/**
	 * 查找单个数据
	 * @param pk
	 * @return
	 */
	T get(PK pk);
	
	/**
	 * 查找所有数据
	 * @return
	 */
	List<T> findAll();
	
	/**
	 * 保存数据
	 * @param t
	 * @return
	 */
	T save(T t);
	
	/**
	 * 修改数据
	 * @param t
	 */
	boolean update(T t);
	
	/**
	 * 删除单个数据
	 * @param pk
	 */
	void remove(PK pk);
	
	/**
	 * 批量删除多个数据
	 * @param pks
	 */
	void removes(PK[] pks);
	
}
