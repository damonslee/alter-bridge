CREATE TABLE t_board (
    id INT(11) NOT NULL AUTO_INCREMENT,
    title VARCHAR(300) NOT NULL,
    contents TEXT NOT NULL,
    hit_cnt SMALLINT(10) NOT NULL DEFAULT '0',
    created_at DATETIME NOT NULL,
    created_by VARCHAR(50) NOT NULL,
    updated_at DATETIME DEFAULT NULL,
    updated_by VARCHAR(50) DEFAULT NULL,
    deleted_yn CHAR(1) NOT NULL DEFAULT 'N',
    PRIMARY KEY (id)
);