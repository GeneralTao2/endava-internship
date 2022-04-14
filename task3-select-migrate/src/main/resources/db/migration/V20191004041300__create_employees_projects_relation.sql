create table employees_projects_relations (
    employee_id     number(6)       unique not null,
    project_id      char(2)         unique not null
);

insert into PROJECTS (PROJECT_ID, PROJECT_DESCRIPTION, PROJECT_INVESTMENT)
values ()