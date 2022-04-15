create table projects (
    project_id          number(6)           primary key,
    project_description varchar2(20),
    project_investment  number(8, -3),
    constraint pro_desc_ck check (LENGTH(project_description) > 10),
    constraint pro_inv_ck check (project_investment > 0)
);

CREATE SEQUENCE projects_seq NOCACHE;

ALTER TABLE projects
    MODIFY project_id DEFAULT projects_seq.nextval;



