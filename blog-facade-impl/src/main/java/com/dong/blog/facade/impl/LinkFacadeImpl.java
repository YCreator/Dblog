package com.dong.blog.facade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.LinkApplication;
import com.dong.blog.core.domain.Link;
import com.dong.blog.facade.LinkFacade;
import com.dong.blog.facade.dto.LinkDTO;
import com.dong.blog.facade.impl.assembler.LinkMapper;

@Named
@Transactional(rollbackFor=Exception.class)
public class LinkFacadeImpl implements LinkFacade {
	
	@Inject
	LinkApplication application;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LinkDTO get(Long pk) {
		LinkDTO dto = null;
		try {
			dto = new LinkMapper().transformBeanData(application.load(pk));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LinkDTO> findAll() {
		List<LinkDTO> list = null;
		try {
			list = (List<LinkDTO>) new LinkMapper().transformBeanDatas(application.findAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public LinkDTO save(LinkDTO t) {
		try {
			Link link = new LinkMapper().transformEntityData(t);
			link = application.save(link);
			t.setId(link.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return t;
	}

	public boolean update(LinkDTO t) {
		boolean isSuccess = false;
		Link link = application.get(t.getId());
		try {
			new LinkMapper().transformEntityData(link, t);
			isSuccess = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isSuccess;
	}

	public void remove(Long pk) {
		application.remove(pk);
	}

	public void removes(Long[] pks) {
		for (Long pk : pks) {
			remove(pk);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<LinkDTO> getPage(int currentPage, int pageSize) {
		Page<Link> pages = application.getPage(currentPage, pageSize);
		List<LinkDTO> list = new ArrayList<LinkDTO>();
		try {
			list = (List<LinkDTO>) new LinkMapper().transformBeanDatas(pages.getData());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Page<LinkDTO>(pages.getStart(), pages.getResultCount(), pageSize, list);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Long getTotal() {
		// TODO Auto-generated method stub
		return application.getTotal();
	}

}
