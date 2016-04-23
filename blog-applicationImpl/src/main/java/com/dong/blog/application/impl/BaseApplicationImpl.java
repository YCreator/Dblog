package com.dong.blog.application.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;

public class BaseApplicationImpl{

	private QueryChannelService queryChannel;
	
	public QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
		}
		return queryChannel;
	}

}
