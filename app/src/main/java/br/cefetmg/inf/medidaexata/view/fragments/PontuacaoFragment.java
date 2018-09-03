package br.cefetmg.inf.medidaexata.view.fragments;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import br.cefetmg.inf.medidaexata.view.IAlteraProgressBar;
import br.cefetmg.inf.medidaexata.viewmodel.MedidaExataViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PontuacaoFragment extends Fragment {

    // Listeners necessários
    private OnPontuacaoFragmentInteractionListener frgListener;
    private IAlteraProgressBar altPbListener;

    // Nosso ViewModel
    private MedidaExataViewModel vm;

    @BindView(R.id.bt_claro_que_sim) MaterialButton refBtClaroQSim;
    @BindView(R.id.bt_nao_quero_voltar) MaterialButton refBtNQroVoltar;
    @BindView(R.id.tv_qtd_pontos) TextView refTvQtdPontuacao;

    // Construtor vazio necessário
    public PontuacaoFragment() {
    }

    public static Fragment newInstance() {
        return new PontuacaoFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPontuacaoFragmentInteractionListener) {
            frgListener = (OnPontuacaoFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " deve implementar OnPontuacaoFragmentInteractionListener");
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
        if(getActivity() != null)
            vm = ViewModelProviders.of(getActivity()).get(MedidaExataViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pontuacao, container, false);
        ButterKnife.bind(this, v);

        // Seta Listeners para os botões do Fragment
        refBtClaroQSim.setOnClickListener(view -> frgListener.onClaroQueSimInteraction());
        refBtNQroVoltar.setOnClickListener(view -> frgListener.onVoltarParaOMenuSelecionado());

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        altPbListener.escondeProgressBar();
        iniciaAnimacaoNumero(0, vm.getQtdPontosAtuais(), 1000, refTvQtdPontuacao);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        frgListener = null;
    }

    /**
     * Cria uma animação de números que aumentam numa TextView num período de tempo
     * @param tv será o recipiente da aniimação
     * @param nroInicial a animação começará nesse número e
     * @param nroFinal acabará nesse,
     * @param tempoEmMs nesse período de tempo (em Milisegundos)
     */
    private void iniciaAnimacaoNumero(int nroInicial, int nroFinal, int tempoEmMs, TextView tv) {
        ValueAnimator animator = ValueAnimator.ofInt(nroInicial, nroFinal);
        animator.setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                tv.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }

    public interface OnPontuacaoFragmentInteractionListener {
        void onClaroQueSimInteraction();
        void onVoltarParaOMenuSelecionado();
    }
}
