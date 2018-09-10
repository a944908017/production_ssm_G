#production_ssm_G
这是一个SSM（Spring+SpringMVC+Mybatis）+jQuery EasyUI开发的生产管理ERP系统。主要包括：计划进度、设备管理、工艺监控、物料监控、人员监控、质量监控、系统管理7大模块。

##项目技术架构(Spring+SpringMVC+Mybatis）
+ Maven
+ Spring（IOC DI AOP 声明式事务处理）
+ SpringMVC（支持Restful风格）
+ Hibernate Validator（参数校验）
+ Mybatis（最少配置方案）
+ shiro权限控制,结合ajax实现了异步认证与异步授权，同时实现了细粒度的权限动态分配（到按钮级别）；添加了shiro session过期的登录跳转
+ jQuery EasyUI开发前端页面，利用jQuery文件上传插件实现拖拽上传的效果并对文件类型、大小、数量进行控制；利用search-box实现查找功能
+ Druid（数据源配置 sql防注入 sql性能监控)
+ 统一的异常处理
+ JSP JSTL JavaScript
+ kindeditor富文本编辑器，处理图片上传和富文本编辑