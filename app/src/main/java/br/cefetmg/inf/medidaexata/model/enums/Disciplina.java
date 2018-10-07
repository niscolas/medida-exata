package br.cefetmg.inf.medidaexata.model.enums;

public enum Disciplina {
    MATEMATICA("Matemática"),
    CIENCIAS("Ciências");

    String disc;
    Disciplina(String disc) {
        this.disc = disc;
    }

    public String getDisc() {
        return disc;
    }
}
