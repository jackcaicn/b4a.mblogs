package b4a.j2.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_title{

public static void LS_general(java.util.HashMap<String, anywheresoftware.b4a.objects.ViewWrapper<?>> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 3;BA.debugLine="imgSearch.Right=100%x-10dip"[title/General script]
views.get("imgsearch").setLeft((int)((100d / 100 * width)-(10d * scale) - (views.get("imgsearch").getWidth())));
//BA.debugLineNum = 4;BA.debugLine="imgSearch.Top=2dip"[title/General script]
views.get("imgsearch").setTop((int)((2d * scale)));
//BA.debugLineNum = 5;BA.debugLine="imgSearch.Width=26dip"[title/General script]
views.get("imgsearch").setWidth((int)((26d * scale)));
//BA.debugLineNum = 6;BA.debugLine="imgSearch.Height=26dip"[title/General script]
views.get("imgsearch").setHeight((int)((26d * scale)));
//BA.debugLineNum = 7;BA.debugLine="lbtitle.Left=10dip"[title/General script]
views.get("lbtitle").setLeft((int)((10d * scale)));

}
}