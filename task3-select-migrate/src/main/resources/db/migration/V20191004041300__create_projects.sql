create table projects (
    project_id          char(2)         primary key,
    project_description varchar2(20),
    project_investment  number(8, -3),
    constraint pro_desc_ck check (project_description > 10),
    constraint pro_inv_ck check (project_investment > 0)
);

