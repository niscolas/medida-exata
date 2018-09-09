package br.cefetmg.inf.medidaexata.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.Map;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import br.cefetmg.inf.medidaexata.model.CoresUi;
import br.cefetmg.inf.medidaexata.view.IAlteraProgressBar;
import br.cefetmg.inf.medidaexata.view.activities.MainActivity;
import br.cefetmg.inf.medidaexata.viewmodel.MedidaExataViewModel;
import br.cefetmg.inf.util.TextViewUtils;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class VerQuestaoFragment extends Fragment {

    // Campos private static final
    private static final String TAG = VerQuestaoFragment.class.getSimpleName();

    // Listener da Activity
    private OnAlternativaSelecionadaListener frgListener;
    private IAlteraProgressBar altPbListener;
    // ViewModel
    private MedidaExataViewModel vm;

    // Binding
    @BindView(R.id.tv_materia_abordada_questao) TextView refTvMatAbordada;
    @BindView(R.id.ll_ver_questao) LinearLayout refLlVerQst;
    @BindViews(
            {R.id.mtrl_bt_alt_a,
                    R.id.mtrl_bt_alt_b,
                    R.id.mtrl_bt_alt_c,
                    R.id.mtrl_bt_alt_d}) MaterialButton[] refMtrlBtsAlts;

    // Construtor vazio obrigatório
    public VerQuestaoFragment() {}

    public static Fragment newInstance() {
        VerQuestaoFragment frg = new VerQuestaoFragment();
        return frg;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAlternativaSelecionadaListener) {
            frgListener = (OnAlternativaSelecionadaListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " deve implementar OnAlternativaSelecionadaListener");
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

        // Seta ViewModel
        vm = ViewModelProviders.of(getActivity()).get(MedidaExataViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        vm.setTituloAtivo("Resolva a questão abaixo");

        // Some com a ProgressBar
        altPbListener.escondeProgressBar();

        View v = inflater.inflate(R.layout.fragment_ver_questao, container, false);
        ButterKnife.bind(this, v);

        // Obtém os recursos necessários do ViewModel para popular os campos da questão
        // Obtém cores para o texto
        int corClara = 0;
        int corEscura = 0;
        if(vm.getContextoCoresAtivo().equals(getString(R.string.disc_ciencias))) {
            corClara = ContextCompat.getColor(getActivity(), R.color.verde_claro);
            corEscura = ContextCompat.getColor(getActivity(), R.color.verde_escuro);
        } else {
            corClara = ContextCompat.getColor(getActivity(), R.color.azul_claro);
            corEscura = ContextCompat.getColor(getActivity(), R.color.azul_escuro);
        }

        // Obtém informações da questão
        List<String> enunciado = vm.getQstAtiva().getValue().getEnunciado();
        String[] alternativas = vm.getQstAtiva().getValue().getAlternativas().toArray(new String[0]);

        // Seta o 'título' da página
        refTvMatAbordada.setText(vm.getQstAtiva().getValue().getMateriaAbordada());

        // Seta o enunciado da questão em si
        VerMateriaFragment
                .populaLinearLayoutComTextoEImagens(
                        refLlVerQst,
                        1,
                        enunciado,
                        corClara,
                        16,
                        0,
                        0,
                        0,
                        0,
                        this.getContext());

        // Seta as alternativas
        char letra = 'a';
        for(int i = 0; i < alternativas.length; i++, letra++) {
            // Faz o Append da letra (a, b, c, d) e da descrição da alternativa
            refMtrlBtsAlts[i]
                    .append(TextViewUtils
                            .getStringColorida(String.format("%c) ", letra), corEscura));
            refMtrlBtsAlts[i]
                    .append(TextViewUtils
                            .getStringColorida(alternativas[i], corClara));

            // Seta o ClickListener
            final int finalI = i;
            refMtrlBtsAlts[i].setOnClickListener(view ->
                    frgListener.onAlternativaSelecionada(refMtrlBtsAlts, finalI));
        }
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        frgListener = null;
    }

    public interface OnAlternativaSelecionadaListener {
        void onAlternativaSelecionada(MaterialButton[] refMtrlBts, int altSelecionada);
    }
}
