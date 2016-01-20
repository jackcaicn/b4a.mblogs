package b4a.mblogs;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class httputils {
private static httputils mostCurrent = new httputils();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.collections.List _tasks = null;
public static anywheresoftware.b4a.objects.collections.Map _successfulurls = null;
public static boolean _working = false;
public static boolean _complete = false;
public static String _job = "";
public static String _callbackactivity = "";
public static String _callbackjobdonesub = "";
public static String _callbackurldonesub = "";
public main _main = null;
public httputilsservice _httputilsservice = null;
public view _view = null;
public static String  _download(anywheresoftware.b4a.BA _ba,String _jobname,String _url) throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Download(JobName As String, URL As String)";
 //BA.debugLineNum = 17;BA.debugLine="DownloadList(JobName, Array As String(URL))";
_downloadlist(_ba,_jobname,anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{_url}));
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
public static String  _downloadlist(anywheresoftware.b4a.BA _ba,String _jobname,anywheresoftware.b4a.objects.collections.List _urls) throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub DownloadList(JobName As String, URLs As List)";
 //BA.debugLineNum = 22;BA.debugLine="If internalCheckIfCanStart(JobName) = False Then Return";
if (_internalcheckifcanstart(_ba,_jobname)==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 23;BA.debugLine="Tasks = URLs";
_tasks = _urls;
 //BA.debugLineNum = 24;BA.debugLine="HttpUtilsService.Post = False";
mostCurrent._httputilsservice._post = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 25;BA.debugLine="StartService(HttpUtilsService)";
anywheresoftware.b4a.keywords.Common.StartService(_ba,(Object)(mostCurrent._httputilsservice.getObject()));
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _getbitmap(anywheresoftware.b4a.BA _ba,String _url) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _b = null;
 //BA.debugLineNum = 97;BA.debugLine="Sub GetBitmap(URL As String) As Bitmap";
 //BA.debugLineNum = 98;BA.debugLine="Dim b As Bitmap";
_b = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 99;BA.debugLine="If IsSuccess(URL) = False Then";
if (_issuccess(_ba,_url)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 100;BA.debugLine="Log(\"Task not completed successfully.\")";
anywheresoftware.b4a.keywords.Common.Log("Task not completed successfully.");
 //BA.debugLineNum = 101;BA.debugLine="Return b";
if (true) return _b;
 };
 //BA.debugLineNum = 103;BA.debugLine="b = LoadBitmap(HttpUtilsService.TempFolder, SuccessfulUrls.Get(URL))";
_b = anywheresoftware.b4a.keywords.Common.LoadBitmap(mostCurrent._httputilsservice._tempfolder,String.valueOf(_successfulurls.Get((Object)(_url))));
 //BA.debugLineNum = 104;BA.debugLine="Return b";
if (true) return _b;
 //BA.debugLineNum = 105;BA.debugLine="End Sub";
return null;
}
public static anywheresoftware.b4a.objects.streams.File.InputStreamWrapper  _getinputstream(anywheresoftware.b4a.BA _ba,String _url) throws Exception{
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _in = null;
 //BA.debugLineNum = 107;BA.debugLine="Sub GetInputStream(URL As String) As InputStream";
 //BA.debugLineNum = 108;BA.debugLine="Dim In As InputStream";
_in = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();
 //BA.debugLineNum = 109;BA.debugLine="If IsSuccess(URL) = False Then";
if (_issuccess(_ba,_url)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 110;BA.debugLine="Log(\"Task not completed successfully.\")";
anywheresoftware.b4a.keywords.Common.Log("Task not completed successfully.");
 //BA.debugLineNum = 111;BA.debugLine="Return In";
if (true) return _in;
 };
 //BA.debugLineNum = 113;BA.debugLine="In = File.OpenInput(HttpUtilsService.TempFolder, SuccessfulUrls.Get(URL))";
_in = anywheresoftware.b4a.keywords.Common.File.OpenInput(mostCurrent._httputilsservice._tempfolder,String.valueOf(_successfulurls.Get((Object)(_url))));
 //BA.debugLineNum = 114;BA.debugLine="Return In";
if (true) return _in;
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return null;
}
public static String  _getstring(anywheresoftware.b4a.BA _ba,String _url) throws Exception{
 //BA.debugLineNum = 89;BA.debugLine="Sub GetString(URL As String) As String";
 //BA.debugLineNum = 90;BA.debugLine="If IsSuccess(URL) = False Then";
if (_issuccess(_ba,_url)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 91;BA.debugLine="Log(\"Task not completed successfully.\")";
anywheresoftware.b4a.keywords.Common.Log("Task not completed successfully.");
 //BA.debugLineNum = 92;BA.debugLine="Return \"\"";
if (true) return "";
 };
 //BA.debugLineNum = 94;BA.debugLine="Return File.GetText(HttpUtilsService.TempFolder, SuccessfulUrls.Get(URL))";
if (true) return anywheresoftware.b4a.keywords.Common.File.GetText(mostCurrent._httputilsservice._tempfolder,String.valueOf(_successfulurls.Get((Object)(_url))));
 //BA.debugLineNum = 95;BA.debugLine="End Sub";
return "";
}
public static boolean  _internalcheckifcanstart(anywheresoftware.b4a.BA _ba,String _jobname) throws Exception{
 //BA.debugLineNum = 71;BA.debugLine="Sub internalCheckIfCanStart(JobName As String) As Boolean";
 //BA.debugLineNum = 72;BA.debugLine="If Working Then";
if (_working) { 
 //BA.debugLineNum = 73;BA.debugLine="Log(\"Already working. Request ignored (\" & JobName & \")\")";
anywheresoftware.b4a.keywords.Common.Log("Already working. Request ignored ("+_jobname+")");
 //BA.debugLineNum = 74;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 76;BA.debugLine="Log(\"Starting Job: \" & JobName)";
anywheresoftware.b4a.keywords.Common.Log("Starting Job: "+_jobname);
 //BA.debugLineNum = 77;BA.debugLine="Job = JobName";
_job = _jobname;
 //BA.debugLineNum = 78;BA.debugLine="Working = True";
_working = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 79;BA.debugLine="Complete = False";
_complete = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 80;BA.debugLine="SuccessfulUrls.Initialize";
_successfulurls.Initialize();
 //BA.debugLineNum = 81;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return false;
}
public static boolean  _issuccess(anywheresoftware.b4a.BA _ba,String _url) throws Exception{
 //BA.debugLineNum = 84;BA.debugLine="Sub IsSuccess(URL As String) As Boolean";
 //BA.debugLineNum = 85;BA.debugLine="Return SuccessfulUrls.ContainsKey(URL)";
if (true) return _successfulurls.ContainsKey((Object)(_url));
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
return false;
}
public static String  _postbytes(anywheresoftware.b4a.BA _ba,String _jobname,String _url,byte[] _data) throws Exception{
 //BA.debugLineNum = 62;BA.debugLine="Sub PostBytes(JobName As String, URL As String, Data() As Byte)";
 //BA.debugLineNum = 63;BA.debugLine="If internalCheckIfCanStart(JobName) = False Then Return";
if (_internalcheckifcanstart(_ba,_jobname)==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 64;BA.debugLine="HttpUtilsService.PostInputStream = Null";
mostCurrent._httputilsservice._postinputstream.setObject((java.io.InputStream)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 65;BA.debugLine="HttpUtilsService.PostBytes = Data";
mostCurrent._httputilsservice._postbytes = _data;
 //BA.debugLineNum = 66;BA.debugLine="Tasks = Array As String(URL)";
_tasks = anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{_url});
 //BA.debugLineNum = 67;BA.debugLine="HttpUtilsService.Post = True";
mostCurrent._httputilsservice._post = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 68;BA.debugLine="StartService(HttpUtilsService)";
anywheresoftware.b4a.keywords.Common.StartService(_ba,(Object)(mostCurrent._httputilsservice.getObject()));
 //BA.debugLineNum = 69;BA.debugLine="End Sub";
return "";
}
public static String  _postfile(anywheresoftware.b4a.BA _ba,String _jobname,String _url,String _dir,String _filename) throws Exception{
int _length = 0;
anywheresoftware.b4a.objects.streams.File.InputStreamWrapper _in = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
 //BA.debugLineNum = 34;BA.debugLine="Sub PostFile(JobName As String, URL As String, Dir As String, FileName As String)";
 //BA.debugLineNum = 35;BA.debugLine="If internalCheckIfCanStart(JobName) = False Then Return";
if (_internalcheckifcanstart(_ba,_jobname)==anywheresoftware.b4a.keywords.Common.False) { 
if (true) return "";};
 //BA.debugLineNum = 36;BA.debugLine="Dim length As Int";
_length = 0;
 //BA.debugLineNum = 37;BA.debugLine="If Dir = File.DirAssets Then";
if ((_dir).equals(anywheresoftware.b4a.keywords.Common.File.getDirAssets())) { 
 //BA.debugLineNum = 38;BA.debugLine="Log(\"Cannot send files from the assets folder.\")";
anywheresoftware.b4a.keywords.Common.Log("Cannot send files from the assets folder.");
 //BA.debugLineNum = 39;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 41;BA.debugLine="length = File.Size(Dir, FileName)";
_length = (int)(anywheresoftware.b4a.keywords.Common.File.Size(_dir,_filename));
 //BA.debugLineNum = 42;BA.debugLine="Dim In As InputStream";
_in = new anywheresoftware.b4a.objects.streams.File.InputStreamWrapper();
 //BA.debugLineNum = 43;BA.debugLine="In = File.OpenInput(Dir, FileName)";
_in = anywheresoftware.b4a.keywords.Common.File.OpenInput(_dir,_filename);
 //BA.debugLineNum = 44;BA.debugLine="If length < 1000000 Then '1mb";
if (_length<1000000) { 
 //BA.debugLineNum = 47;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 48;BA.debugLine="out.InitializeToBytesArray(length)";
_out.InitializeToBytesArray(_length);
 //BA.debugLineNum = 49;BA.debugLine="File.Copy2(In, out)";
anywheresoftware.b4a.keywords.Common.File.Copy2((java.io.InputStream)(_in.getObject()),(java.io.OutputStream)(_out.getObject()));
 //BA.debugLineNum = 50;BA.debugLine="HttpUtilsService.PostInputStream = Null";
mostCurrent._httputilsservice._postinputstream.setObject((java.io.InputStream)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 51;BA.debugLine="HttpUtilsService.PostBytes = out.ToBytesArray";
mostCurrent._httputilsservice._postbytes = _out.ToBytesArray();
 }else {
 //BA.debugLineNum = 53;BA.debugLine="HttpUtilsService.PostInputStream = In";
mostCurrent._httputilsservice._postinputstream = _in;
 //BA.debugLineNum = 54;BA.debugLine="HttpUtilsService.PostLength = length";
mostCurrent._httputilsservice._postlength = _length;
 };
 //BA.debugLineNum = 56;BA.debugLine="Tasks = Array As String(URL)";
_tasks = anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{_url});
 //BA.debugLineNum = 57;BA.debugLine="HttpUtilsService.Post = True";
mostCurrent._httputilsservice._post = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 58;BA.debugLine="StartService(HttpUtilsService)";
anywheresoftware.b4a.keywords.Common.StartService(_ba,(Object)(mostCurrent._httputilsservice.getObject()));
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return "";
}
public static String  _poststring(anywheresoftware.b4a.BA _ba,String _jobname,String _url,String _text) throws Exception{
 //BA.debugLineNum = 29;BA.debugLine="Sub PostString(JobName As String, URL As String, Text As String)";
 //BA.debugLineNum = 30;BA.debugLine="PostBytes(JobName, URL, Text.GetBytes(\"UTF8\"))";
_postbytes(_ba,_jobname,_url,_text.getBytes("UTF8"));
 //BA.debugLineNum = 31;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 3;BA.debugLine="Dim Tasks As List 'List of URLs to fetch.";
_tasks = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 6;BA.debugLine="Dim SuccessfulUrls As Map";
_successfulurls = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 7;BA.debugLine="Dim Working As Boolean 'True when a job is still running";
_working = false;
 //BA.debugLineNum = 8;BA.debugLine="Dim Complete As Boolean 'True after a job has completer";
_complete = false;
 //BA.debugLineNum = 9;BA.debugLine="Dim Job As String 'Name of the current running Job";
_job = "";
 //BA.debugLineNum = 10;BA.debugLine="Dim CallbackActivity As String 'Name of Activity that handles the callbacks.";
_callbackactivity = "";
 //BA.debugLineNum = 11;BA.debugLine="Dim CallbackJobDoneSub As String 'Name of the JobDone callback sub.";
_callbackjobdonesub = "";
 //BA.debugLineNum = 12;BA.debugLine="Dim CallbackUrlDoneSub As String 'Name of the UrlDone callback sub.";
_callbackurldonesub = "";
 //BA.debugLineNum = 13;BA.debugLine="End Sub";
return "";
}
}
