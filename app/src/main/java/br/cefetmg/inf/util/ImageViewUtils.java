package br.cefetmg.inf.util;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImageViewUtils {
    public static ImageView criaImageView(
            int largura,
            int altura,
            int maxLarg,
            int maxAlt,
            int margem,
            int padding,
            Context context) {

        ImageView novoIv = new ImageView(context);

        LinearLayout.LayoutParams parametros
                = new LinearLayout.LayoutParams(largura, altura);
        parametros.setMargins(margem, margem, margem, margem);
        novoIv.setLayoutParams(parametros);

        if(maxAlt != -1 && maxLarg != -1) {
            novoIv.setMaxWidth(maxLarg);
            novoIv.setMaxHeight(maxAlt);
        }
        novoIv.setPadding(padding, padding, padding, padding);

        return novoIv;
    }
}
