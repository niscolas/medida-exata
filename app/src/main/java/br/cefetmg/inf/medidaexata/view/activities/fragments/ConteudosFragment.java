package br.cefetmg.inf.medidaexata.view.activities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.cefetmg.inf.medidaexata.model.Conteudo;
import br.cefetmg.inf.medidaexata.view.adapters.ConteudoAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

public class ConteudosFragment extends Fragment {

    private final static String TAG = ConteudosFragment.class.getSimpleName();

    private ConteudoAdapter adapter;
    private OnConteudoInteractionListener frgListener;

    public ConteudosFragment() {
    }

    public static ConteudosFragment newInstance() {
        ConteudosFragment frg = new ConteudosFragment();
        return frg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_conteudos_list, container, false);

        if (v instanceof RecyclerView) {
            RecyclerView rv = (RecyclerView) v;
            rv.setLayoutManager(new LinearLayoutManager(v.getContext()));

            Log.d(TAG, "Setando adapter");

            FirebaseFirestore bd = FirebaseFirestore.getInstance();
            FirestoreRecyclerOptions<Conteudo> options
                    = new FirestoreRecyclerOptions
                    .Builder<Conteudo>()
                    .setQuery(
                            bd.collection("conteudos")
                            , Conteudo.class)
                    .build();

            adapter = new ConteudoAdapter(options, frgListener);
            adapter.notifyDataSetChanged();
            rv.setAdapter(adapter);

            Log.d(TAG, "Adapter setado");
        }
        return v;
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        frgListener = null;
    }

    public interface OnConteudoInteractionListener {
        void onConteudoInteraction();
    }
}
