package com.dong.blog.application.impl;

import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Named;

import org.dayatang.utils.Page;

import com.dong.blog.application.BlogApplication;
import com.dong.blog.core.domain.Blog;
import com.dong.blog.core.domain.BlogType;
import com.dong.blog.core.domain.Category;

@Named
public class BlogApplicationImpl extends BaseApplicationImpl implements BlogApplication {

	public Blog get(Long pk) {
		return Blog.get(Blog.class, pk);
	}
	
	public Blog load(Long pk) {
		return Blog.load(Blog.class, pk);
	}

	public List<Blog> findAll() {
		return Blog.findAll(Blog.class);
	}

	public Blog save(Blog t, Long blogType) {
		t.setBlogType(BlogType.load(BlogType.class, blogType));
		t.setReleaseDate(new Date());
		t.setClickHit(0);
		t.setReplyHit(0);
		t.save();
		return t;
	}

	public void remove(Long pk) {
		Blog blog = Blog.load(Blog.class, pk);
		blog.remove();
	}

	@SuppressWarnings("unchecked")
	public Page<Blog> pageQuery(Map<String, Object> map, int currentPage, int pageSize) {
		StringBuilder jpql = new StringBuilder("select new com.dong.blog.core.domain.Blog(_blog.id, _blog.title"
				+ ", _blog.summary, _blog.releaseDate, _blog.clickHit, _blog.replyHit, _blog.picPath, _blog.blogType)"
				+ " from Blog _blog where 1=1");
		List<Object> conditionVals = new ArrayList<Object>();
		
		if (map.containsKey("title") && map.get("title") != null) {
			jpql.append(" and _blog.title like ?");
			conditionVals.add(MessageFormat.format("%{0}%", map.get("title")));
		}
		if (map.containsKey("blogTypeId")) {
			jpql.append(" and _blog.blogType.id = ").append(map.get("blogTypeId"));
		}
		if (map.containsKey("orderType")) {
			if ("clickHit".equals(map.get("orderType"))) {
				jpql.append(" order by _blog.clickHit desc");
			} else if ("releaseDate".equals(map.get("orderType"))) {
				jpql.append(" order by _blog.releaseDate desc");
			}
		}
		return this.getQueryChannelService().createJpqlQuery(jpql.toString()).setParameters(conditionVals).setPage(currentPage, pageSize).pagedList();
	}

	
	public BigInteger getTotal(Map<String, Object> params) {
		String sql = "SELECT COUNT(*) FROM t_blog";
		if (params.containsKey("blogTypeId")) { // 根据博客类型查询总数
			sql = sql + " WHERE type_id=" + params.get("blogTypeId");
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

	
	public Blog getLastBlog(Long id) {
		return Blog.getLastBlog(id);
	}

	
	public Blog getNextBlog(Long id) {
		return Blog.getNextBlog(id);
	}

	public List<Blog> getBlogsByProperties(Map<String, Object> properties) {
		return Blog.findByProperties(Blog.class, properties);
	}

	public boolean updateClickHit(Long id, Integer clickHit) {
		Blog blog = Blog.get(Blog.class, id);
		blog.setClickHit(clickHit);
		return true;
	}

	@SuppressWarnings("unchecked")
	public Page<Blog> pageQueryByCate(Long cateId, int currentPage, int pageSize) {
		Category category = Category.get(Category.class, cateId);
		String jpql = "select new com.dong.blog.core.domain.Blog(_blog.id, _blog.title"
				+ ", _blog.summary, _blog.releaseDate, _blog.clickHit, _blog.replyHit, _blog.picPath, _blog.blogType)"
				+ " from Blog _blog where _blog.blogType in(:types) order by _blog.releaseDate desc";
		Set<BlogType> sets = category.getBlogTypes();
		return this.getQueryChannelService()
				.createJpqlQuery(jpql)
				.addParameter("types", sets)
				.setPage(currentPage, pageSize)
				.pagedList();
	}

	public boolean updateReplyHit(Long id, Integer replyHit) {
		// TODO Auto-generated method stub
		Blog blog = Blog.get(Blog.class, id);
		blog.setReplyHit(replyHit);
		return true;
	}

	public Blog save(Blog t) {
		t.setReleaseDate(new Date());
		t.setClickHit(0);
		t.setReplyHit(0);
		this.getLog().debug(t.toString());
		t.save();
		return t;
	}

	public boolean update(Blog t) {
		// TODO Auto-generated method stub
		return false;
	}

	public void removes(Long[] pks) {
		// TODO Auto-generated method stub
		
	}

}
