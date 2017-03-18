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
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.codec.binary.Base64;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dong.blog.facade.BlogFacade;
import com.dong.blog.facade.BloggerFacade;
import com.dong.blog.facade.dto.BlogDTO;
import com.dong.blog.facade.dto.BlogTypeDTO;
import com.dong.blog.lucene.BlogIndex;
import com.dong.blog.util.FileUtil;
import com.dong.blog.web.service.ServerSocketClient;
import com.dong.blog.web.util.Contance;
import com.dong.blog.web.util.ResponseUtil;
import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;


@Controller
@RequestMapping("/")
public class TestController {
	
	/*@Resource*/
	@Inject
	BloggerFacade bloggerFacade;
	@Inject
	BlogFacade blogFacade;
	
	private BlogIndex blogIndex = new BlogIndex();
	
	private List<BlogDTO> dtos = new ArrayList<BlogDTO>();
	
	private WebSocketClient client = null;
	
	@RequestMapping("/t")
	public ModelAndView test() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("param", "你好");
		mv.setViewName("hello");
		return mv;
	}
	
	@RequestMapping("/tt")
	public ModelAndView t() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("param", "How are you!");
		mv.setViewName("hi");
		return mv;
	}
	
	@RequestMapping("/test")
	public void test(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ResponseUtil.write(response, new Gson().toJson(bloggerFacade.getBlogger()));
	}
	
	@RequestMapping("/writeCss")
	public void writeCss(HttpServletRequest request,HttpServletResponse response) throws Exception {
		File file = new File("D:/ExtJSIcons");
		Set<IconJson> list = new HashSet<IconJson>();
		if (file.exists() && file.isDirectory()) {
			File cssFile = new File("D:/icons.json");
			PrintStream ps = new PrintStream(new FileOutputStream(cssFile));
			if (!cssFile.exists()) {
				cssFile.createNewFile();
			}
			File[] fs = file.listFiles();
			for (File f : fs) {
				if (f.isDirectory()) {
					File[] icons = f.listFiles();
					for (File icon : icons) {
						if (icon.getName().equals("Thumbs.db")) {
							continue;
						}
						String name = icon.getName();
						IconJson json = new IconJson();
						json.setIcon(String.format("icon-%s", name.substring(0, name.lastIndexOf("."))));
						json.setIconUrl(String.format("ExtJSIcons/%s/%s", f.getName(), name));
						list.add(json);
						StringBuilder sb = new StringBuilder();
						/*sb.append(".icon-")
						.append(name.substring(0, name.lastIndexOf(".")))
						.append("{background:url('ExtJSIcons/")
						.append(f.getName()).append("/")
						.append(name).append("') no-repeat center cneter;}");*/
						//list.add(sb.toString());
						//ps.println(sb);
					}
				} else {
					if (f.getName().equals("Thumbs.db")) {
						continue;
					}
					String name = f.getName();
					IconJson json = new IconJson();
					json.setIcon(String.format("icon-%s", name.substring(0, name.lastIndexOf("."))));
					json.setIconUrl(String.format("ExtJSIcons/%s", name));
					list.add(json);
					/*StringBuilder sb = new StringBuilder();
					sb.append(".icon-")
					.append(name.substring(0, name.lastIndexOf(".")))
					.append("{background:url('ExtJSIcons/")
					.append(name).append("') no-repeat center cneter;}");*/
					//ps.println(sb);
					//list.add(sb.toString());
				}
			}
			ps.println(new Gson().toJson(list));
		}
		//Logger.getLogger(this.getClass().getName()).debug(list.size());
		ResponseUtil.write(response, new Gson().toJson(list));
	}
	
	@RequestMapping("/dealTaobaoUrl")
	public void dealTaobaoUrl(HttpServletRequest request,HttpServletResponse response) throws Exception {
		File file = new File("D:/goods.txt");
		if (file.exists()) {
			InputStream input = new FileInputStream(file);
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new InputStreamReader(input, "gbk"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			int i = 1;
			PrintStream ps = new PrintStream(new FileOutputStream(new File("D:/goods_2.txt")));
			while ((line = reader.readLine()) != null) {
				if (line.contains("detail.tmall.com")) {
					continue;
				}
				ps.println(line);
				sb.append(line).append("\n\t");
				i++;
			}
			sb.append("size=").append(i).append("\n\t");
			ResponseUtil.write(response, sb);
		}
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
	    ResponseUtil.write(response, s);
		return s;
	}
	
	@RequestMapping("collect")
	public String collectBlog(@RequestParam(value="startPage",required=false)Integer startPage
			, @RequestParam(value="endPage",required=false)Integer endPage
			, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = "ws://localhost:8080/blog-web/websocket/server/client";
		client = new ServerSocketClient(new URI(url), new Draft_17());
		client.connect();
		Integer[] types = {1,13,15,16,17,18};
		String[] typeName = {"java", "android", "html5", "javascript", "php", "ios"};
		Random random = new Random();
		ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1000));
		do {
			int pos = random.nextInt(types.length);
			String f = new String(Base64.encodeBase64(String.valueOf(startPage).getBytes()));
			pool.execute(new MyTask(String.valueOf(types[pos]), typeName[pos], startPage, f));
			startPage ++;
		} while(startPage <= endPage);
		pool.shutdown();
		while(true) {
			if (pool.isTerminated()) {
				int size = dtos.size();
				sendMessage(client, "抓取完毕,关闭资源,总共抓取数据为："+size+"条");
				sendMessage(client, "开始数据存储...");
				sendMessage(client, "成功存储：（<span id=\"count\">0</span>/"+size+"）");
				Collections.shuffle(dtos);
				for (int i = 0; i < size; i++) {
					Thread.sleep(500);
					try {
						BlogDTO dto = dtos.get(i);
						dto = blogFacade.save(dto);
				    	blogIndex.addIndex(dto);
					} catch(Exception e) {
						e.printStackTrace();
					}	
			    	sendMessage(client, "count:"+(i+1));
				}
				sendMessage(client, "存储完毕,释放所有资源...");
				Thread.sleep(500);
				client.close();
				break;
			}
			Thread.sleep(500);
		}
		dtos.clear();
		return "success";
	}
	
	private synchronized void sendMessage(WebSocketClient client, String message) {
		if (client != null) {
			while(true) {
				try {
					client.send(message);
					break;
				} catch(Exception e) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					continue;
				}
			}
		}
	}
	
	class MyTask implements Runnable {
		
		int page;
		String type;
		String tag;
		String typeId;
		
		public MyTask(String typeId, String type, int page, String tag) {
			this.typeId = typeId;
			this.page = page;
			this.type = type;
			this.tag = tag;
		}

		public void run() {
			try {
				sendMessage(client, "初始化数据源...");
				String val = String.format("http://www.codeceo.com/article/tag/%s/page/%s", type, page);
				String urlStr = String.format("http://192.168.1.190:8845/?url=%s", new String(Base64.encodeBase64(val.getBytes("UTF-8")), "utf-8"));
				URL url = new URL(urlStr);
				HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
				httpConn.setReadTimeout(60000);
				httpConn.setConnectTimeout(60000);
				InputStreamReader input = new InputStreamReader(httpConn  
			            .getInputStream(), "utf-8");  
			    BufferedReader bufReader = new BufferedReader(input);  
			    String line = "";  
			    StringBuilder contentBuf = new StringBuilder();  
			    while ((line = bufReader.readLine()) != null) {  
			        contentBuf.append(line);  
			    }
			    sendMessage(client, "数据装载...");
			    List<BlogDTO> list = new Gson()
			    .fromJson(contentBuf.toString(), new TypeToken<List<BlogDTO>>(){}.getType());
			
			    for (BlogDTO dto : list) {
			    	dto.setSummary(dto.getContentNoTag().substring(0, 155));
			    	dto.setKeyWord(dto.getTitle());
			    	BlogTypeDTO typeDTO = new BlogTypeDTO();
			    	typeDTO.setId(Long.parseLong(typeId));
			    	dto.setBlogTypeDTO(typeDTO);
			    	
			    	String content = dto.getContent();
			    	Map<String, String> maps = new HashMap<String, String>();
			    	Matcher m = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(content);
			    	while(m.find()) {
			    		if (!m.group(1).equals("")) {
			    			String imgUrl = m.group(1);
			    			System.out.println(imgUrl);
			    			String fileName = imgUrl.replace("http://static.codeceo.com/images/", "").replaceAll("/", "-");
			    			if(!imgUrl.toLowerCase().contains(".jpg")
			    					&&!imgUrl.toLowerCase().contains(".png")
			    					&&!imgUrl.toLowerCase().contains(".gif")) {
			    				continue;
			    			}
			    			try {
			    				FileUtil.download(imgUrl, Contance.LOCAL_BLOG_CONTENT_IMG_PATH, fileName);
			    				maps.put(imgUrl, String.format("%s%s/%s", Contance.IMAGE_SERVICE_HOST, Contance._BLOG_CONTENT_IMG_PATH, fileName));
			    			} catch(Exception e) {
			    				e.printStackTrace();
			    			}
			    			
			    		}
			    	}
			    	Set<String> keys = maps.keySet();
			    	for (String key : keys) {
			    		content = content.replace(key, maps.get(key));
			    	}
			    	dto.setContent(content);
			    	
			    	URL u = new URL(dto.getPicPath());
					HttpURLConnection conn = (HttpURLConnection) u.openConnection();
					conn.setConnectTimeout(5*1000); 
					InputStream in = conn.getInputStream();
					BufferedImage prevImage = ImageIO.read(in); 
				    int newWidth = 245;  
				    int newHeight = 200;
				    
				    String name = dto.getPicPath().replace("http://static.codeceo.com/images/", "").replaceAll("/", "-");	
				    File imgFile = new File(Contance.LOCAL_BLOG_IMAGES_PATH, name);
				    BufferedImage image = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_BGR);
				    OutputStream os = new FileOutputStream(imgFile);
				    Graphics graphics = image.createGraphics();  
				    graphics.drawImage(prevImage, 0, 0, newWidth, newHeight, null);  
				    ImageIO.write(image, "jpg", os);  	
				    os.flush();
				    input.close();  
				    os.close(); 
			    	dto.setPicPath(String.format("%s%s/%s", Contance.IMAGE_SERVICE_HOST, Contance._BLOG_IMAGES_PATH, name));
			    	sendMessage(client, "成功抓取博客：\""+dto.getTitle()+"\"");
			    	dtos.add(dto);
			    }
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
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
	
	@RequestMapping("/socket")
	public String socket() throws Exception{
		String url = "ws://localhost:8080/blog-web/websocket/server/client";
		WebSocketClient client = new ServerSocketClient(new URI(url), new Draft_17());
		client.connect();
		String message = "hello";
		int i = 0;
		while (true) {
			/*if ("".equals(message)) {
				Scanner scanner = new Scanner(System.in);
				message = scanner.nextLine();
				scanner.close();
			}*/
			System.out.println(message);
			if (i > 50) {
				client.close();
			    break;
			}
			
			try {
				client.send(message);
				i++;
			} catch(Exception e) {
				continue;
			}
			Thread.sleep(1000);
		}
		return "";
	}

}
