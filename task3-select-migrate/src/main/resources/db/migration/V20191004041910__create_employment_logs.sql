create table employment_logs (
    employment_log_id   number(6)       primary key,
    first_name          varchar2(20),
    last_name           varchar2(25)    not null,
    employment_action   varchar(6),
    constraint emp_logs_emp_act_ck check (employment_action in ('HIRED', 'FIRED'))
);

CREATE SEQUENCE employment_logs_seq NOCACHE;

ALTER TABLE employment_logs
    MODIFY employment_log_id DEFAULT employment_logs_seq.nextval;

