--INSERT ALL
--INTO TODO_TABLE (TODO_ID, TODO_DESC, TODO_ISDONE, TODO_TARGETDATE, TODO_USER) 
--VALUES (1001,'Learn Spring Boot',0,sysdate,'imh')
--INTO TODO_TABLE (TODO_ID, TODO_DESC, TODO_ISDONE, TODO_TARGETDATE, TODO_USER)
--VALUES (1002,'Learn Angular JS',0,sysdate,'imh')
--INTO TODO_TABLE (TODO_ID, TODO_DESC, TODO_ISDONE, TODO_TARGETDATE, TODO_USER)
--VALUES (1003,'Learn To Dance',0,sysdate,'imh')
--SELECT * FROM dual;

--CAUTION. Insert ';' only in the last instruction.
INSERT INTO TODO_TABLE VALUES (1001,'Learn Spring Boot',0,sysdate,'imh')
INSERT INTO TODO_TABLE VALUES (1002,'Learn Angular JS',0,sysdate,'imh')
INSERT INTO TODO_TABLE VALUES (1003,'Learn To Dance',0,sysdate,'imh');