package b4a.mblogs;

import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	private static final boolean fullScreen = false;
	private static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.mblogs", "b4a.mblogs.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                anywheresoftware.b4a.keywords.Common.Log("Killing previous instance (main).");
				p.finish();
			}
		}
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
		BA.handler.postDelayed(new WaitForLayout(), 5);

	}
	private static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
		activityBA = new BA(this, layout, processBA, "b4a.mblogs", "b4a.mblogs.main");
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        initializeProcessGlobals();		
        initializeGlobals();
        
        anywheresoftware.b4a.keywords.Common.Log("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (mostCurrent == null || mostCurrent != this)
			return;
        processBA.setActivityPaused(false);
        anywheresoftware.b4a.keywords.Common.Log("** Activity (main) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
		return true;
	}
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return main.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true)
				return true;
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
		this.setIntent(intent);
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        anywheresoftware.b4a.keywords.Common.Log("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            anywheresoftware.b4a.keywords.Common.Log("** Activity (main) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}

public anywheresoftware.b4a.keywords.Common __c = null;
public static String _b4a = "";
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listview1 = null;
public httputils _httputils = null;
public httputilsservice _httputilsservice = null;
public view _view = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 30;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 31;BA.debugLine="Activity.LoadLayout(\"list\")";
mostCurrent._activity.LoadLayout("list",mostCurrent.activityBA);
 //BA.debugLineNum = 32;BA.debugLine="HttpUtils.CallbackActivity = \"Main\" 'Current activity name.";
mostCurrent._httputils._callbackactivity = "Main";
 //BA.debugLineNum = 33;BA.debugLine="HttpUtils.CallbackJobDoneSub = \"JobDone\"";
mostCurrent._httputils._callbackjobdonesub = "JobDone";
 //BA.debugLineNum = 34;BA.debugLine="HttpUtils.Download(\"Job1\", b4a)";
mostCurrent._httputils._download(mostCurrent.activityBA,"Job1",_b4a);
 //BA.debugLineNum = 35;BA.debugLine="Label1.Top=0";
mostCurrent._label1.setTop((int)(0));
 //BA.debugLineNum = 36;BA.debugLine="Label1.Left=0";
mostCurrent._label1.setLeft((int)(0));
 //BA.debugLineNum = 37;BA.debugLine="Label1.Height=40dip";
mostCurrent._label1.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(40)));
 //BA.debugLineNum = 38;BA.debugLine="Label1.Width=100%x";
mostCurrent._label1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float)(100),mostCurrent.activityBA));
 //BA.debugLineNum = 39;BA.debugLine="ListView1.Top=Label1.Height";
mostCurrent._listview1.setTop(mostCurrent._label1.getHeight());
 //BA.debugLineNum = 40;BA.debugLine="ListView1.Left=0";
mostCurrent._listview1.setLeft((int)(0));
 //BA.debugLineNum = 41;BA.debugLine="ListView1.Height=100%y-Label1.Height";
mostCurrent._listview1.setHeight((int)(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float)(100),mostCurrent.activityBA)-mostCurrent._label1.getHeight()));
 //BA.debugLineNum = 42;BA.debugLine="ListView1.Width=100%x";
mostCurrent._listview1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float)(100),mostCurrent.activityBA));
 //BA.debugLineNum = 48;BA.debugLine="ProgressDialogShow(\"loading...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"loading...");
 //BA.debugLineNum = 49;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 50;BA.debugLine="cd.Initialize(Colors.Black, 5dip)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.Black,anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(5)));
 //BA.debugLineNum = 51;BA.debugLine="ListView1.Background=cd";
mostCurrent._listview1.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 52;BA.debugLine="Activity.AddMenuItem(\"Exit\",\"mnuPage2\")";
mostCurrent._activity.AddMenuItem("Exit","mnuPage2");
 //BA.debugLineNum = 53;BA.debugLine="Activity.AddMenuItem(\"About\",\"mnuPage1\")";
mostCurrent._activity.AddMenuItem("About","mnuPage1");
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 60;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 56;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 58;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (processGlobalsRun == false) {
	    processGlobalsRun = true;
		try {
		        main._process_globals();
httputils._process_globals();
httputilsservice._process_globals();
view._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (view.mostCurrent != null);
return vis;}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 26;BA.debugLine="Dim Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Dim ListView1 As ListView";
mostCurrent._listview1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(String _job) throws Exception{
String _s = "";
 //BA.debugLineNum = 64;BA.debugLine="Sub JobDone (Job As String)";
 //BA.debugLineNum = 65;BA.debugLine="Dim s As String";
_s = "";
 //BA.debugLineNum = 66;BA.debugLine="If HttpUtils.IsSuccess(b4a) Then";
if (mostCurrent._httputils._issuccess(mostCurrent.activityBA,_b4a)) { 
 //BA.debugLineNum = 67;BA.debugLine="s = HttpUtils.GetString(b4a)";
_s = mostCurrent._httputils._getstring(mostCurrent.activityBA,_b4a);
 //BA.debugLineNum = 70;BA.debugLine="newsList(s)";
_newslist(_s);
 };
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static String  _listview1_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 99;BA.debugLine="Sub ListView1_ItemClick (Position As Int, Value As Object)";
 //BA.debugLineNum = 101;BA.debugLine="CallSubDelayed2(View,\"viewnews\",Value)";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(mostCurrent.activityBA,(Object)(mostCurrent._view.getObject()),"viewnews",_value);
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return "";
}
public static String  _mnupage1_click() throws Exception{
 //BA.debugLineNum = 105;BA.debugLine="Sub mnuPage1_Click";
 //BA.debugLineNum = 106;BA.debugLine="Msgbox(\"博客园新闻 for Android\"&CRLF&CRLF&\"Created by Basic4android\"&CRLF&CRLF&\"http://chy710.cnblogs.com\",\"About\")";
anywheresoftware.b4a.keywords.Common.Msgbox("博客园新闻 for Android"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"Created by Basic4android"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"http://chy710.cnblogs.com","About",mostCurrent.activityBA);
 //BA.debugLineNum = 107;BA.debugLine="End Sub";
return "";
}
public static String  _mnupage2_click() throws Exception{
 //BA.debugLineNum = 108;BA.debugLine="Sub mnuPage2_Click";
 //BA.debugLineNum = 109;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
return "";
}
public static String  _newslist(String _html) throws Exception{
String _reg = "";
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _mat = null;
String _txt = "";
 //BA.debugLineNum = 73;BA.debugLine="Sub newsList(html As String)";
 //BA.debugLineNum = 74;BA.debugLine="Dim reg As String";
_reg = "";
 //BA.debugLineNum = 75;BA.debugLine="reg=\"<div class=~{0}~list_item~{0}~><a href=~{0}~/mv\\?id=(\\d{6})~{0}~>(.{1,40})</a>~{1}~(.{1,6}),<a href=~{0}~/mc\\?id=\\d{6}~{0}~>\\d{1,3}</a>/\\d{1,3},.{1,6}~{2}~</div>\"";
_reg = "<div class=~{0}~list_item~{0}~><a href=~{0}~/mv\\?id=(\\d{6})~{0}~>(.{1,40})</a>~{1}~(.{1,6}),<a href=~{0}~/mc\\?id=\\d{6}~{0}~>\\d{1,3}</a>/\\d{1,3},.{1,6}~{2}~</div>";
 //BA.debugLineNum = 76;BA.debugLine="reg=reg.Replace(\"~{0}~\",QUOTE)";
_reg = _reg.replace("~{0}~",anywheresoftware.b4a.keywords.Common.QUOTE);
 //BA.debugLineNum = 77;BA.debugLine="reg=reg.Replace(\"~{1}~\",\"\\(\")";
_reg = _reg.replace("~{1}~","\\(");
 //BA.debugLineNum = 78;BA.debugLine="reg=reg.Replace(\"~{2}~\",\"\\)\")";
_reg = _reg.replace("~{2}~","\\)");
 //BA.debugLineNum = 81;BA.debugLine="Dim mat As Matcher";
_mat = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
 //BA.debugLineNum = 82;BA.debugLine="mat=Regex.Matcher(reg,html)";
_mat = anywheresoftware.b4a.keywords.Common.Regex.Matcher(_reg,_html);
 //BA.debugLineNum = 83;BA.debugLine="Dim txt As String";
_txt = "";
 //BA.debugLineNum = 84;BA.debugLine="Do While mat.Find";
while (_mat.Find()) {
 //BA.debugLineNum = 86;BA.debugLine="txt=mat.Group(2)";
_txt = _mat.Group((int)(2));
 //BA.debugLineNum = 87;BA.debugLine="If txt.Length>15 Then";
if (_txt.length()>15) { 
 //BA.debugLineNum = 88;BA.debugLine="txt=txt.SubString2(0,15)";
_txt = _txt.substring((int)(0),(int)(15));
 };
 //BA.debugLineNum = 91;BA.debugLine="ListView1.AddTwoLines2(txt,mat.Group(3),mat.Group(1))";
mostCurrent._listview1.AddTwoLines2(_txt,_mat.Group((int)(3)),(Object)(_mat.Group((int)(1))));
 }
;
 //BA.debugLineNum = 93;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 18;BA.debugLine="Dim b4a As String";
_b4a = "";
 //BA.debugLineNum = 19;BA.debugLine="b4a = \"http://news.cnblogs.com/m\"";
_b4a = "http://news.cnblogs.com/m";
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
}
