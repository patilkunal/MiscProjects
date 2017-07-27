Dim oMD5
Dim Con
Dim RS
Dim sql

Dim DSN, dbuser, dbpassword
DSN = "mmsmrm"
dbuser = "mmsmrmsqllogin"
dbpassword = "password"

Set oMD5 = CreateObject("DigiSign.Signature")
set Con = CreateObject("ADODB.Connection")
Con.Open DSN, dbuser, dbpassword
sql = "select suserid, spassword from tblUsers"
Set RS = CreateObject("ADODB.Recordset")
Set RS.ActiveConnection = Con
RS.Open sql
if Not RS.EOF Then
While Not RS.EOF
	hashpass = oMD5.EncryptMD5(RS("suserid"))
	If hashpass = RS("spassword") Then
		sql = "update tblUsers set dtPasswordUpdateDate=(GETDATE() - 92) where sUserid='" & RS("suserid") & "'"
		Con.execute sql
		WScript.StdOut.Write "Expired password for " & RS("suserid") & vbCrLf
	End if
	RS.MoveNext
WEnd
End if
RS.Close
Set RS.ActiveConnection = Nothing
Con.Close
Set oMD5 = Nothing
Set RS = Nothing
Set Con = Nothing