package br.cefetmg.inf.medidaexata.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cefetmg.inf.android.medidaexata.activities.R;

public class BoasVindasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boas_vindas);

        // Declarações
        final Button refBtComecar = findViewById(R.id.bt_comecar);

        // Adiciona Click listener a refBtComecar(via função Lambda)
        refBtComecar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent INTENT_INTRODUCAO_ACTIVITY
                        = new Intent(BoasVindasActivity.this, IntroducaoActivity.class);
                startActivity(INTENT_INTRODUCAO_ACTIVITY);
            }
        });
    }
}
