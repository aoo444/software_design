# 智慧校园能耗监测系统

## 一、项目背景
当前校园能源管理多依赖人工统计，存在能耗数据滞后、异常难以及时发现、管理效率低等问题。本项目旨在开发一套**智慧校园能耗监测系统**，实现校园电表能耗数据的自动模拟采集、实时异常告警、权限化管理与可视化展示，帮助校园高效管控能源消耗、及时发现能耗异常，支撑绿色校园的节能决策。


## 二、技术栈
### 1. 后端技术栈
| 技术/框架       | 版本       | 用途说明                     |
|----------------|------------|------------------------------|
| Spring Boot    | 3.2.x      | 后端核心开发框架，快速搭建项目 |
| Spring Security| 6.2.x      | 实现用户权限认证与接口授权     |
| MyBatis-Plus   | 3.5.x      | 简化数据库CRUD操作            |
| JWT（JJWT）    | 0.11.5     | 生成无状态登录Token           |
| MySQL          | 8.0.x      | 存储系统数据（楼栋、电表、能耗等）|
| Maven          | 3.8.x      | 后端依赖管理与项目构建        |


### 2. 前端技术栈
| 技术/框架       | 版本       | 用途说明                     |
|----------------|------------|------------------------------|
| Vue            | 3.3.x      | 前端页面开发核心框架          |
| Vue Router     | 4.2.x      | 前端页面路由管理              |
| Axios          | 1.6.x      | 前后端接口数据交互            |
| ECharts        | 5.4.x      | 能耗数据可视化（折线图、统计图表）|
| Element Plus   | 2.4.x      | 快速构建前端UI界面            |
| Node.js        | 16.x       | 前端项目运行环境              |


## 三、数据库配置
本项目使用MySQL作为数据库，配置步骤如下：

1. **创建数据库**
打开MySQL客户端（如Navicat、命令行），执行SQL创建项目数据库：
```sql
CREATE DATABASE IF NOT EXISTS energy_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```


2. **修改后端数据库配置**
进入后端项目目录，打开`src/main/resources/application.yml`文件，修改以下配置（替换为你的MySQL信息）：
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    # 替换为你的MySQL地址、数据库名
    url: jdbc:mysql://localhost:3306/energy_db?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    # 替换为你的MySQL用户名
    username: root
    # 替换为你的MySQL密码
    password: 123456
```


## 四、后端项目启动
1. **拉取项目代码**
```bash
git clone 你的项目仓库地址
cd 项目根目录/backend  # 进入后端子目录
```

2. **安装后端依赖**
```bash
mvn clean install  # 通过Maven安装依赖
```

3. **启动后端项目**
```bash
mvn spring-boot:run
```

启动成功后，后端服务默认运行在`http://localhost:8080`。


## 五、前端项目启动
1. **进入前端子目录**
```bash
cd 项目根目录/frontend  # 回到项目根目录，进入前端子目录
```

2. **安装前端依赖**
```bash
npm install  # 通过npm安装前端依赖
```

3. **启动前端项目**
```bash
npm run dev
```

启动成功后，打开浏览器访问所给地址，即可进入系统页面（初始账号：admin/123456）。
