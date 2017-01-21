package com.dong.blog.application;

import java.io.Serializable;
import java.util.List;

import com.dong.blog.core.AbstractEntity;

public interface BaseApplication<T extends AbstractEntity, PK extends Serializable> {

	/**
	 * 查找单个数据
	 * 
	 * @param pk
	 * @param clazz
	 * @return
	 */
	T get(PK pk);

	/**
	 * 查找单个数据
	 * 
	 * @param pk
	 * @return
	 */
	T load(PK pk);

	/**
	 * 查找所有数据
	 * 
	 * @param clazz
	 * @return
	 */
	List<T> findAll();

	/**
	 * 保存数据
	 * 
	 * @param t
	 * @return
	 */
	T save(T t);

	/**
	 * 修改数据
	 * 
	 * @param t
	 */
	boolean update(T t);

	/**
	 * 删除单个数据
	 * 
	 * @param pk
	 */
	void remove(PK pk);

	/**
	 * 批量删除多个数据
	 * 
	 * @param pks
	 */
	void removes(PK[] pks);

}
