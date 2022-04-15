insert into REGIONS (REGION_ID, REGION_NAME)
values (1, 'Telecentru');

insert into COUNTRIES (COUNTRY_ID, COUNTRY_NAME, REGION_ID)
values (2, 'Moldova', 1);

insert into LOCATIONS (LOCATION_ID, STREET_ADDRESS, POSTAL_CODE, CITY, STATE_PROVINCE, COUNTRY_ID)
values (3, 'Pushkin 13', '2005', 'Chisinau', 'Chishinau province', 2);

insert into DEPARTMENTS (DEPARTMENT_ID, DEPARTMENT_NAME, MANAGER_ID, LOCATION_ID)
values (4, 'Java', null, 3);

insert into JOBS (JOB_ID, JOB_TITLE, MIN_SALARY, MAX_SALARY)
values (5, 'Java Developer', 700, 6000);

insert into EMPLOYEES (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE_NUMBER, HIRE_DATE, JOB_ID, SALARY, COMMISSION_PCT, MANAGER_ID, DEPARTMENT_ID)
values (6, 'Artiom', 'Diulgher', 'artiom.diul@endava.com', '0037369000000', TO_DATE('01-02-2002','DD-MM-YYYY'), 5, 700, null, null, 4);

insert into JOB_HISTORY  (EMPLOYEE_ID, START_DATE, END_DATE, JOB_ID, DEPARTMENT_ID)
values (6, TO_DATE('01-02-2002','DD-MM-YYYY'), TO_DATE('01-02-2003','DD-MM-YYYY'), 5, 4);
