package com.dong.blog.application;

import java.util.List;

public interface Mapper<K, V> {
	
	V transformBeanData(K source);
	
	List<V> transformBeanDatas(List<K> sources);

}
