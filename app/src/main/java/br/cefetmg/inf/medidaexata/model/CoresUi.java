package br.cefetmg.inf.medidaexata.model;

public enum CoresUi {
    COR_CLARA("cor_clara"),
    COR_PADRAO("cor_padrao"),
    COR_ESCURA("cor_escura");

    String tom;
    CoresUi(String tom) {
        this.tom = tom;
    }

    public String getTom() {
        return tom;
    }
}
