package br.cefetmg.inf.medidaexata.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import br.cefetmg.inf.medidaexata.model.QuestaoFechada;
import br.cefetmg.inf.medidaexata.view.adapters.ConteudoAdapter;
import br.cefetmg.inf.medidaexata.view.adapters.QuestaoAdapter;
import butterknife.ButterKnife;

public class QuestoesFragment extends Fragment {

    /**
     * Campos private final static
     */
    // TAG utilizada para fazer Logs
    private final static String TAG = QuestoesFragment.class.getSimpleName();
    // Key para relacionar o conteúdo das questões da UI
    private final static String KEY_CONTEUDO = "conteudo";
    // Key para relacionar as cores da UI
    private final static String KEY_CORES = "cores";

    // int[] que armazena o valor relacionado à Key 'KEY_CORES'
    private int[] cores;
    // String que armazena o valor relacionado à Key 'KEY_CONTEUDO'
    private String conteudo;

    // Adapter da RecyclerView de Questões
    private QuestaoAdapter adapter;
    // Listeners
    private OnQuestaoInteractionListener frgListener;
    private ConteudoAdapter.IAlteraProgressBar altPbListener;

    public QuestoesFragment() { }

    public static Fragment newInstance(int[] cores, String conteudo) {
        QuestoesFragment frg = new QuestoesFragment();

        Bundle args = new Bundle();
        args.putIntArray(KEY_CORES, cores);
        args.putString(KEY_CONTEUDO, conteudo);
        frg.setArguments(args);

        return frg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            this.conteudo = getArguments().getString(KEY_CONTEUDO);
            this.cores = getArguments().getIntArray(KEY_CORES);
        } else {
            throw new RuntimeException("Um nome de conteúdo deve ser passado ao QuestoesFragment");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_questoes_list, container, false);
        ButterKnife.bind(this, v);

        if (v instanceof RecyclerView) {
            RecyclerView rv = (RecyclerView) v;
            rv.setLayoutManager(new LinearLayoutManager(v.getContext()));

            Log.d(TAG, "Setando adapter");
            setRvQuestoesAdapter(rv);
            Log.d(TAG, "Adapter setado");
        }
        return v;
    }

    private void setRvQuestoesAdapter(RecyclerView rv) {
        FirebaseFirestore bd = FirebaseFirestore.getInstance();

        Query qstsQry = bd
                .collection("questoes")
                .whereEqualTo("conteudo", conteudo);

        FirestoreRecyclerOptions<QuestaoFechada> options = new FirestoreRecyclerOptions
                .Builder<QuestaoFechada>()
                .setQuery(qstsQry, QuestaoFechada.class)
                .build();

        adapter = new QuestaoAdapter(options, frgListener, altPbListener, cores);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context ctxt) {
        super.onAttach(ctxt);
        if (ctxt instanceof OnQuestaoInteractionListener) {
            frgListener = (OnQuestaoInteractionListener) ctxt;
        } else {
            throw new RuntimeException(ctxt.toString()
                    + " tem de implementar IProgressBarShower");
        }
        if(ctxt instanceof ConteudoAdapter.IAlteraProgressBar) {
            altPbListener = (ConteudoAdapter.IAlteraProgressBar) ctxt;
        } else {
            throw new RuntimeException(ctxt.toString()
                    + "tem de implementar QuestaoAdapter.IAlteraProgressBar");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        frgListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public interface OnQuestaoInteractionListener {
        void onVerQuestaoInteraction(QuestaoFechada qst);
        void onVerMateriaInteraction(QuestaoFechada qst);
    }
}