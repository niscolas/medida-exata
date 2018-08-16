package br.cefetmg.inf.medidaexata.model.dao;

import com.google.firebase.firestore.CollectionReference;

public interface IColecaoDao {
    // boolean adiciona();

    CollectionReference busca(String caminho);

    // boolean atualiza();

    // boolean deleta();
}
