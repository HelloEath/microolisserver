# 1.项目描述
一个在线查询汽车润滑油的系统，分为移动端和pc端，移动端可根据品牌查询合适的润滑油，PC端属于管理后台，根据相关信息录入汽车合适的润滑油
信息。

# 2.技术涉及
springBoot+springSecurity+springCloud+Redis+consul+springData+spring-boot-admin+nginx+vue

前端技术：

* 基础的HTML、CSS、JavaScript（基于ES6标准）
* JQuery
* Vue.js 2.0
* 前端构建工具：webpack
* 前端安装包工具：npm
* Vue脚手架：vue-cli
* Vue路由：vue-router
* ajax框架：axios
* UI:element-ui

后端技术：

* 基础的SpringMVC、Spring 5.0
* mysql5.6
* Spring Boot 2.2.7
* Spring Cloud Hoxton.SR3
* Redis-5.0
* consul 1.7.1
* nginx-1.13.7
* feign


# 3.系统架构

# 4.数据库设计 
数据库表
![](https://github.com/HelloEath/microolisserver/raw/master/imagesdesc/olis-table.jpg)
数据表关系
![](https://github.com/HelloEath/microolisserver/raw/master/imagesdesc/oils-er.png)
# 5.项目结构
* adminservice：后台管理系统后台
* brandserver：品牌管理模块
* gateserver：网关模块
* fileservice：图片上传模块
* common：通用工具模块
* mobileservice：移动端模块
* userservice：用户管理模块
* olisclient：后台管理系统前端
* olisclient-mobile：移动端前台

整个项目可以分为两部分：后台管理系统、移动端。

后台管理：

后台系统会采用前后端分离开发，而且整个后台管理系统会使用Vue.js框架搭建出单页应用（SPA）。

后台系统主要包含以下功能：
* 品牌管理:品牌增删查改
* 一级分类管理:一级车型增删查改
* 二级分类管理:二级车型增删查改
* 三级分类管理:三级车型增删查改
* 年限管理:发动机年限增删查改
* 发动机类型管理:发动机类型增删查改
* 发动机管理:根据品牌，一级分类，二级分类，三级分类，年限，发动机类型，油品信息增加一条匹配记录表，并可以进行删除，修改操作
* 保护油管理:润滑油的增删查改
* 图片基础管理:包含发动机图片和油品图片的增删查改
* 机器人管理:管理移动端设备的授权信息
* 角色管理:角色的增删查改
* 菜单管理：菜单的增删查改
* 管理员列表:用户的增删查改

预览图：

![](https://github.com/HelloEath/microolisserver/raw/master/imagesdesc/demo2.PNG)
![](https://github.com/HelloEath/microolisserver/raw/master/imagesdesc/demo3.PNG)

![](https://github.com/HelloEath/microolisserver/raw/master/imagesdesc/demo5.PNG)
![](https://github.com/HelloEath/microolisserver/raw/master/imagesdesc/demo7.PNG)
![](https://github.com/HelloEath/microolisserver/raw/master/imagesdesc/demo8.PNG)
![](https://github.com/HelloEath/microolisserver/raw/master/imagesdesc/demo9.PNG)
移动端基于app或是网页形式展现

预览图：
![](https://github.com/HelloEath/microolisserver/raw/master/imagesdesc/demo11.PNG)
![](https://github.com/HelloEath/microolisserver/raw/master/imagesdesc/mobile-demo1.png)
![](https://github.com/HelloEath/microolisserver/raw/master/imagesdesc/mobile-demo2.png.png)
![](https://github.com/HelloEath/microolisserver/raw/master/imagesdesc/mobile-demo3.png)
![](https://github.com/HelloEath/microolisserver/raw/master/imagesdesc/mobile-demo4.png)
![](https://github.com/HelloEath/microolisserver/raw/master/imagesdesc/mobile-demo5.png)
![](https://github.com/HelloEath/microolisserver/raw/master/imagesdesc/mobile-demo6.png)


# 6.Nginx配置

user  root;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;


events {
    worker_connections  1024;
}


http {
    include       mime.types;
    default_type  application/octet-stream;

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    gzip  on;
    gzip_min_length  1k;
    gzip_buffers     4 16k;
    gzip_http_version 1.1;
    gzip_comp_level 9;
    gzip_types       text/plain application/x-javascript text/css application/xml text/javascript application/x-httpd-php application/javascript application/json;
    gzip_disable "MSIE [1-6]\.";
    gzip_vary on;
	#设定负载均衡的服务器列表
    upstream myserver {
      ip_hash;       
      server 127.0.0.1:8106 ;
      server 127.0.0.1:8103 ;
  		}

    server {
        listen       8102;
        server_name  127.0.0.1;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;
 	# 不带数据的请求
        location /olisserver {
            alias   /home/olispage;
            index  index.html index.htm;
        }
	# 带数据的请求
 	location /api{
	proxy_set_header x-forwarded-for $remote_addr;
         proxy_pass http://myserver;
        }

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        #
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}

        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        #location ~ \.php$ {
        #    root           html;
        #    fastcgi_pass   127.0.0.1:9000;
        #    fastcgi_index  index.php;
        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        #    include        fastcgi_params;
        #}

        # deny access to .htaccess files, if Apache's document root
        # concurs with nginx's one
        #
        #location ~ /\.ht {
        #    deny  all;
        #}
    }


    # another virtual host using mix of IP-, name-, and port-based configuration
    #
    #server {
    #    listen       8000;
    #    listen       somename:8080;
    #    server_name  somename  alias  another.alias;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}


    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}

}
