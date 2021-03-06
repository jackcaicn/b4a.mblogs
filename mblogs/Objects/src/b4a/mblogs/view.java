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

public class view extends Activity implements B4AActivity{
	public static view mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.mblogs", "b4a.mblogs.view");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                anywheresoftware.b4a.keywords.Common.Log("Killing previous instance (view).");
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
		activityBA = new BA(this, layout, processBA, "b4a.mblogs", "b4a.mblogs.view");
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        initializeProcessGlobals();		
        initializeGlobals();
        
        anywheresoftware.b4a.keywords.Common.Log("** Activity (view) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (mostCurrent == null || mostCurrent != this)
			return;
        processBA.setActivityPaused(false);
        anywheresoftware.b4a.keywords.Common.Log("** Activity (view) Resume **");
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
		return view.class;
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
        anywheresoftware.b4a.keywords.Common.Log("** Activity (view) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            anywheresoftware.b4a.keywords.Common.Log("** Activity (view) Resume **");
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
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.WebViewWrapper _webview1 = null;
public static String _url = "";
public main _main = null;
public httputils _httputils = null;
public httputilsservice _httputilsservice = null;
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 23;BA.debugLine="Activity.LoadLayout(\"view\")";
mostCurrent._activity.LoadLayout("view",mostCurrent.activityBA);
 //BA.debugLineNum = 24;BA.debugLine="Label1.Top=0";
mostCurrent._label1.setTop((int)(0));
 //BA.debugLineNum = 25;BA.debugLine="Label1.Left=0";
mostCurrent._label1.setLeft((int)(0));
 //BA.debugLineNum = 26;BA.debugLine="Label1.Height=40dip";
mostCurrent._label1.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int)(40)));
 //BA.debugLineNum = 27;BA.debugLine="Label1.Width=100%x";
mostCurrent._label1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float)(100),mostCurrent.activityBA));
 //BA.debugLineNum = 28;BA.debugLine="WebView1.Top=Label1.Height";
mostCurrent._webview1.setTop(mostCurrent._label1.getHeight());
 //BA.debugLineNum = 29;BA.debugLine="WebView1.Left=0";
mostCurrent._webview1.setLeft((int)(0));
 //BA.debugLineNum = 30;BA.debugLine="WebView1.Height=100%y-Label1.Height";
mostCurrent._webview1.setHeight((int)(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float)(100),mostCurrent.activityBA)-mostCurrent._label1.getHeight()));
 //BA.debugLineNum = 31;BA.debugLine="WebView1.Width=100%x";
mostCurrent._webview1.setWidth(anywheresoftware.b4a.keywords.Common.PerXToCurrent((float)(100),mostCurrent.activityBA));
 //BA.debugLineNum = 32;BA.debugLine="ProgressDialogShow(\"loading...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"loading...");
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 39;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 35;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public static String  _downview(String _job) throws Exception{
String _s = "";
 //BA.debugLineNum = 50;BA.debugLine="Sub downview (Job As String)";
 //BA.debugLineNum = 51;BA.debugLine="Dim s As String";
_s = "";
 //BA.debugLineNum = 52;BA.debugLine="If HttpUtils.IsSuccess(url) Then";
if (mostCurrent._httputils._issuccess(mostCurrent.activityBA,mostCurrent._url)) { 
 //BA.debugLineNum = 53;BA.debugLine="s = HttpUtils.GetString(url)";
_s = mostCurrent._httputils._getstring(mostCurrent.activityBA,mostCurrent._url);
 //BA.debugLineNum = 54;BA.debugLine="Log(s)";
anywheresoftware.b4a.keywords.Common.Log(_s);
 //BA.debugLineNum = 55;BA.debugLine="newsContent(s)";
_newscontent(_s);
 };
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Dim Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Dim WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim url As String";
mostCurrent._url = "";
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public static String  _newscontent(String _html) throws Exception{
String _reg = "";
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _mat = null;
 //BA.debugLineNum = 59;BA.debugLine="Sub newsContent(html As String)";
 //BA.debugLineNum = 60;BA.debugLine="Dim reg As String";
_reg = "";
 //BA.debugLineNum = 61;BA.debugLine="reg=\"<title>(.{1,40}) - 博客园新闻手机版</title>[\\s\\S]+<div style=~{0}~line-height:1.6;~{0}~>([\\s\\S]+)</div>[\\s\\S]*上一篇\"";
_reg = "<title>(.{1,40}) - 博客园新闻手机版</title>[\\s\\S]+<div style=~{0}~line-height:1.6;~{0}~>([\\s\\S]+)</div>[\\s\\S]*上一篇";
 //BA.debugLineNum = 62;BA.debugLine="reg=reg.Replace(\"~{0}~\",QUOTE)";
_reg = _reg.replace("~{0}~",anywheresoftware.b4a.keywords.Common.QUOTE);
 //BA.debugLineNum = 63;BA.debugLine="Log(reg)";
anywheresoftware.b4a.keywords.Common.Log(_reg);
 //BA.debugLineNum = 64;BA.debugLine="Dim mat As Matcher";
_mat = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
 //BA.debugLineNum = 65;BA.debugLine="mat=Regex.Matcher(reg,html)";
_mat = anywheresoftware.b4a.keywords.Common.Regex.Matcher(_reg,_html);
 //BA.debugLineNum = 66;BA.debugLine="If mat.Find Then";
if (_mat.Find()) { 
 //BA.debugLineNum = 67;BA.debugLine="Label1.Text=mat.Group(1)";
mostCurrent._label1.setText((Object)(_mat.Group((int)(1))));
 //BA.debugLineNum = 68;BA.debugLine="WebView1.LoadHtml(mat.Group(2))";
mostCurrent._webview1.LoadHtml(_mat.Group((int)(2)));
 };
 //BA.debugLineNum = 71;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 72;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _viewnews(String _newsid) throws Exception{
 //BA.debugLineNum = 43;BA.debugLine="Sub viewnews(newsId As String)";
 //BA.debugLineNum = 44;BA.debugLine="url=\"http://news.cnblogs.com/mv?id=\"&newsId";
mostCurrent._url = "http://news.cnblogs.com/mv?id="+_newsid;
 //BA.debugLineNum = 45;BA.debugLine="HttpUtils.CallbackActivity = \"view\" 'Current activity name.";
mostCurrent._httputils._callbackactivity = "view";
 //BA.debugLineNum = 46;BA.debugLine="HttpUtils.CallbackJobDoneSub = \"downview\"";
mostCurrent._httputils._callbackjobdonesub = "downview";
 //BA.debugLineNum = 47;BA.debugLine="HttpUtils.Download(\"Job2\", url)";
mostCurrent._httputils._download(mostCurrent.activityBA,"Job2",mostCurrent._url);
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return "";
}
}
