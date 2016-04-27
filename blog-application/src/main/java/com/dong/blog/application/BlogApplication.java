package com.dong.blog.application;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.dayatang.utils.Page;

import com.dong.blog.application.dto.BlogDTO;

public interface BlogApplication extends BaseApplication<BlogDTO, Long> {
	
	BigInteger getTotal(Map<String, Object> params); //有条件的查询记录总数
	
	Page<BlogDTO> pageQuery (BlogDTO dto, int currentPage, int pageSize);
	
	Page<BlogDTO> pageQuery (Map<String, Object> map, int currentPage, int pageSize);
	
	BlogDTO getLastBlog(Long id); //查找上一篇博客
	
	BlogDTO getNextBlog(Long id); //查找下一篇博客
	
	List<BlogDTO> getBlogByTypeId(Long typeId); //根据博客类型获取博客列表
	
	boolean updateBlog(BlogDTO blogDTO); //修改博客
}
