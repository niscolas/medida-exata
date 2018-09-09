package br.cefetmg.inf.medidaexata.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import br.cefetmg.inf.medidaexata.model.Conteudo;
import br.cefetmg.inf.medidaexata.model.Materia;
import br.cefetmg.inf.medidaexata.model.QuestaoFechada;

public class MedidaExataViewModel extends ViewModel {
    private MutableLiveData<String> tituloAtivo;
    private String contextoCoresAtivo;
    private MutableLiveData<String> disciplinaAtiva;
    private Conteudo conteudoAtivo;
    private MutableLiveData<QuestaoFechada> qstAtiva;
    private Materia materiaAtiva;
    private int qtdPontosAtiva;

    public void initViewModel() {
        tituloAtivo = new MutableLiveData<>();
        contextoCoresAtivo = "";
        disciplinaAtiva = new MutableLiveData<>();
        conteudoAtivo = new Conteudo();
        qstAtiva = new MutableLiveData<>();
        materiaAtiva = new Materia();
        qtdPontosAtiva = 0;
    }

    public MutableLiveData<String> getTituloAtivo() {
        return tituloAtivo;
    }
    public void setTituloAtivo(String tituloAtivo) {
        this.tituloAtivo.setValue(tituloAtivo);
    }

    public String getContextoCoresAtivo() {
        return contextoCoresAtivo;
    }

    public void setContextoCoresAtivo(String contextoCoresAtivo) {
        this.contextoCoresAtivo = contextoCoresAtivo;
    }

    public MutableLiveData<String> getDisciplinaAtiva() {
        return disciplinaAtiva;
    }

    public void setDisciplinaAtiva(String disciplinaAtiva) {
        this.disciplinaAtiva.setValue(disciplinaAtiva);
    }

    public Conteudo getConteudoAtivo() {
        return conteudoAtivo;
    }
    public void setConteudoAtivo(Conteudo conteudoAtivo) {
        this.conteudoAtivo = conteudoAtivo;
    }

    public MutableLiveData<QuestaoFechada> getQstAtiva() {
        return qstAtiva;
    }
    public void setQstAtiva(QuestaoFechada qstAtiva) {
        this.qstAtiva.setValue(qstAtiva);
    }

    public Materia getMateriaAtiva() {
        return materiaAtiva;
    }
    public void setMateriaAtiva(Materia materiaAtiva) {
        this.materiaAtiva = materiaAtiva;
    }

    public int getQtdPontosAtiva() {
        return qtdPontosAtiva;
    }
    public void setQtdPontosAtiva(int qtdPontosAtiva) {
        this.qtdPontosAtiva = qtdPontosAtiva;
    }
}
