package br.cefetmg.inf.medidaexata.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class QuestaoFechada {
    private String codigo;
    private String disciplina;
    private String conteudo;
    private String materiaAbordada;
    private String enunciado;
    private List<String> alternativas;
    private int resposta;
    private int qtdPontos;
    private DocumentReference materiaRel;

    public QuestaoFechada() { }

    public QuestaoFechada(String codigo, String disciplina, String conteudo, String materiaAbordada, String enunciado, List<String> alternativas, int resposta, int qtdPontos, DocumentReference materiaRel) {
        this.codigo = codigo;
        this.disciplina = disciplina;
        this.conteudo = conteudo;
        this.materiaAbordada = materiaAbordada;
        this.enunciado = enunciado;
        this.alternativas = alternativas;
        this.resposta = resposta;
        this.qtdPontos = qtdPontos;
        this.materiaRel = materiaRel;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getMateriaAbordada() {
        return materiaAbordada;
    }

    public void setMateriaAbordada(String materiaAbordada) {
        this.materiaAbordada = materiaAbordada;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public List<String> getAlternativas() {
        return alternativas;
    }

    public void setAlternativas(List<String> alternativas) {
        this.alternativas = alternativas;
    }

    public int getResposta() {
        return resposta;
    }

    public void setResposta(int resposta) {
        this.resposta = resposta;
    }

    public int getQtdPontos() {
        return qtdPontos;
    }

    public void setQtdPontos(int qtdPontos) {
        this.qtdPontos = qtdPontos;
    }

    public DocumentReference getMateriaRel() {
        return materiaRel;
    }

    public void setMateriaRel(DocumentReference materiaRel) {
        this.materiaRel = materiaRel;
    }
}
