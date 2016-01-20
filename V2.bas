Type=Activity
Version=2.5
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.Width=200dip
	Activity.Height=300dip
	Activity.LoadLayout("v2")
	Activity.Color=Colors.Transparent
	
'	Dim txt As String="HkY7CwT8DBS6KL2yVWagDA=="
'	Dim b() As Byte
'	b=txt.GetBytes("UTF8")
'	
'	Dim iv(0) As Byte
'	Dim ivs As String="sjDY6X+aPLw="
'	iv = ivs.GetBytes("UTF8")
'	
'	Dim a As Cipher
'	a.Initialize("DES/CBC/PKCS5Padding")
'	a.InitialisationVector = iv
'	
'	Dim kg As KeyGenerator
'	kg.Initialize("DES")
'	kg.Key="szzTnrO3yx1uJEOwzo5xkE/MmpmKi3MX"
'	
'	
'	Dim b2() As Byte=a.Decrypt(b,kg.Key,True)
'	Dim txt2 As String=BytesToString(b2,0,b2.Length,"UTF-8")
'	Msgbox(txt2,"a")
'	
'	Dim Label As Label
'	Dim p As Panel
'	p.Initialize("p")
'	Label.Initialize("")
'	Activity.AddView(p,0,0,100%x,100%y)
'	p.Color=Colors.Transparent
'	
'	p.AddView(Label,10dip,10dip,100dip,30dip)
'	Label.Text="hello"
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub Activity_Click
	Log(IsPaused("V2"))
End Sub

