# Community API

社区后端已由多个 Spring Cloud 服务合并为单体 Spring Boot 应用。

## 环境

- JDK 17 LTS
- Maven 3.6.3+
- MySQL 8

## 启动

```powershell
$env:DB_USERNAME = 'community_app'
$env:DB_PASSWORD = 'your-strong-password'
mvn spring-boot:run
```

应用启动时由 Flyway 自动创建或升级表结构。数据库本身和最小权限应用账号需提前创建；本地开发可直接使用：

```powershell
docker compose -f database/docker-compose.yml up -d --wait
$env:DB_USERNAME = 'community_app'
$env:DB_PASSWORD = 'local-community-password'
mvn spring-boot:run
```

服务默认运行于 `http://localhost:8080`，Swagger UI 位于 `http://localhost:8080/swagger-ui.html`。

`DB_PASSWORD` 不再提供代码内默认值。可通过 `DB_URL`、`DB_USERNAME`、`DB_PASSWORD`、`SERVER_PORT`、`CORS_ALLOWED_ORIGINS` 和 `SWAGGER_ENABLED` 环境变量配置服务，生产环境应设置 `SWAGGER_ENABLED=false`。

本地 Compose 会加载开发种子数据：账号 `10001`、`10002`、`10003`，密码均为 `123456`。该种子脚本不得用于生产。

读取接口公开；写接口使用 HTTP Basic。用户名为用户编号，例如 PowerShell：

```powershell
$credential = [Convert]::ToBase64String([Text.Encoding]::ASCII.GetBytes('10002:123456'))
Invoke-RestMethod -Method Post -Uri 'http://localhost:8080/api/articles' `
  -Headers @{ Authorization = "Basic $credential" } -ContentType 'application/json' `
  -Body '{"title":"示例文章","circleId":1002,"content":"正文"}'
```

生产环境必须通过 HTTPS 暴露服务，不能在明文 HTTP 上传输 Basic 凭据。

完整的数据库结构、历史修改、Windows/Linux/macOS/Docker/IDEA 初始化示例，以及备份迁移方法见 [DATABASE_SETUP.md](DATABASE_SETUP.md)。
