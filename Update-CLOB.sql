PROCEDURE UpdateTXNSData(ftext IN varchar2) AS
locator_var CLOB;
offset_var INTEGER;
BEGIN
	select filedata into locator_var from ctable where filename = fname for update;
	offset_var := DBMS_LOB.GETLENGTH(locator_var);
	DBMS_LOB.WRITE(locator_var,LENGTH(ftext), offset_var+1, ftext);
	commit;
END;



