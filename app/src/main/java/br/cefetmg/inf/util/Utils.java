package br.cefetmg.inf.util;

import android.os.Handler;

public class Utils {
    public static void pausaApp(int tempoEmMs) {
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
            }
        }, 5000);
    }
}
