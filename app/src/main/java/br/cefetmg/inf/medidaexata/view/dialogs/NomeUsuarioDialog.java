package br.cefetmg.inf.medidaexata.view.dialogs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import br.cefetmg.inf.medidaexata.view.activities.MainActivity;
import br.cefetmg.inf.medidaexata.view.fragments.ConquistasFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NomeUsuarioDialog extends DialogFragment {

    @BindView(R.id.til_nome_usuario) TextInputLayout refTilNomeUsuario;

    @BindView(R.id.et_nome_usuario) TextInputEditText refEtNomeUsuario;

    private String nomeUsuario;


    public static NomeUsuarioDialog newInstance(MainActivity activity) {
        NomeUsuarioDialog dialog = new NomeUsuarioDialog();
        return dialog;
    }

    @NonNull
    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_nome_usuario, null, false);
        ButterKnife.bind(this, v);

        adicionaTextListener();

        AlertDialog alertDialog = new AlertDialog.Builder(getContext(), R.style.AppTheme_Dialog)
                .setView(v)
                .setCancelable(false)
                .setPositiveButton(R.string.pronto, null)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.setOnShowListener(dialog -> onDialogMostrado(alertDialog));
        return alertDialog;
    }

    private void onDialogMostrado(AlertDialog dialog) {
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(v -> onProntoSelecionado());
    }

    private void onProntoSelecionado() {
            if(eUmNomeValido(refTilNomeUsuario, refTilNomeUsuario.getEditText().getText().toString())) {
                SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor spEditor = sp.edit();

                // Seta o nome do usuário para ser usado posteriormente
                spEditor.putString(ConquistasFragment.NOME_USUARIO, nomeUsuario);
                // Seta que o nome do usuário já foi escrito
                spEditor.putBoolean(ConquistasFragment.NOME_USUARIO_SETADO, true);

                // Aplica as modificações feitas no SharedPreferences
                spEditor.apply();

                dismiss();
            }
    }

    private boolean eUmNomeValido(TextInputLayout layout, String nome) {
        if (TextUtils.isEmpty(nome)) {
            layout.setErrorEnabled(true);
            layout.setError(getString(R.string.nome_vazio));
            return false;
        }

        if (nomeUsuario == null) {
            layout.setErrorEnabled(true);
            return false;
        }

        layout.setErrorEnabled(false);
        layout.setError("");
        return true;
    }

    private void adicionaTextListener() {
        refEtNomeUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int inicio, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                nomeUsuario = s.toString();
            }
        });
    }
}
