package br.cefetmg.inf.medidaexata.view.activities;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

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
import br.cefetmg.inf.medidaexata.view.fragments.VerQuestaoFragment;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements QuestoesFragment.OnQuestaoInteractionListener,
        ConteudoAdapter.IAlteraProgressBar, ConteudosFragment.OnConteudoInteractionListener ,
        VerQuestaoFragment.OnAlternativaSelecionadaListener{

    /*
     * Declaração de campos static final
     */
    private static final int FRAGMENT_CONTAINER_ID = R.id.fragment_container;
    private static final String TAG_CONTEUDOS_FRAGMENT = "conteudos_fragment";
    private static final String TAG_QUESTOES_FRAGMENT = "questoes_fragment";
    private static final String TAG_VER_QUESTAO_FRAGMENT = "ver_questao_fragment";
    private static final String TAG  = MainActivity.class.getSimpleName();

    private int ultimoItemClicado = 0;

    /*
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

    //Outras cores
    @BindColor(R.color.vermelho)
    int corVermelho;
    @BindColor(R.color.branco)
    int corBranco;

    // Binding de Strings
    @BindString(R.string.disc_matematica)
    String discMat;
    @BindString(R.string.disc_ciencias)
    String discCie;
    @BindString(R.string.matematica)
    String matematica;
    @BindString(R.string.ciencias)
    String ciencias;
    /*
      Termina área de Binding
     */

    /*
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
                                        MainActivity.this,
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
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(refTbMenu);
        escondeHomeButton();
        escondeProgressBar();

        refBttNavConteudos
                .setItemIconTintList(ColorStateList.valueOf(corPrimariaClara));
        refBttNavConteudos
                .setItemTextColor(ColorStateList.valueOf(corPrimariaClara));
        refBttNavConteudos.setOnNavigationItemSelectedListener(bttNavConteudosClickListener);
    }

    private void iniciaFragment(Fragment frg, final String TAG_FRAGMENT) {
        someConteudoActivity();

        FragmentManager frgManager = getSupportFragmentManager();
        FragmentTransaction transaction = frgManager.beginTransaction();
        transaction.replace(FRAGMENT_CONTAINER_ID, frg, TAG_FRAGMENT);
        transaction.addToBackStack(TAG_FRAGMENT);
        transaction.commit();
    }

    private void someConteudoActivity() {
        // Seta o conteúdo do meio da página como GONE
        refTvToque.setVisibility(View.GONE);
        refLlDisciplinas.setVisibility(View.GONE);
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
                // Pega o Fragment visível
                Fragment frg = getSupportFragmentManager().findFragmentById(FRAGMENT_CONTAINER_ID);
                if(frg != null && frg.isVisible()) {
                    if(frg instanceof QuestoesFragment) {
                        mostraProgressBar();
                        onBackPressed();
                        escondeHomeButton();
                        return true;
                    } else if (frg instanceof VerQuestaoFragment) {
                        mostraProgressBar();
                        onBackPressed();
                        refBttNavConteudos.setVisibility(View.VISIBLE);
                        return true;
                    }
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

    /*
     * Métodos implementados de 'QuestoesFragment'
     */
    @Override
    public void onVerQuestaoInteraction(QuestaoFechada qst, int[] cores) {
        // Transforma o List<String> de alternativas num Array
        String[] alternativas = qst.getAlternativas().toArray(new String[0]);

        // Obtém uma instância de 'VerQuestaoFragment'
        final Fragment frgVerQst = VerQuestaoFragment.newInstance(
                qst.getEnunciado(),
                alternativas,
                cores,
                qst.getResposta());

        // Faz a barra inferior desaparecer
        refBttNavConteudos.setVisibility(View.GONE);

        Log.d(TAG, "chegou aki");
        // Inicia o fragment
        iniciaFragment(frgVerQst, TAG_VER_QUESTAO_FRAGMENT);
    }
    @Override
    public void onVerMateriaInteraction(QuestaoFechada qst, int[] cores) {}

    /*
     * Métodos implementados de 'ConteudosFragment'
     */
    @Override
    public void onConteudoInteraction(Conteudo conteudo, int[] coresTexto) {
        mostraProgressBar();
        mostraHomeButton();

        final Fragment frgQsts = QuestoesFragment.newInstance(coresTexto, conteudo.getNome());
        iniciaFragment(frgQsts, TAG_QUESTOES_FRAGMENT);
    }

    /*
     * Métodos implementados de 'VerQuestaoFragment'
     */
    @Override
    public void onAlternativaSelecionada(MaterialButton[] refMtrlBts,
                                         int altSelecionada, int resposta) {
        boolean altCerta = altSelecionada == resposta;
        // Verifica se o usuário tocou na alternativa certa e troca a cor do botão
        if(altCerta) {
            refMtrlBts[altSelecionada]
                    .setBackgroundTintList(ColorStateList.valueOf(corSecundariaClara));
        } else {
            refMtrlBts[altSelecionada]
                    .setBackgroundTintList(ColorStateList.valueOf(corVermelho));
        }

        // Troca a cor do texto do botão para branco para melhorar a visualização
        String textoAlt = String.valueOf(refMtrlBts[altSelecionada].getText());
        refMtrlBts[altSelecionada].setText(textoAlt);
        refMtrlBts[altSelecionada].setTextColor(corBranco);

        // Impede que o usuário clique em outros botões e ative suas funções
        for(MaterialButton mtBt : refMtrlBts) {
            mtBt.setOnClickListener(null);
            mtBt.setClickable(false);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Redireciona para a página de pontuação da questão
            }
        }, 1000);
    }
}