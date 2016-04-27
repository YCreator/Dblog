package com.dong.blog.application.impl;

import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseApplicationImpl{
	
	private Logger log = LoggerFactory.getLogger(BaseApplicationImpl.class);

	private QueryChannelService queryChannel;
	
	public QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
		}
		return queryChannel;
	}
	
	public Logger getLog() {
		return log;
	}

}
