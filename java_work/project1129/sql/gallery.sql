--Gallery 테이블
create table gallery(
gallery_id number primary key --갤러리 번호
, title varchar2(100) --제목
, wirter varchar2(30) --작성자
, content clob --내용
, filename varchar2(30) --이미지 이름
, regdate date default sysdate --등록일(현재날짜가 기본값임)
);

--갤러리 시퀀스
create sequence seq_gallery
increment by 1
start with 1;
