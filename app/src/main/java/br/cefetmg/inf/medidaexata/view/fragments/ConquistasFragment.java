package br.cefetmg.inf.medidaexata.view.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cefetmg.inf.android.medidaexata.activities.R;

import androidx.fragment.app.Fragment;
import br.cefetmg.inf.medidaexata.view.IAlteraProgressBar;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        altPbListener.escondeProgressBar();

        View v = inflater.inflate(R.layout.fragment_conquistas, container, false);
        ButterKnife.bind(this, v);

        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);

        refTvNomeUsuario.setText(String.format("%s,", sp.getString(NOME_USUARIO, "Seu nome")));

        refTvPontosMat.setText(String.valueOf(sp.getInt(PONTOS_MATEMATICA, 0)));

        refTvPontosCie.setText(String.valueOf(sp.getInt(PONTOS_CIENCIAS, 0)));

        PontuacaoFragment.iniciaAnimacaoNumero(
                0,
                sp.getInt(PONTOS_TOTAIS, 0),
                2500,
                refTvPontosTotais);

        return v;
    }
}