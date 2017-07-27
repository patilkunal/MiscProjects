Dim oMD5
Dim pass
Set oMD5 = CreateObject("DigiSign.Signature")
pass = InputBox("Please enter the string to MD5 encrypt")
WScript.StdOut.Write "MD5 Encrypted string: " & oMD5.EncryptMD5(pass) & vbCrLf
Set oMD5 = Nothing
