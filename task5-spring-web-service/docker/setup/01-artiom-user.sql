ALTER SESSION SET CONTAINER=PDB;
CREATE USER artiom IDENTIFIED BY artiom;
GRANT CREATE SESSION TO artiom;
GRANT CREATE TABLE, CREATE VIEW, CREATE SEQUENCE, CREATE SYNONYM TO artiom;
GRANT CREATE PROCEDURE TO artiom;
GRANT CREATE TRIGGER TO artiom;
GRANT UNLIMITED TABLESPACE TO artiom;