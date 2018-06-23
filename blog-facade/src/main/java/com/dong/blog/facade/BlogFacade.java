package com.dong.blog.facade;

import java.math.BigInteger;
import java.util.Map;

import org.dayatang.utils.Page;

import com.dong.blog.facade.dto.BlogDTO;
import com.dong.blog.facade.dto.BlogTypeDTO;
import com.dong.blog.facade.dto.CategoryDTO;

public interface BlogFacade extends BaseFacade<BlogDTO, Long> {
	
	BigInteger getTotal(Map<String, Object> params); //有条件的查询记录总数
	
	Page<BlogDTO> pageQuery (Map<String, Object> map, int currentPage, int pageSize);
	
	BlogDTO getLastBlog(Long id); //查找上一篇博客
	
	BlogDTO getNextBlog(Long id); //查找下一篇博客
	
	Integer upClickHit(Long id); //递增点击数
	
	Page<BlogDTO> pageQueryByCate(CategoryDTO dto, int currentPage, int pageSize);	//根据大分类查询博客
	
	Integer upReplyHit(Long id); // 递增评论数

	boolean blogInBlogTypeExist(BlogTypeDTO dto);  //某分类下的博客是否存在
}
