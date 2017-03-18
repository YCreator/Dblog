package com.dong.blog.facade.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.facade.TaccountabilityFacade;
import com.dong.blog.facade.dto.TaccountabilityDTO;

@Named
@Transactional(rollbackFor = Exception.class)
public class TaccountabilityFacadeImple implements TaccountabilityFacade {

	public TaccountabilityDTO get(Long pk) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaccountabilityDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public TaccountabilityDTO save(TaccountabilityDTO t) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean update(TaccountabilityDTO t) {
		// TODO Auto-generated method stub
		return false;
	}

	public void remove(Long pk) {
		// TODO Auto-generated method stub
		
	}

	public void removes(Long[] pks) {
		// TODO Auto-generated method stub
		
	}

}
