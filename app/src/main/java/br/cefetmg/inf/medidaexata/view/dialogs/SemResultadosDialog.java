package br.cefetmg.inf.medidaexata.view.dialogs;

import android.content.Intent;
import android.os.Bundle;

import com.cefetmg.inf.android.medidaexata.activities.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import br.cefetmg.inf.medidaexata.view.activities.MainActivity;

public class SemResultadosDialog extends DialogFragment {

    //// Campos static final
    //

    private static final String KEY_MSG = "mensagem";

    public static final String SEM_QUESTOES =
            "Ops, nos desculpe.\n" +
                    "Pelo jeito nós não temos questões que envolvem este conteúdo.\n" +
                    "Volte para o menu e procure outras questões, nós temos muito conteúdo legal!";

    public static final String SEM_MATERIA =
            "Ops, nos desculpe.\n" +
                    "Pelo jeito nós não temos a matéria que envolve esta questão.\n" +
                    "Volte para o menu e procure outras matérias, nós temos muito conteúdo legal!";

    //
    //// Campos static final

    private String msg;

    public SemResultadosDialog() {
    }

    public static DialogFragment newInstance(String msg) {
        DialogFragment df = new SemResultadosDialog();

        Bundle args = new Bundle();
        args.putString(KEY_MSG, msg);
        df.setArguments(args);

        return df;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            msg = getArguments().getString(KEY_MSG);
        }
    }

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dialog);
        builder
                .setMessage(msg)
                .setPositiveButton("Sim!!", (dialog, id) -> {
                    Intent intent = new Intent(this.getContext(), MainActivity.class);
                    startActivity(intent);
                })
                .setCancelable(false);
        return builder.create();
    }
}
