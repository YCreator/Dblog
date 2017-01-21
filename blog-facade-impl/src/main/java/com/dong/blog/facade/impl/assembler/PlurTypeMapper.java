package com.dong.blog.facade.impl.assembler;

import com.dong.blog.core.domain.PlurType;
import com.dong.blog.facade.dto.PlurTypeDTO;
import com.dong.blog.facade.impl.exception.AssemblerException;
import com.dong.blog.util.BeanCopierUtil;

public class PlurTypeMapper extends AbstractMapper<PlurType, PlurTypeDTO> {

	public PlurTypeDTO transformBeanData(PlurType source)
			throws AssemblerException {
		PlurTypeDTO dto = new PlurTypeDTO();
		BeanCopierUtil.copyProperties(source, dto);
		return dto;
	}

	public PlurType transformEntityData(PlurTypeDTO source)
			throws AssemblerException {
		PlurType plurType = new PlurType();
		BeanCopierUtil.copyProperties(source, plurType);
		return plurType;
	}

	@Override
	public PlurType transformEntityData(PlurType target, PlurTypeDTO source)
			throws AssemblerException {
		// TODO Auto-generated method stub
		return null;
	}

}
