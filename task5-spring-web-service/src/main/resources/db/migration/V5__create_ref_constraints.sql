--------------------------------------------------------
--  Ref Constraints for Table EMPLOYEE
--------------------------------------------------------

alter table
    employee
    add constraint emp_depart_fk
        foreign key (department_id) references department;