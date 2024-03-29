# 图片分享网站
## 1. 引言
本次pj制作了一个在线旅游图片分享网站unsplash，是一个可以浏览、搜索、收藏各类旅游图片的平台。
## 2. 主要页面
### 主页
- 登录前显示登录按钮；登陆后显示个人中心+登出
- 轮播展示最热图片，分页展示最新图片
### 搜索
- 自主选择结果的排序方式：按标题/主题、按热度/时间
### 上传
- 上传图片并填写详细信息
### 我的照片
- 展示该用户上传的所有图片，能够进行删除和修改
### 收藏
- 收藏夹显示该用户收藏的所有图片，能够进行取消收藏操作
- 链接到“我的足迹”页面，显示最近浏览的至多十张图片的标题信息
### 我的好友
- 显示所有好友的用户名和邮箱
- 可以链接到好友的收藏夹
### bonus
- 项目说明文档
- 模糊搜索
- 验证码
- 用户评论
## 3. 技术
- 前端：使用了boostrap框架
- 后端：JSP+Servlet+JavaBean
- 服务器：Apache Tomcat 8.5
- 数据库：MySQL
## 4. 设计模式
- MVC：MVC设计模式贯穿于整个后台与前台功能开发始末
- Filter+Servlet+反射
- 模块化JSP设计：从大的JSP文件中，通过JSP包含关系抽象出多个公共文件，并把业务JSP按照功能，设计为多个小的JSP文件，便于维护和理解
## 5. 使用说明
1. 请务必确认当前环境JDK1.8
2. 导入SQL语句travel-contents/travel.sql：打开MySQL，创建数据库test，并执行test.sql建表语句<br/>
（注：账号、密码分别为root、123456，导入数据以及项目运行所使用的账号密码都是这个，如果不是，请修改mysql的root账号密码）
3. 完整项目下载：下载后将pj文件夹作为一整个项目导入eclipse
4. 访问：启动Tomcat，访问首页http://localhost:8080/OASDProject/index.jsp/
