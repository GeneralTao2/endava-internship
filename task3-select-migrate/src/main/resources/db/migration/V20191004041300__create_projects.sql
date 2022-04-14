create table projects (
    project_id          number(6)           primary key,
    project_description varchar2(20),
    project_investment  number(8, -3),
    constraint pro_desc_ck check (LENGTH(project_description) > 10),
    constraint pro_inv_ck check (project_investment > 0)
);

-- TODO that is this for
CREATE SEQUENCE employees_seq NOCACHE;

ALTER TABLE employees
    MODIFY employee_id DEFAULT employees_seq.nextval;

insert into PROJECTS (PROJECT_ID, PROJECT_DESCRIPTION, PROJECT_INVESTMENT)
values (11, 'descljgshfd', 12000);

