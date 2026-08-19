USE `finally`;

RENAME TABLE tb_userconcern TO tb_user_follow;

ALTER TABLE tb_user_follow
  CHANGE COLUMN concern_id follow_id INT NOT NULL AUTO_INCREMENT,
  CHANGE COLUMN preuser follower_id INT NOT NULL,
  CHANGE COLUMN lastuser followed_user_id INT NOT NULL,
  RENAME INDEX uk_concern_relation TO uk_follow_relation,
  RENAME INDEX idx_concern_followed TO idx_follow_followed_user;
