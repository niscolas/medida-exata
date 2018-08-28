package br.cefetmg.inf.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtils {
    public static String tiraAcentos(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}
