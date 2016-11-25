package com.dong.blog.application.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.LinkApplication;
import com.dong.blog.application.dto.LinkDTO;
import com.dong.blog.application.mapper.LinkMapper;
import com.dong.blog.core.domain.Link;

@Named
@Transactional(rollbackFor=Exception.class)
public class LinkApplicationImpl extends BaseApplicationImpl implements LinkApplication {
	
	@Inject
	private LinkMapper linkMapper;
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LinkDTO get(Long pk) {
		Link link = Link.load(Link.class, pk);
		LinkDTO dto;
		try {
			dto = linkMapper.transformBeanData(link);
		} catch (Exception e) {
			dto = new LinkDTO();
			e.printStackTrace();
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LinkDTO> findAll() {
		List<Link> list = Link.findAll(Link.class);
		List<LinkDTO> dtos;
		try {
			dtos = (List<LinkDTO>) linkMapper.transformBeanDatas(list);
		} catch (Exception e) {
			dtos = new ArrayList<LinkDTO>();
			e.printStackTrace();
		}
		return dtos;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public LinkDTO save(LinkDTO t) {
		try {
			Link link = linkMapper.transformEntityData(t);
			link.save();
			this.getLog().debug(link.getId()+"_");
			t.setId(link.getId());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public boolean update(LinkDTO t) {
		Link link = Link.get(Link.class, t.getId());
		boolean isSuccess;
		try {
			linkMapper.transformEntityData(link, t);
			isSuccess = true;
		} catch(Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}

	public void remove(Long pk) {
		removes(new Long[]{pk});
		
	}

	public void removes(Long[] pks) {
		for (int i = 0; i < pks.length; i ++) {
			Link link = Link.load(Link.class, pks[i]);
			link.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<LinkDTO> getPage(int currentPage, int pageSize) {
		@SuppressWarnings("unchecked")
		Page<Link> page = this.getQueryChannelService()
				.createJpqlQuery("select _link from Link _link").setPage(currentPage, pageSize).pagedList();
		List<Link> list = page.getData();
		List<LinkDTO> dtos;
		try {
			dtos = (List<LinkDTO>) linkMapper.transformBeanDatas(list);
		} catch (Exception e) {
			dtos = new ArrayList<LinkDTO>();
			e.printStackTrace();
		}
		return new Page<LinkDTO>(page.getStart(), page.getResultCount(), pageSize, dtos);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Long getTotal() {
		return (Long)this.getQueryChannelService().createJpqlQuery("select count(*) from Link _link").singleResult();
	}

}
