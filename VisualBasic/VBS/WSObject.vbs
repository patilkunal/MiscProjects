Set inp = WScript.StdIn
WScript.Echo.Write("Enter some text and press [Enter]: ")
text = inp.ReadLine()
WScript.Echo vbCrLf & "You entered: " & vbCrLf & text
Set args = WScript.Arguments
For i = 0 to args.Count - 1
   WScript.Echo args(i)
Next