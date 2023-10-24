USE crewsync;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT(9) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT COMMENT 'システムで自動採番されるユーザーID',
    email VARCHAR(128) NOT NULL COMMENT 'Ｅメールアドレス',
    avf DATE NOT NULL COMMENT '世代管理用の日付',
    name VARCHAR(128) DEFAULT NULL COMMENT 'ユーザー情報に表示されるユーザー名を保持する。',
    password CHAR(60) DEFAULT NULL,
    locked TINYINT(1) UNSIGNED DEFAULT '0' COMMENT 'アカウントがロックされていることを示すフラグ',
    expired TINYINT(1) UNSIGNED DEFAULT '0' COMMENT 'アカウントが期限切れになったことを示すフラグ',
    emp_no CHAR(8) DEFAULT NULL,
    dept_cd CHAR(7) DEFAULT NULL,
    pos_cd CHAR(4) DEFAULT NULL,
    profile_img LONGTEXT DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY email (email,avf)
) AUTO_INCREMENT=119 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT(9) UNSIGNED ZEROFILL NOT NULL COMMENT 'ユーザーマスタのユーザーIDを参照',
    role CHAR(2) NOT NULL,
    delflg CHAR(1) DEFAULT NULL COMMENT 'ユーザーが無効化された場合に設定される削除フラグ',
    PRIMARY KEY (user_id,role)
) ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS personal_info (
    user_id BIGINT(9) UNSIGNED ZEROFILL NOT NULL,
    last_name VARCHAR(32) DEFAULT NULL,
    first_name VARCHAR(32) DEFAULT NULL,
    zipcode CHAR(7) DEFAULT NULL,
    pref VARCHAR(16) DEFAULT NULL,
    city VARCHAR(128) DEFAULT NULL,
    bldg VARCHAR(128) DEFAULT NULL,
    telno VARCHAR(32) DEFAULT NULL,
    mobile_no VARCHAR(32) DEFAULT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS codemst (
    code_id BIGINT(8) UNSIGNED ZEROFILL NOT NULL COMMENT 'コード体系を一意にしきべつするためのID',
    code VARCHAR(8) NOT NULL,
    code_nm VARCHAR(256) DEFAULT NULL,
    delflg CHAR(1) DEFAULT NULL COMMENT 'ユーザーが無効化された場合に設定される削除フラグ',
    PRIMARY KEY (code_id,code)
);

CREATE TABLE IF NOT EXISTS depts (
    dept_cd CHAR(7) NOT NULL,
    avf DATE NOT NULL,
    dept_nm VARCHAR(64) DEFAULT NULL,
    PRIMARY KEY (dept_cd,avf)
);

CREATE TABLE IF NOT EXISTS positions (
    pos_cd CHAR(4) NOT NULL,
    avf DATE NOT NULL,
    pos_nm VARCHAR(64) DEFAULT NULL,
    PRIMARY KEY (pos_cd,avf)
) ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS numbering_ledger (
    numbering_cd VARCHAR(3) NOT NULL,
    avail_year VARCHAR(4) NOT NULL,
    nextno decimal(9,0) DEFAULT NULL,
    digits decimal(9,0) DEFAULT NULL,
    updusr BIGINT(9) UNSIGNED ZEROFILL DEFAULT NULL,
    upddate DATE DEFAULT NULL,
    PRIMARY KEY (numbering_cd,avail_year)
);

CREATE TABLE IF NOT EXISTS topics (
    topicno VARCHAR(12) NOT NULL,
    subject VARCHAR(128) DEFAULT NULL,
    createdby VARCHAR(8) DEFAULT NULL,
    createdat DATE DEFAULT NULL,
    PRIMARY KEY (topicno)
) AUTO_INCREMENT=158 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS posts (
    topicno VARCHAR(12) NOT NULL,
    postno BIGINT(8) UNSIGNED ZEROFILL,
    post VARCHAR(1280) DEFAULT NULL,
    createdby VARCHAR(8) DEFAULT NULL,
    createdat DATE DEFAULT NULL,
    PRIMARY KEY (topicno, postno)
) AUTO_INCREMENT=158 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS post_ratings (
    topicno VARCHAR(12) NOT NULL,
    postno BIGINT(8) UNSIGNED ZEROFILL NOT NULL,
    ratedby VARCHAR(8) NOT NULL,
    rating_flg TINYINT DEFAULT NULL,
    ratedat DATE DEFAULT NULL,
    PRIMARY KEY(topicno, postno, ratedby)
) AUTO_INCREMENT=158 ROW_FORMAT=DYNAMIC;

INSERT INTO
  users (email, avf, name, password, locked, expired, emp_no, dept_cd, pos_cd, profile_img)
VALUES
  ('admin@crewsync.jp', CURDATE(), 'admin', '$2a$10$hf5kUGtOvvk.K6s7xBRGAeZ7a7MC0aDAjWnzMPV1iGFkCfccxzFFK', 0, 0, '00000001', '0000000', '0000', '/img/00_profile/00000001/golden.jpg'),
  ('manager@crewsync.jp', CURDATE(), 'manager', '$2a$10$MWOjX5gNNjlEVRihzt/avuX3Vfx9vX5LtBZFjkKGKuDHxmGxwnviO', 0, 0, '00000002', '0000001', '0001', '/img/00_profile/00000002/husky.jpg'),
  ('user@crewsync.jp', CURDATE(), 'user', '$2a$10$82fBEyPExZYKFcahDPnA7OxIj4cY8d/fK27k5FHaR0cRo6AsEiDFC', 0, 0, '00000003', '0000001', '0002', '/img/00_profile/00000003/doberman.jpg');

INSERT INTO
  user_roles (user_id, role, delflg)
VALUES
  (000000119, '03', '0'),
  (000000120, '02', '0'),
  (000000121, '01', '0');

INSERT INTO
    codemst (code_id, code, code_nm, delflg)
VALUES
    (1, '01', 'ROLE_USER', '0'),
    (1, '02', 'ROLE_MANAGER', '0'),
    (1, '03', 'ROLE_ADMIN', '0'),
    (2, '01', '一般社員', '0'),
    (2, '02', '主任', '0'),
    (2, '03', '係長', '0'),
    (2, '04', '課長', '0'),
    (2, '05', '部長', '0');

INSERT INTO
    depts (dept_cd, avf, dept_nm)
VALUES
    ('0000000', '2023-01-01', 'システム管理者'),
    ('0000000', '2023-02-01', 'システム管理'),
    ('0000001', '2023-01-01', '営業部'),
    ('0000002', '2023-01-01', '経理部'),
    ('0000003', '2023-01-01', '人事部'),
    ('0000004', '2023-01-01', '総務部');

INSERT INTO
    positions (pos_cd, avf, pos_nm)
VALUES
    ('0000', '2023-01-01', '管理者'),
    ('0001', '2023-01-01', '部長'),
    ('0002', '2023-01-01', '課長'),
    ('0003', '2023-01-01', '係長'),
    ('0004', '2023-01-01', '一般');
