package br.cefetmg.inf.medidaexata.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.google.android.material.button.MaterialButton;

import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import br.cefetmg.inf.medidaexata.model.CoresUI;
import br.cefetmg.inf.medidaexata.view.IAlteraProgressBar;
import br.cefetmg.inf.medidaexata.view.adapters.ConteudoAdapter;
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
    @BindView(R.id.tv_enunciado_completo)
    TextView refTvEnunComp;
    @BindViews(
            {R.id.mtrl_bt_alt_a,
                    R.id.mtrl_bt_alt_b,
                    R.id.mtrl_bt_alt_c,
                    R.id.mtrl_bt_alt_d})
    MaterialButton[] refMtrlBtsAlts;

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
        // Some com a ProgressBar
        altPbListener.escondeProgressBar();

        View v = inflater.inflate(R.layout.fragment_ver_questao, container, false);
        ButterKnife.bind(this, v);

        // Some com a ProgressBar

        // Obtém os recursos necessários do ViewModel para popular os campos da questão
        // Obtém cores para o texto
        Map<String, Integer> coresAtuais = vm.getCoresUI().getCoresAtuais();
        int corClara = coresAtuais.get(CoresUI.COR_CLARA);
        int corPadrao = coresAtuais.get(CoresUI.COR_PADRAO);
        int corEscura = coresAtuais.get(CoresUI.COR_ESCURA);
        // Obtém informações da questão
        String enunciado = vm.getQst().getValue().getEnunciado();
        String[] alternativas = vm.getQst().getValue().getAlternativas().toArray(new String[0]);

        // Seta o enunciado da questão
        refTvEnunComp.setTextColor(corPadrao);
        refTvEnunComp.setText(enunciado);

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
