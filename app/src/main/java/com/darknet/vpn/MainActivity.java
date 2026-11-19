package com.darknet.vpn;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import android.content.Context;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(2, 6, 14));
        getWindow().setNavigationBarColor(Color.rgb(2, 6, 14));
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(Color.rgb(2, 6, 14));
        Dashboard dashboard = new Dashboard(this);
        scroll.addView(dashboard, new ScrollView.LayoutParams(-1, dp(1320)));
        setContentView(scroll);
    }

    int dp(float v) {
        return (int)(v * getResources().getDisplayMetrics().density + .5f);
    }

    class Dashboard extends View {
        final Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        final int cyan = Color.rgb(0, 190, 255);
        final int blue = Color.rgb(0, 105, 255);
        final int purple = Color.rgb(174, 0, 255);
        boolean tappedConnect = false;
        String server = "Automatic Selection";
        String language = "FA";

        Dashboard(Context c) {
            super(c);
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        void paint(int color, float width) {
            p.setColor(color);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(width);
            p.setShader(null);
            p.clearShadowLayer();
        }

        void fill(int color) {
            p.setColor(color);
            p.setStyle(Paint.Style.FILL);
            p.setShader(null);
            p.clearShadowLayer();
        }

        void text(Canvas c, String s, float x, float y, float size,
                  int color, boolean bold, Paint.Align align) {
            fill(color);
            p.setTextSize(size);
            p.setTypeface(bold
                ? Typeface.create("sans-serif", Typeface.BOLD)
                : Typeface.create("sans-serif", Typeface.NORMAL));
            p.setTextAlign(align);
            c.drawText(s, x, y, p);
        }

        void round(Canvas c, float l, float t, float r, float b,
                   float rad, int color, int stroke) {
            fill(color);
            c.drawRoundRect(l, t, r, b, rad, rad, p);
            if (stroke != 0) {
                paint(stroke, 1.2f);
                c.drawRoundRect(l, t, r, b, rad, rad, p);
            }
        }

        Path polygon(float[][] pts) {
            Path path = new Path();
            path.moveTo(pts[0][0], pts[0][1]);
            for (int i = 1; i < pts.length; i++) path.lineTo(pts[i][0], pts[i][1]);
            path.close();
            return path;
        }

        void drawDottedLand(Canvas c, float[][] pts, int seed) {
            Path path = polygon(pts);
            RectF bounds = new RectF();
            path.computeBounds(bounds, true);
            fill(Color.rgb(0, 100, 190));
            for (float y = bounds.top; y < bounds.bottom; y += 5.2f) {
                for (float x = bounds.left; x < bounds.right; x += 5.2f) {
                    float xx = x + ((((int)(y * 3) + seed) % 2) * 2.4f);
                    if (path.contains(xx, y)) c.drawCircle(xx, y, 0.85f, p);
                }
            }
        }

        @Override protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float scale = getWidth() / 400f;
            canvas.save();
            canvas.scale(scale, scale);

            p.setShader(new LinearGradient(0, 0, 400, 1320,
                new int[]{Color.rgb(3, 13, 30), Color.rgb(1, 5, 14), Color.BLACK},
                null, Shader.TileMode.CLAMP));
            canvas.drawRect(0, 0, 400, 1320, p);
            p.setShader(null);

            paint(Color.argb(25, 0, 150, 255), .5f);
            for (int x = 0; x < 400; x += 32) canvas.drawLine(x, 180, x, 760, p);
            for (int y = 190; y < 760; y += 32) canvas.drawLine(0, y, 400, y, p);

            // Neon header and DARKNET branding
            round(canvas, 10, 25, 390, 112, 18,
                Color.argb(210, 3, 13, 29), Color.rgb(0, 75, 145));
            text(canvas, "☰", 34, 78, 27, cyan, true, Paint.Align.CENTER);
            text(canvas, "◈", 91, 82, 48, blue, true, Paint.Align.CENTER);
            text(canvas, "DARKNET", 132, 67, 32, Color.WHITE, true, Paint.Align.LEFT);
            text(canvas, "VPN  •  SECURE  •  ANONYMOUS", 134, 91, 10,
                Color.rgb(140, 205, 235), true, Paint.Align.LEFT);
            round(canvas, 326, 43, 382, 91, 18,
                Color.argb(90, 0, 90, 170), cyan);
            text(canvas, "◎ " + language + "⌄", 354, 73, 14,
                Color.WHITE, true, Paint.Align.CENTER);
            paint(Color.rgb(0, 150, 255), 1.2f);
            canvas.drawLine(15, 114, 385, 114, p);

            // Dotted world-map silhouette
            canvas.save();
            canvas.translate(0, 80);
            drawDottedLand(canvas, new float[][]{
                {12,245},{35,215},{65,210},{78,190},{111,198},{132,220},
                {126,250},{105,264},{97,290},{78,300},{70,340},{53,365},
                {42,340},{34,305},{15,280}}, 1);
            drawDottedLand(canvas, new float[][]{
                {102,375},{125,390},{137,425},{130,470},{115,520},
                {101,500},{94,455},{88,415}}, 2);
            drawDottedLand(canvas, new float[][]{
                {174,215},{197,195},{225,202},{239,220},{270,211},{294,225},
                {327,215},{355,232},{383,250},{374,280},{348,287},{332,270},
                {306,278},{287,265},{266,284},{247,270},{229,288},
                {211,270},{191,276},{174,250}}, 3);
            drawDottedLand(canvas, new float[][]{
                {218,300},{245,292},{267,310},{270,350},{252,380},
                {235,365},{222,335}}, 4);
            drawDottedLand(canvas, new float[][]{
                {310,390},{340,380},{369,397},{378,423},{360,444},
                {334,439},{316,420}}, 5);
            canvas.restore();

            // Large neon connection control
            float cx = 200, cy = 490;
            p.setShader(new RadialGradient(cx, cy, 185,
                new int[]{Color.argb(35, 0, 120, 255),
                    Color.argb(5, 0, 40, 100), Color.TRANSPARENT},
                null, Shader.TileMode.CLAMP));
            canvas.drawCircle(cx, cy, 185, p);
            p.setShader(null);

            paint(Color.argb(100, 0, 120, 255), 2);
            canvas.drawCircle(cx, cy, 158, p);
            paint(blue, 7);
            p.setShadowLayer(20, 0, 0, blue);
            canvas.drawArc(47, 337, 353, 643, 190, 150, false, p);
            p.clearShadowLayer();
            paint(cyan, 7);
            p.setShadowLayer(16, 0, 0, cyan);
            canvas.drawArc(47, 337, 353, 643, 205, 118, false, p);
            p.clearShadowLayer();
            paint(purple, 7);
            p.setShadowLayer(18, 0, 0, purple);
            canvas.drawArc(47, 337, 353, 643, 35, 55, false, p);
            p.clearShadowLayer();
            paint(Color.rgb(0, 80, 170), 2);
            canvas.drawCircle(cx, cy, 133, p);

            Path shield = new Path();
            shield.moveTo(200, 407);
            shield.lineTo(226, 419);
            shield.lineTo(225, 453);
            shield.quadTo(221, 473, 200, 484);
            shield.quadTo(179, 473, 175, 453);
            shield.lineTo(174, 419);
            shield.close();
            paint(cyan, 4);
            p.setShadowLayer(12, 0, 0, cyan);
            canvas.drawPath(shield, p);
            p.clearShadowLayer();
            paint(cyan, 4);
            canvas.drawRoundRect(191, 435, 209, 455, 5, 5, p);
            canvas.drawArc(194, 426, 206, 444, 180, 180, false, p);
            fill(cyan);
            canvas.drawRoundRect(188, 440, 212, 461, 5, 5, p);

            text(canvas, tappedConnect ? "ENGINE NOT CONFIGURED" : "DISCONNECTED",
                200, 520, tappedConnect ? 16 : 19, Color.WHITE, true, Paint.Align.CENTER);
            text(canvas, tappedConnect ? "VPN SERVICE NOT SET UP" : "Tap to connect",
                200, 548, 14, Color.rgb(105, 190, 235), false, Paint.Align.CENTER);
            text(canvas, "SECURE NETWORK INTERFACE", 200, 688, 9,
                Color.rgb(65, 130, 180), true, Paint.Align.CENTER);

            // Server and ping cards
            round(canvas, 12, 755, 255, 890, 18,
                Color.argb(235, 5, 19, 38), Color.rgb(0, 125, 220));
            round(canvas, 265, 755, 388, 890, 18,
                Color.argb(235, 5, 19, 38), Color.rgb(90, 0, 190));
            text(canvas, "▤", 42, 814, 31, cyan, true, Paint.Align.CENTER);
            text(canvas, "SERVER", 75, 791, 13, cyan, true, Paint.Align.LEFT);
            text(canvas, server, 75, 831, 14, Color.WHITE, true, Paint.Align.LEFT);
            text(canvas, "›", 230, 824, 31, blue, true, Paint.Align.CENTER);
            text(canvas, "◉", 300, 815, 32, cyan, true, Paint.Align.CENTER);
            text(canvas, "PING", 340, 791, 13, cyan, true, Paint.Align.CENTER);
            text(canvas, "-- ms", 340, 835, 19, Color.WHITE, true, Paint.Align.CENTER);

            // Traffic monitor panel
            round(canvas, 12, 910, 388, 1085, 18,
                Color.argb(235, 4, 16, 34), Color.rgb(90, 0, 190));
            for (int x = 28; x < 390; x += 28) {
                paint(Color.argb(30, 0, 145, 255), .6f);
                canvas.drawLine(x, 960, x, 1070, p);
            }
            for (int y = 960; y < 1080; y += 25) {
                paint(Color.argb(30, 0, 145, 255), .6f);
                canvas.drawLine(20, y, 380, y, p);
            }
            text(canvas, "▥  TRAFFIC", 30, 953, 15, cyan, true, Paint.Align.LEFT);
            text(canvas, "↓", 46, 1036, 33, cyan, true, Paint.Align.CENTER);
            text(canvas, "0 KB/s", 68, 1032, 22, Color.WHITE, false, Paint.Align.LEFT);
            text(canvas, "↑", 204, 1036, 33, purple, true, Paint.Align.CENTER);
            text(canvas, "0 KB/s", 226, 1032, 22, Color.WHITE, false, Paint.Align.LEFT);
            paint(cyan, 2);
            canvas.drawLine(25, 1063, 375, 1063, p);

            // Settings button and footer
            round(canvas, 24, 1120, 376, 1200, 19,
                Color.argb(245, 3, 18, 36), cyan);
            round(canvas, 30, 1126, 370, 1194, 15,
                Color.TRANSPARENT, Color.rgb(0, 80, 170));
            text(canvas, "⚙", 148, 1172, 31, Color.WHITE, true, Paint.Align.CENTER);
            text(canvas, "SETTINGS", 220, 1170, 21, Color.WHITE, true, Paint.Align.CENTER);
            text(canvas, "DARKNET VPN  •  VERSION 2.0", 200, 1250, 11,
                Color.rgb(105, 145, 180), true, Paint.Align.CENTER);
            text(canvas, "━━━", 55, 1250, 15, cyan, true, Paint.Align.CENTER);
            text(canvas, "━━━", 345, 1250, 15, cyan, true, Paint.Align.CENTER);
            canvas.restore();
        }

        @Override public boolean onTouchEvent(MotionEvent e) {
            if (e.getAction() != MotionEvent.ACTION_UP) return true;
            float scale = getWidth() / 400f;
            float x = e.getX() / scale;
            float y = e.getY() / scale;

            if (y > 330 && y < 660 && x > 35 && x < 365) {
                tappedConnect = !tappedConnect;
                invalidate();
                Toast.makeText(MainActivity.this,
                    "VPN engine is not configured yet", Toast.LENGTH_SHORT).show();
                return true;
            }
            if (y > 750 && y < 900) {
                final String[] items = {"Automatic Selection", "Germany",
                    "Netherlands", "United States", "United Kingdom"};
                new AlertDialog.Builder(MainActivity.this)
                    .setTitle("SELECT SERVER")
                    .setItems(items, (dialog, which) -> {
                        server = items[which];
                        invalidate();
                    }).show();
                return true;
            }
            if (y > 1110 && y < 1210) {
                showSettings();
                return true;
            }
            if (y > 35 && y < 105 && x > 315) {
                language = language.equals("FA") ? "EN" : "FA";
                invalidate();
                return true;
            }
            if (y > 35 && y < 105 && x < 65) {
                new AlertDialog.Builder(MainActivity.this)
                    .setTitle("DARKNET MENU")
                    .setItems(new String[]{"Dashboard", "Servers", "Settings", "About"},
                        (dialog, which) -> {
                            if (which == 1) selectServer();
                            else if (which == 2) showSettings();
                            else if (which == 3) new AlertDialog.Builder(MainActivity.this)
                                .setTitle("DARKNET VPN")
                                .setMessage("Cyberpunk VPN interface • Version 2.0\\nVPN engine is not configured.")
                                .setPositiveButton("OK", null).show();
                        }).show();
                return true;
            }
            return true;
        }

        void selectServer() {
            final String[] items = {"Automatic Selection", "Germany",
                "Netherlands", "United States", "United Kingdom"};
            new AlertDialog.Builder(MainActivity.this).setTitle("SELECT SERVER")
                .setItems(items, (dialog, which) -> {
                    server = items[which];
                    invalidate();
                }).show();
        }

        void showSettings() {
            EditText cfg = new EditText(MainActivity.this);
            cfg.setText(getPreferences(0).getString("cdn_config", ""));
            cfg.setTextColor(Color.WHITE);
            cfg.setHintTextColor(Color.GRAY);
            cfg.setHint("Paste your supported VPN configuration");
            cfg.setMinLines(5);
            cfg.setGravity(Gravity.TOP | Gravity.START);
            cfg.setPadding(dp(12), dp(12), dp(12), dp(12));
            cfg.setBackgroundColor(Color.rgb(8, 22, 40));
            new AlertDialog.Builder(MainActivity.this)
                .setTitle("DARKNET SETTINGS")
                .setMessage("Configuration storage • VPN engine is not connected")
                .setView(cfg)
                .setPositiveButton("SAVE", (dialog, which) -> {
                    getPreferences(0).edit()
                        .putString("cdn_config", cfg.getText().toString()).apply();
                    Toast.makeText(MainActivity.this, "Configuration saved",
                        Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("CANCEL", null)
                .show();
        }
    }
}
