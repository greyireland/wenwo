##项目简介：
它是一个类似知乎的问答平台，采用SpringBoot，MyBatis，Redis搭建，主要功能有：问题的发布，回答，赞踩，敏感词与标签的过滤，邮件与站内信通知等。特点：实现自定义异步框架，使用Redis实现消息队列，使用redis进行缓存，使用Lucene 实现站内全文搜索，通过Python爬虫爬取数据，扩充网站内容。

###技术栈：
1.spring-boot
2.mybatis
3.redis
4.solr+中文分词

####首页大概是长这样子的:
![首页](https://github.com/shawnwg/wenwo/blob/master/static/demo_img/index.png)
####问题详情页:
![首页](https://github.com/shawnwg/wenwo/blob/master/static/demo_img/detail.png)
####feed页:
![首页](https://github.com/shawnwg/wenwo/blob/master/static/demo_img/explore.png)
####关注列表与粉丝列表页:
![首页](https://github.com/shawnwg/wenwo/blob/master/static/demo_img/followers.png)
####发送私信:
![首页](https://github.com/shawnwg/wenwo/blob/master/static/demo_img/message.png)
####私信列表与详情:
![首页](https://github.com/shawnwg/wenwo/blob/master/static/demo_img/conversation.png)
![首页](https://github.com/shawnwg/wenwo/blob/master/static/demo_img/conversation_detail.png)
####搜索页:
![首页](https://github.com/shawnwg/wenwo/blob/master/static/demo_img/search.png)

###项目启动
1.开启solr服务,在solr的bin下面执行solr start -p 8983
> E:\DevTools\solr-6.2.0\bin>solr start -p 8983

2.开启redis服务

3.运行WendaApplication.java中的main方法就OK了。
