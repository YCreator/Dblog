package com.dong.blog.facade.impl.assembler;

import java.io.Serializable;
import java.util.Collection;

/**
 * 
 * @author Administrator
 *
 * @param <K>	entity
 * @param <V>	dto
 */
public interface Assembler<K extends Serializable, V extends Serializable> {
	
	/**
	 * bean -> dto
	 * @param source
	 * @return
	 */
	V toDto(K source);
	
	/**
	 * bean数组 -> dto数组
	 * @param sources
	 * @return
	 */
	Collection<V> toDtos(Collection<K> sources);
	
	/**
	 * dto -> bean
	 * @param source
	 * @return
	 */
	K toEntity(V source);
	
	/**
	 * dto数组 -> bean数组
	 * @param sources
	 * @return
	 */
	Collection<K> toEntitys(Collection<V> sources);

}
