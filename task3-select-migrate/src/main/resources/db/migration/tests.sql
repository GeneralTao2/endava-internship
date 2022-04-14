insert into pay (card_nr, employee_id, salary, commission_pct)
values (2, 6, 3343, 1);

insert into employees_projects_relations (employee_id, project_id, project_revenue)
values (6, 11, to_date('20', 'HH24'));

insert into PROJECTS (PROJECT_ID, PROJECT_DESCRIPTION, PROJECT_INVESTMENT)
values (11, 'descljgshfd', 12000);

insert into PROJECTS (PROJECT_DESCRIPTION, PROJECT_INVESTMENT)
values ('descljgshfd', 12000)

-- -------------------------------

select *
from DEPARTMENTS D
         inner join LOCATIONS L on L.LOCATION_ID = D.LOCATION_ID
    insert
into LOCATIONS (LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID)
values (1, 'Pushkin 11', '2001', 'Chisinau1', 'Chishinau province1', 2);

INSERT INTO departments (DEPARTMENT_NAME, MANAGER_ID, LOCATION_ID)
VALUES ( 'D1'
       , NULL
       , 1);

select *
from LOCATIONS;
select *
from DEPARTMENTS;

update LOCATIONS
set DEPARTMENT_AMOUNT = null;
