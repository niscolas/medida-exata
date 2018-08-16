package br.cefetmg.inf.medidaexata.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cefetmg.inf.android.medidaexata.activities.R;

public class IntroducaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introducao);

        // Declarações
        final Button refBtInteressante = findViewById(R.id.bt_interessante);

        // Adiciona Click listener a refBtInteressante (via função Lambda)
        refBtInteressante.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent INTENT_MATERIAS_ACTIVITY
                        = new Intent(IntroducaoActivity.this, DisciplinasActivity.class);
                startActivity(INTENT_MATERIAS_ACTIVITY);
            }
        });
    }
}
