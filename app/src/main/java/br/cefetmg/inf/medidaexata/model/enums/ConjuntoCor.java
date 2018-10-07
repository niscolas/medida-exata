package br.cefetmg.inf.medidaexata.model.enums;

import java.io.Serializable;

public enum ConjuntoCor implements Serializable {
    AZUL("azul"),
    VERDE("verde");

    String conjuntoCor;
    ConjuntoCor(String conjuntoCor) {
        this.conjuntoCor = conjuntoCor;
    }
}
