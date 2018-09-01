package br.cefetmg.inf.medidaexata.model;

import java.util.List;

public class Materia {
    String titulo;
    List materia;

    public Materia() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List getMateria() {
        return materia;
    }

    public void setMateria(List materia) {
        this.materia = materia;
    }
}
