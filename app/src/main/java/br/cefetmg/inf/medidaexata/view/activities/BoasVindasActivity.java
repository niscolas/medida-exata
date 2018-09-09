package br.cefetmg.inf.medidaexata.view.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.google.android.material.button.MaterialButton;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import br.cefetmg.inf.medidaexata.view.fragments.onboarding.ApresentacaoFragment1;
import br.cefetmg.inf.medidaexata.view.fragments.onboarding.ApresentacaoFragment2;
import br.cefetmg.inf.medidaexata.view.fragments.onboarding.ApresentacaoFragment3;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BoasVindasActivity extends AppCompatActivity {

    private final static String TERMINOU_TUTORIAL = "boas_vindas_completa";
    private final static int NRO_PAGS_TUTORIAL = 3;

    //// Binding de Views
    //

    @BindView(R.id.vp_onboarding) ViewPager refVpOnboarding;
    @BindView(R.id.stl_indicador) SmartTabLayout refStlIndicador;
    @BindView(R.id.bt_pular) MaterialButton refBtPular;
    @BindView(R.id.bt_proximo) MaterialButton refBtProximo;

    //
    //// Binding de Views

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        if (preferences.getBoolean(TERMINOU_TUTORIAL, false)) {
            Intent onboarding = new Intent(this, MainActivity.class);
            startActivity(onboarding);

            finish();
        }

        setTheme(R.style.AppTheme_Tutorial);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boas_vindas);
        ButterKnife.bind(this);

        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int pos) {
                switch (pos) {
                    case 0 : return new ApresentacaoFragment1();
                    case 1 : return new ApresentacaoFragment2();
                    case 2 : return new ApresentacaoFragment3();
                    default: return null;
                }
            }

            @Override
            public int getCount() {
                return NRO_PAGS_TUTORIAL;
            }
        };
        refVpOnboarding.setAdapter(adapter);
        refStlIndicador.setViewPager(refVpOnboarding);

        refBtPular.setOnClickListener(v -> terminaBoasVindas());

        refBtProximo.setOnClickListener(v -> {
            if(refVpOnboarding.getCurrentItem() == NRO_PAGS_TUTORIAL - 1) {
                terminaBoasVindas();
            } else {
                refVpOnboarding.setCurrentItem(
                        refVpOnboarding.getCurrentItem() + 1,
                        true
                );
            }
        });

        refStlIndicador.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if(position == NRO_PAGS_TUTORIAL - 1){
                    refBtPular.setVisibility(View.GONE);
                    refBtProximo.setText(getString(R.string.vamos_comecar));
                } else {
                    refBtPular.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (refVpOnboarding.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            refVpOnboarding.setCurrentItem(refVpOnboarding.getCurrentItem() - 1);
            refBtProximo.setText(getString(R.string.proximo));
        }
    }

    private void terminaBoasVindas() {
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        preferences.edit().putBoolean(TERMINOU_TUTORIAL, true).apply();

        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }
}
