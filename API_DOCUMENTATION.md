# 社区系统接口功能记录

> 2026-08-21 安全优化后，所有写接口需要 HTTP Basic 认证，统一响应新增 `code` 和 `traceId`，所有集合接口均支持 `page/size`（`size` 最大 100）。最新变更与迁移说明见 [SYSTEM_OPTIMIZATION.md](SYSTEM_OPTIMIZATION.md)。

## 访问方式

- 服务地址：`http://localhost:8080`
- Swagger UI：`http://localhost:8080/swagger-ui.html`
- OpenAPI JSON：`http://localhost:8080/api-docs`
- 统一响应字段：`success`、`message`、`data`、`timestamp`
- 分页参数：`page` 从 1 开始，`size` 默认为 10

## 接口清单

| 模块 | 方法 | 路径 | 功能 |
|---|---|---|---|
| 用户 | GET | `/api/users/{id}` | 用户详情 |
| 用户 | GET | `/api/users?keyword=&page=1&size=10` | 用户分页搜索 |
| 用户 | POST | `/api/users/search` | 兼容旧版搜索入口 |
| 用户 | POST | `/api/users/login` | 用户登录 |
| 用户 | POST | `/api/users` | 注册并加密密码 |
| 用户 | PUT | `/api/users/{id}` | 修改用户资料 |
| 用户 | PUT | `/api/users/{id}/password` | 修改密码 |
| 用户 | DELETE | `/api/users/{id}` | 删除用户 |
| 圈子 | GET | `/api/circles/{id}` | 圈子详情 |
| 圈子 | GET | `/api/circles/owner/{id}` | 用户创建的圈子 |
| 圈子 | GET | `/api/circles?keyword=&page=1&size=10` | 圈子分页搜索 |
| 圈子 | POST | `/api/circles/search` | 兼容旧版搜索入口 |
| 圈子 | POST | `/api/circles` | 创建圈子 |
| 圈子 | PUT | `/api/circles/{id}` | 修改圈子 |
| 圈子 | DELETE | `/api/circles/{id}` | 删除圈子 |
| 文章 | GET | `/api/articles?keyword=&page=1&size=10` | 文章分页搜索 |
| 文章 | GET | `/api/articles/{id}` | 文章详情 |
| 文章 | GET | `/api/articles/user/{id}` | 用户文章 |
| 文章 | GET | `/api/articles/circle/{id}` | 圈子文章 |
| 文章 | POST | `/api/articles` | 发布文章，ID 可自动生成 |
| 文章 | PUT | `/api/articles/{id}` | 修改文章 |
| 文章 | DELETE | `/api/articles/{id}` | 删除文章及其评论 |
| 评论 | GET | `/api/reviews/{id}` | 评论详情 |
| 评论 | GET | `/api/reviews/article/{id}` | 文章评论列表 |
| 评论 | POST | `/api/reviews` | 发表评论 |
| 评论 | PUT | `/api/reviews/{id}` | 修改评论 |
| 评论 | DELETE | `/api/reviews/{id}` | 删除评论 |
| 评论 | DELETE | `/api/reviews/article/{id}` | 删除文章全部评论 |
| 关注 | GET | `/api/follows/{userId}/followers` | 粉丝列表 |
| 关注 | GET | `/api/follows/{userId}/following` | 关注列表 |
| 关注 | GET | `/api/follows/status?followerId=10002&followedUserId=10001` | 关注状态 |
| 关注 | POST | `/api/follows/status` | 兼容旧版状态入口 |
| 关注 | POST | `/api/follows` | 新建关注关系 |
| 关注 | DELETE | `/api/follows?followerId=...&followedUserId=...` | 取消关注 |

`followerId` 表示关注者，`followedUserId` 表示被关注者。Swagger 页面内包含请求模型，可直接执行验证。

## 数据库与测试数据

执行 `database/init.sql` 可创建 `finally` 数据库、5 张表和测试数据。测试账号为 `10001`、`10002`、`10003`，密码均为 `123456`。默认连接为 MySQL `root/123456`，可用 `DB_URL`、`DB_USERNAME`、`DB_PASSWORD` 覆盖。数据库修改汇总、跨电脑初始化、Docker、备份与恢复示例见 [DATABASE_SETUP.md](DATABASE_SETUP.md)。

## 标准分层

```text
src/main/java/com/example/community
├─ controller  控制层及 Swagger 描述
├─ service     业务服务接口（控制层只依赖接口）
│  └─ impl     服务实现、业务逻辑和事务
├─ mapper      MyBatis 持久层接口
├─ entity      数据库实体和请求模型
├─ config      MyBatis、Swagger、密码配置
└─ utils       统一响应和异常处理
src/main/resources/mapper
└─ *Mapper.xml MyBatis 数据库字段映射
```

## 测试说明

`CommunityApplicationTests` 会启动完整 Spring 容器并连接 MySQL，验证用户登录与搜索、圈子详情与搜索、文章详情与搜索、评论列表、粉丝列表和关注状态。执行命令：

```shell
mvn test
```

最近一次验证结果：`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0`，`BUILD SUCCESS`。
