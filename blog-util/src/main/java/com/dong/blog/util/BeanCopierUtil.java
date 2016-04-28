package com.dong.blog.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.cglib.beans.BeanCopier;

/**
 * 对象属性值拷贝工具类，貌似在各种对象拷贝类如(PropertyUtils，BeanUtils，BeanCopier)当中，cglib的BeanCopier的效率是比较高的
 * 在使用BeanCopier的时候，要注意，拷贝的对象内的属性一定要有对应的getter和setter，否则会抛出异常
 * @author Administrator
 *
 */
public class BeanCopierUtil {
	
	public static Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();
	
	public static void copyProperties(Object source, Object target) {
		String beanKey = generateKey(source.getClass(), target.getClass());
		BeanCopier copier = null;
		if (!beanCopierMap.containsKey(beanKey)) {
			copier = BeanCopier.create(source.getClass(), target.getClass(), false);
			beanCopierMap.put(beanKey, copier);
		} else {
			copier = beanCopierMap.get(beanKey);
		}
		copier.copy(source, target, null);
	}
	
	private static String generateKey(Class<?> class1, Class<?> class2) {
		return class1.toString() + class2.toString();
	}

}
