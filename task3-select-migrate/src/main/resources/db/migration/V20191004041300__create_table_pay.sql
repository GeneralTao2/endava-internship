create table pay (
    card_nr         char(2)         primary key,
    employee_id     number(6)       unique,
    salary          number(8, 2),
    commission_pct  number(2, 2)
);

insert into pay (card_nr, employee_id, salary, commission_pct)
values (2,6,3343,1)
