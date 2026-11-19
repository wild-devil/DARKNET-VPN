package com.darknet.vpn;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    int blue = Color.rgb(0, 170, 255);
    LinearLayout root;
    TextView status;
    boolean connected = false;

    GradientDrawable bg(int color, int radius, int stroke) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radius);
        if (stroke > 0) d.setStroke(2, blue);
        return d;
    }

    TextView text(String value, int size, int color) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER);
        return t;
    }

    void addSpace(int height) {
        Space s = new Space(this);
        root.addView(s, new LinearLayout.LayoutParams(1, height));
    }

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(Color.rgb(5, 9, 18));
        getWindow().setNavigationBarColor(Color.rgb(5, 9, 18));

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setPadding(24, 30, 24, 24);
        root.setBackground(new GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            new int[]{Color.rgb(5, 12, 27), Color.rgb(0, 0, 0)}
        ));

        ScrollView scroll = new ScrollView(this);
        scroll.addView(root);
        setContentView(scroll);

        TextView logo = text("DΛRKИΞT", 34, blue);
        logo.setTypeface(Typeface.DEFAULT_BOLD);
        root.addView(logo);
        root.addView(text("SECURE CONNECTION INTERFACE", 11, Color.LTGRAY));

        addSpace(35);

        status = text("DISCONNECTED", 16, Color.LTGRAY);
        root.addView(status);

        addSpace(20);

        TextView connect = text("⏻", 64, Color.WHITE);
        connect.setBackground(bg(Color.rgb(0, 65, 110), 150, 1));
        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(190, 190);
        cp.gravity = Gravity.CENTER;
        root.addView(connect, cp);

        connect.setOnClickListener(v -> {
            connected = !connected;
            status.setText(connected ? "ENGINE NOT CONFIGURED" : "DISCONNECTED");
            connect.setBackground(bg(
                connected ? Color.rgb(0, 95, 130) : Color.rgb(0, 65, 110),
                150, 1
            ));
        });

        addSpace(25);
        root.addView(text("SERVER", 13, blue));
        root.addView(text("Automatic selection", 17, Color.WHITE));

        addSpace(25);
        root.addView(text("DOWNLOAD       0 KB/s       UPLOAD       0 KB/s",
            13, Color.WHITE));

        addSpace(30);
        Button settings = new Button(this);
        settings.setText("SETTINGS");
        settings.setTextColor(Color.WHITE);
        settings.setBackground(bg(Color.rgb(12, 30, 50), 24, 1));
        root.addView(settings, new LinearLayout.LayoutParams(-1, 55));

        settings.setOnClickListener(v -> showSettings());
        addSpace(15);
        root.addView(text("DARKNET VPN  •  VERSION 1.0", 11, Color.GRAY));
    }

    void showSettings() {
        EditText config = new EditText(this);
        config.setText(getPreferences(0).getString("cdn_config", ""));
        config.setHint("Paste supported configuration here");
        config.setTextColor(Color.WHITE);
        config.setHintTextColor(Color.GRAY);
        config.setGravity(Gravity.TOP | Gravity.START);
        config.setMinLines(5);
        config.setBackground(bg(Color.rgb(12, 25, 42), 16, 1));

        LinearLayout box = new LinearLayout(this);
        box.setPadding(24, 12, 24, 12);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setBackgroundColor(Color.rgb(5, 12, 24));
        box.addView(text("CDN CONFIGURATION", 17, blue));
        box.addView(config);

        new android.app.AlertDialog.Builder(this)
            .setTitle("DARKNET SETTINGS")
            .setView(box)
            .setPositiveButton("SAVE", (d, w) -> {
                getPreferences(0).edit()
                    .putString("cdn_config", config.getText().toString())
                    .apply();
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("CANCEL", null)
            .show();
    }
}
