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

INSERT INTO
  users (email,avf,name,password,locked,expired,emp_no,dept_cd,pos_cd,profile_img)
VALUES
-- dept:システム管理, position:管理者
  (
  'admin@crewsync.jp',
  '2023-07-17',
  'admin',
  '$2a$10$6zqr3VI3c/iB9FLOWj7kP.kWSaf2dL7TnAxuPqkxWK18US4.3DzB2',
  0,
  0,
  '00000000',
  '0000000',
  '0000',
  '/img/00_profile/00000000/admin.jpg'
  ),
  -- dept:営業部, position:部長
  (
  'manager@crewsync.jp',
  '2023-07-17',
  'manager',
  '$2a$10$6zqr3VI3c/iB9FLOWj7kP.kWSaf2dL7TnAxuPqkxWK18US4.3DzB2',
  0,
  0,
  '00000001',
  '0000001',
  '0001',
  '/img/00_profile/00000001/manager.jpg'
  ),
  -- dept:経理部, position:課長
  (
  'manager2@crewsync.jp',
  '2023-07-17',
  'manager2',
  '$2a$10$6zqr3VI3c/iB9FLOWj7kP.kWSaf2dL7TnAxuPqkxWK18US4.3DzB2',
  0,
  0,
  '00000002',
  '0000002',
  '0002',
  '/img/00_profile/00000002/manager2.jpg'
  ),
  -- dept:人事部, position:一般
  (
  'user@crewsync.jp',
  '2023-07-17',
  'user',
  '$2a$10$6zqr3VI3c/iB9FLOWj7kP.kWSaf2dL7TnAxuPqkxWK18US4.3DzB2',
  0,
  0,
  '00000003',
  '0000003',
  '0004',
  '/img/00_profile/00000003/user.jpg'
  );

INSERT INTO
  user_roles (user_id,`role`,delflg)
VALUES
  (1, '03', '0'),
  (120,'03','0'),
  (121,'02','0'),
  (122,'02','0'),
  (123,'01','0');

INSERT INTO
  personal_info (user_id,last_name,first_name,zipcode,pref,city,bldg,telno,mobile_no)
VALUES
  (120,'テスト','太郎','1001000','東京都','港区六本木','X-X-X','0312345678','09012345678');

INSERT INTO
  numbering_ledger (numbering_cd, avail_year, nextno, digits)
VALUES
  ('E00', '2023', 1, 4);
