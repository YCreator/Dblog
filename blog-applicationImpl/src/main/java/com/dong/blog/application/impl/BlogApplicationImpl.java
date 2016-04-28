package com.dong.blog.application.impl;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.BlogApplication;
import com.dong.blog.application.dto.BlogDTO;
import com.dong.blog.application.dto.BlogTypeDTO;
import com.dong.blog.core.domain.Blog;
import com.dong.blog.core.domain.BlogType;
import com.dong.blog.util.BeanCopierUtil;

@Named
@Transactional(rollbackFor=Exception.class)
public class BlogApplicationImpl extends BaseApplicationImpl implements BlogApplication {
	
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogDTO get(Long pk) {
		Blog blog = Blog.load(Blog.class, pk);
		BlogDTO blogDTO = transformBeanData(blog);
		blogDTO.setId((java.lang.Long)blog.getId());
		return blogDTO;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogDTO> findAll() {
		List<Blog> all = Blog.findAll(Blog.class);	
		return transformBeanDatas(all);
	}

	public BlogDTO save(BlogDTO t) {
		Blog blog = new Blog();
		try {
			BeanCopierUtil.copyProperties(t, blog);
		} catch(Exception e) {
			e.printStackTrace();
		}
		blog.save();
		t.setId(blog.getId());
		return t;
	}

	public boolean update(BlogDTO t) {
		Blog blog = Blog.get(Blog.class, t.getId());
		boolean isSuccess;
		try {
			BeanCopierUtil.copyProperties(t, blog);
			BlogType blogType = new BlogType();
			try {
				BeanCopierUtil.copyProperties(t.getBlogTypeDTO(), blogType);
			} catch(Exception e) {
				e.printStackTrace();
			}
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
		for (int i = 0; i < pks.length; i++) {
			Blog blog = Blog.load(Blog.class, pks[i]);
			blog.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BlogDTO> pageQuery(BlogDTO dto, int currentPage, int pageSize) {
		StringBuilder jpql = new StringBuilder("select _blog from Blog _blog");
		if (dto.getBlogTypeDTO() != null) {
			jpql.append(" where _blog.blogType.id = ").append(dto.getBlogTypeDTO().getId());
		}
		jpql.append(" order by _blog.releaseDate desc");
		@SuppressWarnings("unchecked")
		Page<Blog> pages = this.getQueryChannelService().createJpqlQuery(jpql.toString()).setPage(currentPage, pageSize).pagedList();
		List<Blog> blogs = pages.getData();
		List<BlogDTO> list = this.transformBeanDatas(blogs);
		return new Page<BlogDTO>(pages.getStart(), pages.getResultCount(), pageSize, list);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BlogDTO> pageQuery(Map<String, Object> map, int currentPage, int pageSize) {
		StringBuilder jpql = new StringBuilder("select _blog from Blog _blog where 1=1");
		List<Object> conditionVals = new ArrayList<Object>();
		if (map.get("title") != null) {
			jpql.append(" and _blog.title like ?");
			conditionVals.add(MessageFormat.format("%{0}%", map.get("title")));
		}
		if (map.get("orderType") != null) {
			if (map.get("orderType").toString().equals("clickHit")) {
				jpql.append(" order by _blog.clickHit desc");
			} else if (map.get("orderType").toString().equals("releaseDate")) {
				jpql.append(" order by _blog.releaseDate desc");
			}
		}
		@SuppressWarnings("unchecked")
		Page<Blog> pages = this.getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
		List<Blog> blogs = pages.getData();
		List<BlogDTO> list = this.transformBeanDatas(blogs);
		return new Page<BlogDTO>(pages.getStart(), pages.getResultCount(), pageSize, list);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BigInteger getTotal(Map<String, Object> params) {
		String sql = "SELECT COUNT(*) FROM t_blog";
		if (params.containsKey("typeId")) { //根据博客类型查询总数
			sql = sql + " WHERE typeId="+params.get("typeId");
		} else if (params.containsKey("title") && params.get("title") != null) {
			sql = sql + " WHERE title LIKE '" + params.get("title") +"'"; //根据关键字查询总数
		} else if (params.containsKey("releaseDateStr")) {
			sql = sql + " WHERE DATE_FORMAT(releaseDate, '%Y年%m月')=" + params.get("releaseDateStr"); //根据日期查询总数
		}
		return (BigInteger) this.getQueryChannelService().createSqlQuery(sql).singleResult();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogDTO getLastBlog(Long id) {
		String jpql = "select _blog.id, _blog.title from Blog _blog where _blog.id < "+id+" order by _blog.id desc";
		Object[] blog =  (Object[]) this.getQueryChannelService().createJpqlQuery(jpql).setPage(0, 1).singleResult();
		BlogDTO dto = new BlogDTO();
		if (blog != null) {
			dto.setId((Long)blog[0]);
			dto.setTitle(blog[1].toString());
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogDTO getNextBlog(Long id) {
		String jpql = "select _blog.id, _blog.title from Blog _blog where _blog.id>"+id+" order by _blog.id asc";
		Object[] blog =  (Object[]) this.getQueryChannelService().createJpqlQuery(jpql).setPage(0, 1).singleResult();
		BlogDTO dto = new BlogDTO();
		if (blog != null) {
			dto.setId((Long)blog[0]);
			dto.setTitle(blog[1].toString());
		}
		return dto;
	}

	public boolean updateBlog(BlogDTO blogDTO) {
		Blog blog = Blog.get(Blog.class, blogDTO.getId());
		boolean isSuccess;
		try {
		//	BlogType type = new BlogType();
		//	BeanUtils.copyProperties(type, blogDTO.getBlogTypeDTO());
			blog.setBlogType(BlogType.get(BlogType.class, blogDTO.getBlogTypeDTO().getId()));
			blog.setTitle(blogDTO.getTitle());
			blog.setContent(blogDTO.getContent());
			blog.setKeyWord(blogDTO.getKeyWord());
			blog.setPicPath(blogDTO.getPicPath());
			blog.setSummary(blogDTO.getSummary());
			isSuccess = true;
		} catch(Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogDTO> getBlogsByProperty(String propertyName, Object propertyValue) {
		List<Blog> list = Blog.findByProperty(Blog.class, propertyName, propertyValue);
		return transformBeanDatas(list);
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogDTO> getBlogsByProperties(Map<String, Object> properties) {
		List<Blog> list = Blog.findByProperties(Blog.class, properties);
		return transformBeanDatas(list);
	}
	
	/**
	 * 转换对象数组
	 * @param source
	 * @return
	 */
	private List<BlogDTO> transformBeanDatas(List<Blog> source) {
		List<BlogDTO> list = new ArrayList<BlogDTO>();
		for (Blog blog : source) {
			list.add(transformBeanData(blog));
		}
		return list;
	}
	
	/**
	 * 转换对象
	 * @param source
	 * @return
	 */
	private BlogDTO transformBeanData(Blog source) {
		BlogDTO blogDTO = new BlogDTO();
		try {
			BeanCopierUtil.copyProperties(source, blogDTO);
			BlogTypeDTO blogTypeDTO = new BlogTypeDTO();
			try {
				BeanCopierUtil.copyProperties(source.getBlogType(), blogTypeDTO);
			} catch(Exception e) {
				e.printStackTrace();
			}
			blogDTO.setBlogTypeDTO(blogTypeDTO);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return blogDTO;
	}
}
