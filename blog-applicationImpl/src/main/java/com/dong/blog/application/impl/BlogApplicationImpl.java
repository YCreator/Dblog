package com.dong.blog.application.impl;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.dayatang.utils.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dong.blog.application.BlogApplication;
import com.dong.blog.application.dto.BlogDTO;
import com.dong.blog.application.dto.BlogTypeDTO;
import com.dong.blog.application.dto.CategoryDTO;
import com.dong.blog.application.mapper.BlogMapper;
import com.dong.blog.core.domain.Blog;
import com.dong.blog.core.domain.BlogType;
import com.dong.blog.core.domain.Category;
import com.dong.blog.util.BeanCopierUtil;

@Named
@Transactional(rollbackFor = Exception.class)
public class BlogApplicationImpl extends BaseApplicationImpl implements BlogApplication {

	@Inject
	private BlogMapper blogMapper;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogDTO get(Long pk) {
		Blog blog = Blog.load(Blog.class, pk);
		BlogDTO blogDTO;
		try {
			blogDTO = blogMapper.transformBeanData(blog);
		} catch (Exception e) {
			blogDTO = new BlogDTO();
			e.printStackTrace();
		}
		return blogDTO;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogDTO> findAll() {
		List<Blog> all = Blog.findAll(Blog.class);
		List<BlogDTO> list;
		try {
			list = (List<BlogDTO>) blogMapper.transformBeanDatas(all);
		} catch (Exception e) {
			e.printStackTrace();
			list = new ArrayList<BlogDTO>();
		}
		return list;
	}

	public BlogDTO save(BlogDTO t) {
		try {
			Blog blog = blogMapper.transformEntityData(t);
			blog.save();
			t.setId(blog.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	public boolean update(BlogDTO t) {
		Blog blog = Blog.get(Blog.class, t.getId());
		boolean isSuccess;
		try {
			blogMapper.transformEntityData(blog, t);
			isSuccess = true;
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		}
		return isSuccess;
	}

	public void remove(Long pk) {
		removes(new Long[] { pk });
	}

	public void removes(Long[] pks) {
		for (int i = 0; i < pks.length; i++) {
			Blog blog = Blog.load(Blog.class, pks[i]);
			blog.remove();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BlogDTO> pageQuery(BlogDTO dto, int currentPage, int pageSize) {
		StringBuilder jpql = new StringBuilder("select new com.dong.blog.application.dto.BlogDTO(_blog.id, _blog.title"
				+ ", _blog.summary, _blog.releaseDate, _blog.clickHit, _blog.replyHit, _blog.picPath, _blog.blogType.id"
				+ ", _blog.blogType.typeName, _blog.blogType.orderNo) from Blog _blog");
		
		if (dto.getBlogTypeDTO() != null && dto.getBlogTypeDTO().getId() != null) {
			jpql.append(" where _blog.blogType.id = ").append(dto.getBlogTypeDTO().getId());
		}
		
		jpql.append(" order by _blog.releaseDate desc");
		
		@SuppressWarnings("unchecked")
		Page<BlogDTO> pages = this.getQueryChannelService().createJpqlQuery(jpql.toString()).setPage(currentPage, pageSize).pagedList();
		
		return new Page<BlogDTO>(pages.getStart(), pages.getResultCount(), pageSize, pages.getData());
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BlogDTO> pageQuery(Map<String, Object> map, int currentPage, int pageSize) {
		StringBuilder jpql = new StringBuilder("select new com.dong.blog.application.dto.BlogDTO(_blog.id, _blog.title"
				+ ", _blog.releaseDate, _blog.picPath, _blog.blogType.id, _blog.blogType.typeName, _blog.blogType.orderNo)"
				+ " from Blog _blog where 1=1");
		List<Object> conditionVals = new ArrayList<Object>();
		
		if (map.get("title") != null) {
			jpql.append(" and _blog.title like ?");
			conditionVals.add(MessageFormat.format("%{0}%", map.get("title")));
		}
		
		if (map.get("orderType") != null && map.get("orderType").toString().equals("clickHit")) {
			jpql.append(" order by _blog.clickHit desc");
		} else if (map.get("orderType") != null && map.get("orderType").toString().equals("releaseDate")) {
			jpql.append(" order by _blog.releaseDate desc");
		}
		
		@SuppressWarnings("unchecked")
		Page<BlogDTO> pages = this.getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
		
		return new Page<BlogDTO>(pages.getStart(), pages.getResultCount(), pageSize, pages.getData());
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BigInteger getTotal(Map<String, Object> params) {
		String sql = "SELECT COUNT(*) FROM t_blog";
		if (params.containsKey("typeId")) { // 根据博客类型查询总数
			sql = sql + " WHERE type_id=" + params.get("typeId");
		} else if (params.containsKey("title") && params.get("title") != null) {
			sql = sql + " WHERE title LIKE '" + params.get("title") + "'"; // 根据关键字查询总数
		} else if (params.containsKey("releaseDateStr")) {
			sql = sql + " WHERE DATE_FORMAT(release_date, '%Y年%m月')="
					+ params.get("releaseDateStr"); // 根据日期查询总数
		} else if (params.containsKey("cateId")) {
			sql = sql + " WHERE type_id IN(SELECT type_id FROM category_blogtype WHERE cate_id = "+params.get("cateId")+")"; //查询大分类结果数
		}
		return (BigInteger) this.getQueryChannelService().createSqlQuery(sql).singleResult();
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogDTO getLastBlog(Long id) {
		String jpql = "select _blog.id, _blog.title from Blog _blog where _blog.id < "
				+ id + " order by _blog.id desc";
		Object[] blog = (Object[]) this.getQueryChannelService()
				.createJpqlQuery(jpql).setPage(0, 1).singleResult();
		BlogDTO dto = new BlogDTO();
		if (blog != null) {
			dto.setId((Long) blog[0]);
			dto.setTitle(blog[1].toString());
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BlogDTO getNextBlog(Long id) {
		String jpql = "select _blog.id, _blog.title from Blog _blog where _blog.id>"
				+ id + " order by _blog.id asc";
		Object[] blog = (Object[]) this.getQueryChannelService()
				.createJpqlQuery(jpql).setPage(0, 1).singleResult();
		BlogDTO dto = new BlogDTO();
		if (blog != null) {
			dto.setId((Long) blog[0]);
			dto.setTitle(blog[1].toString());
		}
		return dto;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogDTO> getBlogsByProperty(String propertyName,
			Object propertyValue) {
		if (propertyValue instanceof BlogTypeDTO) {
			BlogType blogType = new BlogType();
			BeanCopierUtil.copyProperties(propertyValue, blogType);
			propertyValue = blogType;
		}
		List<Blog> list = Blog.findByProperty(Blog.class, propertyName,
				propertyValue);
		List<BlogDTO> blogDTOs;
		try {
			blogDTOs = (List<BlogDTO>) blogMapper.transformBeanDatas(list);
		} catch (Exception e) {
			e.printStackTrace();
			blogDTOs = new ArrayList<BlogDTO>();
		}
		return blogDTOs;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<BlogDTO> getBlogsByProperties(Map<String, Object> properties) {
		List<Blog> list = Blog.findByProperties(Blog.class, properties);
		List<BlogDTO> blogDTOs;
		try {
			blogDTOs = (List<BlogDTO>) blogMapper.transformBeanDatas(list);
		} catch (Exception e) {
			e.printStackTrace();
			blogDTOs = new ArrayList<BlogDTO>();
		}
		return blogDTOs;
	}

	public boolean updateClickHit(Long id, Integer clickHit) {
		Blog blog = Blog.get(Blog.class, id);
		blog.setClickHit(clickHit);
		return true;
	}

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Page<BlogDTO> pageQueryByCate(CategoryDTO dto, int currentPage,
			int pageSize) {
		String jpql = "select _cate from Category _cate where _cate.id=?";
		Category category = (Category) this.getQueryChannelService().createJpqlQuery(jpql).setParameters(new Object[] { dto.getId() }).singleResult();
		
		StringBuilder bjpql = new StringBuilder("select new com.dong.blog.application.dto.BlogDTO(_blog.id, _blog.title"
				+ ", _blog.summary, _blog.releaseDate, _blog.clickHit, _blog.replyHit, _blog.picPath, _blog.blogType.id"
				+ ", _blog.blogType.typeName, _blog.blogType.orderNo) from Blog _blog where _blog.blogType in(:types) order by _blog.releaseDate desc");
		Set<BlogType> sets = category.getBlogTypes();
		
		@SuppressWarnings("unchecked")
		Page<BlogDTO> pages = this.getQueryChannelService().createJpqlQuery(bjpql.toString()).addParameter("types", sets).setPage(currentPage, pageSize).pagedList();
		
		return new Page<BlogDTO>(pages.getStart(), pages.getResultCount(), pageSize, pages.getData());
	}

	public boolean updateReplyHit(Long id, Integer replyHit) {
		// TODO Auto-generated method stub
		Blog blog = Blog.get(Blog.class, id);
		blog.setReplyHit(replyHit);
		return true;
	}

}
