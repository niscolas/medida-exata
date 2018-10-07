package br.cefetmg.inf.medidaexata.model.enums;

import java.io.Serializable;

public enum Secao implements Serializable {
    MATEMATICA("Matemática"),
    CIENCIAS("Ciências"),
    CONQUISTAS("Conquistas");

    String secao;
    Secao(String secao) {
        this.secao = secao;
    }

    public String getSecao() {
        return secao;
    }
}
