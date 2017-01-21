package com.dong.blog.facade.impl.assembler;

import java.util.HashSet;
import java.util.Set;

import com.dong.blog.core.domain.BlogType;
import com.dong.blog.core.domain.Category;
import com.dong.blog.facade.dto.BlogTypeDTO;
import com.dong.blog.facade.dto.CategoryDTO;
import com.dong.blog.facade.impl.exception.AssemblerException;
import com.dong.blog.util.BeanCopierUtil;

public class CategoryMapper extends AbstractMapper<Category, CategoryDTO> {

	public CategoryDTO transformBeanData(Category source) throws AssemblerException {
		CategoryDTO dto = new CategoryDTO();
		BeanCopierUtil.copyProperties(source, dto);
		Set<BlogType> sets = source.getBlogTypes();
		for (BlogType blogType : sets) {
			BlogTypeDTO blogTypeDTO = new BlogTypeDTO();
			BeanCopierUtil.copyProperties(blogType, blogTypeDTO);
			dto.getList().add(blogTypeDTO);
		}
		return dto;
	}

	public Category transformEntityData(CategoryDTO source) throws AssemblerException {
		Category category = new Category();
		BeanCopierUtil.copyProperties(source, category);
		Set<BlogType> set = new HashSet<BlogType>();
		for (BlogTypeDTO dto : source.getList()) {
			set.add(BlogType.get(BlogType.class, dto.getId()));
		}
		category.setBlogTypes(set);
		return category;
	}

	@Override
	public Category transformEntityData(Category target, CategoryDTO source)
			throws AssemblerException {
		target.setCategoryName(source.getCategoryName());
		target.setSort(source.getSort());
		Set<BlogType> set = new HashSet<BlogType>();
		for (BlogTypeDTO dto : source.getList()) {
			set.add(BlogType.get(BlogType.class, dto.getId()));
		}
		target.setBlogTypes(set);
		return target;
	}

}
