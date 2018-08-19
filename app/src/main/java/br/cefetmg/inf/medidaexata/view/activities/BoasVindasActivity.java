package br.cefetmg.inf.medidaexata.view.activities;

import android.content.Intent;
import com.google.android.material.button.MaterialButton;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;

import com.cefetmg.inf.android.medidaexata.activities.R;

public class BoasVindasActivity extends AppCompatActivity {

    @BindView(R.id.bt_comecar)
    MaterialButton refBtComecar;

    @OnClick(R.id.bt_comecar)
    void onClickBtComecar() {
        final Intent INTENT_INTRODUCAO_ACTIVITY
                = new Intent(BoasVindasActivity.this, IntroducaoActivity.class);
        startActivity(INTENT_INTRODUCAO_ACTIVITY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boas_vindas);
        ButterKnife.bind(this);
    }
}
