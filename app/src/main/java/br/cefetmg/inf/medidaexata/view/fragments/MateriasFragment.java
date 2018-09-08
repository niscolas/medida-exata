package br.cefetmg.inf.medidaexata.view.fragments;

import android.content.Context;
import android.os.Bundle;

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

import br.cefetmg.inf.medidaexata.model.Materia;
import br.cefetmg.inf.medidaexata.view.IAlteraProgressBar;
import br.cefetmg.inf.medidaexata.view.adapters.MateriaAdapter;
import br.cefetmg.inf.medidaexata.viewmodel.MedidaExataViewModel;
import br.cefetmg.inf.util.StringUtils;
import butterknife.ButterKnife;

public class MateriasFragment extends Fragment {

    // String 'Tag' usada para fazer Logs de Debug (que podem ser vistos no LogCat)
    private final static String TAG = MateriasFragment.class.getSimpleName();

    // View Model
    private MedidaExataViewModel vm;
    // Variáveis relacionadas às Keys acima
    private String disciplina;
    // Adapter da RecyclerView de Conteúdos
    private MateriaAdapter adapter;
    // Listener de toque em conteúdos
    private OnMateriasFragmentInteractionListener frgListener;
    // Objeto para permitir a mudança de visibilidade da ProgressBar
    private IAlteraProgressBar altPbListener;

    // Construtor vazio obrigatório, favor não apagar
    public MateriasFragment() {
    }

    // Obtém instância
    public static Fragment newInstance() {
        return new MateriasFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MateriasFragment.OnMateriasFragmentInteractionListener) {
            frgListener = (MateriasFragment.OnMateriasFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " tem de implementar OnMateriasFragmentInteractionListener");
        }
        if(context instanceof IAlteraProgressBar) {
            altPbListener = (IAlteraProgressBar) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "tem de implementar QuestaoAdapter.IAlteraProgressBar");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtém o ViewModel
        vm = ViewModelProviders.of(getActivity()).get(MedidaExataViewModel.class);
        // Seta a disciplina utilizada nas Querys do FireStore
        disciplina = StringUtils.tiraAcentos(vm.getDisciplinaAtiva().toLowerCase());
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        vm.setTituloAtivo("Escolha uma matéria");

        View v = inflater.inflate(R.layout.fragment_materias_list, container, false);
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

        Log.d(TAG, "DISCIPLINA: " + disciplina);

        Query qry = bd
                .collection("materias")
                .document(disciplina)
                .collection("materias_" + disciplina);

        FirestoreRecyclerOptions<Materia> options = new FirestoreRecyclerOptions
                .Builder<Materia>()
                .setQuery(qry, Materia.class)
                .build();

        Map<String, Integer> coresTexto = vm.getCoresUI().getCoresAtuais();
        adapter = new MateriaAdapter(options, frgListener, altPbListener, coresTexto);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }


    // Interface para fornecer a interação com as CardViews de matéria,
    // tem de ser implementada na Activity que chamou este Fragment
    public interface OnMateriasFragmentInteractionListener {
        void onMateriaInteraction(Materia m);
    }
}
