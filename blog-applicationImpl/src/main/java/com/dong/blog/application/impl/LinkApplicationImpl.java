package com.dong.blog.application.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.LinkApplication;
import com.dong.blog.application.dto.LinkDTO;
import com.dong.blog.core.domain.Link;
import com.dong.blog.util.BeanCopierUtil;


@Named
@Transactional(rollbackFor=Exception.class)
public class LinkApplicationImpl extends BaseApplicationImpl implements LinkApplication {

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public LinkDTO get(Long pk) {
		Link link = Link.load(Link.class, pk);
		return transformatBeanData(link);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<LinkDTO> findAll() {
		List<Link> list = Link.findAll(Link.class);
		return transformatBeanDatas(list);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public LinkDTO save(LinkDTO t) {
		Link link = new Link();
		try {
			BeanCopierUtil.copyProperties(t, link);
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
			BeanCopierUtil.copyProperties(t, link);
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
		List<LinkDTO> dtos = transformatBeanDatas(list);
		return new Page<LinkDTO>(page.getStart(), page.getResultCount(), pageSize, dtos);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Long getTotal() {
		return (Long)this.getQueryChannelService().createJpqlQuery("select count(*) from Link _link").singleResult();
	}
	
	private List<LinkDTO> transformatBeanDatas(List<Link> source) {
		List<LinkDTO> list = new ArrayList<LinkDTO>();
		for (Link link : source) {
			list.add(transformatBeanData(link));
		}
		return list;
	}
	
	private LinkDTO transformatBeanData(Link source) {
		LinkDTO linkDTO = new LinkDTO();
		try {
			BeanCopierUtil.copyProperties(source, linkDTO);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return linkDTO;
	}

}
