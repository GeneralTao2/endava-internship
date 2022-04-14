create table employees_projects_relations (
    employee_id     number(6)       unique not null,
    project_id      number(6)       unique not null,
    project_revenue date
);

insert into employees_projects_relations (employee_id, project_id, project_revenue)
values (6, 11, to_date('20', 'HH24'))


