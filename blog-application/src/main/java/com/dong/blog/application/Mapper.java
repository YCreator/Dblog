package com.dong.blog.application;

import java.util.Collection;

/**
 * 
 * @author Administrator
 *
 * @param <K>	entity
 * @param <V>	dto
 */
public interface Mapper<K, V> {
	
	/**
	 * bean -> dto
	 * @param source
	 * @return
	 */
	V transformBeanData(K source) throws Exception;
	
	/**
	 * bean数组 -> dto数组
	 * @param sources
	 * @return
	 */
	Collection<V> transformBeanDatas(Collection<K> sources) throws Exception;
	
	/**
	 * dto -> bean
	 * @param source
	 * @return
	 */
	K transformEntityData(V source) throws Exception;
	
	/**
	 * dto数组 -> bean数组
	 * @param sources
	 * @return
	 */
	Collection<K> transformEntityDatas(Collection<V> sources) throws Exception;

}
