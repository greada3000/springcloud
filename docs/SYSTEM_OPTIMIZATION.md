# 社区后端系统优化实施记录

> 实施日期：2026-08-21  
> 依据文档：`BACKEND_SYSTEM_ASSESSMENT.md`  
> 优化基线：Spring Boot 3.5.16、Java 17、MyBatis、MySQL 8

## 1. 优化结果摘要

本轮优先处理评估中的 P0 和 P1 风险，并补充部分 P2 工程能力。系统已从“匿名 CRUD 原型”提升为具备身份认证、资源所有权、数据库约束、统一错误语义、分页边界、版本化迁移、健康检查、可追踪日志和持续集成基础的后端服务。

本轮完成的核心结果：

- 所有写接口默认需要 Spring Security HTTP Basic 认证。
- 普通用户只能修改自己或自己创建的资源；管理员可以执行受控管理操作。
- 客户端提交的作者、所有者和关注者 ID 不再可信，统一由服务端认证主体覆盖。
- 注册接口不能通过请求体直接创建管理员。
- 数据库不再内置 root/123456 默认连接，生产必须显式提供密码。
- Flyway 管理数据库版本，并增加用户、圈子、文章、评论和关注关系的外键约束。
- 开发测试数据与结构迁移分离，生产迁移不会创建已知测试管理员。
- 所有集合接口均分页，`page >= 1`，`1 <= size <= 100`。
- 错误响应具有稳定错误码、字段校验详情和 `traceId`，并使用更准确的 400/401/403/404/409/500 状态码。
- 文章按创建时间稳定倒序，返回创建与更新时间。
- 新增 Actuator 健康检查、JVM/HTTP/连接池指标、优雅停机和请求 traceId。
- 新增非 root 容器镜像和 GitHub Actions 干净构建流水线。
- 自动化测试由 1 项增加到 3 项，覆盖核心读取、匿名写入拦截和分页错误契约。

优化后静态生产就绪度估计由 **42/100 提升至约 72/100**。该估计不替代压测、渗透测试、恢复演练和生产验收。

## 2. 按评估项的整改状态

| ID | 原优先级 | 状态 | 实施结果 |
|---|---|---|---|
| SEC-01 | P0 | 已完成核心整改 | Spring Security 无状态 HTTP Basic；写接口认证；RBAC 与资源所有权校验 |
| SEC-02 | P0 | 已完成 | 移除 root/123456 应用默认值；默认应用账号为 `community_app`；密码必须显式注入 |
| SEC-03 | P0 | 已完成 | 测试种子迁移到 `database/seed-dev.sql`，Flyway 生产迁移不写测试账号 |
| DATA-01 | P1 | 已完成 | Flyway V2 添加外键；用户/圈子采用 RESTRICT，文章评论 CASCADE，关注关系 CASCADE |
| API-01 | P1 | 已完成核心整改 | 服务端覆盖 owner/userId/ownerId/followerId；更新时禁止转移资源归属 |
| API-02 | P1 | 已完成 | 统一错误码、字段错误、traceId 和准确 HTTP 状态码；创建返回 201 |
| PERF-01 | P1 | 已完成 | 搜索和所有关联列表均分页，统一限制最大 100 条 |
| TEST-01 | P1 | 部分完成 | 增加安全与错误契约集成测试；尚未接入 Testcontainers 和完整写链路测试 |
| OPS-01 | P1 | 已完成基线 | Actuator、探针、指标、优雅停机、traceId 与异常日志 |
| DATA-02 | P1 | 已完成 | 引入 Flyway，提供 V1 核心结构和 V2 引用完整性迁移 |
| SEC-04 | P1 | 部分完成 | CORS 已实际注册；Swagger 可由环境变量关闭；尚未实现登录限流/锁定 |
| REL-01 | P2 | 未实施 | 仍需为并发更新增加版本字段与乐观锁 |
| PERF-02 | P2 | 部分完成 | 文章映射时间字段、按时间稳定排序并在新库建立联合索引；尚未压测与分析执行计划 |
| OPS-02 | P2 | 已完成基线 | 新增 Dockerfile、非 root 运行、`.dockerignore` 和 GitHub Actions CI |
| REL-02 | P2 | 待运维执行 | 备份、RPO/RTO 和恢复演练不能仅靠代码完成 |
| API-03 | P2 | 未实施 | 兼容 POST 搜索接口仍保留，尚未建立正式 API v1 下线计划 |
| PERF-03 | P3 | 未实施 | 未在缺乏压测证据时改造 UUID 或引入全文搜索 |

## 3. 认证与授权

### 3.1 访问规则

| 请求类型 | 访问规则 |
|---|---|
| `GET /api/**` | 公开读取 |
| `POST /api/users` | 公开注册，但只能注册普通用户 |
| `POST /api/users/login` | 公开凭据校验 |
| 兼容搜索 POST | 公开 |
| 其他 POST/PUT/DELETE | 必须认证 |
| 健康检查和 API 文档 | 默认公开；生产应关闭 Swagger 并由网关限制管理端点 |

认证用户名为用户编号，密码为用户密码。示例：

```http
Authorization: Basic base64(userId:password)
```

Spring Security 会在每次请求时从数据库加载用户，`usertype=true` 映射为 `ROLE_ADMIN`，否则映射为 `ROLE_USER`。

### 3.2 所有权规则

- 用户资料、密码和删除：本人或管理员。
- 圈子修改和删除：圈主或管理员；创建时 `owner` 由认证主体设置。
- 文章修改和删除：作者或管理员；创建时 `userId/username` 由服务端设置。
- 评论修改和删除：评论作者或管理员；创建时 `ownerId` 由服务端设置。
- 批量删除文章评论：文章作者或管理员。
- 关注和取消关注：关注者必须是当前用户；管理员可执行管理性取消操作。

### 3.3 部署要求

HTTP Basic 会在每次请求携带可解码的凭据，因此**生产必须使用 HTTPS**，并应在反向代理层启用 HSTS、TLS 1.2+ 和请求限流。若后续需要第三方客户端、单点登录或长会话，应迁移到 OAuth 2.1/OIDC 或短期访问令牌 + 可撤销刷新令牌。

## 4. API 契约变化

统一响应由原来的四个字段扩展为：

```json
{
  "success": false,
  "code": "ARTICLE_NOT_FOUND",
  "message": "文章不存在",
  "data": null,
  "traceId": "03d9f0cfbc174740bc796cedad71ca55",
  "timestamp": "2026-08-21T11:00:00Z"
}
```

主要状态语义：

| 状态码 | 场景 |
|---:|---|
| 201 | 创建成功 |
| 400 | 参数或分页校验错误 |
| 401 | 未提供有效认证信息或登录凭据错误 |
| 403 | 已认证但无资源操作权限 |
| 404 | 用户、圈子、文章或评论不存在 |
| 409 | 唯一键冲突、重复关注或资源仍被引用 |
| 500 | 未预期服务端错误；详情只写日志，不返回堆栈 |

所有列表接口现返回 `PageResult`，包含 `records/total/size/current/pages`。以下原无界列表也需要 `page` 和 `size`，参数省略时为 1 和 10：

- `/api/articles/user/{id}`
- `/api/articles/circle/{id}`
- `/api/circles/owner/{id}`
- `/api/reviews/article/{id}`
- `/api/follows/{userId}/followers`
- `/api/follows/{userId}/following`

注册密码和新密码要求 12–72 个字符。历史开发账号的短密码只用于兼容本地种子数据，不符合生产密码策略。

## 5. 数据库优化与迁移

### 5.1 迁移文件

- `V1__create_core_schema.sql`：创建五张核心表、时间字段和查询索引。
- `V2__add_referential_integrity.sql`：添加外键和明确删除策略。
- `database/seed-dev.sql`：独立的本地演示数据，不在 Flyway locations 中。

### 5.2 删除策略

| 关系 | 策略 | 原因 |
|---|---|---|
| 用户 → 圈子/文章/评论 | RESTRICT | 防止删除用户时静默丢失内容 |
| 圈子 → 文章 | RESTRICT | 圈子有文章时需先显式迁移或删除文章 |
| 文章 → 评论 | CASCADE | 评论生命周期依附文章 |
| 用户 → 关注关系 | CASCADE | 用户删除后关注边自动清理 |

### 5.3 既有数据库升级步骤

1. 完整备份并验证备份可以还原。
2. 查询并清理孤儿用户、圈子、文章、评论和关注关系，否则 V2 外键迁移会失败。
3. 在测试环境使用生产数据副本执行 Flyway。
4. 验证行数、外键、核心查询和回滚方案。
5. 安排生产维护窗口，部署时保持 `FLYWAY_ENABLED=true`。

Flyway 的 MySQL DDL 不是完全事务性的。如果 V2 中途失败，应检查已创建的约束后人工修复，再执行 `flyway repair`；不得盲目重复 ALTER。

## 6. 配置与启动

最低必需环境变量：

```text
DB_USERNAME=community_app
DB_PASSWORD=<strong-secret>
```

推荐生产配置：

```text
DB_URL=jdbc:mysql://db:3306/finally?useUnicode=true&characterEncoding=utf8&useSSL=true&serverTimezone=Asia/Shanghai
DB_USERNAME=community_app
DB_PASSWORD=<secret-manager-injected>
CORS_ALLOWED_ORIGINS=https://community.example.com
SWAGGER_ENABLED=false
DB_POOL_MAX_SIZE=20
```

本地启动：

```powershell
docker compose -f database/docker-compose.yml up -d --wait
$env:DB_USERNAME = 'community_app'
$env:DB_PASSWORD = 'local-community-password'
mvn spring-boot:run
```

容器构建：

```powershell
docker build -t community-api:1.1.0 .
```

镜像使用 Java 17 JRE，并以 UID 10001 的非 root 用户运行。

## 7. 可观测性

- 每个请求接受或生成 `X-Trace-Id`，响应头和响应体会返回相同 traceId。
- 日志 pattern 自动记录 traceId；未处理异常在服务端记录完整堆栈，客户端仅看到稳定错误码。
- `/actuator/health/liveness`：进程存活探针。
- `/actuator/health/readiness`：服务就绪探针。
- `/actuator/metrics`：JVM、HTTP、HikariCP 等运行指标。
- 服务开启优雅停机，关闭阶段最多等待 30 秒。

生产环境应由网关、管理端口或网络策略限制 `/actuator`，当前安全链只匿名开放 health 和 info，metrics 需要认证。

## 8. 构建与测试结果

本轮实际执行：

```text
mvn compile
BUILD SUCCESS

mvn test
Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

测试时设置 `FLYWAY_ENABLED=false`，避免修改已有本机测试数据库，只执行读取与 MockMvc 安全契约验证。覆盖内容：

1. 用户、圈子、文章、评论、关注五个业务模块的 MySQL 读取。
2. 匿名发布文章返回 401 和 `UNAUTHORIZED`。
3. `page=0` 返回 400 和 `INVALID_PAGE`。

一次增量测试曾因 `target/classes` 中残留已删除的 MyBatis-Plus 配置而失败；执行 `mvn clean test` 后清除。CI 已固定使用 clean build，避免旧字节码污染。

当前环境没有 Docker CLI，因此本轮未实际构建镜像或执行 Compose；Dockerfile、Compose 和 CI 已完成静态复核，仍需由 CI 首次运行验证镜像构建链路。

## 9. 尚未完成的风险与后续优先级

### 下一批 P1

- 为 HTTP Basic 和 `/login` 增加 IP + 账号维度限流、失败次数锁定与安全审计。
- 使用 Testcontainers MySQL 取代对本机固定数据库和种子数据的依赖。
- 增加认证用户成功写入、跨用户 403、管理员操作、外键冲突和事务回滚测试。
- 将实体与创建/更新/响应 DTO 完全拆分。目前已通过服务端覆盖身份字段防止越权，但 DTO 分离仍能进一步收紧接口面。
- 在反向代理或网关落地 HTTPS、HSTS、安全响应头和请求体大小限制。

### P2

- 为用户、圈子、文章和评论增加 `version` 乐观锁，冲突返回 409。
- 对目标数据规模运行 `EXPLAIN ANALYZE`、负载测试、峰值测试和稳定性测试。
- 建立 API `/v1` 与兼容 POST 搜索接口的下线日期。
- 建立自动备份、异地保存、RPO/RTO、恢复演练和发布回滚手册。
- 接入依赖漏洞扫描、SAST、容器镜像扫描和密钥扫描。

### P3

- 只有在压测证明需要时，评估 MySQL FULLTEXT、外部搜索、缓存或 UUID 存储改造。
- 当前体量继续保持单体，不建议为了形式重新拆分微服务。

## 10. 验收建议

本轮代码已经通过本地构建与基础测试，但生产发布前仍必须完成：

- 在预发布环境执行 Flyway 全量迁移与脏数据检查。
- 使用非 root 最小权限数据库账号验证启动、迁移和运行权限。
- 对所有写接口执行匿名、越权、管理员和资源不存在四类测试。
- 完成 HTTPS、CORS、Swagger 关闭和管理端点网络隔离验证。
- 完成依赖漏洞扫描、性能测试、备份恢复和回滚演练。

只有上述生产验收完成，才能把本次“代码层优化完成”认定为“系统生产上线完成”。
