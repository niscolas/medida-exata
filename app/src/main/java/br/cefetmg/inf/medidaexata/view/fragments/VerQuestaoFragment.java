package br.cefetmg.inf.medidaexata.view.fragments;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.google.android.material.button.MaterialButton;

import androidx.fragment.app.Fragment;
import br.cefetmg.inf.util.TextViewUtils;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class VerQuestaoFragment extends Fragment {

    // Campos private static final
    private static final String TAG = VerQuestaoFragment.class.getSimpleName();
    private static final String KEY_ENUNCIADO = "enunciado";
    private static final String KEY_ALTERNATIVAS = "alternativas";
    private static final String KEY_CORES_TEXTO = "cores texto";
    private static final String KEY_RESPOSTA = "resposta";

    // Campos relacionadas às KEYs constantes
    private String enunciado;
    private String[] alternativas;
    private int[] coresTexto;
    private int resposta;

    // Binding
    @BindView(R.id.tv_enunciado_completo)
    TextView refTvEnunComp;
    @BindViews(
            {R.id.mtrl_bt_alt_a,
                    R.id.mtrl_bt_alt_b,
                    R.id.mtrl_bt_alt_c,
                    R.id.mtrl_bt_alt_d})
    MaterialButton[] refMtrlBtsAlts;

    // Listener da Activity
    private OnAlternativaSelecionadaListener frgListener;

    // Construtor vazio obrigatório
    public VerQuestaoFragment() {}

    public static Fragment newInstance(String enunciado,
                                       String[] alternativas,
                                       int[] coresTexto,
                                       int resposta) {
        VerQuestaoFragment frg = new VerQuestaoFragment();

        // Monta do Bundle de informações da questão
        Bundle args = new Bundle();
        args.putString(KEY_ENUNCIADO, enunciado);
        args.putStringArray(KEY_ALTERNATIVAS, alternativas);
        args.putIntArray(KEY_CORES_TEXTO, coresTexto);
        args.putInt(KEY_RESPOSTA, resposta);
        frg.setArguments(args);

        return frg;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().size() >= 4) {
            enunciado = getArguments().getString(KEY_ENUNCIADO);
            alternativas = getArguments().getStringArray(KEY_ALTERNATIVAS);
            coresTexto = getArguments().getIntArray(KEY_CORES_TEXTO);
            resposta = getArguments().getInt(KEY_RESPOSTA);
        } else {
            throw new RuntimeException("Enunciado, alternativas e cores de texto devem ser providos à " +
                    VerQuestaoFragment.class.getSimpleName() + " obrigatoriamente");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "chegou aki 2");
        View v = inflater.inflate(R.layout.fragment_ver_questao, container, false);
        ButterKnife.bind(this, v);
        Log.d(TAG, "chegou aki 3");

        // Seta o enunciado da questão
        refTvEnunComp.setTextColor(coresTexto[1]);
        refTvEnunComp.setText(enunciado);

        // Seta as alternativas
        char letra = 'a';
        for(int i = 0; i < alternativas.length; i++, letra++) {
            // Faz o Append da letra (a, b, c, d) e da descrição da alternativa
            refMtrlBtsAlts[i]
                    .append(TextViewUtils
                            .getStringColorida(String.format("%c) ", letra), coresTexto[2]));
            refMtrlBtsAlts[i]
                    .append(TextViewUtils
                            .getStringColorida(alternativas[i], coresTexto[0]));

            // Seta o ClickListener
            final int finalI = i;
            refMtrlBtsAlts[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    frgListener.onAlternativaSelecionada(
                            refMtrlBtsAlts,
                            finalI,
                            resposta);
                }
            });
        }
        return v;
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        frgListener = null;
    }

    public interface OnAlternativaSelecionadaListener {
        void onAlternativaSelecionada(MaterialButton[] refMtrlBts, int altSelecionada, int resposta);
    }
}
