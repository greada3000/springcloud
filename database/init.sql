CREATE DATABASE IF NOT EXISTS `finally` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `finally`;

CREATE TABLE IF NOT EXISTS tb_user (
  user_id INT NOT NULL,
  username VARCHAR(64) NOT NULL,
  password VARCHAR(100) NOT NULL,
  usertype TINYINT(1) NOT NULL DEFAULT 0,
  userpic VARCHAR(255) DEFAULT '/images/default-avatar.png',
  PRIMARY KEY (user_id),
  KEY idx_user_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS tb_circle (
  circle_id INT NOT NULL AUTO_INCREMENT,
  owner INT NOT NULL,
  circle_name VARCHAR(100) NOT NULL,
  detail VARCHAR(500) DEFAULT NULL,
  PRIMARY KEY (circle_id),
  KEY idx_circle_owner (owner),
  KEY idx_circle_name (circle_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS tb_article (
  article_id VARCHAR(36) NOT NULL,
  title VARCHAR(200) NOT NULL,
  user_id INT NOT NULL,
  username VARCHAR(64) NOT NULL,
  circle_id INT NOT NULL,
  content TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (article_id),
  KEY idx_article_user (user_id),
  KEY idx_article_circle (circle_id),
  KEY idx_article_title (title)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS tb_review (
  review_id INT NOT NULL AUTO_INCREMENT,
  owner_id INT NOT NULL,
  article_id VARCHAR(36) NOT NULL,
  content VARCHAR(1000) NOT NULL,
  PRIMARY KEY (review_id),
  KEY idx_review_article (article_id),
  KEY idx_review_owner (owner_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS tb_userconcern (
  concern_id INT NOT NULL AUTO_INCREMENT,
  preuser INT NOT NULL,
  lastuser INT NOT NULL,
  PRIMARY KEY (concern_id),
  UNIQUE KEY uk_concern_relation (preuser, lastuser),
  KEY idx_concern_followed (lastuser)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 以下测试用户的登录密码均为 123456（BCrypt）。
INSERT INTO tb_user (user_id, username, password, usertype, userpic) VALUES
  (10001, '测试管理员', '$2b$10$Xdd4.3UMEhx93He95SUPbedbmZM7Yl5.8/rKRmsQV2g7gmPcSnvJW', 1, '/images/default-avatar.png'),
  (10002, '小明',       '$2b$10$Xdd4.3UMEhx93He95SUPbedbmZM7Yl5.8/rKRmsQV2g7gmPcSnvJW', 0, '/images/default-avatar.png'),
  (10003, '小红',       '$2b$10$Xdd4.3UMEhx93He95SUPbedbmZM7Yl5.8/rKRmsQV2g7gmPcSnvJW', 0, '/images/default-avatar.png')
ON DUPLICATE KEY UPDATE username=VALUES(username), password=VALUES(password), usertype=VALUES(usertype), userpic=VALUES(userpic);

INSERT INTO tb_circle (circle_id, owner, circle_name, detail) VALUES
  (1001, 10001, 'Java 技术交流', '交流 Spring Boot、JVM 与后端工程实践'),
  (1002, 10002, '前端开发', '分享 Vue、TypeScript 和 Web 开发经验'),
  (1003, 10003, '校园生活', '记录与讨论校园生活')
ON DUPLICATE KEY UPDATE owner=VALUES(owner), circle_name=VALUES(circle_name), detail=VALUES(detail);

INSERT INTO tb_article (article_id, title, user_id, username, circle_id, content) VALUES
  ('00000000-0000-0000-0000-000000000001', 'Spring Boot 3.5 入门', 10001, '测试管理员', 1001, '这是第一篇测试文章，用于验证文章列表、详情和搜索接口。'),
  ('00000000-0000-0000-0000-000000000002', 'Vue 与后端 API 联调', 10002, '小明', 1002, '本文介绍如何通过统一的 /api 前缀访问 Spring Boot 后端。'),
  ('00000000-0000-0000-0000-000000000003', '校园技术社团活动', 10003, '小红', 1003, '欢迎参加本周的技术分享活动。')
ON DUPLICATE KEY UPDATE title=VALUES(title), user_id=VALUES(user_id), username=VALUES(username), circle_id=VALUES(circle_id), content=VALUES(content);

INSERT INTO tb_review (review_id, owner_id, article_id, content) VALUES
  (10001, 10002, '00000000-0000-0000-0000-000000000001', '内容很清晰，测试评论成功。'),
  (10002, 10003, '00000000-0000-0000-0000-000000000001', '期待更多 Spring Boot 内容。'),
  (10003, 10001, '00000000-0000-0000-0000-000000000002', '统一 API 后联调方便多了。')
ON DUPLICATE KEY UPDATE owner_id=VALUES(owner_id), article_id=VALUES(article_id), content=VALUES(content);

INSERT INTO tb_userconcern (concern_id, preuser, lastuser) VALUES
  (10001, 10002, 10001),
  (10002, 10003, 10001),
  (10003, 10001, 10002)
ON DUPLICATE KEY UPDATE preuser=VALUES(preuser), lastuser=VALUES(lastuser);
