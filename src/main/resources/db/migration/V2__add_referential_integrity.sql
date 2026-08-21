ALTER TABLE tb_circle
  ADD CONSTRAINT fk_circle_owner FOREIGN KEY (owner) REFERENCES tb_user(user_id) ON DELETE RESTRICT;

ALTER TABLE tb_article
  ADD CONSTRAINT fk_article_user FOREIGN KEY (user_id) REFERENCES tb_user(user_id) ON DELETE RESTRICT,
  ADD CONSTRAINT fk_article_circle FOREIGN KEY (circle_id) REFERENCES tb_circle(circle_id) ON DELETE RESTRICT;

ALTER TABLE tb_review
  ADD CONSTRAINT fk_review_owner FOREIGN KEY (owner_id) REFERENCES tb_user(user_id) ON DELETE RESTRICT,
  ADD CONSTRAINT fk_review_article FOREIGN KEY (article_id) REFERENCES tb_article(article_id) ON DELETE CASCADE;

ALTER TABLE tb_user_follow
  ADD CONSTRAINT fk_follow_follower FOREIGN KEY (follower_id) REFERENCES tb_user(user_id) ON DELETE CASCADE,
  ADD CONSTRAINT fk_follow_followed FOREIGN KEY (followed_user_id) REFERENCES tb_user(user_id) ON DELETE CASCADE;
