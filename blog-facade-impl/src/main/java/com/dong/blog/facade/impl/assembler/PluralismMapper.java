package com.dong.blog.facade.impl.assembler;

import com.dong.blog.core.domain.PlurType;
import com.dong.blog.core.domain.Pluralism;
import com.dong.blog.facade.dto.PlurTypeDTO;
import com.dong.blog.facade.dto.PluralismDTO;
import com.dong.blog.facade.impl.exception.AssemblerException;
import com.dong.blog.util.BeanCopierUtil;

public class PluralismMapper extends AbstractMapper<Pluralism, PluralismDTO> {

	public PluralismDTO transformBeanData(Pluralism source)
			throws AssemblerException {
		PluralismDTO dto = new PluralismDTO();
		BeanCopierUtil.copyProperties(source, dto);
		PlurTypeDTO typeDTO = new PlurTypeDTO();
		BeanCopierUtil.copyProperties(source.getPlurType(), typeDTO);
		dto.setPlurTypeDTO(typeDTO);
		return dto;
	}

	public Pluralism transformEntityData(PluralismDTO source)
			throws AssemblerException {
		Pluralism pluralism = new Pluralism();
		BeanCopierUtil.copyProperties(source, pluralism);
		PlurType plurType = new PlurType();
		BeanCopierUtil.copyProperties(source.getPlurTypeDTO(), plurType);
		pluralism.setPlurType(plurType);
		return pluralism;
	}

	@Override
	public Pluralism transformEntityData(Pluralism target, PluralismDTO source)
			throws AssemblerException {
		// TODO Auto-generated method stub
		return null;
	}

}
