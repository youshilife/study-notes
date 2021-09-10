CREATE DATABASE IF NOT EXISTS study_notes;

USE study_notes;

CREATE TABLE articles
(
    id INT AUTO_INCREMENT COMMENT '文章ID',
    parent_id INT NULL COMMENT '父文章ID',
    path VARCHAR(512) NOT NULL COMMENT '访问路径',
    sort_code INT NOT NULL DEFAULT 0 COMMENT '排序值',
    title TEXT NOT NULL COMMENT '标题',
    content LONGTEXT NOT NULL COMMENT '内容',
    created_at DATETIME NOT NULL DEFAULT NOW() COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT NOW() COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY (path),
    FOREIGN KEY (parent_id) REFERENCES articles(id) ON DELETE CASCADE
) COMMENT '文章表';
