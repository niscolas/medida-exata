package br.cefetmg.inf.medidaexata.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
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

import java.util.Map;

import br.cefetmg.inf.medidaexata.model.QuestaoFechada;
import br.cefetmg.inf.medidaexata.view.IAlteraProgressBar;
import br.cefetmg.inf.medidaexata.view.IOnSemResultados;
import br.cefetmg.inf.medidaexata.view.adapters.QuestaoAdapter;
import br.cefetmg.inf.medidaexata.viewmodel.MedidaExataViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestoesFragment extends Fragment {

    /**
     * Campos private final static
     */
    // TAG utilizada para fazer Logs
    private final static String TAG = QuestoesFragment.class.getSimpleName();

    // Adapter da RecyclerView de Questões
    private QuestaoAdapter adapter;
    // Listeners
    private OnQuestaoInteractionListener frgListener;
    private IAlteraProgressBar altPbListener;
    private IOnSemResultados semResultadosListener;
    // ViewModel
    private MedidaExataViewModel vm;

    public QuestoesFragment() {
    }

    public static Fragment newInstance() {
        return new QuestoesFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnQuestaoInteractionListener) {
            frgListener = (OnQuestaoInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " tem de implementar OnQuestaoInteractionListener");
        }
        if(context instanceof IAlteraProgressBar) {
            altPbListener = (IAlteraProgressBar) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "tem de implementar IAlteraProgressBar");
        }
        if(context instanceof IOnSemResultados) {
            semResultadosListener = (IOnSemResultados) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "tem de implementar IOnSemResultados");
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Seta ViewModel
        vm = ViewModelProviders.of(getActivity()).get(MedidaExataViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        vm.setTituloAtivo("Escolha uma questão");

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

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    private void setRvQuestoesAdapter(RecyclerView rv) {
        FirebaseFirestore bd = FirebaseFirestore.getInstance();

        // Obtém o nome do conteúdo ativo
        String conteudo = vm.getConteudoAtivo().getNome();

        Query qstsQry = bd
                .collection("questoes")
                .whereEqualTo("conteudo", conteudo);

        FirestoreRecyclerOptions<QuestaoFechada> options = new FirestoreRecyclerOptions
                .Builder<QuestaoFechada>()
                .setQuery(qstsQry, QuestaoFechada.class)
                .build();

        adapter = new QuestaoAdapter(options, frgListener, altPbListener, semResultadosListener);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        frgListener = null;
    }

    public interface OnQuestaoInteractionListener {
        void onVerQuestaoInteraction(QuestaoFechada qst);
        void onVerMateriaInteraction(QuestaoFechada qst);
    }
}