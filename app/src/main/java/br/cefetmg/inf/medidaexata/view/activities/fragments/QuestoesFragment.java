package br.cefetmg.inf.medidaexata.view.activities.fragments;

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

import br.cefetmg.inf.medidaexata.model.QuestaoFechada;
import br.cefetmg.inf.medidaexata.view.adapters.QuestaoAdapter;
import butterknife.ButterKnife;

public class QuestoesFragment extends Fragment {

    private final static String TAG = QuestoesFragment.class.getSimpleName();

    private QuestaoAdapter adapter;
    private IProgressBarShower frgListener;

    public QuestoesFragment() {
    }

    public static Fragment newInstance() {
        QuestoesFragment f = new QuestoesFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
//        }
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

            FirebaseFirestore bd = FirebaseFirestore.getInstance();
            FirestoreRecyclerOptions<QuestaoFechada> options
                    = new FirestoreRecyclerOptions
                    .Builder<QuestaoFechada>()
                    .setQuery(
                            bd.collection("questoes")
                            , QuestaoFechada.class)
                    .build();

            adapter = new QuestaoAdapter(options, frgListener);
            adapter.notifyDataSetChanged();
            rv.setAdapter(adapter);

            Log.d(TAG, "Adapter setado");
        }
        return v;
    }

    @Override
    public void onAttach(Context ctxt) {
        super.onAttach(ctxt);
        if (ctxt instanceof IProgressBarShower) {
            frgListener = (IProgressBarShower) ctxt;
        } else {
            throw new RuntimeException(ctxt.toString()
                    + " tem de implementar IProgressBarShower");
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

    public interface IProgressBarShower {
        void escondeProgressBar();
    }

    public interface OnQuestaoInteractionListener {
        void onVerQuestaoInteraction(QuestaoFechada qst);
        void onVerMateriaInteraction(QuestaoFechada qst);
    }
}