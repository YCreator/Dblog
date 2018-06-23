package com.dong.blog.facade.impl.assembler;

import com.dong.blog.core.domain.PlurPosition;
import com.dong.blog.core.domain.Pluralism;
import com.dong.blog.facade.dto.PlurPositionDTO;
import com.dong.blog.facade.dto.PluralismDTO;
import com.dong.blog.util.BeanCopierUtil;

public class PluralismAssembler extends
		AbstractAssembler<Pluralism, PluralismDTO> {

	public PluralismDTO toDto(Pluralism source) {
		PluralismDTO dto = new PluralismDTO();
		try {
			BeanCopierUtil.copyProperties(source, dto);
			PlurPositionDTO plurPosition = new PlurPositionDTO();
			BeanCopierUtil.copyProperties(source.getPlurPosition(),
					plurPosition);
			dto.setPlurPositionDTO(plurPosition);
		} catch (Exception e) {

		}
		return dto;
	}

	public Pluralism toEntity(PluralismDTO source) {
		Pluralism pluralism = new Pluralism();
		BeanCopierUtil.copyProperties(source, pluralism);
		PlurPosition plurPosition = new PlurPosition();
		BeanCopierUtil
				.copyProperties(source.getPlurPositionDTO(), plurPosition);
		pluralism.setPlurPosition(plurPosition);
		return pluralism;
	}

}
