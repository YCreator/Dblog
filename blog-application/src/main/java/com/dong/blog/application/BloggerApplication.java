package com.dong.blog.application;

import com.dong.blog.core.domain.Blogger;

public interface BloggerApplication extends BaseApplication<Blogger, Long>{
	
	Blogger findByUsername(String username);

}
