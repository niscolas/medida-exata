package br.cefetmg.inf.medidaexata.view.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import br.cefetmg.inf.medidaexata.view.activities.fragments.QuestoesFragment;

public class DisciplinasActivity extends AppCompatActivity {

    private static final int FRAGMENT_CONTAINER_ID = R.id.fragment_container;

    private BottomNavigationView refBttNavConteudos;
    private Toolbar refTbMenu;
    private final
    BottomNavigationView.OnNavigationItemSelectedListener
            bttNavConteudosClickListener =
            new BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.i_btt_nav_matematica:
                            atualizaActivity(
                                    R.color.colorPrimary,
                                    R.string.matematica,
                                    R.color.selector_bttnav_colors_mat);

                            final Fragment qfm = QuestoesFragment.newInstance();
                            iniciaFragment(qfm);
                            break;

                        case R.id.i_btt_nav_ciencias:
                            atualizaActivity(
                                    R.color.colorSecondary,
                                    R.string.ciencias,
                                    R.color.selector_bttnav_colors_cie);

                            final Fragment qfc = QuestoesFragment.newInstance();
                            iniciaFragment(qfc);
                            break;

                        case R.id.i_btt_nav_perfil:
                            Toast.makeText(
                                    DisciplinasActivity.this,
                                    "TODO",
                                    Toast.LENGTH_SHORT)
                                    .show();
                            break;
                    }
                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas);

        refTbMenu = findViewById(R.id.tb_menu_disciplinas);
        refBttNavConteudos = findViewById(R.id.bttnav_conteudos);
        refBttNavConteudos
                .setItemIconTintList(
                        getResources()
                                .getColorStateList(
                                        R.color.colorPrimaryLight));
        refBttNavConteudos
                .setItemTextColor(
                        getResources()
                                .getColorStateList(
                                        R.color.colorPrimaryLight));

        refBttNavConteudos.setOnNavigationItemSelectedListener(bttNavConteudosClickListener);
    }

    private void iniciaFragment(Fragment f) {
        final TextView refTvToque = findViewById(R.id.tv_toque);
        refTvToque.setVisibility(View.GONE);
        final LinearLayout refLlDisciplinas = findViewById(R.id.ll_disciplinas);
        refLlDisciplinas.setVisibility(View.GONE);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(FRAGMENT_CONTAINER_ID, f)
                .commit();
    }

    private void atualizaActivity(int corId, int titulo, int colorStateList) {
        refTbMenu.setTitle(titulo);
        refTbMenu.setBackgroundColor(ContextCompat
                .getColor(this, corId));

        refBttNavConteudos.setBackgroundColor(ContextCompat
                .getColor(this, corId));
        refBttNavConteudos
                .setItemIconTintList(
                        ContextCompat
                                .getColorStateList(
                                        refBttNavConteudos.getContext(),
                                        colorStateList));
        refBttNavConteudos
                .setItemTextColor(
                        ContextCompat
                                .getColorStateList(
                                        refBttNavConteudos.getContext(),
                                        colorStateList));
    }
}