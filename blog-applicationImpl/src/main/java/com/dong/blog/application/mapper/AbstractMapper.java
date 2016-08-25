package com.dong.blog.application.mapper;

import java.util.ArrayList;
import java.util.Collection;

import com.dong.blog.application.Mapper;

public abstract class AbstractMapper<K, V> implements Mapper<K, V> {

	public Collection<V> transformBeanDatas(Collection<K> sources) throws Exception {
		return transformBeanDatas(new ArrayList<V>(), sources);
	}

	public Collection<V> transformBeanDatas(Collection<V> targets,
			Collection<K> sources) throws Exception {
		for (K k : sources) {
			targets.add(transformBeanData(k));
		}
		return targets;
	}

	public Collection<K> transformEntityDatas(Collection<V> sources) throws Exception {
		return transformEntityDatas(new ArrayList<K>(), sources);
	}
	
	public Collection<K> transformEntityDatas(Collection<K> targets, Collection<V> sources) throws Exception {
		for (V v : sources) {
			targets.add(transformEntityData(v));
		}
		return targets;
	}
	
	public abstract K transformEntityData(K target, V source) throws Exception;

}
