
**本地部署**
- 通过git下载源码
- 创建数据库,数据库编码为UTF-8
- 执行sql文件，初始化数据
- 修改application.properties，更新数据库连接，redis连接,mongodb连接的账号和密码
- Eclipse、IDEA运行Application.java，则可启动项目
- Swagger路径：http://localhost:8080/swagger-ui.html


web包下提供了两个测试Demo
- DemoInfoController 用于测试mongodb
- DemoController 用于测试mysql数据库及redis