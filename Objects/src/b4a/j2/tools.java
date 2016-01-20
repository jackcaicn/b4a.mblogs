package b4a.j2;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class tools {
private static tools mostCurrent = new tools();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public main _main = null;
public v2 _v2 = null;
public smsservice _smsservice = null;
public static String  _b(anywheresoftware.b4a.BA _ba) throws Exception{
 //BA.debugLineNum = 8;BA.debugLine="Sub b";
 //BA.debugLineNum = 9;BA.debugLine="Log(\"\")";
anywheresoftware.b4a.keywords.Common.Log("");
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _filesize(anywheresoftware.b4a.BA _ba,long _lenght) throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub fileSize(lenght As Long) As String";
 //BA.debugLineNum = 13;BA.debugLine="If lenght<1024 Then";
if (_lenght<1024) { 
 //BA.debugLineNum = 14;BA.debugLine="Return lenght&\" Byte\"";
if (true) return BA.NumberToString(_lenght)+" Byte";
 };
 //BA.debugLineNum = 16;BA.debugLine="lenght=lenght/1024";
_lenght = (long)(_lenght/(double)1024);
 //BA.debugLineNum = 17;BA.debugLine="If lenght<1024 Then";
if (_lenght<1024) { 
 //BA.debugLineNum = 18;BA.debugLine="Return lenght&\" KB\"";
if (true) return BA.NumberToString(_lenght)+" KB";
 }else {
 //BA.debugLineNum = 20;BA.debugLine="lenght=lenght/1024";
_lenght = (long)(_lenght/(double)1024);
 //BA.debugLineNum = 21;BA.debugLine="Return lenght&\" MB\"";
if (true) return BA.NumberToString(_lenght)+" MB";
 };
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
}
