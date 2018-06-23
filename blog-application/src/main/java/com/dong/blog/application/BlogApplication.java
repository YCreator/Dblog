package com.dong.blog.application;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.dayatang.domain.Entity;
import org.dayatang.utils.Page;

import com.dong.blog.core.domain.Blog;
import com.dong.blog.core.domain.BlogType;

public interface BlogApplication{
	
	<T extends Entity> T getEntity(Class<T> entityClass, Long entityId);
	
	<T extends Entity> T loadEntity(Class<T> clazz, Long entityId);
	
	<T extends Entity> List<T> findAll(Class<T> clazz);
	
	BigInteger getTotal(Map<String, Object> params); //有条件的查询记录总数
	
	Page<Blog> pageQuery (Map<String, Object> map, int currentPage, int pageSize);
	
	Blog getLastBlog(Long id); //查找上一篇博客
	
	Blog getNextBlog(Long id); //查找下一篇博客
	
	void upClickHit(Blog blog); //修改点击数
	
	Page<Blog> pageQueryByCate(Long cateId, int currentPage, int pageSize);	//根据大分类查询博客
	
	void upReplyHit(Blog blog); // 修改评论数
	
	Blog save(Blog t, BlogType blogType);
	
	void remove(Long pk);
		
}
