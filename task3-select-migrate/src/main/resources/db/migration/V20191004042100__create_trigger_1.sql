create or replace trigger dep_amt_upd_trg
    after
        insert or update or delete
    on DEPARTMENTS
declare
begin
    -- TODO: How to replace nulls to zeros
    update LOCATIONS L1
    set DEPARTMENT_AMOUNT = (
        select count(D.DEPARTMENT_ID)
        from DEPARTMENTS D
                 inner join LOCATIONS L2 on L2.LOCATION_ID = D.LOCATION_ID
        where L1.LOCATION_ID = L2.LOCATION_ID
        group by L2.LOCATION_ID
    );

end;

