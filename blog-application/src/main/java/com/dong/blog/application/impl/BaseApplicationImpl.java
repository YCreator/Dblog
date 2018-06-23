package com.dong.blog.application.impl;

import java.util.List;

import org.dayatang.domain.Entity;
import org.dayatang.domain.EntityRepository;
import org.dayatang.domain.InstanceFactory;
import org.dayatang.querychannel.QueryChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseApplicationImpl{
	
	private Logger log = LoggerFactory.getLogger(BaseApplicationImpl.class);

	private QueryChannelService queryChannel;
	
    private EntityRepository repository;
	
    public <T extends Entity> T getEntity(Class<T> entityClass, Long entityId) {
        return getRepository().get(entityClass, entityId);
    }
    
    public <T extends Entity> T loadEntity(Class<T> clazz, Long entityId) {
        return getRepository().load(clazz, entityId);
    }
    
    public <T extends Entity> List<T> findAll(Class<T> clazz) {
        return getRepository().createCriteriaQuery(clazz).list();
    }
	
	public Logger getLog() {
		return log;
	}
	
	public QueryChannelService getQueryChannelService() {
		if (queryChannel == null) {
			queryChannel = InstanceFactory.getInstance(QueryChannelService.class,"queryChannel");
		}
		return queryChannel;
	}
	
	public EntityRepository getRepository() {
        if (repository == null) {
            repository = InstanceFactory.getInstance(EntityRepository.class);
        }
        return repository;
    }


}
