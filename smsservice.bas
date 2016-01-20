Type=Service
Version=2.5
@EndOfDesignText@
#Region  Service Attributes 
	#StartAtBoot: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
	Dim g_PhoneEvents As PhoneEvents
	Dim T As Timer
End Sub
Sub Service_Create
	Dim pid As PhoneId
	g_PhoneEvents.InitializeWithPhoneState("PhoneEvents",pid)
End Sub

Sub Service_Start (StartingIntent As Intent)
	t.Initialize("timerTest",1000)
	t.Enabled=True
End Sub

Sub Service_Destroy
	g_PhoneEvents.StopListening
End Sub

Sub PhoneEvents_SmsSentStatus(Success As Boolean, ErrorMessage As String, PhoneNumber As String, Intent As Intent)
	
	Log("call back")
	Log(Success)
	Log(ErrorMessage)
	Log(PhoneNumber)
	Log(Intent)
	
	ToastMessageShow(Success&","&ErrorMessage&","&PhoneNumber,True)
End Sub

Sub PhoneEvents_SmsDelivered(PhoneNumber As String, Intent As Intent)
	ToastMessageShow("PhoneEvents_SmsDelivered phoneNumber:"&PhoneNumber&" Intent:"&Intent,True)
End Sub

Public Sub sendmsg
	
End Sub
Sub timerTest_Tick
	Log(DateTime.Now)
	If (DateTime.Now-Main.timeStart)>5000 Then
		T.Enabled=False
		'If IsPaused(Main)=False Then
			CallSub(Main,"cb")
		'End If
	End If
End Sub