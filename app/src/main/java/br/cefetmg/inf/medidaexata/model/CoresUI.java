package br.cefetmg.inf.medidaexata.model;

import android.util.Log;

import java.util.Map;

import androidx.lifecycle.MutableLiveData;

public class CoresUI {
    // Única instância de 'CoresUI'
    private static CoresUI instancia;

    // Keys para identificar o set de cores ativo
    public static final String CORES_PRIMARIAS = "cores_primarias";
    public static final String CORES_SECUNDARIAS = "cores_secundarias";
    // Keys para identificar as cores
    public static final String COR_CLARA = "cor_clara";
    public static final String COR_PADRAO = "cor_padrao";
    public static final String COR_ESCURA = "cor_escura";
    public static final String TEMA = "tema";

    private MutableLiveData<String> tipoCoresAtuais;
    private static Map<String, Map<String, Integer>> conjCores;

    private CoresUI(Map<String, Map<String, Integer>> cores) {
        tipoCoresAtuais = new MutableLiveData<>();
        setTipoCoresAtuais(CORES_PRIMARIAS);
        conjCores = cores;
    }
    public static synchronized CoresUI getInstance(Map<String, Map<String, Integer>> mapCoresPadronizado) {
        if(instancia == null) {
            instancia = new CoresUI(mapCoresPadronizado);
        }
        return instancia;
    }

    public MutableLiveData<String> getTipoCoresAtuais() {
        return tipoCoresAtuais;
    }

    public void setTipoCoresAtuais(String constConjDeCores) {
        if(constConjDeCores.equals(CORES_PRIMARIAS) || constConjDeCores.equals(CORES_SECUNDARIAS)) {
            tipoCoresAtuais.setValue(constConjDeCores);
        }
    }

    public Map<String, Integer> getCoresAtuais() {
        return conjCores.get(tipoCoresAtuais.getValue());
    }
}
