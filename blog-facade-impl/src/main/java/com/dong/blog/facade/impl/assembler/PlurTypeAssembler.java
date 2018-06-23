package com.dong.blog.facade.impl.assembler;

import com.dong.blog.core.domain.PlurPosition;
import com.dong.blog.facade.dto.PlurPositionDTO;
import com.dong.blog.util.BeanCopierUtil;

public class PlurTypeAssembler extends
		AbstractAssembler<PlurPosition, PlurPositionDTO> {

	public PlurPositionDTO toDto(PlurPosition source) {
		PlurPositionDTO dto = new PlurPositionDTO();
		try {
			BeanCopierUtil.copyProperties(source, dto);
		} catch (Exception e) {

		}
		return dto;
	}

	public PlurPosition toEntity(PlurPositionDTO source) {
		PlurPosition plurType = new PlurPosition();
		BeanCopierUtil.copyProperties(source, plurType);
		return plurType;
	}

}
