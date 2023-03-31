create table product(
	product_idx number primary key
	, subcategory_idx number
	, product_name varchar2(30)
	, brand varchar2(30)
	, price number default 0
	, filename varchar2(20)
	, constraint fk_subcategory_product 
		foreign key(subcategory_idx)
		references subcategory(subcategory_idx)
);

create sequence seq_product
increment by 1
start with 1;

--상품과 하위카테고리 조인
select s.subcategory_idx as subcategory_idx, subcategory_name, 
product_idx, product_name, brand, price, filename
from subcategory s, product p
where s.subcategory_idx=p.subcategory_idx;

--3개 테이블 조인
select t.topcategory_idx, topcategory_name, s.subcategory_idx, subcategory_name, product_idx, product_name, brand, price, filename
from topcategory t, subcategory s, product p
where t.topcategory_idx=s.topcategory_idx and s.subcategory_idx=p.subcategory_idx
and p.product_idx=1;