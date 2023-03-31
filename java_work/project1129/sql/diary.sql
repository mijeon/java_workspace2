CREATE TABLE diary(
	   diary_idx NUMBER primary key
	  , yy number
	  , mm number
	  , dd number
	  , content varchar2(1000)
	  , icon varchar2(20)
);

CREATE SEQUENCE seq_diary
INCREMENT BY 1
START WITH 1;