Type=StaticCode
Version=2.5
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	
End Sub
Sub b 
	Log("")
End Sub

Sub fileSize(lenght As Long) As String
	If lenght<1024 Then
		Return lenght&" Byte"
	End If
	lenght=lenght/1024
	If lenght<1024 Then
		Return lenght&" KB"
	Else
		lenght=lenght/1024
		Return lenght&" MB"
	End If
End Sub