package br.cefetmg.inf.medidaexata.view.activities;

import android.content.res.ColorStateList;
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

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import br.cefetmg.inf.medidaexata.model.Conteudo;
import br.cefetmg.inf.medidaexata.model.CoresUI;
import br.cefetmg.inf.medidaexata.model.QuestaoFechada;
import br.cefetmg.inf.medidaexata.view.fragments.ConteudosFragment;
import br.cefetmg.inf.medidaexata.view.fragments.QuestoesFragment;
import br.cefetmg.inf.medidaexata.view.adapters.ConteudoAdapter;
import br.cefetmg.inf.medidaexata.view.fragments.VerQuestaoFragment;
import br.cefetmg.inf.medidaexata.viewmodel.MedidaExataViewModel;
import br.cefetmg.inf.util.StringUtils;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements QuestoesFragment.OnQuestaoInteractionListener,
        ConteudoAdapter.IAlteraProgressBar, ConteudosFragment.OnConteudoInteractionListener ,
        VerQuestaoFragment.OnAlternativaSelecionadaListener{


    // Declaração de campos static final
    //
    // Referência a View que receberá os Fragments
    private static final int FRAGMENT_CONTAINER_ID = R.id.fragment_container;
    // Tags referentes aos Fragment do app
    private static final String TAG_CONTEUDOS_FRAGMENT = "conteudos_fragment";
    private static final String TAG_QUESTOES_FRAGMENT = "questoes_fragment";
    private static final String TAG_VER_QUESTAO_FRAGMENT = "ver_questao_fragment";
    // TAG usada para Logging
    private static final String TAG  = MainActivity.class.getSimpleName();
    //
    // Declaração de campos static final

    // Indica o último item clicado, para impossibilitar que seja clicado de novo
    private int ultimoItemClicado = 0;
    // Declara o ViewModel do aplicativo
    private MedidaExataViewModel vm;

    // Área de Binding de Resources e Views
    //

    // Binding de Views
    //
    @BindView(R.id.rl_rootv_disciplinas_act) RelativeLayout refRlDiscAct;
    @BindView(R.id.bttnav_conteudos) BottomNavigationView refBttNavConteudos;
    @BindView(R.id.ll_disciplinas) LinearLayout refLlDisciplinas;
    @BindView(R.id.pb_questoes)ProgressBar refPbQuestoes;
    @BindView(R.id.tv_toque) TextView refTvToque;
    @BindView(R.id.tb_menu_disciplinas_act) Toolbar refTbMenu;
    //
    // Binding de Views

    // Binding de Cores
    //
    // Cores Primárias
    @BindColor(R.color.colorPrimaryLight) int corPrimClara;
    @BindColor(R.color.colorPrimary) int corPrim;
    @BindColor(R.color.colorPrimaryDark) int corPrimEscura;
    // Cores Primárias - ColorStateLists e Selectors
    @BindColor(R.color.selector_bttnav_colors_mat) ColorStateList selectorItemBttNavMat;
    // Cores Secundárias
    @BindColor(R.color.colorSecondaryLight) int corSecClara;
    @BindColor(R.color.colorSecondary) int corSec;
    @BindColor(R.color.colorSecondaryDark) int corSecEscura;
    // Cores Secundárias - ColorStateLists e Selectors
    @BindColor(R.color.selector_bttnav_colors_cie) ColorStateList selectorItemBttNavCie;
    //Outras cores
    @BindColor(R.color.vermelho) int corAltErrada;
    @BindColor(R.color.branco) int corBranco;
    //
    // Binding de Cores

    // Binding de Strings
    //
    @BindString(R.string.disc_matematica) String discMat;
    @BindString(R.string.disc_ciencias) String discCie;
    //
    // Binding de Strings

    //
    // Termina área de Binding

    // Variável responsável por receber o toque de um dos botões da Bottom Nav e tratá-lo
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

                                // Atualiza UI
                                // Atualiza título
                                vm.setNomeDisciplina(discMat);
                                // Atualiza a UI para as cores Primárias
                                vm.getCoresUI().setTipoCoresAtuais(CoresUI.CORES_PRIMARIAS);
                                setBttNavColorStateList(selectorItemBttNavMat);

                                final Fragment frgMat = ConteudosFragment.newInstance();
                                iniciaFragment(frgMat, TAG_CONTEUDOS_FRAGMENT);
                                break;

                            case R.id.i_btt_nav_ciencias:
                                escondeHomeButton();
                                mostraProgressBar();

                                // Atualiza UI
                                // Atualiza título
                                vm.setNomeDisciplina(discCie);
                                // Atualiza a UI para as cores Primárias
                                vm.getCoresUI().setTipoCoresAtuais(CoresUI.CORES_SECUNDARIAS);
                                setBttNavColorStateList(selectorItemBttNavCie);

                                final Fragment frgCie = ConteudosFragment.newInstance();
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

        // Vincula as Views que estão no topo do arquivo com a Activity
        ButterKnife.bind(this);

        // Instancia o ViewModel
        vm = ViewModelProviders
                .of(this)
                .get(MedidaExataViewModel.class);
        vm.initViewModel(getCorMap());

        // Seta os Observer do LiveData
        vm.getCoresUI().getTipoCoresAtuais().observe(this, this::atualizaCoresUi);
        vm.getNomeDisciplina().observe(this, this::onNomeDisciplinaChange);

        // Seta a Toolbar como a SupportActionBar
        setSupportActionBar(refTbMenu);
        // Esconde o botão de Home e a ProgressBar
        // (quando a Activity é criada esses itens não são necessários)
        escondeHomeButton();
        escondeProgressBar();

        vm.getCoresUI().setTipoCoresAtuais(CoresUI.CORES_PRIMARIAS);

        setBttNavColorStateList(ColorStateList
                .valueOf(vm.getCoresUI().getCoresAtuais().get(CoresUI.COR_CLARA)));
        refBttNavConteudos.setOnNavigationItemSelectedListener(bttNavConteudosClickListener);
    }

    // Método para preencher um HashMap com cores
    private Map<String, Map<String, Integer>> getCorMap() {
        Map<String, Map<String, Integer>> corMapCompleto = new HashMap<>();
        Map<String, Integer> corMapParte = new HashMap<>();

        // Coloca cores Primárias no corMapParte
        corMapParte.put(CoresUI.COR_CLARA, corPrimClara);
        corMapParte.put(CoresUI.COR_PADRAO, corPrim);
        corMapParte.put(CoresUI.COR_ESCURA, corPrimEscura);
        // Coloca no corMapCompleto apenas as cores Primárias
        corMapCompleto.put(CoresUI.CORES_PRIMARIAS, corMapParte);

        // Coloca cores Secundárias no corMapParte
        corMapParte.put(CoresUI.COR_CLARA, corSecClara);
        corMapParte.put(CoresUI.COR_PADRAO, corSec);
        corMapParte.put(CoresUI.COR_ESCURA, corSecEscura);
        // Coloca no corMapCompleto apenas as cores Secundárias
        corMapCompleto.put(CoresUI.CORES_SECUNDARIAS, corMapParte);

        return corMapCompleto;
    }

    // Métodos usados para atualizar a UI
    //
    private void setBttNavColorStateList(ColorStateList csl) {
        refBttNavConteudos.setItemIconTintList(csl);
        refBttNavConteudos.setItemTextColor(csl);
    }

    private void onNomeDisciplinaChange(String s) {
        refTbMenu.setTitle(s);
        // Seta 'disciplina' igual à 'nomeDisciplina', no entanto,
        // sem acentos e em Lower Case.
        // Utilizado para as consultas no FireBase
        vm.setDisciplina(StringUtils.tiraAcentos(s.toLowerCase()));
    }

    // Métodos de Observer
    //
    private void atualizaCoresUi(String constCoresUi) {
        // Obtém cores atuais da Ui
        Map<String, Integer> cores = vm.getCoresUI().getCoresAtuais();

        refRlDiscAct.setBackgroundColor(cores.get(CoresUI.COR_CLARA));

        refPbQuestoes.getIndeterminateDrawable().setColorFilter(corPadrao, PorterDuff.Mode.SRC_IN );

        refTbMenu.setBackgroundColor(corPadrao);

        refBttNavConteudos.setBackgroundColor(corPadrao);
    }


    //
    // Métodos usados para atualizar a UI

    /**
     * @param frg fragment a ser ativado na UI
     * @param TAG_FRAGMENT Tag referente ao 1° argumento, para ser colocado na BackStack,
     *                     para poder ser identificado posteriormente
     */
    private void iniciaFragment(Fragment frg, final String TAG_FRAGMENT) {
        escondeConteudoCentral();

        FragmentManager frgManager = getSupportFragmentManager();
        FragmentTransaction transaction = frgManager.beginTransaction();
        transaction.replace(FRAGMENT_CONTAINER_ID, frg, TAG_FRAGMENT);
        transaction.addToBackStack(TAG_FRAGMENT);
        transaction.commit();
    }

    /**
     * Método chamado quando algum botão da ActionBar é clicado
     * @param item o item clicado
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Pega o Fragment visível
                Fragment frg = getSupportFragmentManager().findFragmentById(FRAGMENT_CONTAINER_ID);
                if(frg != null && frg.isVisible()) {
                    if(frg instanceof QuestoesFragment) {
                        // Seta o conteúdo como nulo, pois não há conteúdo selecionado
                        vm.setConteudo(null);

                        mostraProgressBar();
                        onBackPressed();
                        escondeHomeButton();
                        return true;
                    } else if (frg instanceof VerQuestaoFragment) {
                        // Seta a questão como nula, pois não há questão selecionada
                        vm.setQst(null);

                        mostraProgressBar();
                        onBackPressed();
                        refBttNavConteudos.setVisibility(View.VISIBLE);
                        return true;
                    }
                }
        }
        return super.onOptionsItemSelected(item);
    }

    // Métodos que alteram Visibility das Views
    //
    private void escondeConteudoCentral() {
        // Seta o conteúdo do meio da página como GONE
        refTvToque.setVisibility(View.GONE);
        refLlDisciplinas.setVisibility(View.GONE);
    }

    // Altera Visibility do Home Button
    private void mostraHomeButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    private void escondeHomeButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void mostraProgressBar() {
        refPbQuestoes.setVisibility(View.VISIBLE);
    }
    @Override
    public void escondeProgressBar() {
        refPbQuestoes.setVisibility(View.GONE);
    }
    //
    // Métodos que alteram Visibility das Views

    // Métodos implementados de 'QuestoesFragment'
    //
    @Override
    public void onVerQuestaoInteraction(QuestaoFechada qst) {
        // Seta a questão ativa
        vm.setQst(qst);

        // Obtém uma instância de 'VerQuestaoFragment'
        final Fragment frgVerQst = VerQuestaoFragment.newInstance();

        // Faz a barra inferior desaparecer
        refBttNavConteudos.setVisibility(View.GONE);

        Log.d(TAG, "chegou aki");
        // Inicia o fragment
        iniciaFragment(frgVerQst, TAG_VER_QUESTAO_FRAGMENT);
    }
    @Override
    public void onVerMateriaInteraction(QuestaoFechada qst) {

    }
    //
    // Métodos implementados de 'QuestoesFragment'

    // Métodos implementados de 'ConteudosFragment'
    //
    @Override
    public void onConteudoInteraction(Conteudo conteudo) {
        mostraProgressBar();
        mostraHomeButton();

        vm.setConteudo(conteudo);

        final Fragment frgQsts = QuestoesFragment.newInstance();
        iniciaFragment(frgQsts, TAG_QUESTOES_FRAGMENT);
    }
    //
    // Métodos implementados de 'ConteudosFragment'

    // Métodos implementados de 'VerQuestaoFragment'
    //
    @Override
    public void onAlternativaSelecionada(MaterialButton[] refMtrlBts, int altSelecionada) {
        boolean altCerta = altSelecionada == vm.getQst().getResposta();
        // Verifica se o usuário tocou na alternativa certa e troca a cor do botão
        if(altCerta) {
            refMtrlBts[altSelecionada]
                    .setBackgroundTintList(ColorStateList.valueOf(corAltCerta));
        } else {
            refMtrlBts[altSelecionada]
                    .setBackgroundTintList(ColorStateList.valueOf(corAltErrada));
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
    //
    //  Métodos implementados de 'VerQuestaoFragment'
}