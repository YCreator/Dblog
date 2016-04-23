package com.dong.blog.application;

import com.dong.blog.application.dto.BloggerDTO;

public interface BloggerApplication extends BaseApplication<BloggerDTO, Long>{
	
	BloggerDTO findByUsername(String username);
	
	BloggerDTO getBlogger(); //获取管理员信息

}
