--사원 테이블
CREATE TABLE emp(
	  empno NUMBER PRIMARY KEY --사원 번호
	, ename varchar2(20) -- 사원명
	, sal NUMBER DEFAULT 0 -- 사원 급여
	, job varchar2(20) -- 사원 업무
	, deptno NUMBER --부서 번호
	, constraint fk_dept_emp FOREIGN key(deptno) 	references dept(deptno) -- deptno에 외래키 제약조건 추가
);

--EMP 시퀀스
CREATE SEQUENCE seq_emp
INCREMENT BY 1
START WITH 1;

--실행 단축키 alt+x
--외래키를 지정했을 때 어떤 효과가 있는가?
--외래키를 정의하면, 부모가 구속을 받음

--내가 넣고싶은 컬럼만 넣을때 : default값이 들어가거나 null이 들어갈 수있음
insert into emp(empno,detpno) values(...);
--값을 전부 넣을때
insert into emp values(...);
