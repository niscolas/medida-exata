package br.cefetmg.inf.medidaexata.view.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cefetmg.inf.android.medidaexata.activities.R;

public class MateriasFragment extends Fragment {

    private OnMateriasFragmentInteractionListener frgListener;

    public MateriasFragment() {
    }

    public static Fragment newInstance() {
        MateriasFragment frg = new MateriasFragment();
        return frg;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMateriasFragmentInteractionListener) {
            frgListener = (OnMateriasFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnMateriasFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_materias, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        frgListener = null;
    }

    public interface OnMateriasFragmentInteractionListener {
        void onMateriaInteraction();
    }
}
