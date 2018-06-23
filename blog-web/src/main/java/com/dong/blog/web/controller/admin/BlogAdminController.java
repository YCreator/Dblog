package com.dong.blog.web.controller.admin;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dong.blog.facade.BlogFacade;
import com.dong.blog.facade.BlogTypeFacade;
import com.dong.blog.facade.dto.BlogDTO;
import com.dong.blog.facade.dto.PageBean;
import com.dong.blog.lucene.BlogIndex;
import com.dong.blog.util.HtmlUtil;
import com.dong.blog.util.StringUtil;
import com.dong.blog.web.util.ConfigUtil;
import com.google.gson.Gson;


/**
 * 管理员博客Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {

	@Inject
	private BlogFacade blogFacade;
	
	@Inject
	private BlogTypeFacade blogTypeFacade;
	
	// 博客索引
	private BlogIndex blogIndex = BlogIndex.getInstance();
	
	/**
	 * 添加或者修改博客信息
	 * @param blog
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	public Map<String, Object> save(BlogDTO blog) throws Exception {
		
		boolean isUpdateSuccess = false;
		
		if (blog.getId()==null) {
			blogFacade.save(blog);
			blogIndex.addIndex(blog); // 添加博客索引
		} else {
			blog.setBlogTypeDTO(blogTypeFacade.get(blog.getBlogTypeDTO().getId()));
			isUpdateSuccess = blogFacade.update(blog);
			blogIndex.updateIndex(blog); // 更新博客索引
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", blog.getId() != null || isUpdateSuccess);
		return result;
		
	}
	
	/**
	 * 分页查询博客信息
	 * @param page
	 * @param rows
	 * @param s_customer
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
	public JSONObject list(
			@RequestParam(value="page",required=false)String page,
			@RequestParam(value="rows",required=false)String rows,
			BlogDTO s_blog) throws Exception{
		
		Map<String,Object> map = new HashMap<String,Object>();
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		map.put("title", StringUtil.formatLike(s_blog.getTitle()));
		List<BlogDTO> blogList=blogFacade.pageQuery(map, pageBean.getPage(), pageBean.getPageSize()).getData();
		Long total=blogFacade.getTotal(map).longValue();
		
		JSONObject result=new JSONObject();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray=JSONArray.fromObject(blogList,jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", total);
		return result;
		
	}
	
	/**
	 * 删除博客信息
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Map<String, Object> delete(@RequestParam(value="ids")String ids) throws Exception{
		String []idsStr=ids.split(",");
		
		for(int i=0;i<idsStr.length;i++){
			blogFacade.remove(Long.valueOf(idsStr[i]));
			blogIndex.deleteIndex(idsStr[i]); // 删除对应博客的索引
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return result;
	}
	
	/**
	 * 通过ID查找实体
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/findById")
	public String findById(@RequestParam(value="id")String id)throws Exception{
		BlogDTO blog = blogFacade.get(Long.valueOf(id));
		return new Gson().toJson(blog);
	}
	
	/**
	 * 上传博客图片
	 * @param imageFile
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/uploadImg")
	public Map<String, Object> uploadImg(
			@RequestParam(value="imageFile") MultipartFile imageFile,
			HttpServletRequest request) throws Exception{
		
		Map<String, Object> result = new HashMap<String, Object>();
		if (imageFile != null && !imageFile.isEmpty()) {
			
			String imageName = imageFile.getOriginalFilename();
			String imgForm = imageName.substring(imageName.lastIndexOf(".") + 1, imageName.length());
			String name = String.format("%s.%s", System.currentTimeMillis(), imgForm);
			File imgFile = new File(ConfigUtil.LOCAL_BLOG_IMAGES_PATH, name);
			imageFile.transferTo(imgFile);
			result.put("success", true);
			String imgUrl = String.format("%s%s/%s", ConfigUtil.IMAGE_SERVICE_HOST, ConfigUtil._BLOG_IMAGES_PATH, name);
			result.put("imgPath", imgUrl);
		} else {
			result.put("success", false);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/uploadNetImg")
	public Map<String, Object> uploadNetImg(@RequestParam(value="netImage")String netImage, HttpServletRequest request) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		URL url = new URL(netImage);
		URLConnection conn = url.openConnection();
		conn.setConnectTimeout(5*1000); 
		InputStream input = conn.getInputStream();
		BufferedImage prevImage = ImageIO.read(input); 
	    int newWidth = 245;  
	    int newHeight = 200;
	    String name = String.format("%s.%s", System.currentTimeMillis(), "jpg");
	    File imgFile = new File(ConfigUtil.LOCAL_BLOG_IMAGES_PATH, name);
	    BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
	    OutputStream os = new FileOutputStream(imgFile);
	    Graphics graphics = image.createGraphics();  
	    graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);  
	    ImageIO.write(image, "jpg", os);  
	    os.flush();
	    input.close();  
	    os.close(); 
	    result.put("success", true);
		result.put("imgPath", String.format("%s%s/%s", ConfigUtil.IMAGE_SERVICE_HOST, ConfigUtil._BLOG_IMAGES_PATH, name));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/rebuildIndex")
	public Map<String, Object> rebuildIndex() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		blogIndex.clearIndex();
		List<BlogDTO> list = blogFacade.findAll();
		for (BlogDTO dto : list) {
			dto.setContentNoTag(HtmlUtil.getTextFromHtml(dto.getContent()));
			blogIndex.addIndex(dto);
		}
		result.put("success", true);
		return result;
	}
	
}
