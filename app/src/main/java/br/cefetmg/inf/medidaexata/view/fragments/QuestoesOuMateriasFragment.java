package br.cefetmg.inf.medidaexata.view.fragments;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cefetmg.inf.android.medidaexata.activities.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import br.cefetmg.inf.medidaexata.model.enums.ConjuntoCor;
import br.cefetmg.inf.medidaexata.model.enums.Secao;
import br.cefetmg.inf.medidaexata.view.IAlteraProgressBar;
import br.cefetmg.inf.medidaexata.view.activities.MainActivity;
import br.cefetmg.inf.medidaexata.viewmodel.MedidaExataViewModel;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestoesOuMateriasFragment extends Fragment {

    // Campos static final
    public static final int CARD_VIEW_VER_MATERIA_SELECIONADO = 0;
    public static final int CARD_VIEW_VER_QUESTOES_SELECIONADO = 1;

    // Listener desse Fragment
    private OnQuestoesOuMateriasFragmentInteractionListener fListener;
    // Objeto para permitir a mudança de visibilidade da ProgressBar
    private IAlteraProgressBar altPbListener;

    //// Binding
    //

    @BindView(R.id.cv_ver_materia) CardView refCvVerMateria;
    @BindView(R.id.cv_ver_questoes) CardView refCvVerQuestoes;

    @BindView(R.id.tv_qro_ver_as_materias) TextView refTvQroVerMaterias;
    @BindView(R.id.tv_qro_ver_as_questoes) TextView refTvQroVerQuestoes;

    @BindColor(R.color.selector_bttnav_colors_azul) ColorStateList cslBttNavAzul;
    @BindColor(R.color.selector_bttnav_colors_verde) ColorStateList cslBttNavVerde;

    //
    //// Binding


    public QuestoesOuMateriasFragment() {
    }

    public static Fragment newInstance() {
        return new QuestoesOuMateriasFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnQuestoesOuMateriasFragmentInteractionListener) {
            fListener = (OnQuestoesOuMateriasFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnQuestoesOuMateriasFragmentInteractionListener");
        }
        if(context instanceof IAlteraProgressBar) {
            altPbListener = (IAlteraProgressBar) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " tem de implementar IAlteraProgressBar");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_questoes_ou_materias, container, false);
        ButterKnife.bind(this, v);

        // Seta os ClickListeners
        refCvVerMateria.setOnClickListener(view ->
                fListener.onQuestoesOuMateriasInteraction(CARD_VIEW_VER_MATERIA_SELECIONADO));
        refCvVerQuestoes.setOnClickListener(view ->
                fListener.onQuestoesOuMateriasInteraction(CARD_VIEW_VER_QUESTOES_SELECIONADO));

        altPbListener.escondeProgressBar();

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fListener = null;
    }

    public interface OnQuestoesOuMateriasFragmentInteractionListener {
        void onQuestoesOuMateriasInteraction(int cvSelecionado);
    }
}
