package br.cefetmg.inf.medidaexata.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.google.android.material.button.MaterialButton;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import br.cefetmg.inf.medidaexata.view.fragments.onboarding.OnboardingFragment1;
import br.cefetmg.inf.medidaexata.view.fragments.onboarding.OnboardingFragment2;
import br.cefetmg.inf.medidaexata.view.fragments.onboarding.OnboardingFragment3;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OnboardingActivity extends Activity {

//    @BindView(R.id.vp_onboarding) ViewPager refVpOnboarding;
//    @BindView(R.id.stl_indicador) SmartTabLayout refStlIndicador;
    @BindView(R.id.bt_pular) MaterialButton refBtPular;
    @BindView(R.id.bt_proximo) MaterialButton refBtProximo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);

        //a FragmentStatePagerAdapter that the ViewPager can use to display the onboarding screens
//        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//
//                switch (position) {
//                    case 0 : return new OnboardingFragment1();
//                    case 1 : return new OnboardingFragment2();
//                    case 2 : return new OnboardingFragment3();
//                    default: return null;
//                }
//            }
//
//            @Override
//            public int getCount() {
//                return 3;
//            }
//        };//fim do adapter

//        refVpOnboarding.setAdapter(adapter);
//        refStlIndicador.setViewPager(refVpOnboarding);

        //agora os botao
//        refBtPular.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finishOnboarding();
//            }
//        });

//        refBtProximo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(refVpOnboarding.getCurrentItem() == 2) { // The last screen
//                    finishOnboarding();
//                } else {
//                    refVpOnboarding.setCurrentItem(
//                            refVpOnboarding.getCurrentItem() + 1,
//                            true
//                    );
//                }
//            }
//        });
//
//        refStlIndicador.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                if(position == 2){
//                    refBtPular.setVisibility(View.GONE);
//                    refBtProximo.setText("Done");
//                } else {
//                    refBtPular.setVisibility(View.VISIBLE);
//                    refBtProximo.setText("Next");
//                }
//            }
//        });

        // Get the shared preferences
        SharedPreferences preferences =  getSharedPreferences("my_preferences", MODE_PRIVATE);

        // Check if onboarding_complete is false
        if(!preferences.getBoolean("onboarding_complete",false)) {
            // Start the onboarding Activity
            Intent onboarding = new Intent(this, OnboardingActivity.class);
            startActivity(onboarding);

            // Close the main Activity
            finish();
        }
    }//fim do oncreate

//    private void finishOnboarding() {
//        // Get the shared preferences
//        SharedPreferences preferences =
//                getSharedPreferences("my_preferences", MODE_PRIVATE);
//
//        // Set onboarding_complete to true
//        preferences.edit()
//                .putBoolean("onboarding_complete",true).apply();
//
//        // Launch the main Activity, called MainActivity
//        Intent main = new Intent(this, MainActivity.class);
//        startActivity(main);
//
//        // Close the OnboardingActivity
//        finish();
//    }
}
