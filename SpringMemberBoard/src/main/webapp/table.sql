create table smember (
  id varchar2(15),
  password varchar2(10),
  name varchar2(15),
  age number,
  gender varchar2(5),
  email varchar2(30),
  primary key(id)
);

create table smemberboard (
	num number,
	id varchar2(20),
	subject varchar2(50),
	content varchar2(2000),
	re_ref number,
	re_lev number,
	re_seq number,
	readcount number,
	boarddate date,
	primary key(num)
);