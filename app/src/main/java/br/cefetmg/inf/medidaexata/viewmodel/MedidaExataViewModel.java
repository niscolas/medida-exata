package br.cefetmg.inf.medidaexata.viewmodel;

import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import br.cefetmg.inf.medidaexata.model.Conteudo;
import br.cefetmg.inf.medidaexata.model.CoresUI;
import br.cefetmg.inf.medidaexata.model.QuestaoFechada;

public class MedidaExataViewModel extends ViewModel {
    // Campo que armazena o valor acima, sem acentos e em Lower Case
    private MutableLiveData<String> titulo;
    private String disciplina;
    private Conteudo conteudo;
    private MutableLiveData<QuestaoFechada> qst;
    private int qtdPontosAtuais;
    private CoresUI coresUI;

    public void initViewModel(Map<String, Map<String, Integer>> corMap) {
        titulo = new MutableLiveData<>();
        disciplina = "";
        conteudo = new Conteudo();
        qst = new MutableLiveData<>();
        qtdPontosAtuais = 0;
        coresUI = CoresUI.getInstance(corMap);
    }

    public MutableLiveData<String> getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo.setValue(titulo);
    }

    public String getDisciplina() {
        return disciplina;
    }
    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public Conteudo getConteudo() {
        return conteudo;
    }
    public void setConteudo(Conteudo conteudo) {
        this.conteudo = conteudo;
    }

    public MutableLiveData<QuestaoFechada> getQst() {
        return qst;
    }
    public void setQst(QuestaoFechada qst) {
        this.qst.setValue(qst);
    }

    public int getQtdPontosAtuais() {
        return qtdPontosAtuais;
    }
    public void setQtdPontosAtuais(int qtdPontosAtuais) {
        this.qtdPontosAtuais = qtdPontosAtuais;
    }

    public CoresUI getCoresUI() {
        return coresUI;
    }
}
