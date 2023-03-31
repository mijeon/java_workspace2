create table topcategory(
	topcategory_idx number primary key
	, topcategory_name varchar2(20)
);

create sequence seq_topcategory
increment by 1
start with 1;

--subcategory에 제약조건 부여
create table subcategory(
	subcategory_idx number primary key
	, topcategory_idx number  --부모의 idx
	, subcategory_name varchar2(20)
	, constraint fk_topcategory_subcategory foreign key (topcategory_idx) 
	references topcategory(topcategory_idx)
);

create sequence seq_subcategory
increment by 1
start with 1;

drop table topcategory;
drop table subcategory;
drop sequence seq_topcategory;
drop sequence seq_subcategory;

--상의 카테고리 중 '상의' 등록
insert into TOPCATEGORY(topcategory_idx, topcategory_name)
values(seq_topcategory.nextval, '상의');

insert into TOPCATEGORY(topcategory_idx, topcategory_name)
values(seq_topcategory.nextval, '하의');

insert into TOPCATEGORY(topcategory_idx, topcategory_name)
values(seq_topcategory.nextval, '액세서리');

insert into TOPCATEGORY(topcategory_idx, topcategory_name)
values(seq_topcategory.nextval, '신발');

insert into TOPCATEGORY(topcategory_idx, topcategory_name)
values(seq_topcategory.nextval, '모자');


select * from topcategory;

insert into SUBCATEGORY(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval, 1, '티셔츠');  --부모에게 존재하는 foreign key만 넣을 수 있음

insert into SUBCATEGORY(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval, 1, '가디건');

insert into SUBCATEGORY(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval, 1, '니트');  

insert into SUBCATEGORY(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval, 1, '점퍼');  


insert into SUBCATEGORY(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval, 2, '면바지');  

insert into SUBCATEGORY(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval, 2, '반바지');  

insert into SUBCATEGORY(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval, 2, '청바지');  

insert into SUBCATEGORY(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval, 2, '레깅스');  


insert into SUBCATEGORY(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval, 3, '귀걸이');  

insert into SUBCATEGORY(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval, 3, '목걸이');  

insert into SUBCATEGORY(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval, 3, '팔찌');  

insert into SUBCATEGORY(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval, 3, '반지');  


insert into SUBCATEGORY(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval, 4, '운동화');  

insert into SUBCATEGORY(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval, 4, '구두');  

insert into SUBCATEGORY(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval, 4, '샌들');  

insert into SUBCATEGORY(subcategory_idx, topcategory_idx, subcategory_name)
values(seq_subcategory.nextval, 4, '슬리퍼');  

select * from subcategory;  

delete from TOPCATEGORY;

delete from subcategory;

select * from subcategory where topcategory_idx=1;



