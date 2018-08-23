package br.cefetmg.inf.medidaexata.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.cefetmg.inf.medidaexata.model.Conteudo;
import br.cefetmg.inf.medidaexata.view.adapters.ConteudoAdapter;
import butterknife.ButterKnife;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ConteudosFragment extends Fragment {

    /**
     * Campos static final
     */
    // String 'Tag' usada para fazer Logs de Debug (que podem ser vistos no LogCat)
    private final static String TAG = ConteudosFragment.class.getSimpleName();
    // Key do Bundle
    private final static String UI_CORES = "cores_da_interface_do_claro_para_escuro";
    private final static String DISCIPLINA = "disciplina";

    // Variáveis relacionadas às Keys acima
    private int[] coresUi;
    private String disciplina;
    // Adapter da RecyclerView de Conteúdos
    private ConteudoAdapter adapter;
    // Listener de toque em conteúdos
    private OnConteudoInteractionListener frgListener;
    // Objeto para permitir a mudança de visibilidade da ProgressBar
    private ConteudoAdapter.IAlteraProgressBar iAltPbListener;

    /**
     * Construtor vazio obrigatório, favor não apagar
     */
    public ConteudosFragment() { }

    /**
     * @param cores => corresponde as cores que serão mostradas na UI
     * @param disciplina => corresponde ao nome da disciplina a ser usado na query do Firebase
     * @return => retorna uma instância da superclasse Fragment para
     *      generalizar a chamada de Fragments em 'DisciplinasActivity'
     */
    public static Fragment newInstance(int[] cores, String disciplina) {
        ConteudosFragment frg = new ConteudosFragment();

        Bundle args = new Bundle();
        args.putIntArray(UI_CORES, cores);
        args.putString(DISCIPLINA, disciplina);
        frg.setArguments(args);

        return frg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            coresUi = getArguments().getIntArray(UI_CORES);
            disciplina = getArguments().getString(DISCIPLINA);
        } else {
            throw new RuntimeException("Todos os parâmetros devem ser passados " +
                    "na instância da atividade");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_conteudos_list, container, false);
        ButterKnife.bind(this, v);

        if (v instanceof RecyclerView) {
            RecyclerView rv = (RecyclerView) v;
            rv.setLayoutManager(new LinearLayoutManager(v.getContext()));

            Log.d(TAG, "Setando adapter");
            setRvConteudosAdapter(rv);
            Log.d(TAG, "Adapter setado");
        }
        return v;
    }

    private void setRvConteudosAdapter(RecyclerView rv) {
        FirebaseFirestore bd = FirebaseFirestore.getInstance();

        Query conteudosQry = bd
                .collection("conteudos")
                .document(disciplina)
                .collection("conteudos_" + disciplina);

        FirestoreRecyclerOptions<Conteudo> options = new FirestoreRecyclerOptions
                .Builder<Conteudo>()
                .setQuery(conteudosQry, Conteudo.class)
                .build();

        adapter = new ConteudoAdapter(options, frgListener, iAltPbListener, coresUi);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }


    @Override
    public void onAttach(Context ctxt) {
        super.onAttach(ctxt);
        if (ctxt instanceof OnConteudoInteractionListener) {
            frgListener = (OnConteudoInteractionListener) ctxt;
        } else {
            throw new RuntimeException(ctxt.toString()
                    + " tem de implementar OnConteudoInteractionListener");
        }
        if(ctxt instanceof ConteudoAdapter.IAlteraProgressBar) {
            iAltPbListener = (ConteudoAdapter.IAlteraProgressBar) ctxt;
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

    /**
     * Interface para fornecer a interação com as CardViews de conteúdo,
     * tem de ser implementada na Activity que chamou este Fragment
     */
    public interface OnConteudoInteractionListener {
        void onConteudoInteraction(Conteudo conteudo, int[] coresTexto);
    }
}
