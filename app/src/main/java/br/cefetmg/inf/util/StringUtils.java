package br.cefetmg.inf.util;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.cefetmg.inf.android.medidaexata.activities.R;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtils {
    public static String tiraAcentos(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static void tornaTextoLink(TextView tv) {
        final CharSequence text = tv.getText();
        final SpannableString notClickedString = new SpannableString(text);
        notClickedString.setSpan(new URLSpan(""), 0, notClickedString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(notClickedString, TextView.BufferType.SPANNABLE);
        final SpannableString clickedString = new SpannableString(notClickedString);
        clickedString.setSpan(new BackgroundColorSpan(Color.BLUE), 0, notClickedString.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    tv.setText(clickedString);
                    break;
                case MotionEvent.ACTION_UP:
                    tv.setText(notClickedString, TextView.BufferType.SPANNABLE);
                    v.performClick();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    tv.setText(notClickedString, TextView.BufferType.SPANNABLE);
                    break;
            }
            return true;
        });
    }
}
