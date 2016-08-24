package com.dong.blog.application.mapper;

import java.util.ArrayList;
import java.util.Collection;

import com.dong.blog.application.Mapper;

public abstract class AbstractMapper<K, V> implements Mapper<K, V> {

	public Collection<V> transformBeanDatas(Collection<K> sources) {
		Collection<V> list = new ArrayList<V>();
		for (K k : sources) {
			list.add(transformBeanData(k));
		}
		return list;
	}

	public Collection<V> transformBeanDatas(Collection<V> targets,
			Collection<K> sources) {
		for (K k : sources) {
			targets.add(transformBeanData(k));
		}
		return targets;
	}

}
