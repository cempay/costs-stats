select *  from employee

set serveroutput on

delete from category;
select * from category;

delete from purchase;
select * from purchase;

-- tables
create table USERS (id NUMBER(20,0), login VARCHAR2(50), password VARCHAR2(50), name VARCHAR2(50), CONSTRAINT USER_ID PRIMARY KEY(id));
create table CATEGORY (id NUMBER(20,0), name VARCHAR2(100), user_id NUMBER(20,0), CONSTRAINT CATEGORY_ID PRIMARY KEY(id));
create table PURCHASE (id number(20,0), name VARCHAR2(200), category_id NUMBER(20,0), payDate DATE, price NUMBER(10,2), CONSTRAINT PURCHASE_ID PRIMARY KEY(id));

-- sequances
create sequence CATEGORY_SEQ;
create sequence PURCHASE_SEQ;

--

create or replace directory dir as 'D:\TEMP';
grant read on directory dir to scott;
grant write on directory dir to scott;
expdp system/manager schemas=scott directory=dir2 dumpfile=scott_backup_150202.dmp logfile=expdp_150202.log
impdp system/manager schemas=scott directory=dir2 remap_schema=scott:scott_release dumpfile=scott_backup_150202.dmp logfile=expdp_150202.log
alter user scott_release identified by manager