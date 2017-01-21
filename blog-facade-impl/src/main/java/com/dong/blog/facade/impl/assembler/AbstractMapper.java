package com.dong.blog.facade.impl.assembler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.dong.blog.facade.impl.exception.AssemblerException;

public abstract class AbstractMapper<K extends Serializable, V extends Serializable> implements Mapper<K, V> {

	public Collection<V> transformBeanDatas(Collection<K> sources) throws AssemblerException {
		return transformBeanDatas(new ArrayList<V>(), sources);
	}

	public Collection<V> transformBeanDatas(Collection<V> targets,
			Collection<K> sources) throws AssemblerException {
		for (K k : sources) {
			targets.add(transformBeanData(k));
		}
		return targets;
	}

	public Collection<K> transformEntityDatas(Collection<V> sources) throws AssemblerException {
		return transformEntityDatas(new ArrayList<K>(), sources);
	}
	
	public Collection<K> transformEntityDatas(Collection<K> targets, Collection<V> sources) throws AssemblerException {
		for (V v : sources) {
			targets.add(transformEntityData(v));
		}
		return targets;
	}
	
	public abstract K transformEntityData(K target, V source) throws AssemblerException;

}
