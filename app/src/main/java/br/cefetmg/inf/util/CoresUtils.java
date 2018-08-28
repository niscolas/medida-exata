package br.cefetmg.inf.util;

import android.content.Context;

import androidx.core.content.ContextCompat;

public class CoresUtils {
    public static int[] getColorDeInt(Context c, int... cores) {
        for(int cor : cores) {
            cor =  ContextCompat.getColor(c, cor);
        }
        return cores;
    }
}
