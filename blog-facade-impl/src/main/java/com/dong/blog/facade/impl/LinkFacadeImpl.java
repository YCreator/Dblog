package com.dong.blog.facade.impl;

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
import com.dong.blog.facade.impl.assembler.LinkAssembler;
import com.dong.blog.util.BeanCopierUtil;

@Named
@Transactional(rollbackFor=Exception.class)
public class LinkFacadeImpl implements LinkFacade {
	
	@Inject
	LinkApplication application;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LinkDTO get(Long pk) {
		Link link = application.load(pk);
		return new LinkAssembler().toDto(link);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LinkDTO> findAll() {
		List<Link> list = application.findAll();
		return new LinkAssembler().toDtos(list);
	}

	public void save(LinkDTO t) {
		Link link = new LinkAssembler().toEntity(t);
		link = application.save(link);
		t.setId(link.getId());
	}

	public boolean update(LinkDTO t) {
		boolean isSuccess = false;
		Link link = application.get(t.getId());
		try {
			BeanCopierUtil.copyProperties(t, link);
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
		for (Long pk : pks) remove(pk);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<LinkDTO> getPage(int currentPage, int pageSize) {
		Page<Link> pages = application.getPage(currentPage, pageSize);
		List<LinkDTO> list = new LinkAssembler().toDtos(pages.getData());
		return new Page<LinkDTO>(pages.getStart(), pages.getResultCount(), pageSize, list);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Long getTotal() {
		// TODO Auto-generated method stub
		return application.getTotal();
	}

}
