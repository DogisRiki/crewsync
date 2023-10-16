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
  numbering_ledger (numbering_cd, avail_year, nextno, digits, updusr, upddate)
VALUES
  ('E00', '2023', 1, 4, NULL, CURDATE());
