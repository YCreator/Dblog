package com.dong.blog.application;

import java.math.BigInteger;
import java.util.Map;

import org.dayatang.utils.Page;
import com.dong.blog.core.domain.Blog;

public interface BlogApplication extends BaseApplication<Blog, Long> {
	
	BigInteger getTotal(Map<String, Object> params); //有条件的查询记录总数
	
	Page<Blog> pageQuery (Map<String, Object> map, int currentPage, int pageSize);
	
	Blog getLastBlog(Long id); //查找上一篇博客
	
	Blog getNextBlog(Long id); //查找下一篇博客
	
	boolean updateClickHit(Long id, Integer clickHit); //修改点击数
	
	Page<Blog> pageQueryByCate(Long cateId, int currentPage, int pageSize);	//根据大分类查询博客
	
	boolean updateReplyHit(Long id, Integer replyHit); // 修改评论数
	
	Blog save(Blog t, Long typeId);
		
}
