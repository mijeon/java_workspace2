--page package에서 사용할 회원테이블을 생성하자
--회원테이블
create table member(
	  member_idx number primary key
	, id varchar2(20)
	, pass varchar2(64) --sha256에 의한 64자
	, email varchar2(50)
	, regdate date default sysdate
);

--MEMBER 시퀀스
CREATE SEQUENCE seq_member
INCREMENT BY 1
START WITH 1;
