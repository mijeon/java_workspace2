--부서 테이블
CREATE TABLE dept(
	deptno NUMBER PRIMARY KEY --부서 번호
	,dname varchar2(20) -- 부서명
	,loc varchar2(30) -- 부서 지역
);

-- 부서 시퀀스
CREATE SEQUENCE seq_dept
INCREMENT BY 1
START WITH 1;
