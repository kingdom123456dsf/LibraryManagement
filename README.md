# 基于MVC设计模式的在线图书馆管理系统
vue3 + ElementPlus  + Java Servlet

---

## 1. 环境配置

### 后端环境

| 工具 / 技术       | 版本      | 说明                           |
| ------------- | ------- | ---------------------------- |
| IntelliJ IDEA | 2023    | 主开发工具（IDE）                   |
| JDK           | 21.0.5  | Java 编译和运行环境                 |
| Tomcat        | 10.1.36 | Servlet 容器（兼容 Jakarta EE 9）  |
| Jakarta EE    | 9.1     | Java 企业版 API（支持 Servlet 5.0） |
| Servlet API   | 5.0.0   | 基于 Jakarta 命名空间的 Servlet 规范  |
| Jackson       | 最新版     | 用于 Java 对象与 JSON 的互转         |
| HikariCP      | 最新版     | 高性能数据库连接池                    |
| MySQL         | 8.0.39  | 后端数据库                        |

### 前端环境

| 工具 / 技术  | 版本      | 说明               |
| -------- | ------- | ---------------- |
| Node.js  | 20.13.1 | JavaScript 运行时环境 |
| npm      | 10.5.2  | Node 包管理工具       |
| Vue      | 3.x     | 现代前端框架           |
| @vue/cli | 5.0.8   | 快速创建 Vue 项目      |

---

## 2. 总体设计思路

本系统采用前后端分离架构，结合 Vue3 + ElementPlus + Java Servlet 构建，遵循 MVC 模式，支持基本借阅功能与管理功能。

### （1）MVC 三层架构

* **Entity（实体类）**：

  * 封装系统核心业务实体（如用户、图书、借阅记录等），映射数据库结构，支持数据传输与持久化。

* **Controller（控制器）**：

  * Servlet 接收前端请求，调用 Service 层处理业务逻辑，并通过 Jackson 返回 JSON 响应。

* **View（前端页面）**：

  * 使用 Vue3 + ElementPlus 构建响应式 UI，Axios 实现异步通信。

---

### （2）关键技术实现

#### 后端部分

* Java Servlet + Jakarta EE：处理请求响应，担任控制器角色
* MVC 架构：分离控制、业务、数据访问
* HikariCP：数据库连接池，提升效率
* Jackson：实现对象与 JSON 的转换
* Session + Filter：实现登录控制与中文编码处理
* CorsFilter：跨域响应头处理
* Listener：记录 Session 的创建与销毁
* MySQL：存储用户、图书、借阅记录等信息
* Maven：项目依赖管理

#### 前端部分

* Vue3：构建 SPA 应用
* ElementPlus：组件库（表格、弹窗等）
* Vue Router：路由控制
* Axios：异步 HTTP 请求
* ECharts：图书统计图表展示
* vue.config.js：配置本地代理解决跨域

---

### （3）数据交互流程

* 前端通过 Axios 请求后端 REST 接口（如 /api/books、/api/borrow）
* 后端 Controller解析请求，调用 Service 层执行业务逻辑
* Service 层通过 DAO 操作数据库，返回数据模型（JavaBean）
* Controller将结果封装为 JSON，响应给前端
* 前端根据返回的数据动态渲染页面（图书列表、弹窗等）

---

### （4）核心模块说明

#### 前端注册用户：

* 用户注册 / 登录
* 图书浏览、分类筛选、模糊搜索（分页）
* 借阅图书 / 查看借阅信息
* 个人信息查看与修改

#### 后台管理员功能：

* 登录后台
* 图书增删改查（分页 + 分类）
* 查看用户借阅记录，手动还书
* 图书统计图表（柱状图 / 饼图）

---

## 3. 数据库表结构设计

### `users` 用户表

| 字段名      | 类型      | 说明              |
| -------- | ------- | --------------- |
| id       | INT, PK | 用户 ID（自增）       |
| username | VARCHAR | 用户名（唯一）         |
| password | VARCHAR | 密码              |
| role     | VARCHAR | 角色：admin / user |

> ⚠️ `role` 字段用于权限控制

---

### `books` 图书表

| 字段名         | 类型      | 说明        |
| ----------- | ------- | --------- |
| id          | INT, PK | 图书 ID（自增） |
| title       | VARCHAR | 书名        |
| author      | VARCHAR | 作者        |
| isbn        | VARCHAR | ISBN 编号   |
| description | TEXT    | 简介        |
| available   | BOOLEAN | 是否可借      |
| image       | VARCHAR | 封面图地址     |
| category    | VARCHAR | 图书分类      |

> ⚠️ `available` 用于控制是否可借；`category` 便于分类筛选。

---

### `borrow_records` 借阅记录表

| 字段名          | 类型       | 说明                 |
| ------------ | -------- | ------------------ |
| id           | INT, PK  | 借阅记录 ID（自增）        |
| user\_id     | INT, FK  | 借阅人 ID（关联 users 表） |
| book\_id     | INT, FK  | 图书 ID（关联 books 表）  |
| borrow\_time | DATETIME | 借阅时间               |
| return\_time | DATETIME | 归还时间（可为空）          |
| returned     | BOOLEAN  | 是否归还               |

> 💡 通过该表实现用户与图书的多对多关联关系

---


