package br.cefetmg.inf.util;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

public class TextViewUtils {

    private static final String TAG = TextViewUtils.class.getSimpleName();

    /**
     * @param str String a ser criada
     * @param corId cor a ser setada na String acima
     * @return retorna a String com a cor passada
     */
    public static Spannable getStringColorida(String str, int corId) {
        Spannable spannable = new SpannableString(str);
        spannable
                .setSpan(
                        new ForegroundColorSpan(corId),
                        0,
                        spannable.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Log.d(TAG, spannable.toString());
        return spannable;
    }
}
