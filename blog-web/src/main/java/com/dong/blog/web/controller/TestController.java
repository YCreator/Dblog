package com.dong.blog.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dong.blog.application.BloggerApplication;
import com.dong.blog.web.util.ResponseUtil;
import com.google.gson.Gson;


@Controller
@RequestMapping("/")
public class TestController {
	
	/*@Resource*/
	@Inject
	BloggerApplication bloggerApplication;
	
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

}
