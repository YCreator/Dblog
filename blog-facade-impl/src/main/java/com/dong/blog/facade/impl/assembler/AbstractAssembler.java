package com.dong.blog.facade.impl.assembler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractAssembler<K extends Serializable, V extends Serializable>
		implements Assembler<K, V> {

	public List<V> toDtos(Collection<K> sources) {
		return (List<V>) toDtos(new ArrayList<V>(), sources);
	}

	public Collection<V> toDtos(Collection<V> targets, Collection<K> sources) {
		for (K k : sources)
			targets.add(toDto(k));
		return targets;
	}

	public List<K> toEntitys(Collection<V> sources) {
		return (List<K>) toEntitys(new ArrayList<K>(), sources);
	}

	public Collection<K> toEntitys(Collection<K> targets, Collection<V> sources) {
		for (V v : sources)
			targets.add(toEntity(v));
		return targets;
	}

}
