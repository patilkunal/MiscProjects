strComputer = "alfexbe1.itlogon.com"

Set wbemServices = GetObject("winmgmts:\\" & strComputer)
Set wbemObjectSet = wbemServices.InstancesOf("Win32_LogicalMemoryConfiguration")

For Each wbemObject In wbemObjectSet
    WScript.Echo "Total Physical Memory (kb): " & wbemObject.TotalPhysicalMemory
Next
