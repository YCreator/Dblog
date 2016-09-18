package com.dong.blog.application;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.dayatang.utils.Page;

import com.dong.blog.application.dto.BlogDTO;
import com.dong.blog.application.dto.CategoryDTO;

public interface BlogApplication extends BaseApplication<BlogDTO, Long> {
	
	BigInteger getTotal(Map<String, Object> params); //有条件的查询记录总数
	
	Page<BlogDTO> pageQuery (BlogDTO dto, int currentPage, int pageSize);
	
	Page<BlogDTO> pageQuery (Map<String, Object> map, int currentPage, int pageSize);
	
	BlogDTO getLastBlog(Long id); //查找上一篇博客
	
	BlogDTO getNextBlog(Long id); //查找下一篇博客
	
	List<BlogDTO> getBlogsByProperty(String propertyName, Object propertyValue); //根据键值 key==value条件查询
	
	List<BlogDTO> getBlogsByProperties(Map<String, Object> properties);//根据多个键值 key==value条件查询
	
	boolean updateClickHit(Long id, Integer clickHit); //修改点击数
	
	Page<BlogDTO> pageQueryByCate(CategoryDTO dto, int currentPage, int pageSize);	//根据大分类查询博客
	
	boolean updateReplyHit(Long id, Integer replyHit); // 修改评论数
		
}
