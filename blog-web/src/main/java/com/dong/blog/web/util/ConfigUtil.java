package com.dong.blog.web.util;

public final class ConfigUtil {

	// public static final String LOCAL_IMAGES_PATH = "E:/image_server";
	public final static String LOCAL_IMAGES_PATH;

	public final static String _BLOG_IMAGES_PATH;

	public final static String _BLOG_CONTENT_IMG_PATH;

	public final static String LOCAL_BLOG_IMAGES_PATH;

	public final static String LOCAL_BLOG_CONTENT_IMG_PATH;

	public final static String IMAGE_SERVICE_HOST;

	public final static String GATHER_SERVICE_HOST;

	public final static String SOCKET_CLIENT_HOST;

	static {
		Configuration mConfiguration = new Configuration(Contance.class
				.getResource("/META-INF/props/config.properties").getFile());
		LOCAL_IMAGES_PATH = mConfiguration.getValue("app.img.serverPath");
		_BLOG_IMAGES_PATH = mConfiguration.getValue("app.blogImgPath");
		_BLOG_CONTENT_IMG_PATH = mConfiguration
				.getValue("app.blogContentImgPath");
		LOCAL_BLOG_IMAGES_PATH = LOCAL_IMAGES_PATH + _BLOG_IMAGES_PATH;
		LOCAL_BLOG_CONTENT_IMG_PATH = LOCAL_IMAGES_PATH
				+ _BLOG_CONTENT_IMG_PATH;
		IMAGE_SERVICE_HOST = mConfiguration.getValue("app.img.serverHost");
		GATHER_SERVICE_HOST = String.format("%s:%s",
				mConfiguration.getValue("app.gatherServerHost"),
				mConfiguration.getValue("app.gatherServerPort"));
		SOCKET_CLIENT_HOST = String.format("%s:%s",
				mConfiguration.getValue("app.socketClient.Host"),
				mConfiguration.getValue("app.socketClient.Port"));
		
		System.out.println("配置参数：LOCAL_IMAGES_PATH="+LOCAL_IMAGES_PATH);
		System.out.println("配置参数：_BLOG_IMAGES_PATH="+_BLOG_IMAGES_PATH);
		System.out.println("配置参数：_BLOG_CONTENT_IMG_PATH="+_BLOG_CONTENT_IMG_PATH);
		System.out.println("配置参数：LOCAL_BLOG_IMAGES_PATH="+LOCAL_BLOG_IMAGES_PATH);
		System.out.println("配置参数：LOCAL_BLOG_CONTENT_IMG_PATH="+LOCAL_BLOG_CONTENT_IMG_PATH);
		System.out.println("配置参数：IMAGE_SERVICE_HOST="+IMAGE_SERVICE_HOST);
	
		System.out.println("配置参数：GATHER_SERVICE_HOST="+GATHER_SERVICE_HOST);
		System.out.println("配置参数：SOCKET_CLIENT_HOST="+SOCKET_CLIENT_HOST);
		
	}
}
