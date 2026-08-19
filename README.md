# Community API

社区后端已由多个 Spring Cloud 服务合并为单体 Spring Boot 应用。

## 环境

- JDK 17 LTS
- Maven 3.6.3+
- MySQL 8

## 启动

```powershell
& 'C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe' --default-character-set=utf8mb4 -uroot -p123456 -e "source database/init.sql"
mvn spring-boot:run
```

服务默认运行于 `http://localhost:8080`，Swagger UI 位于 `http://localhost:8080/swagger-ui.html`。

可通过 `DB_URL`、`DB_USERNAME`、`DB_PASSWORD`、`SERVER_PORT` 和 `CORS_ALLOWED_ORIGINS` 环境变量覆盖配置。

测试账号为 `10001`、`10002`、`10003`，密码均为 `123456`。

完整的数据库结构、历史修改、Windows/Linux/macOS/Docker/IDEA 初始化示例，以及备份迁移方法见 [DATABASE_SETUP.md](DATABASE_SETUP.md)。
