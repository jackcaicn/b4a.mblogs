Type=Activity
Version=2.5
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Dim Label1 As Label
	Dim WebView1 As WebView
	Dim url As String
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Activity.LoadLayout("view")
	Label1.Top=0
	Label1.Left=0
	Label1.Height=40dip
	Label1.Width=100%x
	WebView1.Top=Label1.Height
	WebView1.Left=0
	WebView1.Height=100%y-Label1.Height
	WebView1.Width=100%x
	ProgressDialogShow("loading...")
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

Sub viewnews(newsId As String)
	url="http://news.cnblogs.com/mv?id="&newsId
	HttpUtils.CallbackActivity = "view" 'Current activity name.
	HttpUtils.CallbackJobDoneSub = "downview"
	HttpUtils.Download("Job2", url)
End Sub

Sub downview (Job As String)
	Dim s As String
	If HttpUtils.IsSuccess(url) Then
		s = HttpUtils.GetString(url)
		Log(s)
		newsContent(s)
	End If
End Sub

Sub newsContent(html As String)
	Dim reg As String
	reg="<title>(.{1,40}) - 博客园新闻手机版</title>[\s\S]+<div style=~{0}~line-height:1.6;~{0}~>([\s\S]+)</div>[\s\S]*上一篇"
	reg=reg.Replace("~{0}~",QUOTE)
	Log(reg)
	Dim mat As Matcher
	mat=Regex.Matcher(reg,html)
	If mat.Find Then
		Label1.Text=mat.Group(1)
		WebView1.LoadHtml(mat.Group(2))
	End If
	
	ProgressDialogHide
End Sub