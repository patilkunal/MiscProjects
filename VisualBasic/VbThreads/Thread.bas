Attribute VB_Name = "Module1"
' Structure to hold IDispatch GUID
Type GUID
    Data1 As Long
    Data2 As Integer
    Data3 As Integer
    Data4(7) As Byte
End Type

Public IID_IDispatch As GUID

Declare Function CoMarshalInterThreadInterfaceInStream Lib "ole32.dll" (riid As GUID, ByVal pUnk As IUnknown, ppStm As Long) As Long
Declare Function CoGetInterfaceAndReleaseStream Lib "ole32.dll" (ByVal pStm As Long, riid As GUID, pUnk As IUnknown) As Long
Declare Function CoInitialize Lib "ole32.dll" (ByVal pvReserved As Long) As Long
Declare Sub CoUninitialize Lib "ole32.dll" ()
Declare Function CreateThread Lib "kernel32" (ByVal _
lpSecurityAttributes As Long, ByVal dwStackSize As Long, _
ByVal lpStartAddress As Long, ByVal lpParameter As Long, _
ByVal dwCreationFlags As Long, lpThreadId As Long) _
As Long
Declare Function CloseHandle Lib "kernel32" _
(ByVal hObject As Long) As Long


' A correctly marshaled apartment model callback.
' This is the correct approach, though slower.
Public Function BackgroundFuncApt(ByVal param As Long) As Long
    Dim qobj As Object
    Dim qobj2 As clsBackground
    Dim res&
    ' This new thread is a new apartment, we must
    ' initialize OLE for this apartment
        ' (VB doesn't seem to do it)
    res = CoInitialize(0)
    ' Proper apartment modeled approach
    res = CoGetInterfaceAndReleaseStream(param, IID_IDispatch, qobj)
    Set qobj2 = qobj
    qobj2.DoTheCount (param)
    
    'qobj2.ShowAForm
    ' Alternatively, you can put a wait function here,
    ' then call the qobj function when the wait is satisfied
    ' All calls to CoInitialize must be balanced
    CoUninitialize
End Function

' Start the background thread for this object
' using the apartment model
' Returns zero on error
Public Function StartBackgroundThreadApt(ByVal qobj As _
    clsBackground, ByVal cnt As Integer)
    Dim threadid As Long
    Dim hnd&, res&
    Dim threadparam As Long
    Dim tobj As Object
    Set tobj = qobj
    ' Proper marshaled approach
    InitializeIID
    threadparam = VarPtr(cnt)
    res = CoMarshalInterThreadInterfaceInStream _
              (IID_IDispatch, qobj, threadparam)
    If res <> 0 Then
        StartBackgroundThreadApt = 0
        Exit Function
    End If
    hnd = CreateThread(0, 2000, AddressOf _
              BackgroundFuncApt, threadparam, 0, threadid)
    If hnd = 0 Then
        ' Return with zero (error)
        Exit Function
    End If
    ' We don't need the thread handle
    CloseHandle hnd
    StartBackgroundThreadApt = threadid
End Function


' Initialize the GUID structure
Private Sub InitializeIID()
    Static Initialized As Boolean
    If Initialized Then Exit Sub
    With IID_IDispatch
        .Data1 = &H20400
        .Data2 = 0
        .Data3 = 0
        .Data4(0) = &HC0
        .Data4(7) = &H46
    End With
    Initialized = True
End Sub

Sub Main()
    Set c = New clsBackground
    Debug.Print "starting"
    
    StartBackgroundThreadApt c, 2
End Sub
