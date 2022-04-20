-- Write a query to display: 
-- 1. the first name, last name, salary, and job grade for all employees.
SELECT first_name, last_name, salary, job_title
FROM employees
         LEFT JOIN jobs USING (job_id);

-- 2. the first and last name, department, city, and state province for each employee.
select FIRST_NAME, LAST_NAME, DEPARTMENT_NAME, CITY, STATE_PROVINCE
FROM EMPLOYEES
         left join DEPARTMENTS using (department_id)
         left join LOCATIONS using (location_id);

-- 3. the first name, last name, department number and department name, for all employees for departments 80 or 40.
select FIRST_NAME, LAST_NAME, DEPARTMENT_ID, DEPARTMENT_NAME
from EMPLOYEES innner
         join DEPARTMENTS using (department_id)
where DEPARTMENT_ID = 80
   OR DEPARTMENT_ID = 60;

-- 4. those employees who contain a letter z to their first name and also display their last name, department, city, and state province.
select FIRST_NAME, LAST_NAME, DEPARTMENT_NAME, CITY, STATE_PROVINCE
from EMPLOYEES
         left join DEPARTMENTS using (department_id)
         left join LOCATIONS using (location_id)
where FIRST_NAME like '%z%';

-- 5. the first and last name and salary for those employees who earn less than the employee earn whose number is 182.
select FIRST_NAME, LAST_NAME, SALARY
from EMPLOYEES
where SALARY < (select SALARY from EMPLOYEES where EMPLOYEE_ID = 182);

-- 6. the first name of all employees including the first name of their manager.
select E1.FIRST_NAME                                                              EMMPLYEE_FIRST_NAME,
       (select FIRST_NAME from EMPLOYEES E2 where E2.EMPLOYEE_ID = E1.MANAGER_ID) MANAGER_FIRST_NAME
from EMPLOYEES E1
where E1.MANAGER_ID is not null;

-- 7. the first name of all employees and the first name of their manager including those who does not working under any manager.
select E1.FIRST_NAME                                                              EMMPLYEE_FIRST_NAME,
       (select FIRST_NAME from EMPLOYEES E2 where E2.EMPLOYEE_ID = E1.MANAGER_ID) MANAGER_FIRST_NAME
from EMPLOYEES E1;

-- 8. the details of employees who manage a department.
select EMPLOYEE_ID,
       FIRST_NAME,
       LAST_NAME,
       EMAIL,
       PHONE_NUMBER,
       HIRE_DATE,
       SALARY,
       COMMISSION_PCT
from EMPLOYEES
         inner join DEPARTMENTS D on EMPLOYEE_ID = D.MANAGER_ID;

-- 9. the first name, last name, and department number for those employees who works in the same department as the employee who holds the last name as Taylor.
select FIRST_NAME, LAST_NAME, DEPARTMENT_ID
from EMPLOYEES
where DEPARTMENT_ID in (select DEPARTMENT_ID
                        from EMPLOYEES
                        where LAST_NAME = 'Taylor');

--10. the department name and number of employees in each of the department.
-- noinspection SqlShadowingAlias

select NVL(DEPARTMENT_NAME, 'No department') DEPARTMENT_NAME, count(EMPLOYEE_ID)
from EMPLOYEES
         left join DEPARTMENTS using (DEPARTMENT_ID)
group by DEPARTMENT_NAME;

--11. the name of the department, average salary and number of employees working in that department who got commission.
-- noinspection SqlShadowingAlias

select NVL(DEPARTMENT_NAME, 'No department') DEPARTMENT_NAME, AVG(SALARY)
from EMPLOYEES
         left join DEPARTMENTS using (DEPARTMENT_ID)
where COMMISSION_PCT is not null
group by DEPARTMENT_NAME;

--12. job title and average salary of employees.
select JOB_TITLE, AVG(SALARY)
from EMPLOYEES
         inner join JOBS using (JOB_ID)
group by JOB_TITLE;

--13. the country name, city, and number of those departments where at least 2 employees are working.
select COUNTRY_NAME, CITY, count(DEPARTMENT_ID)
from EMPLOYEES
         inner join DEPARTMENTS D1 using (DEPARTMENT_ID)
         inner join LOCATIONS using (LOCATION_ID)
         inner join COUNTRIES using (COUNTRY_ID)
group by COUNTRY_NAME, CITY
having count(EMPLOYEE_ID) >= 2;

--14. the employee ID, job name, number of days worked in for all those jobs in department 80.
select EMPLOYEE_ID, JOB_TITLE, END_DATE - START_DATE DAYS
from EMPLOYEES
         inner join JOBS using (JOB_ID)
         inner join JOB_HISTORY using (EMPLOYEE_ID)
where EMPLOYEES.DEPARTMENT_ID = 80;

--15. the name ( first name and last name ) for those employees who gets more salary than the employee whose ID is 163.
select FIRST_NAME || ' ' || LAST_NAME name
from EMPLOYEES
where SALARY > (select SALARY from EMPLOYEES where EMPLOYEE_ID = 163);

--16. the employee id, employee name (first name and last name ) for all employees who earn more than the average salary.
select EMPLOYEE_ID, FIRST_NAME || ' ' || LAST_NAME name
from EMPLOYEES
where SALARY > (select avg(SALARY) from EMPLOYEES);

--17. the employee name ( first name and last name ), employee id and salary of all employees who report to Payam.
select EM.EMPLOYEE_ID, EM.FIRST_NAME || ' ' || EM.LAST_NAME name, EM.EMPLOYEE_ID, EM.SALARY
from EMPLOYEES EM
         inner join EMPLOYEES MR on EM.MANAGER_ID = MR.EMPLOYEE_ID
where MR.FIRST_NAME = 'Payam';

--18. the department number, name ( first name and last name ), job and department name for all employees in the Finance department.
select DEPARTMENT_ID, FIRST_NAME || ' ' || LAST_NAME name, JOB_TITLE, DEPARTMENT_NAME
from EMPLOYEES
         left join JOBS using (JOB_ID)
         left join DEPARTMENTS using (DEPARTMENT_ID)
where DEPARTMENT_NAME = 'Finance';

--19. all the information of an employee whose id is any of the number 134, 159 and 183.
select emp.EMPLOYEE_ID,
       EMP.FIRST_NAME,
       EMP.LAST_NAME,
       EMP.EMAIL,
       EMP.PHONE_NUMBER,
       EMP.HIRE_DATE,
       EMP.SALARY,
       EMP.COMMISSION_PCT,
       JOB_TITLE,
       START_DATE,
       END_DATE,
       DEPARTMENT_NAME,
       STREET_ADDRESS,
       POSTAL_CODE,
       CITY,
       STATE_PROVINCE,
       COUNTRY_NAME,
       REGION_NAME
from EMPLOYEES EMP
         left join EMPLOYEES MGR on EMP.MANAGER_ID = EMP.EMPLOYEE_ID
         left join DEPARTMENTS D on EMP.DEPARTMENT_ID = D.DEPARTMENT_ID
         left join JOBS JS on EMP.JOB_ID = JS.JOB_ID
         left join JOB_HISTORY JH on EMP.JOB_ID = JH.JOB_ID
         left join LOCATIONS L on D.LOCATION_ID = L.LOCATION_ID
         left join COUNTRIES CT on L.COUNTRY_ID = CT.COUNTRY_ID
         left join REGIONS R on CT.REGION_ID = R.REGION_ID
where EMP.EMPLOYEE_ID in (134, 159, 183);

--20. all the information of the employees whose salary is within the range of smallest salary and 2500.
select emp.EMPLOYEE_ID,
       EMP.FIRST_NAME,
       EMP.LAST_NAME,
       EMP.EMAIL,
       EMP.PHONE_NUMBER,
       EMP.HIRE_DATE,
       EMP.SALARY,
       EMP.COMMISSION_PCT,
       JOB_TITLE,
       START_DATE,
       END_DATE,
       DEPARTMENT_NAME,
       STREET_ADDRESS,
       POSTAL_CODE,
       CITY,
       STATE_PROVINCE,
       COUNTRY_NAME,
       REGION_NAME
from EMPLOYEES EMP
         left join EMPLOYEES MGR on EMP.MANAGER_ID = EMP.EMPLOYEE_ID
         left join DEPARTMENTS D on EMP.DEPARTMENT_ID = D.DEPARTMENT_ID
         left join JOBS JS on EMP.JOB_ID = JS.JOB_ID
         left join JOB_HISTORY JH on EMP.JOB_ID = JH.JOB_ID
         left join LOCATIONS L on D.LOCATION_ID = L.LOCATION_ID
         left join COUNTRIES CT on L.COUNTRY_ID = CT.COUNTRY_ID
         left join REGIONS R on CT.REGION_ID = R.REGION_ID
where EMP.SALARY < 2500;

--21. all the information of the employees who does not work in those departments where some employees works whose id within the range 100 and 200.
select emp.EMPLOYEE_ID,
       EMP.FIRST_NAME,
       EMP.LAST_NAME,
       EMP.EMAIL,
       EMP.PHONE_NUMBER,
       EMP.HIRE_DATE,
       EMP.SALARY,
       EMP.COMMISSION_PCT,
       JOB_TITLE,
       START_DATE,
       END_DATE,
       DEPARTMENT_NAME,
       STREET_ADDRESS,
       POSTAL_CODE,
       CITY,
       STATE_PROVINCE,
       COUNTRY_NAME,
       REGION_NAME
from EMPLOYEES EMP
         left join EMPLOYEES MGR on EMP.MANAGER_ID = EMP.EMPLOYEE_ID
         left join DEPARTMENTS D on EMP.DEPARTMENT_ID = D.DEPARTMENT_ID
         left join JOBS JS on EMP.JOB_ID = JS.JOB_ID
         left join JOB_HISTORY JH on EMP.JOB_ID = JH.JOB_ID
         left join LOCATIONS L on D.LOCATION_ID = L.LOCATION_ID
         left join COUNTRIES CT on L.COUNTRY_ID = CT.COUNTRY_ID
         left join REGIONS R on CT.REGION_ID = R.REGION_ID
where EMP.DEPARTMENT_ID not in
      (select distinct DEPARTMENT_ID from EMPLOYEES where EMPLOYEE_ID >= 100 and EMPLOYEE_ID <= 200);

--22. all the information for those employees whose id is any id who earn the second highest salary.
select emp.EMPLOYEE_ID,
       EMP.FIRST_NAME,
       EMP.LAST_NAME,
       EMP.EMAIL,
       EMP.PHONE_NUMBER,
       EMP.HIRE_DATE,
       EMP.SALARY,
       EMP.COMMISSION_PCT,
       JOB_TITLE,
       START_DATE,
       END_DATE,
       DEPARTMENT_NAME,
       STREET_ADDRESS,
       POSTAL_CODE,
       CITY,
       STATE_PROVINCE,
       COUNTRY_NAME,
       REGION_NAME
from EMPLOYEES EMP
         left join EMPLOYEES MGR on EMP.MANAGER_ID = EMP.EMPLOYEE_ID
         left join DEPARTMENTS D on EMP.DEPARTMENT_ID = D.DEPARTMENT_ID
         left join JOBS JS on EMP.JOB_ID = JS.JOB_ID
         left join JOB_HISTORY JH on EMP.JOB_ID = JH.JOB_ID
         left join LOCATIONS L on D.LOCATION_ID = L.LOCATION_ID
         left join COUNTRIES CT on L.COUNTRY_ID = CT.COUNTRY_ID
         left join REGIONS R on CT.REGION_ID = R.REGION_ID
where EMP.SALARY = (select SALARY
                    from (select SALARY, ROW_NUMBER() over (order by SALARY desc NULLS LAST) ROWNUMBER
                          from EMPLOYEES
                          group by SALARY)
                    where ROWNUMBER = 2
);

--23. the employee name( first name and last name ) and hiredate for all employees in the same department as Clara. Exclude Clara.
select FIRST_NAME || ' ' || LAST_NAME name, HIRE_DATE
from EMPLOYEES
where DEPARTMENT_ID in (
    select DEPARTMENT_ID
    from EMPLOYEES
    where FIRST_NAME = 'Clara'
)
  and FIRST_NAME != 'Clara';

--24. the employee number and name( first name and last name ) for all employees who work in a department with any employee whose name contains a T.
select EMPLOYEE_ID, FIRST_NAME || ' ' || LAST_NAME name
from EMPLOYEES
where DEPARTMENT_ID in (select DEPARTMENT_ID from EMPLOYEES where concat(FIRST_NAME, LAST_NAME) like '%T%');

--25. full name(first and last name), job title, starting and ending date of last jobs for those employees with worked without a commission percentage.
select FIRST_NAME || ' ' || LAST_NAME name, JOB_TITLE, START_DATE, END_DATE
from EMPLOYEES E
         inner join JOBS JS on E.JOB_ID = JS.JOB_ID
         left join JOB_HISTORY JH on JH.JOB_ID = JS.JOB_ID
where COMMISSION_PCT is null;

--26. the employee number, name( first name and last name ), and salary for all employees who earn more than the average salary and who work in a department with any employee with a J in their name.
select EMPLOYEE_ID, FIRST_NAME || ' ' || LAST_NAME name, SALARY
from EMPLOYEES
where SALARY > (select AVG(SALARY) from EMPLOYEES)
  and DEPARTMENT_ID in (select distinct DEPARTMENT_ID from EMPLOYEES where concat(FIRST_NAME, LAST_NAME) like '%J%');

--27. the employee number, name( first name and last name ) and job title for all employees whose salary is smaller than any salary of those employees whose job title is MK_MAN.
select EMPLOYEE_ID, FIRST_NAME || ' ' || LAST_NAME name, JOB_TITLE
from EMPLOYEES
         inner join JOBS using (JOB_ID)
where SALARY < (select MIN(SALARY)
                from EMPLOYEES
                where JOB_ID = 'MK_MAN');

--28. the employee number, name( first name and last name ) and job title for all employees whose salary is smaller than any salary of those employees whose job title is MK_MAN. Exclude Job title MK_MAN.
select EMPLOYEE_ID, FIRST_NAME || ' ' || LAST_NAME name, JOB_TITLE
from EMPLOYEES
         inner join JOBS using (JOB_ID)
where SALARY < (select MIN(SALARY)
                from EMPLOYEES
                where JOB_ID = 'MK_MAN');

--29. all the information of those employees who did not have any job in the past.
select emp.EMPLOYEE_ID,
       EMP.FIRST_NAME,
       EMP.LAST_NAME,
       EMP.EMAIL,
       EMP.PHONE_NUMBER,
       EMP.HIRE_DATE,
       EMP.SALARY,
       EMP.COMMISSION_PCT,
       JOB_TITLE,
       START_DATE,
       END_DATE,
       DEPARTMENT_NAME,
       STREET_ADDRESS,
       POSTAL_CODE,
       CITY,
       STATE_PROVINCE,
       COUNTRY_NAME,
       REGION_NAME
from EMPLOYEES EMP
         left join EMPLOYEES MGR on EMP.MANAGER_ID = EMP.EMPLOYEE_ID
         left join DEPARTMENTS D on EMP.DEPARTMENT_ID = D.DEPARTMENT_ID
         left join JOBS JS on EMP.JOB_ID = JS.JOB_ID
         left join JOB_HISTORY JH on EMP.JOB_ID = JH.JOB_ID
         left join LOCATIONS L on D.LOCATION_ID = L.LOCATION_ID
         left join COUNTRIES CT on L.COUNTRY_ID = CT.COUNTRY_ID
         left join REGIONS R on CT.REGION_ID = R.REGION_ID
where EMP.EMPLOYEE_ID not in (select EMPLOYEE_ID from JOB_HISTORY);

--30. the employee number, name( first name and last name ) and job title for all employees whose salary is more than any average salary of any department.
select EMPLOYEE_ID, FIRST_NAME || ' ' || LAST_NAME name, JOB_TITLE
from EMPLOYEES
         inner join JOBS using (JOB_ID)
where SALARY > (select max(AVG_SALARY)
                from (select avg(SALARY) AVG_SALARY
                      from EMPLOYEES
                      group by DEPARTMENT_ID));

--31. the employee id, name ( first name and last name ) and the job id column with a modified title SALESMAN for
-- those employees whose job title is ST_MAN and DEVELOPER for whose job title is IT_PROG.
select EMPLOYEE_ID,
       FIRST_NAME || ' ' || LAST_NAME as name,
       (case
            when JOB_ID = 'ST_MAN' then 'SALESMAN'
            when JOB_ID = 'IT_PROG' then 'DEVELOPER'
            else JOB_ID end)          as JOB
from EMPLOYEES;

--32. the employee id, name ( first name and last name ), salary and the SalaryStatus column with a title HIGH and LOW respectively for those employees whose salary is more than and less than the average salary of all employees.
select EMPLOYEE_ID,
       FIRST_NAME || ' ' || LAST_NAME                                            name,
       SALARY,
       (case
            when SALARY > (select avg(SALARY) from EMPLOYEES) then 'HIGH'
            when SALARY < (select avg(SALARY) from EMPLOYEES) then 'LOW' end) as salary_status
from EMPLOYEES;

--33. the employee id, name ( first name and last name ), SalaryDrawn, AvgCompare (salary - the average salary of all employees)
-- and the SalaryStatus column with a title HIGH and LOW respectively for those employees whose salary is more than and less than
-- the average salary of all employees.
select EMPLOYEE_ID,
       FIRST_NAME || ' ' || LAST_NAME                                         as name,
       SALARY                                                                 as salary_drown,
       (select avg(SALARY) from EMPLOYEES)                                    as avg_compare,
       (case
            when SALARY > (select avg(SALARY) from EMPLOYEES) then 'HIGH'
            when SALARY < (select avg(SALARY) from EMPLOYEES) then 'LOW' end) as salary_status
from EMPLOYEES;

--34. all the employees who earn more than the average and who work in any of the IT departments.
select EMPLOYEE_ID,
       FIRST_NAME || ' ' || LAST_NAME as name
from EMPLOYEES E
         inner join DEPARTMENTS D on D.DEPARTMENT_ID = E.DEPARTMENT_ID
where SALARY > (select avg(SALARY) from EMPLOYEES)
  and DEPARTMENT_NAME like 'IT%';

--35. who earns more than Mr. Ozer.
select EMPLOYEE_ID,
       FIRST_NAME || ' ' || LAST_NAME as name
from EMPLOYEES
where SALARY > (select SALARY from EMPLOYEES where LAST_NAME = 'Ozer');

--36. which employees have a manager who works for a department based in the US.
select EMP.EMPLOYEE_ID,
       EMP.FIRST_NAME || ' ' || EMP.LAST_NAME as name
from EMPLOYEES EMP
         inner join EMPLOYEES MGR on EMP.MANAGER_ID = MGR.EMPLOYEE_ID
         inner join DEPARTMENTS D on MGR.DEPARTMENT_ID = D.DEPARTMENT_ID
         inner join LOCATIONS L on D.LOCATION_ID = L.LOCATION_ID
where L.COUNTRY_ID = 'US';

--37. the names of all employees whose salary is greater than 50% of their department’s total salary bill.
select FIRST_NAME || ' ' || LAST_NAME as name
from EMPLOYEES E
where SALARY > (select sum(SALARY) / 2 from EMPLOYEES where E.DEPARTMENT_ID = DEPARTMENT_ID);

--38. the employee id, name ( first name and last name ), salary, department name and city for all
--the employees who gets the salary as the salary earn by the employee which is maximum within the
-- joining person January 1st, 2002 and December 31st, 2003.
select EMPLOYEE_ID, FIRST_NAME || ' ' || LAST_NAME as name, SALARY, DEPARTMENT_NAME, CITY
from EMPLOYEES E
         left join DEPARTMENTS D on E.DEPARTMENT_ID = D.DEPARTMENT_ID
         left join LOCATIONS L on D.LOCATION_ID = L.LOCATION_ID
where SALARY = (select max(SALARY)
                from EMPLOYEES
                where HIRE_DATE between to_date('1 Jan 2002', 'DD MON YYYY') and to_date('31 Dec 2003', 'DD MON YYYY'));

--39. the first and last name, salary, and department ID for all those employees who earn more
-- than the average salary and arrange the list in descending order on salary.
select FIRST_NAME, LAST_NAME, SALARY, DEPARTMENT_ID
from EMPLOYEES
where SALARY > (select avg(SALARY) from EMPLOYEES)
order by SALARY desc;

--40. the first and last name, salary, and department ID for those employees who earn more than the maximum
-- salary of a department which ID is 40.
select FIRST_NAME, LAST_NAME, SALARY, DEPARTMENT_ID
from EMPLOYEES
where SALARY > (select max(SALARY) from EMPLOYEES where DEPARTMENT_ID = 40);

--41. the department name and Id for all departments where they located, that Id is equal to the
-- Id for the location where department number 30 is located.
select DEPARTMENT_NAME, DEPARTMENT_ID
from DEPARTMENTS
where LOCATION_ID = (select LOCATION_ID
                     from DEPARTMENTS
                     where DEPARTMENT_ID = 30);

--42. the first and last name, salary, and department ID for all those employees who work in that
-- department where the employee works who hold the ID 201.
select FIRST_NAME, LAST_NAME, SALARY, DEPARTMENT_ID
from EMPLOYEES
where DEPARTMENT_ID = (select DEPARTMENT_ID from EMPLOYEES where EMPLOYEE_ID = 201);

--43. the first and last name, salary, and department ID for those employees whose salary is equal to the salary of the
-- employee who works in that department which ID is 40.
select FIRST_NAME, LAST_NAME, SALARY, DEPARTMENT_ID
from EMPLOYEES E1
where SALARY in (select SALARY from EMPLOYEES E2 where E2.DEPARTMENT_ID = 40);

--44. the first and last name, salary, and department ID for those employees who earn more than the minimum
-- salary of a department which ID is 40.
select FIRST_NAME, LAST_NAME, DEPARTMENT_ID
from EMPLOYEES
where SALARY > (select min(SALARY) from EMPLOYEES where DEPARTMENT_ID = 40);

--45. the first and last name, salary, and department ID for those employees who earn less than the minimum
-- salary of a department which ID is 70.
select FIRST_NAME, LAST_NAME, DEPARTMENT_ID
from EMPLOYEES
where SALARY < (select min(SALARY) from EMPLOYEES where DEPARTMENT_ID = 70);

--46. the first and last name, salary, and department ID for those employees who earn less than the average
-- salary, and also work at the department where the employee Laura is working as a first name holder.
select FIRST_NAME, LAST_NAME, DEPARTMENT_ID
from EMPLOYEES
where SALARY < (select avg(SALARY) from EMPLOYEES)
  and DEPARTMENT_ID in (select DEPARTMENT_ID from EMPLOYEES where FIRST_NAME = 'Laura');

--47. the full name (first and last name) of manager who is supervising 4 or more employees.
select FIRST_NAME || ' ' || LAST_NAME as name
from EMPLOYEES
where EMPLOYEE_ID in (select MANAGER_ID from EMPLOYEES group by MANAGER_ID having count(*) > 4);

--48. the details of the current job for those employees who worked as a Sales Representative in the past.
select E.EMPLOYEE_ID, CUR_JOB.JOB_TITLE, CUR_JOB.MIN_SALARY, CUR_JOB.MAX_SALARY
from EMPLOYEES E
         inner join JOBS CUR_JOB on E.JOB_ID = CUR_JOB.JOB_ID
where E.EMPLOYEE_ID in (select JH.EMPLOYEE_ID
                        from JOB_HISTORY JH
                                 left join JOBS PRV_JOB on JH.JOB_ID = PRV_JOB.JOB_ID
                        where PRV_JOB.JOB_TITLE = 'Sales Representative');


--49. all the infromation about those employees who earn second lowest salary of all the employees.
select emp.EMPLOYEE_ID,
       EMP.FIRST_NAME,
       EMP.LAST_NAME,
       EMP.EMAIL,
       EMP.PHONE_NUMBER,
       EMP.HIRE_DATE,
       EMP.SALARY,
       EMP.COMMISSION_PCT,
       JOB_TITLE,
       START_DATE,
       END_DATE,
       DEPARTMENT_NAME,
       STREET_ADDRESS,
       POSTAL_CODE,
       CITY,
       STATE_PROVINCE,
       COUNTRY_NAME,
       REGION_NAME
from EMPLOYEES EMP
         left join EMPLOYEES MGR on EMP.MANAGER_ID = EMP.EMPLOYEE_ID
         left join DEPARTMENTS D on EMP.DEPARTMENT_ID = D.DEPARTMENT_ID
         left join JOBS JS on EMP.JOB_ID = JS.JOB_ID
         left join JOB_HISTORY JH on EMP.JOB_ID = JH.JOB_ID
         left join LOCATIONS L on D.LOCATION_ID = L.LOCATION_ID
         left join COUNTRIES CT on L.COUNTRY_ID = CT.COUNTRY_ID
         left join REGIONS R on CT.REGION_ID = R.REGION_ID
where EMP.SALARY = (select SALARY
                    from (select SALARY, ROW_NUMBER() over (order by SALARY NULLS LAST) ROWNUMBER
                          from EMPLOYEES
                          group by SALARY)
                    where ROWNUMBER = 2
);
--50. the department ID, full name (first and last name), salary for those employees who is highest salary drawar in a department.
select DEPARTMENT_ID, FIRST_NAME, LAST_NAME, SALARY
from EMPLOYEES E1
where SALARY =
      (select max(E2.SALARY) from EMPLOYEES E2 where E1.DEPARTMENT_ID = E2.DEPARTMENT_ID group by E2.DEPARTMENT_ID)
