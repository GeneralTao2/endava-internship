create or replace procedure
    insert_into_employment_logs (
        first_name      varchar2(20),
        last_name       varchar2(25),
        what_happened   varchar(5)
    ) as
    begin
        insert into EMPLOYMENT_LOGS (FIRST_NAME, LAST_NAME, EMPLOYMENT_ACTION)
        values (first_name, last_name, what_happened);
    end;

-- TODO: have to finish
create or replace trigger trigger_2
    before insert or delete on EMPLOYEES
    for each row
    declare
        what_happened varchar(5);
    begin
        what_happened := case
            when INSERTING then 'HIRING'
            when DELETING  then 'FIRING'
        end;
        insert_into_employment_logs(
            (select FIRST_NAME from INSERTING),
            (select FIRST_NAME from INSERTING),
            what_happened
            );
    end;