package b4a.j2.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_title2{

public static void LS_general(java.util.HashMap<String, anywheresoftware.b4a.objects.ViewWrapper<?>> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("imgsearchgo").setLeft((int)((100d / 100 * width)-(10d * scale) - (views.get("imgsearchgo").getWidth())));
views.get("imgback").setWidth((int)(24d));
views.get("imgback").setHeight((int)(24d));
views.get("txtsearch").setHeight((int)((28d * scale)));
//BA.debugLineNum = 7;BA.debugLine="txtSearch.SetLeftAndRight(imgBack.Right+10dip,imgSearchGo.Left-10dip)"[title2/General script]
views.get("txtsearch").setLeft((int)((views.get("imgback").getLeft() + views.get("imgback").getWidth())+(10d * scale)));
views.get("txtsearch").setWidth((int)((views.get("imgsearchgo").getLeft())-(10d * scale) - ((views.get("imgback").getLeft() + views.get("imgback").getWidth())+(10d * scale))));
//BA.debugLineNum = 8;BA.debugLine="panlinebot.SetLeftAndRight(txtSearch.Left,txtSearch.Right)"[title2/General script]
views.get("panlinebot").setLeft((int)((views.get("txtsearch").getLeft())));
views.get("panlinebot").setWidth((int)((views.get("txtsearch").getLeft() + views.get("txtsearch").getWidth()) - ((views.get("txtsearch").getLeft()))));
//BA.debugLineNum = 9;BA.debugLine="panlinebot.SetTopAndBottom(txtSearch.Bottom-1,txtSearch.Bottom)"[title2/General script]
views.get("panlinebot").setTop((int)((views.get("txtsearch").getTop() + views.get("txtsearch").getHeight())-1d));
views.get("panlinebot").setHeight((int)((views.get("txtsearch").getTop() + views.get("txtsearch").getHeight()) - ((views.get("txtsearch").getTop() + views.get("txtsearch").getHeight())-1d)));
//BA.debugLineNum = 10;BA.debugLine="txtSearch.TextSize=14"[title2/General script]
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("txtsearch")).setTextSize((float)(14d));

}
}