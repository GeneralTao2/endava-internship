-- TODO: why do I need this view?
CREATE
OR REPLACE VIEW emp_details_view AS
SELECT e.employee_id,
       e.email,
       e.first_name,
       e.last_name,
       e.phone_number,
       e.salary,
       e.department_id,
       d.location,
       d.name

FROM employee e,
     department d
WHERE e.department_id = d.department_id WITH READ ONLY;

