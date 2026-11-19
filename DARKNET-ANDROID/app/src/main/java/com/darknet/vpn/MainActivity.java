package com.darknet.vpn;

import android.app.*;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.content.*;
import android.view.*;
import android.widget.*;
import android.text.*;
import java.util.*;

public class MainActivity extends Activity {
    final int BG=0xff070b16, PANEL=0xff111a2d, PANEL2=0xff17243b, CYAN=0xff00dcff, PURPLE=0xffb45cff, MUTED=0xff9aabc5;
    SharedPreferences pref;
    LinearLayout root, body;
    String page="home", lang="FA", country="Automatic";
    final String[][] countries={
        {"🇩🇪","Germany","آلمان"},{"🇳🇱","Netherlands","هلند"},{"🇺🇸","United States","ایالات متحده"},
        {"🇬🇧","United Kingdom","بریتانیا"},{"🇫🇷","France","فرانسه"},{"🇨🇦","Canada","کانادا"},
        {"🇯🇵","Japan","ژاپن"},{"🇸🇬","Singapore","سنگاپور"},{"🇨🇭","Switzerland","سوئیس"},
        {"🇸🇪","Sweden","سوئد"},{"🇳🇴","Norway","نروژ"},{"🇫🇮","Finland","فنلاند"},
        {"🇩🇰","Denmark","دانمارک"},{"🇮🇹","Italy","ایتالیا"},{"🇪🇸","Spain","اسپانیا"},
        {"🇵🇹","Portugal","پرتغال"},{"🇧🇪","Belgium","بلژیک"},{"🇦🇹","Austria","اتریش"},
        {"🇵🇱","Poland","لهستان"},{"🇨🇿","Czechia","چک"},{"🇷🇴","Romania","رومانی"},
        {"🇹🇷","Türkiye","ترکیه"},{"🇦🇪","UAE","امارات"},{"🇮🇳","India","هند"},
        {"🇰🇷","South Korea","کره جنوبی"},{"🇭🇰","Hong Kong","هنگ‌کنگ"},{"🇦🇺","Australia","استرالیا"},
        {"🇳🇿","New Zealand","نیوزیلند"},{"🇧🇷","Brazil","برزیل"},{"🇲🇽","Mexico","مکزیک"},
        {"🇿🇦","South Africa","آفریقای جنوبی"},{"🇮🇩","Indonesia","اندونزی"},{"🇲🇾","Malaysia","مالزی"},
        {"🇹🇭","Thailand","تایلند"},{"🇺🇦","Ukraine","اوکراین"},{"🇮🇪","Ireland","ایرلند"}
    };
    ArrayList<String> events=new ArrayList<>();

    public void onCreate(Bundle b){super.onCreate(b); pref=getSharedPreferences("darknet",0);
        lang=pref.getString("lang","FA"); country=pref.getString("country","Automatic");
        getWindow().setStatusBarColor(BG); getWindow().setNavigationBarColor(BG); event("Interface started"); render();}
    int dp(float n){return (int)(n*getResources().getDisplayMetrics().density+.5f);}
    boolean fa(){return lang.equals("FA");}
    String tr(String en,String f){return fa()?f:en;}
    void event(String s){events.add(0,String.format(Locale.US,"%tT  %s",System.currentTimeMillis(),s)); if(events.size()>60)events.remove(events.size()-1);}
    GradientDrawable bg(int color,int stroke,int radius){GradientDrawable d=new GradientDrawable();d.setColor(color);d.setCornerRadius(dp(radius));if(stroke!=0)d.setStroke(dp(1),stroke);return d;}
    TextView txt(String s,int size,int color,boolean bold){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);t.setTypeface(Typeface.create("sans-serif",bold?1:0));t.setGravity(fa()?Gravity.RIGHT:Gravity.LEFT);t.setTextDirection(fa()?View.TEXT_DIRECTION_RTL:View.TEXT_DIRECTION_LTR);return t;}
    LinearLayout col(){LinearLayout l=new LinearLayout(this);l.setOrientation(1);l.setLayoutDirection(fa()?View.LAYOUT_DIRECTION_RTL:View.LAYOUT_DIRECTION_LTR);return l;}
    LinearLayout row(){LinearLayout l=new LinearLayout(this);l.setOrientation(0);l.setGravity(Gravity.CENTER_VERTICAL);l.setLayoutDirection(fa()?View.LAYOUT_DIRECTION_RTL:View.LAYOUT_DIRECTION_LTR);return l;}
    void gap(LinearLayout l,int h){View v=new View(this);l.addView(v,new LinearLayout.LayoutParams(1,dp(h)));}
    LinearLayout card(){LinearLayout c=col();c.setPadding(dp(15),dp(14),dp(15),dp(14));c.setBackground(bg(PANEL,0xff263957,17));return c;}
    TextView btn(String s,boolean primary,Runnable run){TextView t=txt(s,14,primary?Color.BLACK:Color.WHITE,true);t.setGravity(Gravity.CENTER);t.setPadding(dp(14),dp(12),dp(14),dp(12));t.setBackground(bg(primary?CYAN:PANEL2,primary?CYAN:0xff3b5274,12));t.setOnClickListener(v->run.run());return t;}
    void line(LinearLayout c){View v=new View(this);v.setBackgroundColor(0xff2b3b56);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,dp(1));p.topMargin=dp(9);p.bottomMargin=dp(9);c.addView(v,p);}
    void heading(String over,String title,String desc){body.addView(txt(over,10,CYAN,true));TextView h=txt(title,24,Color.WHITE,true);h.setPadding(0,dp(5),0,dp(5));body.addView(h);body.addView(txt(desc,12,MUTED,false));gap(body,15);}
    void render(){
        root=col();root.setBackgroundColor(BG);root.setPadding(dp(15),dp(8),dp(15),dp(15));setContentView(root);
        LinearLayout top=row();top.setPadding(0,dp(8),0,dp(15));
        TextView menu=txt("☰",26,CYAN,true);menu.setGravity(Gravity.CENTER);top.addView(menu,new LinearLayout.LayoutParams(dp(45),dp(45)));menu.setOnClickListener(v->nav());
        LinearLayout brand=col();brand.addView(txt("DARKNET",22,Color.WHITE,true));brand.addView(txt("NEON NETWORK INTERFACE",9,CYAN,true));top.addView(brand,new LinearLayout.LayoutParams(0,-2,1));
        top.addView(btn(lang+" ⇄",false,()->{lang=fa()?"EN":"FA";pref.edit().putString("lang",lang).apply();event("Language changed");render();}));
        root.addView(top);
        ScrollView sc=new ScrollView(this);body=col();body.setPadding(0,dp(4),0,dp(12));sc.addView(body);root.addView(sc,new LinearLayout.LayoutParams(-1,0,1));
        switch(page){case "servers":servers();break;case "cdn":cdn();break;case "dns":dns();break;case "lab":lab();break;case "logs":logs();break;case "settings":settings();break;case "about":about();break;default:home();}
        LinearLayout nav=row();nav.setPadding(dp(4),dp(8),dp(4),dp(4));nav.setBackground(bg(0xff0d1526,0xff293b59,15));
        String[][] tabs={{"⌂",tr("Home","خانه"),"home"},{"◎",tr("Servers","سرورها"),"servers"},{"◇","CDN","cdn"},{"⚙",tr("Settings","تنظیمات"),"settings"}};
        for(String[] a:tabs){LinearLayout item=col();item.setGravity(Gravity.CENTER);item.addView(txt(a[0],20,page.equals(a[2])?CYAN:MUTED,true));item.addView(txt(a[1],10,page.equals(a[2])?CYAN:MUTED,true));nav.addView(item,new LinearLayout.LayoutParams(0,-2,1));item.setOnClickListener(v->{page=a[2];render();});}
        root.addView(nav);
    }
    void home(){
        heading("SYSTEM / OVERVIEW",tr("Your network. Your control.","شبکه‌ات؛ کنترل دست خودت."),tr("Cyberpunk control center","مرکز کنترل سایبرپانکی"));
        LinearLayout c=card();c.addView(txt("●  "+tr("ENGINE NOT INSTALLED","موتور نصب نشده"),11,0xffffc36b,true));gap(c,9);
        c.addView(txt(tr("Psiphon integration is the next phase","ادغام سایفون مرحله بعدی است"),18,Color.WHITE,true));gap(c,7);
        c.addView(txt(tr("This build is the interface only. VPN is not connected.","این نسخه فعلاً رابط کاربری است و VPN وصل نمی‌شود."),12,MUTED,false));
        body.addView(c);gap(body,12);
        LinearLayout s=card();s.addView(txt(tr("SELECTED LOCATION","موقعیت انتخاب‌شده"),11,CYAN,true));gap(s,6);
        LinearLayout rr=row();rr.addView(txt(countryName(),16,Color.WHITE,true),new LinearLayout.LayoutParams(0,-2,1));rr.addView(btn(tr("CHANGE","تغییر"),false,()->choose()));s.addView(rr);
        s.addView(txt(tr("Country catalog • no live servers configured","فهرست کشورها • سرور زنده تنظیم نشده"),10,MUTED,false));body.addView(s);gap(body,12);
        LinearLayout q=card();q.addView(txt(tr("QUICK ACCESS","دسترسی سریع"),15,Color.WHITE,true));quick(q,"◇",tr("CDN Fronting profiles","پروفایل CDN"),"cdn");quick(q,"⌘",tr("DNS Center","مرکز DNS"),"dns");quick(q,"⌁",tr("Network Lab","آزمایشگاه شبکه"),"lab");quick(q,"▤",tr("System logs","گزارش رویدادها"),"logs");body.addView(q);
    }
    void quick(LinearLayout c,String ico,String label,String target){LinearLayout r=row();r.setPadding(0,dp(10),0,dp(10));r.addView(txt(ico,20,CYAN,true),new LinearLayout.LayoutParams(dp(40),-2));r.addView(txt(label,14,Color.WHITE,true),new LinearLayout.LayoutParams(0,-2,1));r.addView(txt("›",22,MUTED,true));c.addView(r);r.setOnClickListener(v->{page=target;render();});}
    String countryName(){if(country.equals("Automatic"))return tr("Automatic selection","انتخاب خودکار");for(String[] c:countries)if(c[1].equals(country))return c[0]+" "+(fa()?c[2]:c[1]);return country;}
    void servers(){heading("SERVER DIRECTORY",tr("Server locations","موقعیت سرورها"),tr("Country catalog only. No live endpoints are configured.","فقط فهرست کشورها؛ هیچ سرور فعالی تنظیم نشده."));
        body.addView(btn(tr("SEARCH COUNTRIES","جست‌وجوی کشورها"),true,()->choose()));gap(body,12);
        for(String[] c:countries){LinearLayout r=row();r.setPadding(dp(12),dp(12),dp(12),dp(12));r.setBackground(bg(PANEL,0xff263957,12));r.addView(txt(c[0],22,Color.WHITE,true),new LinearLayout.LayoutParams(dp(42),-2));r.addView(txt(fa()?c[2]:c[1],14,Color.WHITE,true),new LinearLayout.LayoutParams(0,-2,1));r.addView(txt(country.equals(c[1])?"✓":"›",18,CYAN,true));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.bottomMargin=dp(7);body.addView(r,p);r.setOnClickListener(v->{country=c[1];pref.edit().putString("country",country).apply();event("Country selected: "+country);render();});}}
    void choose(){ArrayList<String> a=new ArrayList<>();a.add(tr("🌐 Automatic selection","🌐 انتخاب خودکار"));for(String[] c:countries)a.add(c[0]+" "+(fa()?c[2]:c[1]));new AlertDialog.Builder(this).setTitle(tr("Choose country","انتخاب کشور")).setItems(a.toArray(new String[0]),(d,w)->{country=w==0?"Automatic":countries[w-1][1];pref.edit().putString("country",country).apply();event("Country selected");render();}).setNegativeButton(tr("CLOSE","بستن"),null).show();}
    EditText field(LinearLayout c,String label,String key,String hint){c.addView(txt(label,12,MUTED,true));gap(c,4);EditText e=new EditText(this);e.setTextColor(Color.WHITE);e.setHintTextColor(MUTED);e.setTextSize(14);e.setSingleLine(true);e.setHint(hint);e.setText(pref.getString(key,""));e.setPadding(dp(12),dp(10),dp(12),dp(10));e.setBackground(bg(PANEL2,0xff3b5274,10));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.bottomMargin=dp(10);c.addView(e,p);return e;}
    void cdn(){heading("CDN / CONFIGURATION",tr("CDN Fronting profiles","پروفایل‌های CDN Fronting"),tr("For a CDN and origin you own or are authorized to use.","برای CDN و مبدأ متعلق به خودت یا دارای مجوز."));
        LinearLayout c=card();c.addView(txt(tr("LOCAL STORAGE • NO TUNNEL ACTIVE","ذخیره محلی • تونل فعال نیست"),10,0xffffc36b,true));gap(c,10);
        EditText n=field(c,tr("Profile name","نام پروفایل"),"cdn_name","My CDN");EditText h=field(c,tr("Authorized CDN hostname","هاست CDN مجاز"),"cdn_host","cdn.example.com");EditText o=field(c,tr("Origin hostname","هاست مبدأ"),"cdn_origin","origin.example.com");
        c.addView(btn(tr("SAVE PROFILE","ذخیره پروفایل"),true,()->{if(h.getText().toString().trim().isEmpty()||o.getText().toString().trim().isEmpty()){toast(tr("Enter both hostnames","هر دو هاست را وارد کن"));return;}pref.edit().putString("cdn_name",n.getText().toString()).putString("cdn_host",h.getText().toString()).putString("cdn_origin",o.getText().toString()).apply();event("CDN profile saved");toast(tr("Saved locally","محلی ذخیره شد"));}));
        body.addView(c);gap(body,12);LinearLayout note=card();note.addView(txt(tr("This stores a profile only. Real CDN fronting needs a compatible tunnel engine and valid configuration. Psiphon is not integrated yet.","این بخش فقط پروفایل را ذخیره می‌کند. فرانتینگ واقعی به موتور تونل سازگار و تنظیمات معتبر نیاز دارد؛ سایفون هنوز ادغام نشده."),12,MUTED,false));body.addView(note);}
    void dns(){heading("DNS / RESOLVER",tr("DNS Center","مرکز DNS"),tr("Save resolver preferences for later VPN integration.","تنظیمات ریزالور را برای ادغام VPN ذخیره کن."));LinearLayout c=card();EditText a=field(c,tr("Primary DNS","DNS اصلی"),"dns1","1.1.1.1");EditText b=field(c,tr("Secondary DNS","DNS دوم"),"dns2","8.8.8.8");c.addView(btn(tr("SAVE DNS","ذخیره DNS"),true,()->{if(a.getText().toString().trim().isEmpty()){toast(tr("DNS cannot be empty","DNS خالی نباشد"));return;}pref.edit().putString("dns1",a.getText().toString()).putString("dns2",b.getText().toString()).apply();event("DNS settings saved");toast(tr("Saved locally","محلی ذخیره شد"));}));body.addView(c);gap(body,12);LinearLayout n=card();n.addView(txt(tr("DNS is not changed system-wide in this build. It will be applied by a supported VPN service later.","در این نسخه DNS کل سیستم تغییر نمی‌کند و بعداً توسط سرویس VPN اعمال می‌شود."),12,MUTED,false));body.addView(n);}
    void lab(){heading("NETWORK DIAGNOSTICS",tr("Network Lab","آزمایشگاه شبکه"),tr("Live diagnostics will be enabled after network modules are integrated.","عیب‌یابی زنده پس از ادغام ماژول‌های شبکه فعال می‌شود."));LinearLayout c=card();String[] names={tr("VPN tunnel","تونل VPN"),tr("Latency test","تست تأخیر"),tr("DNS lookup","بررسی DNS"),tr("Traffic monitor","مانیتور ترافیک")};for(String s:names){c.addView(txt("◈  "+s+"      —      "+tr("Not available yet","فعلاً در دسترس نیست"),13,Color.WHITE,true));line(c);}body.addView(c);}
    void logs(){heading("EVENT STREAM",tr("System logs","گزارش رویدادها"),tr("Local events on this device.","رویدادهای محلی دستگاه."));LinearLayout c=card();c.addView(btn(tr("CLEAR LOGS","پاک‌کردن گزارش‌ها"),false,()->{events.clear();event("Logs cleared");render();}));gap(c,8);for(String s:events){c.addView(txt(s,11,0xffd0d9e8,false));line(c);}body.addView(c);}
    void settings(){heading("PREFERENCES",tr("Settings","تنظیمات"),tr("Customize your interface.","شخصی‌سازی رابط."));LinearLayout c=card();c.addView(txt(tr("LANGUAGE","زبان"),14,Color.WHITE,true));gap(c,8);LinearLayout r=row();r.addView(btn("فارسی",lang.equals("FA"),()->setLang("FA")),new LinearLayout.LayoutParams(0,-2,1));View gap=new View(this);r.addView(gap,new LinearLayout.LayoutParams(dp(8),1));r.addView(btn("English",lang.equals("EN"),()->setLang("EN")),new LinearLayout.LayoutParams(0,-2,1));c.addView(r);body.addView(c);gap(body,12);LinearLayout d=card();d.addView(txt(tr("LOCAL DATA","داده‌های محلی"),15,Color.WHITE,true));gap(d,8);d.addView(btn(tr("RESET LOCAL SETTINGS","بازنشانی تنظیمات محلی"),false,()->new AlertDialog.Builder(this).setTitle(tr("Reset?","بازنشانی؟")).setMessage(tr("Clear saved CDN and DNS settings?","تنظیمات CDN و DNS پاک شوند؟")).setNegativeButton(tr("CANCEL","لغو"),null).setPositiveButton(tr("RESET","پاک کن"),(x,w)->{pref.edit().clear().putString("lang",lang).apply();country="Automatic";event("Preferences reset");render();}).show()));body.addView(d);}
    void setLang(String s){lang=s;pref.edit().putString("lang",s).apply();event("Language changed");render();}
    void about(){heading("PROJECT / DARKNET",tr("About DARKNET","درباره DARKNET"),tr("Modular Android network client interface.","رابط ماژولار اندرویدی مدیریت شبکه."));LinearLayout c=card();TextView logo=txt("◈  DARKNET",27,CYAN,true);logo.setGravity(Gravity.CENTER);c.addView(logo);TextView v=txt("VPN INTERFACE • VERSION 1.1",10,MUTED,true);v.setGravity(Gravity.CENTER);c.addView(v);line(c);c.addView(txt(tr("ROADMAP","نقشه راه"),15,Color.WHITE,true));String[] steps={tr("✓ Responsive cyberpunk UI","✓ رابط سایبرپانکی واکنش‌گرا"),tr("✓ Local CDN and DNS settings","✓ تنظیمات محلی CDN و DNS"),tr("○ Psiphon engine integration","○ ادغام موتور سایفون"),tr("○ Real VPN connection tests","○ تست اتصال واقعی VPN")};for(String s:steps){gap(c,8);c.addView(txt(s,12,CYAN,false));}body.addView(c);gap(body,12);LinearLayout n=card();n.addView(txt(tr("This APK is currently an interface prototype. It does not create a VPN connection. Psiphon integration is a separate next phase.","این APK فعلاً نمونه رابط کاربری است و اتصال VPN برقرار نمی‌کند. ادغام سایفون مرحله بعدی است."),12,MUTED,false));body.addView(n);}
    void nav(){String[] names={tr("Dashboard","داشبورد"),tr("Servers","سرورها"),"CDN Fronting",tr("DNS Center","مرکز DNS"),tr("Network Lab","آزمایشگاه شبکه"),tr("Logs","گزارش‌ها"),tr("Settings","تنظیمات"),tr("About","درباره")};String[] keys={"home","servers","cdn","dns","lab","logs","settings","about"};new AlertDialog.Builder(this).setTitle("◈ DARKNET").setItems(names,(d,w)->{page=keys[w];render();}).show();}
    void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
}
