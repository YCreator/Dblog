package com.dong.blog.facade.impl.assembler;

import java.io.Serializable;
import java.util.Collection;

import com.dong.blog.facade.impl.exception.AssemblerException;

/**
 * 
 * @author Administrator
 *
 * @param <K>	entity
 * @param <V>	dto
 */
public interface Mapper<K extends Serializable, V extends Serializable> {
	
	/**
	 * bean -> dto
	 * @param source
	 * @return
	 */
	V transformBeanData(K source) throws AssemblerException;
	
	/**
	 * bean数组 -> dto数组
	 * @param sources
	 * @return
	 */
	Collection<V> transformBeanDatas(Collection<K> sources) throws AssemblerException;
	
	/**
	 * dto -> bean
	 * @param source
	 * @return
	 */
	K transformEntityData(V source) throws AssemblerException;
	
	/**
	 * dto数组 -> bean数组
	 * @param sources
	 * @return
	 */
	Collection<K> transformEntityDatas(Collection<V> sources) throws AssemblerException;

}
