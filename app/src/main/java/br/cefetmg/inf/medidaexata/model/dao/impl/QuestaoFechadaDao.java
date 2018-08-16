package br.cefetmg.inf.medidaexata.model.dao.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.cefetmg.inf.medidaexata.model.dao.IQuestaoFechadaDao;
import br.cefetmg.inf.medidaexata.model.domain.QuestaoFechada;

public class QuestaoFechadaDao implements IQuestaoFechadaDao {
    private final FirebaseFirestore bd = FirebaseFirestore.getInstance();
    private static final String TAG = QuestaoFechadaDao.class.getSimpleName();

    @Override
    public QuestaoFechada busca(String questao) {
        final QuestaoFechada[] qst = new QuestaoFechada[1];

        DocumentReference docRef = bd
                .collection("questoes")
                .document(questao);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                QuestaoFechada questaoFechada
                        = documentSnapshot.toObject(QuestaoFechada.class);
                qst[0] = questaoFechada;
            }
        });
        return  qst[0];
    }

    @Override
    public List<QuestaoFechada> buscaTodasPorParametro(String dado, String parametro) {
        final List<QuestaoFechada> qsts = new ArrayList<>();

        bd.collection("questoes")
            .whereEqualTo(parametro, dado)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            QuestaoFechada qst = document.toObject(QuestaoFechada.class);
                            qsts.add(qst);

                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.d(TAG, "Erro ao receber documento: ", task.getException());
                    }
                }
            });
        return qsts;
    }

    @Override
    public List<QuestaoFechada> buscaTodas(String colecao) {
        final List<QuestaoFechada> qsts = new ArrayList<>();

        CollectionReference collRef = bd.collection(colecao);
        collRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        QuestaoFechada qst = document.toObject(QuestaoFechada.class);
                        qsts.add(qst);

                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d(TAG, "Erro ao receber documento: ", task.getException());
                }
            }
        });
        return qsts;
    }
}
