# MySQL 数据库修改汇总与本地运行指南

> 2026-08-21 起，应用使用 Flyway 管理表结构，基础配置不再提供数据库密码默认值，测试数据已拆分到 `database/seed-dev.sql`。本文件中的 root/123456 示例仅为旧版记录；请优先遵循 [SYSTEM_OPTIMIZATION.md](SYSTEM_OPTIMIZATION.md) 和 README 的最小权限账号配置。

本文档记录项目数据库的最终结构、历史修改和在其他计算机上的初始化方法。项目不使用 Elasticsearch，文章、用户、圈子、评论和关注数据全部保存在 MySQL 中。

## 1. 最终数据库配置

| 配置 | 默认值 |
|---|---|
| MySQL 版本 | 8.x，推荐 8.4 LTS |
| 数据库 | `finally` |
| 字符集 | `utf8mb4` |
| 排序规则 | `utf8mb4_unicode_ci` |
| 地址 | `localhost:3306` |
| 用户名 | `root` |
| 密码 | `123456` |

Spring Boot 默认 JDBC 地址：

```text
jdbc:mysql://localhost:3306/finally?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
```

生产环境不要继续使用示例 root 密码，应通过 `DB_USERNAME` 和 `DB_PASSWORD` 环境变量传入独立数据库账号。

## 2. 数据库修改汇总

| 修改项 | 最终结果 |
|---|---|
| Elasticsearch 文章索引 | 已移除，改为 MySQL `tb_article` |
| 用户数据 | `tb_user`，密码由 MD5 改为 BCrypt |
| 圈子数据 | `tb_circle` |
| 评论数据 | `tb_review` |
| 旧关注表 | `tb_userconcern` 改名为 `tb_user_follow` |
| 旧关注字段 | `concern_id/preuser/lastuser` 改为 `follow_id/follower_id/followed_user_id` |
| 搜索 | Elasticsearch 搜索改为 MySQL `LIKE` 与 MyBatis 分页 |
| 初始化 | `database/init.sql` 可重复执行并补齐测试数据 |

最终包含 5 张表：

| 表 | 主键 | 主要字段 | 用途 |
|---|---|---|---|
| `tb_user` | `user_id` | `username/password/usertype/userpic` | 用户与登录 |
| `tb_circle` | `circle_id` | `owner/circle_name/detail` | 圈子 |
| `tb_article` | `article_id` | `title/user_id/username/circle_id/content` | 文章与搜索 |
| `tb_review` | `review_id` | `owner_id/article_id/content` | 文章评论 |
| `tb_user_follow` | `follow_id` | `follower_id/followed_user_id` | 用户关注关系 |

## 3. 全新计算机初始化示例

以下方式任选一种，不要重复执行不同方式创建多个 MySQL 实例。

### 示例 A：Windows PowerShell + 本机 MySQL

在项目根目录执行：

```powershell
& 'C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe' --default-character-set=utf8mb4 -uroot -p123456 -e "source database/init.sql"
mvn spring-boot:run
```

如果 MySQL 已加入 `PATH`：

```powershell
mysql --default-character-set=utf8mb4 -uroot -p123456 -e "source database/init.sql"
$env:DB_USERNAME = 'root'
$env:DB_PASSWORD = '123456'
mvn spring-boot:run
```

### 示例 B：Linux 或 macOS + 本机 MySQL

```bash
mysql --default-character-set=utf8mb4 -uroot -p123456 < database/init.sql
export DB_USERNAME=root
export DB_PASSWORD=123456
mvn spring-boot:run
```

### 示例 C：Docker Compose（推荐用于新电脑）

只需要安装 Docker Desktop，然后执行：

```powershell
cd database
docker compose up -d
docker compose ps
cd ..
mvn spring-boot:run
```

首次创建容器时会自动执行 `init.sql`。数据保存在 Docker volume `community_mysql_data` 中，停止容器不会丢失：

```powershell
docker compose -f database/docker-compose.yml stop
docker compose -f database/docker-compose.yml start
```

若明确需要删除容器及全部数据库数据后重新初始化：

```powershell
docker compose -f database/docker-compose.yml down -v
docker compose -f database/docker-compose.yml up -d
```

注意：`down -v` 会永久删除该 Compose 项目的数据库卷，仅适用于可丢弃的本地测试数据。

### 示例 D：在 IntelliJ IDEA 中初始化

1. 打开 `View → Tool Windows → Database`。
2. 新建 MySQL 数据源，Host 填 `localhost`，Port 填 `3306`，User 填 `root`，Password 填 `123456`。
3. 测试连接成功后，打开 `database/init.sql`。
4. 选择刚创建的数据源并执行整个脚本。
5. 运行 `CommunityApplication`。

## 4. 使用其他端口或密码

### PowerShell 示例

```powershell
$env:DB_URL = 'jdbc:mysql://127.0.0.1:3307/finally?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true'
$env:DB_USERNAME = 'root'
$env:DB_PASSWORD = 'your-password'
$env:SERVER_PORT = '9090'
mvn spring-boot:run
```

### Bash 示例

```bash
DB_URL='jdbc:mysql://127.0.0.1:3307/finally?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true' \
DB_USERNAME=root DB_PASSWORD='your-password' SERVER_PORT=9090 \
mvn spring-boot:run
```

### IDEA 环境变量示例

在 `Run → Edit Configurations → Environment variables` 中添加：

```text
DB_URL=jdbc:mysql://localhost:3306/finally?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true;DB_USERNAME=root;DB_PASSWORD=123456;SERVER_PORT=8080
```

## 5. 旧数据库升级

只有旧库仍存在 `tb_userconcern` 时，才执行：

```powershell
mysql --default-character-set=utf8mb4 -uroot -p123456 finally < database/migrate_user_follow.sql
```

先检查表名：

```sql
USE finally;
SHOW TABLES LIKE 'tb_userconcern';
SHOW TABLES LIKE 'tb_user_follow';
```

- 只有 `tb_userconcern`：执行迁移脚本。
- 已有 `tb_user_follow`：不要执行迁移脚本。
- 全新电脑：直接执行 `init.sql`，不需要执行迁移脚本。

## 6. 验证数据库和项目

检查表和测试数据：

```sql
USE finally;
SHOW TABLES;
SELECT COUNT(*) AS users FROM tb_user;
SELECT COUNT(*) AS circles FROM tb_circle;
SELECT COUNT(*) AS articles FROM tb_article;
SELECT COUNT(*) AS reviews FROM tb_review;
SELECT COUNT(*) AS follows FROM tb_user_follow;
SELECT user_id, username, usertype FROM tb_user ORDER BY user_id;
```

命令行一次验证：

```powershell
mysql --default-character-set=utf8mb4 -uroot -p123456 -D finally -e "SHOW TABLES; SELECT COUNT(*) AS users FROM tb_user; SELECT COUNT(*) AS articles FROM tb_article;"
mvn test
```

预置用户为 `10001`、`10002`、`10003`，登录密码均为 `123456`。启动成功后可打开：

- Swagger UI：`http://localhost:8080/swagger-ui.html`
- OpenAPI JSON：`http://localhost:8080/api-docs`

登录接口示例：

```powershell
Invoke-RestMethod -Method Post -Uri 'http://localhost:8080/api/users/login' -ContentType 'application/json' -Body '{"userId":10001,"password":"123456"}'
```

文章搜索示例：

```powershell
Invoke-RestMethod 'http://localhost:8080/api/articles?keyword=Spring%20Boot&page=1&size=10'
```

## 7. 备份与迁移到另一台电脑

旧电脑导出：

```powershell
mysqldump -uroot -p123456 --default-character-set=utf8mb4 --databases finally > finally-backup.sql
```

将项目和 `finally-backup.sql` 复制到新电脑后导入：

```powershell
mysql --default-character-set=utf8mb4 -uroot -p123456 < finally-backup.sql
mvn test
mvn spring-boot:run
```

若只需要演示数据，不需要保留旧电脑产生的数据，复制项目后直接执行 `database/init.sql` 即可。

## 8. 常见问题

- `Access denied for user 'root'`：实际 root 密码与 `123456` 不同，设置 `DB_PASSWORD`。
- `Communications link failure`：确认 MySQL 已启动，端口与 `DB_URL` 一致。
- `Unknown database 'finally'`：尚未执行 `init.sql`。
- 中文乱码：确认数据库、连接和终端均使用 UTF-8；表应为 `utf8mb4`。
- Docker 的 `init.sql` 没有再次执行：入口脚本只在数据卷首次创建时运行，可手动执行 SQL，或在确认数据可删除后使用 `down -v`。
