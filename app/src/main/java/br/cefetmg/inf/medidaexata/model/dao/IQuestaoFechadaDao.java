package br.cefetmg.inf.medidaexata.model.dao;

import java.util.List;

import br.cefetmg.inf.medidaexata.model.domain.QuestaoFechada;

public interface IQuestaoFechadaDao {
    // boolean adiciona();

    QuestaoFechada busca(String questao);
    List<QuestaoFechada> buscaTodasPorParametro(String dado, String parametro);
    List<QuestaoFechada> buscaTodas(String colecao);

    // boolean atualiza();

    // boolean deleta();
}
