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
*一本书可以提供多条借阅记录，一名用户也可以借阅多本图书。
![image](https://github.com/user-attachments/assets/a6ee2291-bf79-42c8-959d-df4530215de7)

---


## 4. MVC 设计模式详细设计

本系统后端遵循标准 **MVC 架构** 分层设计：

* **Entity (JavaBean)**：负责数据传输，封装数据库映射对象。
* **Controller**：处理请求与响应，调用业务逻辑。
* **Service**：封装业务逻辑。
* **DAO (Data Access Object)**：处理数据库交互。
* **View**：负责用户界面展示，包含登录、注册、图书展示等模块。

---

### （1）Entity 实体层

| 实体类            | 对应数据库表                 |
| -------------- | ---------------------- |
| `User`         | `users` 用户表            |
| `Book`         | `books` 图书表            |
| `BorrowRecord` | `borrow_records` 借阅记录表 |

---

### （2）Controller 控制器层

| 控制器类                           | 路径                               | 功能描述                   |
| ------------------------------ | -------------------------------- | ---------------------- |
| `LoginController`              | `/api/login`                     | 用户登录验证，创建会话（Session）   |
| `RegisterController`           | `/api/register`                  | 用户注册，检查用户名唯一性          |
| `LogoutController`             | `/api/logout`                    | 用户退出登录，销毁会话            |
| `UserInfoController`           | `/api/user-info`                 | 获取当前登录用户信息（用户名、角色）     |
| `UpdateUserController`         | `/api/update-user`               | 用户修改用户名或密码             |
| `BookController`               | `/api/books`                     | 用户图书列表查询（分页、模糊查询、分类过滤） |
| `BorrowController`             | `/api/borrow`                    | 图书借阅功能（检查状态、借阅逻辑）      |
| `MyBorrowsController`          | `/api/my-borrows`                | 当前用户借阅记录查询             |
| `AdminUserController`          | `/api/admin/users`               | 管理员查询所有用户信息            |
| `AdminBookController`          | `/api/admin/books`               | 管理员增删改查图书信息            |
| `AdminBorrowRecordsController` | `/api/admin/borrow-records`      | 管理员查看指定用户借阅记录          |
| `AdminReturnController`        | `/api/admin/return`              | 管理员执行还书操作              |
| `AdminStatController`          | `/api/admin/stat/category-count` | 图书分类统计分析               |

---

### （3）Service 业务逻辑层

| Service 类             | 功能描述          |
| --------------------- | ------------- |
| `UserService`         | 用户登录、注册、更新等逻辑 |
| `BookService`         | 图书查询、分类、分页等逻辑 |
| `BorrowBookService`   | 借阅图书逻辑处理      |
| `BorrowRecordService` | 借阅记录查看、还书处理   |
| `StatService`         | 图书分类统计服务逻辑    |

---

### （4）DAO 数据访问层

| DAO 类             | 说明                        |
| ----------------- | ------------------------- |
| `UserDao`         | 操作 `users` 用户表            |
| `BookDao`         | 操作 `books` 图书表            |
| `BorrowRecordDao` | 操作 `borrow_records` 借阅记录表 |
| `StatDao`         | 统计图书分类数据                  |

---

### （5）View 表现层

| Vue 页面组件         | 功能页面         |
| ---------------- | ------------ |
| `LoginView`      | 登录界面         |
| `RegisterView`   | 注册界面         |
| `BookList`       | 用户登录后的图书展示页面 |
| `MyBorrows`      | 用户借阅信息界面     |
| `ProfileView`    | 个人信息查看与修改页面  |
| `AdminDashboard` | 管理员后台主页面     |

![包图](https://github.com/user-attachments/assets/894fc44b-5035-46e8-8f8d-767c19ec7b7c)

---

## 5. 系统时序图

### 📘 用户端时序说明

* 用户登录/注册后进入图书列表界面；
* 支持模糊搜索、分类筛选、图书详情查看；
* 可借阅图书，借出后状态设为“不可借”；
* 用户头像下拉菜单可访问：借阅记录、个人信息修改等。

#### 图书管理系统用户端时序图

![用户端时序图（控制焦点完整）](https://github.com/user-attachments/assets/cd24a5b3-1dc4-4485-a320-bf0b7a8b73e1)

---

### 🛠 管理员端时序说明

* 管理员登录后进入后台；
* 可管理图书（增删改查）、查看用户与借阅信息；
* 可手动执行还书操作；
* 支持查看图书分类统计图表（柱状图/饼图）。

#### 图书管理系统管理员端时序图

![管理员端时序图（控制焦点完整）](https://github.com/user-attachments/assets/d6dfe874-dce8-4b8e-a395-b276f65ac802)

---


## 6. 前端项目架构搭建

### 🔧 创建前端项目

在终端中输入以下命令初始化 Vue3 项目：

```bash
vue create frontend
```

> 选择配置时选择 **Vue3** 和 **Router、Pinia、TypeScript（可选）**

---

### 📁 目录结构示意

```
frontend/
├── public/
├── src/
│   ├── assets/         # 静态资源
│   ├── components/     # Vue 组件
│   ├── views/          # 页面组件（LoginView、BookList 等）
│   ├── router/         # 路由配置
│   ├── utils/          # 工具类，如 request.js
│   └── App.vue
├── package.json
└── vue.config.js       # 本地代理配置
```

---

### ▶️ 启动前端项目

在前端项目根目录运行：

```bash
npm install
npm run serve
```

---


