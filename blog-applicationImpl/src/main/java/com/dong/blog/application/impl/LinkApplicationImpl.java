package com.dong.blog.application.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.apache.commons.beanutils.BeanUtils;
import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.LinkApplication;
import com.dong.blog.application.dto.LinkDTO;
import com.dong.blog.core.domain.Link;


@Named
@Transactional(rollbackFor=Exception.class)
public class LinkApplicationImpl extends BaseApplicationImpl implements LinkApplication {

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LinkDTO get(Long pk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LinkDTO> findAll() {
		List<Link> list = Link.findAll(Link.class);
		List<LinkDTO> dtos = new ArrayList<LinkDTO>();
		for (Link link : list) {
			LinkDTO dto = new LinkDTO();
			try {
				BeanUtils.copyProperties(dto, link);
			} catch (Exception e) {
				e.printStackTrace();
			}
			dtos.add(dto);
		}
		return dtos;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public LinkDTO save(LinkDTO t) {
		Link link = new Link();
		try {
			BeanUtils.copyProperties(link, t);
		} catch(Exception e) {
			e.printStackTrace();
		}
		link.save();
		t.setId(link.getId());
		return t;
	}

	public boolean update(LinkDTO t) {
		Link link = Link.get(Link.class, t.getId());
		boolean isSuccess;
		try {
			BeanUtils.copyProperties(link, t);
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
		List<LinkDTO> dtos = new ArrayList<LinkDTO>();
		@SuppressWarnings("unchecked")
		Page<Link> page = this.getQueryChannelService()
				.createJpqlQuery("select _link from Link _link").setPage(currentPage, pageSize).pagedList();
		List<Link> list = page.getData();
		for (Link link : list) {
			LinkDTO dto = new LinkDTO();
			try {
				BeanUtils.copyProperties(dto, link);
			} catch(Exception e) {
				e.printStackTrace();
			}
			dtos.add(dto);
		}
		return new Page<LinkDTO>(page.getStart(), page.getResultCount(), pageSize, dtos);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Long getTotal() {
		return (Long)this.getQueryChannelService().createJpqlQuery("select count(*) from Link _link").singleResult();
	}

}
