package com.dong.blog.application.mapper;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Named;

import com.dong.blog.application.dto.BlogTypeDTO;
import com.dong.blog.application.dto.CategoryDTO;
import com.dong.blog.core.domain.BlogType;
import com.dong.blog.core.domain.Category;
import com.dong.blog.util.BeanCopierUtil;

@Named
public class CategoryMapper extends AbstractMapper<Category, CategoryDTO> {

	public CategoryDTO transformBeanData(Category source) throws Exception {
		CategoryDTO dto = new CategoryDTO();
		try {
			BeanCopierUtil.copyProperties(source, dto);
			Set<BlogType> sets = source.getBlogTypes();
			for (BlogType blogType : sets) {
				BlogTypeDTO blogTypeDTO = new BlogTypeDTO();
				BeanCopierUtil.copyProperties(blogType, blogTypeDTO);
				dto.getList().add(blogTypeDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public Category transformEntityData(CategoryDTO source) throws Exception {
		return transformEntityData(new Category(), source);
	}

	@Override
	public Category transformEntityData(Category target, CategoryDTO source) throws Exception {
		try {
			BeanCopierUtil.copyProperties(source, target);
			Set<BlogType> set = new HashSet<BlogType>();
			for (BlogTypeDTO dto : source.getList()) {
				set.add(BlogType.get(BlogType.class, dto.getId()));
			}
			target.setBlogTypes(set);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return target;
	}

}
