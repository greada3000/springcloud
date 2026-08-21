CREATE TABLE IF NOT EXISTS tb_user (
  user_id INT NOT NULL,
  username VARCHAR(64) NOT NULL,
  password VARCHAR(100) NOT NULL,
  usertype TINYINT(1) NOT NULL DEFAULT 0,
  userpic VARCHAR(255) DEFAULT '/images/default-avatar.png',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id),
  KEY idx_user_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS tb_circle (
  circle_id INT NOT NULL AUTO_INCREMENT,
  owner INT NOT NULL,
  circle_name VARCHAR(100) NOT NULL,
  detail VARCHAR(500) DEFAULT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
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
  KEY idx_article_user_created (user_id, created_at),
  KEY idx_article_circle_created (circle_id, created_at),
  KEY idx_article_created (created_at, article_id),
  KEY idx_article_title (title)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS tb_review (
  review_id INT NOT NULL AUTO_INCREMENT,
  owner_id INT NOT NULL,
  article_id VARCHAR(36) NOT NULL,
  content VARCHAR(1000) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (review_id),
  KEY idx_review_article (article_id),
  KEY idx_review_owner (owner_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS tb_user_follow (
  follow_id INT NOT NULL AUTO_INCREMENT,
  follower_id INT NOT NULL,
  followed_user_id INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (follow_id),
  UNIQUE KEY uk_follow_relation (follower_id, followed_user_id),
  KEY idx_follow_followed_user (followed_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
