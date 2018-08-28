package br.cefetmg.inf.medidaexata.viewmodel;

import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import br.cefetmg.inf.medidaexata.model.Conteudo;
import br.cefetmg.inf.medidaexata.model.CoresUI;
import br.cefetmg.inf.medidaexata.model.QuestaoFechada;

public class MedidaExataViewModel extends ViewModel {
    // Campo que armazena o valor acima, sem acentos e em Lower Case
    private MutableLiveData<String> nomeDisciplina;
    private String disciplina;
    private Conteudo conteudo;
    private QuestaoFechada qst;
    private CoresUI coresUI;

    public void initViewModel(Map<String, Map<String, Integer>> corMap) {
        nomeDisciplina = new MutableLiveData<>();
        disciplina = "";
        conteudo = new Conteudo();
        qst = new QuestaoFechada();
        coresUI = CoresUI.getInstance(corMap);
    }

    public MutableLiveData<String> getNomeDisciplina() {
        return nomeDisciplina;
    }
    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina.setValue(nomeDisciplina);
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

    public QuestaoFechada getQst() {
        return qst;
    }
    public void setQst(QuestaoFechada qst) {
        this.qst = qst;
    }

    public CoresUI getCoresUI() {
        return coresUI;
    }
}
