package br.cefetmg.inf.medidaexata.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cefetmg.inf.android.medidaexata.activities.R;

public class OnboardingFragment3 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle s) {

        return inflater.inflate(
                R.layout.screen_onboarding_3,
                container,
                false
        );

    }
}
