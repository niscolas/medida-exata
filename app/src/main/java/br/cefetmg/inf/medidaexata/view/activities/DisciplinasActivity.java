package br.cefetmg.inf.medidaexata.view.activities;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import br.cefetmg.inf.medidaexata.model.QuestaoFechada;
import br.cefetmg.inf.medidaexata.view.activities.fragments.QuestoesFragment;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DisciplinasActivity extends AppCompatActivity
        implements QuestoesFragment.IProgressBarShower, QuestoesFragment.OnQuestaoInteractionListener {

    private static final int FRAGMENT_CONTAINER_ID = R.id.fragment_container;
    private static final String TAG  = DisciplinasActivity.class.getSimpleName();

    private int ultimoItemClicado = 0;

    @BindView(R.id.bttnav_conteudos)
    BottomNavigationView refBttNavConteudos;
    @BindView(R.id.ll_disciplinas)
    LinearLayout refLlDisciplinas;
    @BindView(R.id.pb_questoes)
    ProgressBar refPbQuestoes;
    @BindView(R.id.tv_toque)
    TextView refTvToque;
    @BindView(R.id.tb_menu_disciplinas)
    Toolbar refTbMenu;

    @BindColor(R.color.colorPrimaryLight)
    ColorStateList corPrimariaClara;
    @BindColor(R.color.colorPrimary)
    int corPrimaria;
    @BindColor(R.color.selector_bttnav_colors_mat)
    ColorStateList selectorItemBttNavMat;
    @BindColor(R.color.colorSecondary)
    int corSecundaria;
    @BindColor(R.color.selector_bttnav_colors_cie)
    ColorStateList selectorItemBttNavCie;

    @BindString(R.string.matematica)
    String discMat;
    @BindString(R.string.ciencias)
    String discCie;

    private final BottomNavigationView.OnNavigationItemSelectedListener
            bttNavConteudosClickListener =
            new BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int itemClicado = menuItem.getItemId();
                    if(itemClicado != ultimoItemClicado) {
                        ultimoItemClicado = itemClicado;

                        switch (itemClicado) {
                            case R.id.i_btt_nav_matematica:
                                mostraProgressBar();

                                atualizaCores(
                                        corPrimaria,
                                        discMat,
                                        selectorItemBttNavMat);

                                final Fragment qfm = QuestoesFragment.newInstance();
                                iniciaFragment(qfm);
                                break;

                            case R.id.i_btt_nav_ciencias:
                                mostraProgressBar();

                                atualizaCores(
                                        corSecundaria,
                                        discCie,
                                        selectorItemBttNavCie);

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
                    }
                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas);
        ButterKnife.bind(this);

        refBttNavConteudos
                .setItemIconTintList(corPrimariaClara);
        refBttNavConteudos
                .setItemTextColor(corPrimariaClara);
        refBttNavConteudos.setOnNavigationItemSelectedListener(bttNavConteudosClickListener);
    }

    private void iniciaFragment(Fragment frg) {
        refTvToque.setVisibility(View.GONE);
        refLlDisciplinas.setVisibility(View.GONE);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(FRAGMENT_CONTAINER_ID, frg)
                .commit();
    }

    private void atualizaCores(int cor, String titulo, ColorStateList colorStateList) {
        refPbQuestoes.getIndeterminateDrawable().setColorFilter(cor,PorterDuff.Mode.SRC_IN );

        refTbMenu.setTitle(titulo);
        refTbMenu.setBackgroundColor(cor);

        refBttNavConteudos.setBackgroundColor(cor);
        refBttNavConteudos.setItemIconTintList(colorStateList);
        refBttNavConteudos.setItemTextColor(colorStateList);
    }

    private void mostraProgressBar() {
        refPbQuestoes.setVisibility(View.VISIBLE);
    }

    @Override
    public void escondeProgressBar() {
        refPbQuestoes.setVisibility(View.GONE);
    }

    @Override
    public void onVerQuestaoInteraction(QuestaoFechada qst) { }

    @Override
    public void onVerMateriaInteraction(QuestaoFechada qst) { }
}