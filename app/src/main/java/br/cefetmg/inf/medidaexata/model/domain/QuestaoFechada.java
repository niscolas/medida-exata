package br.cefetmg.inf.medidaexata.model.domain;

import java.util.List;

public class QuestaoFechada {
    private String codigo;
    private String disciplina;
    private String conteudo;
    private String objCon;
    private String enunciado;
    private List<String> alternativas;
    private int resposta;

    public QuestaoFechada() { }

    public QuestaoFechada(String codigo, String disciplina, String conteudo, String objCon,
                          String enunciado, List<String> alternativas, int resposta) {
        this.codigo = codigo;
        this.disciplina = disciplina;
        this.conteudo = conteudo;
        this.objCon = objCon;
        this.enunciado = enunciado;
        this.alternativas = alternativas;
        this.resposta = resposta;
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

    public String getObjCon() {
        return objCon;
    }

    public void setObjCon(String objCon) {
        this.objCon = objCon;
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
}
