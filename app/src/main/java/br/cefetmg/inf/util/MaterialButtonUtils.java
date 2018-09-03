package br.cefetmg.inf.util;

import com.google.android.material.button.MaterialButton;

public class MaterialButtonUtils {
    public static void impedeClicks(boolean anulaClickListener, MaterialButton... bts) {
        for(MaterialButton bt : bts) {
            if(anulaClickListener) {
                bt.setOnClickListener(null);
            }
            bt.setClickable(false);
        }
    }

    public static void permiteClicks(MaterialButton... bts) {
        for(MaterialButton bt : bts) {
            bt.setClickable(true);
        }
    }
}
