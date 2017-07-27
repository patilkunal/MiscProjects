strComputer = "kpatilxp"

Set wbemServices = GetObject("winmgmts:\\" & strComputer)
Set wbemObjectSet = wbemServices.InstancesOf("Win32_Service")

For Each wbemObject In wbemObjectSet
'    WScript.Echo "Display Name:  " & wbemObject.DisplayName & vbCrLf & _
'                 "   State:      " & wbemObject.State       & vbCrLf & _
'                 "   Start Mode: " & wbemObject.StartMode

    WScript.Echo wbemObject.DisplayName & vbTab & vbTab & _
                 wbemObject.State       & vbTab & vbTab & _
                 wbemObject.StartMode
Next
