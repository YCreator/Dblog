package com.dong.blog.web.controller;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dong.blog.application.BloggerApplication;
import com.dong.blog.application.dto.BlogDTO;
import com.dong.blog.application.dto.BlogTypeDTO;
import com.dong.blog.lucene.BlogIndex;
import com.dong.blog.web.util.ResponseUtil;
import com.google.common.io.Files;
import com.google.gson.Gson;


@Controller
@RequestMapping("/")
public class TestController {
	
	/*@Resource*/
	@Inject
	BloggerApplication bloggerApplication;
	
	private BlogIndex blogIndex = new BlogIndex();
	
	@RequestMapping("/test")
	public void test(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ResponseUtil.write(response, new Gson().toJson(bloggerApplication.getBlogger()));
	}
	
	@RequestMapping("/text")
	public void text(@RequestParam(value="from", required=false)String from, @RequestParam(value="to", required=false)String to, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fPath = request.getServletContext().getRealPath("/");
		String res = "";
		try {
			File file = new File(fPath, from);
			if (file.exists()) {
				InputStream input = new FileInputStream(file);
				@SuppressWarnings("resource")
				BufferedReader reader = new BufferedReader(new InputStreamReader(input, "gbk"));
		        String line;
		        String val = "\"(.*?)\""; 
		        List<String> strs = new ArrayList<String>();
		        Pattern p=Pattern.compile(val);
		        while ((line = reader.readLine()) != null) {
		        	if (line.contains("\"")) {
		        		Matcher m = p.matcher(line); //提取双引号里的内容
		        		while(m.find()) {
		        			strs.add(m.group());
		        		}
		        	}
		            // res += line;
		        }
		        File f = new File(fPath+to);
		        if (!f.exists()) {
		        	f.createNewFile();
		        }
		        @SuppressWarnings("resource")
				PrintStream ps = new PrintStream(new FileOutputStream(f));
		        
		        String[] strList = (String[]) strs.toArray(new String[strs.size()]); //list转object[]
		        Arrays.sort(strList,String.CASE_INSENSITIVE_ORDER); //根据字母排序
		        for (int i = 0; i< strList.length; i++) {
		        	String s = strList[i]; //常量名
		        	s = s.replace("\"", "");
		        	if (s.contains("/")) {
		        		s = s.replace("/", "_");
		        	}
		        	s = s.toUpperCase(); //小写变大写
		        	strList[i] = "String " + s + " = " + strList[i] + ";"; //完整的常量定义
		        	res = res + "-------------------<br/>" + i + ":" + strList[i] + "<br/>";
		        	ps.println(strList[i]);
		        }
		           
			}
		} catch(Exception e) {
			res = "error";
			e.printStackTrace();
		}
		
		ResponseUtil.write(response, res);
	}
	
	@RequestMapping("getdata")
	public String catchData(@RequestParam(value="src",required=false)String src, @RequestParam(value="typeId",required=false)String typeId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//String strUrl = "http://www.codeceo.com/article/tag/android/page/1";
		URL url = new URL(src);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		InputStreamReader input = new InputStreamReader(httpConn  
	            .getInputStream(), "utf-8");  
	    BufferedReader bufReader = new BufferedReader(input);  
	    String line = "";  
	    StringBuilder contentBuf = new StringBuilder();  
	    while ((line = bufReader.readLine()) != null) {  
	        contentBuf.append(line);  
	    }
	    Pattern pattern = Pattern.compile("<a\\starget=\"_blank\"\\shref=\".*?\"\\stitle=\".*?\">(.+?)</a>");
	    Matcher matcher = pattern.matcher(contentBuf);
	   // List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	    List<BlogDTO> list = new ArrayList<BlogDTO>();
	    while(matcher.find()) {
	    	String s = matcher.group();
	    	//System.out.println(s);
	    	HashMap<String, String> maps = new HashMap<String, String>();
	    	Matcher m = Pattern.compile("href=\".*?\"").matcher(s);
	    	while(m.find()) {
	    		maps.put("url", m.group().replaceAll("href=\"|\">", "").replace("\"", ""));
	    	}
	    	m = Pattern.compile("src=\".*?\"").matcher(s);
	    	while(m.find()) {
	    		maps.put("image", m.group().replaceAll("src=\"|\">", "").replace("\"", ""));
	    	}
	    	maps.put("title", s.replaceAll("</?[^<]+>", "").replace("\t", ""));
	    	BlogDTO dto = new BlogDTO();
	    	String title = maps.get("title");
	    	dto.setTitle(title);
	    	BlogTypeDTO typeDTO = new BlogTypeDTO();
	    	typeDTO.setId(Long.parseLong(typeId));
	    	dto.setBlogTypeDTO(typeDTO);
	    	String content = catchContent(maps.get("url"), request, response);
	    	//dto.setContent(content);
	    	String noTagContent = content.replaceAll("<script[^>]*?>.*?</script>", "");
	    	noTagContent = noTagContent.replaceAll("<[^>]*>", "");
	    	noTagContent = noTagContent.replaceAll("\t", "");
	    	dto.setContentNoTag(noTagContent);
	    	dto.setSummary(noTagContent.substring(0, 155));
	    	//dto.setPicPath(downImage(maps.get("image"), request, response));
	    	dto.setKeyWord(title);
	    	blogIndex.addIndex(dto);
	    	list.add(dto);
	    }
	    JSONArray array = JSONArray.fromObject(list);
	    ResponseUtil.write(response, array.toString());
		return null;
	}
	
	@RequestMapping("getcontent")
	public String catchContent(@RequestParam(value="src",required=false)String src, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//String strUrl = "http://www.codeceo.com/article/app-process-create.html";
		URL url = new URL(src);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		InputStreamReader input = new InputStreamReader(httpConn  
	            .getInputStream(), "utf-8");  
	    BufferedReader bufReader = new BufferedReader(input);  
	    String line = "";  
	    StringBuilder contentBuf = new StringBuilder();  
	    while ((line = bufReader.readLine()) != null) {  
	        contentBuf.append(line);  
	    }
	    Pattern pattern = Pattern.compile("<div class=\"article-entry\" id=\"article-entry\">([\\s\\S]*)<a\\sid=\"soft\\-link\"\\sname=\"soft\\-link\">");
	    Matcher matcher = pattern.matcher(contentBuf);
	    String s = "";
	    while(matcher.find()) {
	    	//System.out.println(matcher.group());
	    	s = s + matcher.group();
	    }
	    //ResponseUtil.write(response, s);
		return s;
	}
	
	@RequestMapping("/testdown")
	public String downImage(@RequestParam(value="src",required=false)String src, HttpServletRequest request, HttpServletResponse response) throws Exception {
		URL url = new URL(src);
		URLConnection conn = url.openConnection();
		conn.setConnectTimeout(5*1000); 
		InputStream input = conn.getInputStream();
		BufferedImage prevImage = ImageIO.read(input); 
	    int newWidth = 245;  
	    int newHeight = 200;
	    String name = String.format("/resources/images/%s.%s", System.currentTimeMillis(), "jpg");
	    File targetFile = new File(request.getSession().getServletContext().getRealPath(name));
	    File sourceFile = new File(String.format("D:/workplace/blog/blog-web/src/main/webapp%s",name));
	    BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
	    OutputStream os = new FileOutputStream(targetFile);
	    Graphics graphics = image.createGraphics();  
	    graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);  
	    ImageIO.write(image, "jpg", os);  
	    os.flush();
	    Files.copy(targetFile, sourceFile);
	    input.close();  
	    os.close(); 
	    return name;
	}

}
