CREATE TABLE dot(
dot_id NUMBER PRIMARY KEY
, x NUMBER
, y NUMBER
, gallery_id number
);

CREATE SEQUENCE seq_dot
increment by 1
start with 1;