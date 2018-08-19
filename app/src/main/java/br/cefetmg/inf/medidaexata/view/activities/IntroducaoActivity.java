package br.cefetmg.inf.medidaexata.view.activities;

import android.content.Intent;
import com.google.android.material.button.MaterialButton;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;

import com.cefetmg.inf.android.medidaexata.activities.R;

public class IntroducaoActivity extends AppCompatActivity {

    @BindView(R.id.bt_interessante)
    MaterialButton refBtInteressante;

    @OnClick(R.id.bt_interessante)
    void onClickBtInteressante() {
        final Intent INTENT_MATERIAS_ACTIVITY
                = new Intent(IntroducaoActivity.this, DisciplinasActivity.class);
        startActivity(INTENT_MATERIAS_ACTIVITY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introducao);
        ButterKnife.bind(this);
    }
}
