package com.example.sample_k;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements Runnable {

    SampleView sv;
    Handler hn;
    float x, y, dx, dy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        LinearLayout ll = new LinearLayout(this);
        setContentView(ll);

        hn = new Handler();
        hn.postDelayed(this, 10);

        sv = new SampleView(this);

        ll.addView(sv);
    }

    public void run() {
        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display dp = wm.getDefaultDisplay();
        Point p = new Point();
        dp.getSize(p);

        if(x < 0 || x > p.x) dx = -dx;
        if(y < 0 || y > p.y) dy = -dy;

        x += dx;
        y += dy;

        sv.invalidate();

        hn.postDelayed(this, 10);
    }

    public void onDestroy() {
        super.onDestroy();
        hn.removeCallbacks(this);
    }

    class SampleView extends View {

        Paint p;

        public SampleView(Context cn) {
            super(cn);
            x = 0; y = 0;
            dx = 10; dy = 10;
            p = new Paint();
        }

        protected void onDraw(Canvas cs) {
            super.onDraw(cs);
            p.setColor(Color.BLACK);
            p.setStyle(Paint.Style.FILL);
            cs.drawCircle(x, y, 10, p);
        }
    }
}
