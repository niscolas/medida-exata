package br.cefetmg.inf.medidaexata.view.activities;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import br.cefetmg.inf.medidaexata.model.Conteudo;
import br.cefetmg.inf.medidaexata.model.QuestaoFechada;
import br.cefetmg.inf.medidaexata.view.fragments.ConteudosFragment;
import br.cefetmg.inf.medidaexata.view.fragments.QuestoesFragment;
import br.cefetmg.inf.medidaexata.view.adapters.ConteudoAdapter;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DisciplinasActivity extends AppCompatActivity
        implements QuestoesFragment.OnQuestaoInteractionListener,
        ConteudoAdapter.IAlteraProgressBar, ConteudosFragment.OnConteudoInteractionListener {

    /**
     * Declaração de campos static final
     */
    private static final int FRAGMENT_CONTAINER_ID = R.id.fragment_container;
    private static final String TAG_CONTEUDOS_FRAGMENT = "conteudos_fragment";
    private static final String TAG_QUESTOES_FRAGMENT = "questoes_fragment";
    private static final String TAG  = DisciplinasActivity.class.getSimpleName();

    private int ultimoItemClicado = 0;

    /**
     * Área de Binding de Resources e Views
     */
    // Binding de Views
    @BindView(R.id.rl_rootv_disciplinas_act)
    RelativeLayout refRlDiscAct;
    @BindView(R.id.bttnav_conteudos)
    BottomNavigationView refBttNavConteudos;
    @BindView(R.id.ll_disciplinas)
    LinearLayout refLlDisciplinas;
    @BindView(R.id.pb_questoes)
    ProgressBar refPbQuestoes;
    @BindView(R.id.tv_toque)
    TextView refTvToque;
    @BindView(R.id.tb_menu_disciplinas_act)
    Toolbar refTbMenu;

    // Binding de Cores
    // Cores Primárias
    @BindColor(R.color.colorPrimaryLight)
    int corPrimariaClara;
    @BindColor(R.color.colorPrimary)
    int corPrimaria;
    @BindColor(R.color.colorPrimaryDark)
    int corPrimariaEscura;
    // Cores Primárias - ColorStateLists e Selectors
    @BindColor(R.color.colorPrimaryLight)
    ColorStateList corStateListPrimariaClara;
    @BindColor(R.color.selector_bttnav_colors_mat)
    ColorStateList selectorItemBttNavMat;
    // Cores Secundárias
    @BindColor(R.color.colorSecondaryLight)
    int corSecundariaClara;
    @BindColor(R.color.colorSecondary)
    int corSecundaria;
    @BindColor(R.color.colorSecondaryDark)
    int corSecundariaEscura;
    // Cores Secundárias - ColorStateLists e Selectors
    @BindColor(R.color.selector_bttnav_colors_cie)
    ColorStateList selectorItemBttNavCie;

    // Binding de Strings
    @BindString(R.string.disc_matematica)
    String discMat;
    @BindString(R.string.disc_ciencias)
    String discCie;
    @BindString(R.string.matematica)
    String matematica;
    @BindString(R.string.ciencias)
    String ciencias;
    /**
     * Termina área de Binding
     */

    /**
     * Variável responsável por receber o toque de um dos botões da Bottom Nav e tratá-lo
     */
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
                                escondeHomeButton();
                                mostraProgressBar();

                                atualizaUi(
                                        new int[]{corPrimariaClara,
                                                corPrimaria,
                                                corPrimariaEscura},
                                        discMat,
                                        selectorItemBttNavMat);

                                final Fragment frgMat =
                                        ConteudosFragment
                                                .newInstance(
                                                        new int[]{corPrimariaClara,
                                                                corPrimaria,
                                                                corPrimariaEscura},
                                                        matematica);
                                iniciaFragment(frgMat, TAG_CONTEUDOS_FRAGMENT);
                                break;

                            case R.id.i_btt_nav_ciencias:
                                escondeHomeButton();
                                mostraProgressBar();

                                atualizaUi(new int[]{corSecundariaClara,
                                                corSecundaria,
                                                corSecundariaEscura},
                                        discCie,
                                        selectorItemBttNavCie);

                                final Fragment frgCie =
                                        ConteudosFragment
                                                .newInstance(
                                                        new int[]{corSecundariaClara,
                                                                corSecundaria,
                                                                corSecundariaEscura},
                                                        ciencias);
                                iniciaFragment(frgCie, TAG_CONTEUDOS_FRAGMENT);
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
        setSupportActionBar(refTbMenu);
        escondeHomeButton();
        escondeProgressBar();

        refBttNavConteudos
                .setItemIconTintList(corStateListPrimariaClara);
        refBttNavConteudos
                .setItemTextColor(corStateListPrimariaClara);
        refBttNavConteudos.setOnNavigationItemSelectedListener(bttNavConteudosClickListener);
    }

    private void iniciaFragment(Fragment frg, final String TAG_FRAGMENT) {
        refTvToque.setVisibility(View.GONE);
        refLlDisciplinas.setVisibility(View.GONE);

        FragmentManager frgManager = getSupportFragmentManager();
        FragmentTransaction transaction = frgManager.beginTransaction();
        transaction.replace(FRAGMENT_CONTAINER_ID, frg, TAG_FRAGMENT);
        transaction.addToBackStack(TAG_FRAGMENT);
        transaction.commit();
    }

    private void atualizaUi(int[] cores, String titulo, ColorStateList colorStateList) {
        refRlDiscAct.setBackgroundColor(cores[0]);

        refPbQuestoes.getIndeterminateDrawable().setColorFilter(cores[1], PorterDuff.Mode.SRC_IN );

        refTbMenu.setTitle(titulo);
        refTbMenu.setBackgroundColor(cores[1]);

        refBttNavConteudos.setBackgroundColor(cores[1]);
        refBttNavConteudos.setItemIconTintList(colorStateList);
        refBttNavConteudos.setItemTextColor(colorStateList);
    }

    private void mostraHomeButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void escondeHomeButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Fragment frg = getSupportFragmentManager().findFragmentByTag(TAG_QUESTOES_FRAGMENT);
                if(frg != null && frg.isVisible()) {
                    mostraProgressBar();
                    onBackPressed();
                    escondeHomeButton();
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void mostraProgressBar() {
        refPbQuestoes.setVisibility(View.VISIBLE);
    }

    @Override
    public void escondeProgressBar() {
        refPbQuestoes.setVisibility(View.GONE);
    }

    @Override
    public void onVerQuestaoInteraction(QuestaoFechada qst) {}

    @Override
    public void onVerMateriaInteraction(QuestaoFechada qst) {}

    @Override
    public void onConteudoInteraction(Conteudo conteudo, int[] coresTexto) {
        mostraProgressBar();
        mostraHomeButton();

        final Fragment frgQsts = QuestoesFragment.newInstance(coresTexto, conteudo.getNome());
        iniciaFragment(frgQsts, TAG_QUESTOES_FRAGMENT);
    }
}