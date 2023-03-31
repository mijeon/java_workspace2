CREATE TABLE dept(
	deptno NUMBER PRIMARY KEY
	,dname varchar2(20)
	,loc varchar2(30)
);

CREATE SEQUENCE seq_dept
INCREMENT BY 1
START WITH 1;

CREATE TABLE emp(
	  empno NUMBER PRIMARY KEY
	, ename varchar2(20)
	, sal NUMBER DEFAULT 0
	, job varchar2(20)
	, deptno NUMBER 
	, constraint fk_dept_emp FOREIGN key(deptno) 
	references dept(deptno)
);

CREATE SEQUENCE seq_emp
INCREMENT BY 1
START WITH 1;