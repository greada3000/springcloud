# Community API

社区后端已由多个 Spring Cloud 服务合并为单体 Spring Boot 应用。

## 环境

- JDK 17 LTS
- Maven 3.6.3+
- MySQL 8
- Elasticsearch（需安装 IK 分词插件，以兼容原 `article` 索引）

## 启动

```powershell
$env:DB_PASSWORD = "your-password"
mvn spring-boot:run
```

服务默认运行于 `http://localhost:8080`，Swagger UI 位于 `http://localhost:8080/swagger-ui.html`。

可通过 `DB_URL`、`DB_USERNAME`、`DB_PASSWORD`、`ELASTICSEARCH_URIS`、`SERVER_PORT` 和 `CORS_ALLOWED_ORIGINS` 环境变量覆盖配置。
