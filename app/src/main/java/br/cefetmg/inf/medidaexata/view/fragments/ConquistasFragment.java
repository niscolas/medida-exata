package br.cefetmg.inf.medidaexata.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cefetmg.inf.android.medidaexata.activities.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import br.cefetmg.inf.medidaexata.model.enums.Secao;
import br.cefetmg.inf.medidaexata.view.IAlteraProgressBar;
import br.cefetmg.inf.medidaexata.view.activities.MainActivity;
import br.cefetmg.inf.medidaexata.viewmodel.MedidaExataViewModel;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ConquistasFragment extends Fragment {

    //// Campos static final
    //

    private static final String TAG = ConquistasFragment.class.getSimpleName();
    public static final String NOME_USUARIO = "nome_do_usuario";
    public static final String NOME_USUARIO_SETADO = "nome_do_usuario_foi_setado";
    public static final String PONTOS_MATEMATICA = "pontos_em_matematica";
    public static final String PONTOS_CIENCIAS = "pontos_em_ciencias";
    public static final String PONTOS_TOTAIS = "pontos_totais";

    //
    //// Campos static final

    // Objeto para permitir a mudança de visibilidade da ProgressBar
    private IAlteraProgressBar altPbListener;

    //// Binding
    //

    @BindColor(android.R.color.white) int branco;
    @BindColor(R.color.selector_bttnav_colors_azul) ColorStateList cslBttNavAzul;

    @BindView(R.id.tv_pontos_matematica) TextView refTvPontosMat;
    @BindView(R.id.tv_pontos_ciencias) TextView refTvPontosCie;
    @BindView(R.id.tv_pontos_totais) TextView refTvPontosTotais;
    @BindView(R.id.tv_nome_usuario) TextView refTvNomeUsuario;

    //
    //// Binding

    public ConquistasFragment() {
    }

    public static Fragment newInstance() {
        return new ConquistasFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IAlteraProgressBar) {
            altPbListener = (IAlteraProgressBar) context;
        } else {
            throw new RuntimeException(context.toString() + "tem de implementar IAlteraProgressBar");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_conquistas, container, false);
        ButterKnife.bind(this, v);

        // Obtém o SharedPreferences e coloca o texto na tela
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);

        refTvNomeUsuario.setText(String.format("%s,", sp.getString(NOME_USUARIO, "Seu nome")));
        refTvPontosMat.setText(String.valueOf(sp.getInt(PONTOS_MATEMATICA, 0)));
        refTvPontosCie.setText(String.valueOf(sp.getInt(PONTOS_CIENCIAS, 0)));

        // Esconde a PB depois que o texto completo estiver na tela
        altPbListener.escondeProgressBar();

        // Gera a animação do número que vai aumentando
        PontuacaoFragment.iniciaAnimacaoNumero(
                0,
                sp.getInt(PONTOS_TOTAIS, 0),
                2500,
                refTvPontosTotais);

        return v;
    }
}