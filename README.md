# qcc-crawler

#### 介绍
企查查js爬虫，通过将js程序制作成chrome浏览器插件的方式植入到 http://www.qcc.com 网站中实现公司信息爬虫

#### 软件架构
软件架构说明


#### 安装教程

1. 开发chrome浏览器→

   ![image-20211114162753958](https://raw.githubusercontent.com/zjwan461/images/master/img/image-20211114162753958.png)

2. 选择js-crawler文件夹，完成后可以看到qcc js插件已经成功添加到浏览器拓展中，确认此时插件已被开启。

   ![image-20211114183345964](https://raw.githubusercontent.com/zjwan461/images/master/img/image-20211114183345964.png)

   ![image-20211114183515673](https://raw.githubusercontent.com/zjwan461/images/master/img/image-20211114183515673.png)

3. 开启后台服务，可以使用eclipse或者idea打开qcc-crawler项目，启动项目。或者直接使用maven命令进行打包后运行。在此之前请先建好数据库和表，相关DDL放在resources目录下的qcc_crawler.sql文件中。

   ![image-20211114183829291](https://raw.githubusercontent.com/zjwan461/images/master/img/image-20211114183829291.png)

   如果使用maven命令运行：

   ```powershell
   maven clean compile spring-boot:run
   ```

   ![image-20211114184036472](https://raw.githubusercontent.com/zjwan461/images/master/img/image-20211114184036472.png)

4. 打开qcc.js文件，找到getRootSearchText()方法，在此处可以修改你想要爬取的父公司名称。在文件头可以修改level、download、send这三个参数，分表代表爬虫的层级、是否下载文件、是否发送到后端保存。可以根据情况进行修改。

   ```javascript
   function getRootSearchText() {
   		return "深圳市引导基金投资有限公司";
   	}
   ```

   ```javascript
   console.log("qcc爬虫")
   const level = 2;
   const download = false;
   const send = true;
   
   $(function () {
   
   ```

5. 回到浏览器打开http://www.qcc.com, 刷新网页。等待一会爬虫就会开始了。默认效果会将数据传输到后台进行保存。

   ![image-20211114184641978](https://raw.githubusercontent.com/zjwan461/images/master/img/image-20211114184641978.png)

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request
