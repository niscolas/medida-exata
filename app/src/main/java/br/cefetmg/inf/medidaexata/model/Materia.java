package br.cefetmg.inf.medidaexata.model;

import java.util.List;

public class Materia {
    private String titulo;
    private List<String> materia;

    public Materia() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getMateria() {
        return materia;
    }

    public void setMateria(List<String> materia) {
        this.materia = materia;
    }
}
