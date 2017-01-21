package com.dong.blog.facade;

import com.dong.blog.facade.dto.BloggerDTO;

public interface BloggerFacade extends BaseFacade<BloggerDTO, Long>{
	
	BloggerDTO findByUsername(String username);
	
	BloggerDTO getBlogger(); //获取管理员信息

}
