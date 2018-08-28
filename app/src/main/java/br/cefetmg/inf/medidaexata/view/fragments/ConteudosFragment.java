package br.cefetmg.inf.medidaexata.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.cefetmg.inf.medidaexata.model.Conteudo;
import br.cefetmg.inf.medidaexata.view.adapters.ConteudoAdapter;
import br.cefetmg.inf.medidaexata.viewmodel.MedidaExataViewModel;
import butterknife.ButterKnife;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Map;

public class ConteudosFragment extends Fragment {

    // String 'Tag' usada para fazer Logs de Debug (que podem ser vistos no LogCat)
    private final static String TAG = ConteudosFragment.class.getSimpleName();

    // View Model
    private MedidaExataViewModel vm;
    // Variáveis relacionadas às Keys acima
    private String disciplina;
    // Adapter da RecyclerView de Conteúdos
    private ConteudoAdapter adapter;
    // Listener de toque em conteúdos
    private OnConteudoInteractionListener frgListener;
    // Objeto para permitir a mudança de visibilidade da ProgressBar
    private ConteudoAdapter.IAlteraProgressBar iAltPbListener;

    // Construtor vazio obrigatório, favor não apagar
    public ConteudosFragment() { }

    // Obtém instância
    public static Fragment newInstance() {
        ConteudosFragment frg = new ConteudosFragment();
        return frg;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtém o ViewModel
        vm = ViewModelProviders.of(getActivity()).get(MedidaExataViewModel.class);
        // Seta a disciplina utilizada nas Querys do FireStore
        disciplina = vm.getDisciplina();
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

    @Override
    public void onStart() {
        super.onStart();

        // Inicia o Adapter
        adapter.startListening();
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

        Map<String, Integer> coresTexto = vm.getCoresUI().getCoresAtuais();
        adapter = new ConteudoAdapter(options, frgListener, iAltPbListener, coresTexto);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }


    // Interface para fornecer a interação com as CardViews de conteúdo,
    // tem de ser implementada na Activity que chamou este Fragment
    public interface OnConteudoInteractionListener {
        void onConteudoInteraction(Conteudo conteudo);
    }
}
