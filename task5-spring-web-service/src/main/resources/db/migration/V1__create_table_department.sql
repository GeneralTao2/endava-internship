create sequence hibernate_sequence start with 1 increment by 1;

create table department
(
    department_id number(19,0) not null,
    location      varchar2(255 char) not null,
    name          varchar2(255 char) not null,
    primary key (department_id)
);

