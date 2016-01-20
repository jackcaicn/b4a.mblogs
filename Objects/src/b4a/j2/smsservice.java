package b4a.j2;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class smsservice extends android.app.Service {
	public static class smsservice_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
			android.content.Intent in = new android.content.Intent(context, smsservice.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
			context.startService(in);
		}

	}
    static smsservice mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return smsservice.class;
	}
	@Override
	public void onCreate() {
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "b4a.j2", "b4a.j2.smsservice");
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        processBA.setActivityPaused(false);
        anywheresoftware.b4a.keywords.Common.Log("** Service (smsservice) Create **");
        processBA.raiseEvent(null, "service_create");
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		handleStart(intent);
    }
    @Override
    public int onStartCommand(android.content.Intent intent, int flags, int startId) {
    	handleStart(intent);
		return android.app.Service.START_NOT_STICKY;
    }
    private void handleStart(android.content.Intent intent) {
    	anywheresoftware.b4a.keywords.Common.Log("** Service (smsservice) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = new anywheresoftware.b4a.objects.IntentWrapper();
    			if (intent != null) {
    				if (intent.hasExtra("b4a_internal_intent"))
    					iw.setObject((android.content.Intent) intent.getParcelableExtra("b4a_internal_intent"));
    				else
    					iw.setObject(intent);
    			}
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}
	@Override
	public void onDestroy() {
        anywheresoftware.b4a.keywords.Common.Log("** Service (smsservice) Destroy **");
		processBA.raiseEvent(null, "service_destroy");
        processBA.service = null;
		mostCurrent = null;
		processBA.setActivityPaused(true);
	}
public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.phone.PhoneEvents _g_phoneevents = null;
public static anywheresoftware.b4a.objects.Timer _t = null;
public main _main = null;
public tools _tools = null;
public v2 _v2 = null;
public static String  _phoneevents_smsdelivered(String _phonenumber,anywheresoftware.b4a.objects.IntentWrapper _intent) throws Exception{
 //BA.debugLineNum = 36;BA.debugLine="Sub PhoneEvents_SmsDelivered(PhoneNumber As String, Intent As Intent)";
 //BA.debugLineNum = 37;BA.debugLine="ToastMessageShow(\"PhoneEvents_SmsDelivered phoneNumber:\"&PhoneNumber&\" Intent:\"&Intent,True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("PhoneEvents_SmsDelivered phoneNumber:"+_phonenumber+" Intent:"+String.valueOf(_intent),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public static String  _phoneevents_smssentstatus(boolean _success,String _errormessage,String _phonenumber,anywheresoftware.b4a.objects.IntentWrapper _intent) throws Exception{
 //BA.debugLineNum = 25;BA.debugLine="Sub PhoneEvents_SmsSentStatus(Success As Boolean, ErrorMessage As String, PhoneNumber As String, Intent As Intent)";
 //BA.debugLineNum = 27;BA.debugLine="Log(\"call back\")";
anywheresoftware.b4a.keywords.Common.Log("call back");
 //BA.debugLineNum = 28;BA.debugLine="Log(Success)";
anywheresoftware.b4a.keywords.Common.Log(String.valueOf(_success));
 //BA.debugLineNum = 29;BA.debugLine="Log(ErrorMessage)";
anywheresoftware.b4a.keywords.Common.Log(_errormessage);
 //BA.debugLineNum = 30;BA.debugLine="Log(PhoneNumber)";
anywheresoftware.b4a.keywords.Common.Log(_phonenumber);
 //BA.debugLineNum = 31;BA.debugLine="Log(Intent)";
anywheresoftware.b4a.keywords.Common.Log(String.valueOf(_intent));
 //BA.debugLineNum = 33;BA.debugLine="ToastMessageShow(Success&\",\"&ErrorMessage&\",\"&PhoneNumber,True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(String.valueOf(_success)+","+_errormessage+","+_phonenumber,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Dim g_PhoneEvents As PhoneEvents";
_g_phoneevents = new anywheresoftware.b4a.phone.PhoneEvents();
 //BA.debugLineNum = 9;BA.debugLine="Dim T As Timer";
_t = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _sendmsg() throws Exception{
 //BA.debugLineNum = 40;BA.debugLine="Public Sub sendmsg";
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneId _pid = null;
 //BA.debugLineNum = 11;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 12;BA.debugLine="Dim pid As PhoneId";
_pid = new anywheresoftware.b4a.phone.Phone.PhoneId();
 //BA.debugLineNum = 13;BA.debugLine="g_PhoneEvents.InitializeWithPhoneState(\"PhoneEvents\",pid)";
_g_phoneevents.InitializeWithPhoneState(processBA,"PhoneEvents",_pid);
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 22;BA.debugLine="g_PhoneEvents.StopListening";
_g_phoneevents.StopListening();
 //BA.debugLineNum = 23;BA.debugLine="End Sub";
return "";
}
public static String  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Service_Start (StartingIntent As Intent)";
 //BA.debugLineNum = 17;BA.debugLine="t.Initialize(\"timerTest\",1000)";
_t.Initialize(processBA,"timerTest",(long)(1000));
 //BA.debugLineNum = 18;BA.debugLine="t.Enabled=True";
_t.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public static String  _timertest_tick() throws Exception{
 //BA.debugLineNum = 43;BA.debugLine="Sub timerTest_Tick";
 //BA.debugLineNum = 44;BA.debugLine="Log(DateTime.Now)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DateTime.getNow()));
 //BA.debugLineNum = 45;BA.debugLine="If (DateTime.Now-Main.timeStart)>5000 Then";
if ((anywheresoftware.b4a.keywords.Common.DateTime.getNow()-mostCurrent._main._timestart)>5000) { 
 //BA.debugLineNum = 46;BA.debugLine="T.Enabled=False";
_t.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 48;BA.debugLine="CallSub(Main,\"cb\")";
anywheresoftware.b4a.keywords.Common.CallSub(processBA,(Object)(mostCurrent._main.getObject()),"cb");
 };
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
}
