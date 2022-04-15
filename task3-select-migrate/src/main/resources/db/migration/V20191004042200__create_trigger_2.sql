create or replace procedure
    insert_into_employment_logs (
<<<<<<< HEAD
        first_name_arg  in  varchar2,
        last_name_arg   in  varchar2,
        what_happened   in  varchar2
    ) as
    begin
        insert into EMPLOYMENT_LOGS (FIRST_NAME, LAST_NAME, EMPLOYMENT_ACTION)
        values (first_name_arg, last_name_arg, what_happened);
    end insert_into_employment_logs;

=======
        first_name      varchar2(20),
        last_name       varchar2(25),
        what_happened   varchar(5)
    ) as
    begin
        insert into EMPLOYMENT_LOGS (FIRST_NAME, LAST_NAME, EMPLOYMENT_ACTION)
        values (first_name, last_name, what_happened);
    end;

-- TODO: have to finish
>>>>>>> origin/feature/task3Migrate
create or replace trigger trigger_2
    before insert or delete on EMPLOYEES
    for each row
    declare
    begin
        case
            when INSERTING then insert_into_employment_logs(:new.first_name, :new.last_name, 'HIRED');
            when DELETING then insert_into_employment_logs(:old.first_name, :old.last_name, 'FIRED');
        end case;
    end;

