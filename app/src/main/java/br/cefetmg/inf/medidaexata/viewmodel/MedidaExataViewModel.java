package br.cefetmg.inf.medidaexata.viewmodel;

import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import br.cefetmg.inf.medidaexata.model.Conteudo;
import br.cefetmg.inf.medidaexata.model.Materia;
import br.cefetmg.inf.medidaexata.model.QuestaoFechada;
import br.cefetmg.inf.medidaexata.model.enums.ConjuntoCor;
import br.cefetmg.inf.medidaexata.model.enums.TonalidadeCor;
import br.cefetmg.inf.medidaexata.model.enums.Disciplina;

public class MedidaExataViewModel extends ViewModel {
    private MutableLiveData<String> tituloAtivo;
    private ConjuntoCor conjCorAtivo;
    private Map<ConjuntoCor, Map<TonalidadeCor, Integer>> coresMedidaExata;
    private Disciplina disciplinaAtiva;
    private Conteudo conteudoAtivo;
    private MutableLiveData<QuestaoFechada> qstAtiva;
    private Materia materiaAtiva;
    private int qtdPontosAtiva;

    public void initViewModel(Map<ConjuntoCor, Map<TonalidadeCor, Integer>> coresMedidaExata) {
        tituloAtivo = new MutableLiveData<>();
        conjCorAtivo = ConjuntoCor.AZUL;
        this.coresMedidaExata = coresMedidaExata;
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

    public ConjuntoCor getConjCorAtivo() {
        return conjCorAtivo;
    }

    public void setConjCorAtivo(ConjuntoCor conjCorAtivo) {
        this.conjCorAtivo = conjCorAtivo;
    }

    public Map<ConjuntoCor, Map<TonalidadeCor, Integer>> getCoresMedidaExata() {
        return coresMedidaExata;
    }

    public void setCoresMedidaExata(Map<ConjuntoCor, Map<TonalidadeCor, Integer>> coresMedidaExata) {
        this.coresMedidaExata = coresMedidaExata;
    }

    /**
     * Retorna uma cor de tonalidade específica, baseando na cor contextual do aplicativo e na
     * tonalidade passada por parâmetro
     *
     * @param tonalidadeCor a tonalidade da cor a ser obtida
     * @return retorna a cor do contexto na tonalidade especificada
     */
    public int getCorContextualEspecifica(TonalidadeCor tonalidadeCor) {
        return coresMedidaExata.get(conjCorAtivo).get(tonalidadeCor);
    }

    public Disciplina getDisciplinaAtiva() {
        return disciplinaAtiva;
    }

    public void setDisciplinaAtiva(Disciplina disciplinaAtiva) {
        this.disciplinaAtiva = disciplinaAtiva;
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
