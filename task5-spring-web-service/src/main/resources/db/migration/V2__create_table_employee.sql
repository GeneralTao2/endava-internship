create table employee
(
    employee_id   number(19,0) not null,
    email         varchar2(255 char) not null,
    first_name    varchar2(255 char) not null,
    last_name     varchar2(255 char) not null,
    phone_number  varchar2(255 char) not null,
    salary        number CHECK (salary > 1),
    department_id number(19,0),
    primary key (employee_id)
);

alter table employee
    add constraint emp_email_uk unique (email);
alter table employee
    add constraint emp_phon_nmbr_uk unique (phone_number);