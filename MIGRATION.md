# Spring Boot 单体化迁移记录

## 迁移目标

将原 Spring Cloud 多服务项目合并为一个符合 Spring Boot 标准目录结构的应用，用 API 前缀代替多个服务端口，降低部署和服务间调用复杂度。

## 技术栈变更

| 项目 | 迁移前 | 迁移后 |
| --- | --- | --- |
| Java | 8 | 17 LTS |
| Spring Boot | 2.6.6 | 3.5.16 |
| MyBatis-Plus | 3.4.0 | 3.5.17 Boot 3 Starter |
| MySQL Driver | 8.0.30 手动版本 | Spring Boot BOM 管理 |
| API 文档 | Springfox 2.9.2 | springdoc-openapi 2.8.13 |
| 服务治理 | Eureka + Gateway | 单体应用内调用 |
| 密码散列 | MD5 | BCrypt |

选择 Spring Boot 3.5 是因为它是 Spring Boot 3 的最后一个次版本，拥有额外的长期商业支持周期；Java 17 是 LTS 版本，也是当前环境可直接验证的 Spring Boot 3.5 基线。

## 端口到 API 的映射

| 原模块/端口 | 新 API |
| --- | --- |
| user-service / 8101 | `/api/users` |
| userconcern-service / 8110 | `/api/concerns` |
| article-service / 8201 | `/api/articles` |
| circle-service / 8301 | `/api/circles` |
| review-service / 8401 | `/api/reviews` |
| getway-service / 8503 | 已移除，统一使用 8080 |
| eureka-server / 8888 | 已移除，不再需要服务注册 |

## 结构调整

- 使用标准的 `src/main/java`、`src/main/resources`、`src/test/java` 结构。
- 以业务领域组织 `article`、`circle`、`concern`、`review`、`user` 包。
- 删除多模块 POM、重复启动类、重复工具类、重复 CORS 配置、Eureka 和 Gateway 配置。
- 将原 HTTP 回环调用替换为 Spring Bean 之间的直接调用。
- 统一 `ApiResponse` 响应和全局参数异常处理。
- 加入 MyBatis-Plus 分页拦截器、Bean Validation 和 OpenAPI 文档。
- 数据库凭据及外部服务地址改由环境变量注入。
- 新增 `database/init.sql`，用于创建 MySQL 数据库、5 张业务表并写入可重复执行的测试数据。

## 兼容性提醒

- API 路径已改变，前端需按上表更新请求地址。
- 新注册用户密码使用 BCrypt；旧库中的 MD5 密码无法直接通过新登录校验，需执行一次密码重置或编写迁移流程。
- 用户、圈子、评论和关注关系表名保持不变；文章已从 Elasticsearch `article` 索引迁移为 MySQL `tb_article` 表。
- 文章搜索改用 MySQL `LIKE` 查询，不再需要 Elasticsearch 或 IK 分词器。
