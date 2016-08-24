package com.dong.blog.application;

import java.util.Collection;

public interface Mapper<K, V> {
	
	V transformBeanData(K source);
	
	Collection<V> transformBeanDatas(Collection<K> sources);

}
