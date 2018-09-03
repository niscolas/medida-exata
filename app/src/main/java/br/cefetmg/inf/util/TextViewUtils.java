package br.cefetmg.inf.util;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    public static  TextView criaTextView(
            int largura,
            int altura,
            String texto,
            int tamanhoFonte,
            int margem,
            int padding,
            int corTexto,
            Context context) {

        TextView novoTv = new TextView(context);

        LinearLayout.LayoutParams parametros
                = new LinearLayout.LayoutParams(largura, altura);

        parametros.setMargins(margem, margem, margem, margem);
        novoTv.setLayoutParams(parametros);

        novoTv.setText(texto);
        novoTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, tamanhoFonte);
        novoTv.setTextColor(corTexto);
        novoTv.setPadding(padding, padding, padding, padding);

        return novoTv;
    }
}
