# 博客系统
### 项目说明
* 采用spring4+springMVC+dddlib架构
* 配置了MySQL数据库
* 使用Maven3管理项目；
* 使用Shiro作为项目安全框架；
* 使用Apache Lucene全文检索引擎；
* 支持restful风格；
* 前台网页使用主流的Bootstrap3 UI框架；后台管理使用主流易用的EasyUI轻量级框架；
* 数据库连接池使用的是阿里巴巴的Druid；
* 在线编辑器使用了百度的UEditor，支持单图，多图上传，支持截图上传，支持代码高亮特性；
* 配置了jetty服务器，以及tomcat服务器两种服务器；
* 配置jsp，velocity，freemarker三种视图引擎，并兼容使用三种视图引擎开发页面；

### 图片服务器
* 采用nginx作为图片服务器

### 博客采集器
* 使用netty服务器，处理高并发

###	缓存服务器
* 使用Redis的Key-Value数据库做缓存
* 添加使用Memcached做二级缓存
