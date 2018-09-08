package br.cefetmg.inf.medidaexata.view.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import br.cefetmg.inf.medidaexata.model.Conteudo;
import br.cefetmg.inf.medidaexata.model.CoresUI;
import br.cefetmg.inf.medidaexata.model.Materia;
import br.cefetmg.inf.medidaexata.model.QuestaoFechada;
import br.cefetmg.inf.medidaexata.view.IAlteraProgressBar;
import br.cefetmg.inf.medidaexata.view.dialogs.NomeUsuarioDialog;
import br.cefetmg.inf.medidaexata.view.fragments.ConquistasFragment;
import br.cefetmg.inf.medidaexata.view.fragments.ConteudosFragment;
import br.cefetmg.inf.medidaexata.view.fragments.MateriasFragment;
import br.cefetmg.inf.medidaexata.view.fragments.PontuacaoFragment;
import br.cefetmg.inf.medidaexata.view.fragments.QuestoesFragment;
import br.cefetmg.inf.medidaexata.view.fragments.QuestoesOuMateriasFragment;
import br.cefetmg.inf.medidaexata.view.fragments.VerMateriaFragment;
import br.cefetmg.inf.medidaexata.view.fragments.VerQuestaoFragment;
import br.cefetmg.inf.medidaexata.viewmodel.MedidaExataViewModel;
import br.cefetmg.inf.util.MaterialButtonUtils;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity
        extends
        AppCompatActivity
        implements
        QuestoesOuMateriasFragment.OnQuestoesOuMateriasFragmentInteractionListener,
        MateriasFragment.OnMateriasFragmentInteractionListener,
        ConteudosFragment.OnConteudoInteractionListener,
        QuestoesFragment.OnQuestaoInteractionListener,
        VerQuestaoFragment.OnAlternativaSelecionadaListener,
        PontuacaoFragment.OnPontuacaoFragmentInteractionListener,
        VerMateriaFragment.OnVerMateriaFragmentInteractionListener,
        IAlteraProgressBar {


    //// Declaração de campos static final
    //

    // Referência a View que receberá os Fragments
    private static final int FRAGMENT_CONTAINER_ID = R.id.frg_container;

    // Tags referentes aos Fragment do app
    private static final String TAG_QUESTOES_OU_MATERIAS_FRAGMENT = "questoes_ou_materias_fragment";
    private static final String TAG_MATERIAS_FRAGMENT = "materias_fragment";
    private static final String TAG_CONTEUDOS_FRAGMENT = "conteudos_fragment";
    private static final String TAG_QUESTOES_FRAGMENT = "questoes_fragment";
    private static final String TAG_VER_QUESTAO_FRAGMENT = "ver_questao_fragment";
    private static final String TAG_VER_MATERIA_FRAGMENT = "ver_materia_fragment";
    private static final String TAG_PONTUACAO_FRAGMENT = "pontuacao_fragment";
    private static final String TAG_CONQUISTAS_FRAGMENT = "conquistas_fragment";
    private static final String TAG_NOME_USUARIO_DIALOG = "nome_usuario_dialog";

    // TAG usada para Logging
    private static final String TAG  = MainActivity.class.getSimpleName();

    //
    //// Declaração de campos static final

    // Indica que o app acabou de ser iniciado
    private boolean primeiraVezIniciado = true;

    // Indica o último item clicado, para impossibilitar que seja clicado de novo
    private int ultimoItemClicado = 0;

    // Declara o ViewModel do aplicativo
    private MedidaExataViewModel vm;

    // O Toast a ser usado durante toda a Activity
    private Toast toast;

    //// Área de Binding de Resources e Views
    //

    //// Binding de Views
    //

    @BindView(R.id.bttnav_app) BottomNavigationView refBttNavConteudos;
    @BindView(R.id.rl_rootv_disciplinas_act) ConstraintLayout refRootViewMainAct;
    @BindView(R.id.pb_carregando)ProgressBar refPbQuestoes;
    @BindView(R.id.tv_toque) TextView refTvToque;
    @BindView(R.id.tb_menu_disciplinas_act) Toolbar refTbMenu;
    @BindView(R.id.dw_sombra_btt_nav) View refSombraBttNav;
    @BindView(R.id.dw_sombra_tb) View refSombraTb;

    //
    //// Binding de Views

    //// Binding de Cores
    //

    // Cores Primárias
    @BindColor(R.color.azul_claro) int corPrimClara;
    @BindColor(R.color.azul) int corPrim;
    @BindColor(R.color.azul_escuro) int corPrimEscura;

    // Cores Primárias - ColorStateLists e Selectors
    @BindColor(R.color.selector_bttnav_colors_mat) ColorStateList selectorItemBttNavMat;

    // Cores Secundárias
    @BindColor(R.color.verde_claro) int corSecClara;
    @BindColor(R.color.verde) int corSec;
    @BindColor(R.color.verde_escuro) int corSecEscura;

    // Cores Secundárias - ColorStateLists e Selectors
    @BindColor(R.color.selector_bttnav_colors_cie) ColorStateList selectorItemBttNavCie;

    @BindColor(R.color.selector_bttnav_colors_conquistas) ColorStateList selectorItemBttNavConquistas;

    //Outras cores
    @BindColor(R.color.vermelho) int corAltErrada;
    @BindColor(R.color.branco) int corBranco;
    //
    //// Binding de Cores

    //// Binding de Strings
    //

    @BindString(R.string.disc_matematica) String discMat;
    @BindString(R.string.disc_ciencias) String discCie;
    @BindString(R.string.conquistas) String conquistas;

    //
    //// Binding de Strings

    //
    //// Termina área de Binding

    // Variável responsável por receber o toque de um dos botões da Bottom Nav e tratá-lo
    private final BottomNavigationView.OnNavigationItemSelectedListener
            bttNavConteudosClickListener =
            new BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int itemClicado = menuItem.getItemId();
                    if(itemClicado != ultimoItemClicado) {
                        ultimoItemClicado = itemClicado;

                        escondeHomeButton();
                        escondeConteudoCentral();
                        mostraProgressBar();
                        switch (itemClicado) {
                            case R.id.i_btt_nav_matematica:
                                setTheme(R.style.AppTheme_Azul);
                                // Atualiza UI
                                // Atualiza título
                                vm.setTituloAtivo(discMat);
                                // Atualiza a UI para as cores Primárias
                                vm.getCoresUI().setTipoCoresAtuais(CoresUI.CORES_PRIMARIAS);
                                setBttNavColorStateList(selectorItemBttNavMat);

                                iniciaFragment(TAG_QUESTOES_OU_MATERIAS_FRAGMENT, false);
                                break;

                            case R.id.i_btt_nav_ciencias:
                                setTheme(R.style.AppTheme_Verde);
                                // Atualiza UI
                                // Atualiza título
                                vm.setTituloAtivo(discCie);
                                // Atualiza a UI para as cores Primárias
                                vm.getCoresUI().setTipoCoresAtuais(CoresUI.CORES_SECUNDARIAS);
                                setBttNavColorStateList(selectorItemBttNavCie);

                                iniciaFragment(TAG_QUESTOES_OU_MATERIAS_FRAGMENT, false);
                                break;

                            case R.id.i_btt_nav_perfil:
                                setTheme(R.style.AppTheme_AzulClaro);
                                // Atualiza UI
                                // Atualiza título
                                vm.setTituloAtivo(conquistas);
                                // Atualiza a UI
                                setBttNavColorStateList(selectorItemBttNavConquistas);

                                refRootViewMainAct.setBackgroundColor(corBranco);

                                refBttNavConteudos.setBackgroundColor(corBranco);

                                iniciaFragment(TAG_CONQUISTAS_FRAGMENT, false);
                                break;
                        }
                    }
                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Azul);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Vincula as Views que estão no topo do arquivo com a Activity
        ButterKnife.bind(this);

        // Verifica se já foi pedido o nome do usuário, caso não tenha sido, abre o Dialog que o pede
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        boolean nomeDoUsuarioJaFoiSetado = sp.getBoolean(ConquistasFragment.NOME_USUARIO_SETADO, false);
        if(!nomeDoUsuarioJaFoiSetado) {
            pedeNomeUsuario();
        }

        // Instancia o ViewModel
        vm = ViewModelProviders
                .of(this)
                .get(MedidaExataViewModel.class);
        vm.initViewModel(getCorMap());

        // Seta os Observer do LiveData
        vm.getCoresUI().getTipoCoresAtuais().observe(this, this::atualizaCoresUi);
        vm.getTituloAtivo().observe(this, this::onNomeDisciplinaChange);
        vm.getQstAtiva().observe(this, this::onQstFechadaChange);

        // Seta a Toolbar como a SupportActionBar
        setSupportActionBar(refTbMenu);

        setConteudoInicialUi(false);
        refBttNavConteudos.setOnNavigationItemSelectedListener(bttNavConteudosClickListener);
    }

    private void pedeNomeUsuario() {
        NomeUsuarioDialog dialog = NomeUsuarioDialog.newInstance(this);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), TAG_NOME_USUARIO_DIALOG);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // Pega o Fragment visível
        Fragment f = getSupportFragmentManager().findFragmentById(FRAGMENT_CONTAINER_ID);

        if(f != null && f.isVisible()) {
            if (f instanceof QuestoesOuMateriasFragment) {
                super.onBackPressed();
                setConteudoInicialUi(false);
                return;
            } else if (f instanceof ConteudosFragment) {
                mostraProgressBar();
                escondeHomeButton();
            } else if (f instanceof QuestoesFragment) {
                vm.setConteudoAtivo(null);
                mostraProgressBar();
            } else if (f instanceof VerQuestaoFragment) {
                vm.setQstAtiva(null);
                mostraProgressBar();
                mostraBttNav();
            } else if (f instanceof PontuacaoFragment) {
                vm.setQstAtiva(null);
                mostraProgressBar();
                onVoltarParaOMenuSelecionado();
                return;
            }else if (f instanceof  MateriasFragment) {
                setConteudoInicialUi(true);
                return;
            } else if (f instanceof VerMateriaFragment) {
                setConteudoInicialUi(true);
                return;
            }
        }
        super.onBackPressed();
    }

    /**
     * Preenche um HashMap com as cores do app para serem usadas no ViewModel
     * @return o HasMap completo
     */
    private Map<String, Map<String, Integer>> getCorMap() {
        // Map que armazenará todas as cores
        Map<String, Map<String, Integer>> corMapCompleto = new HashMap<>();
        // Map que armazenará parte das cores, será limpo e receberá o resto das cores
        Map<String, Integer> corMapParte = new HashMap<>();

        // Coloca cores Primárias no corMapParte
        corMapParte.put(CoresUI.COR_CLARA, corPrimClara);
        corMapParte.put(CoresUI.COR_PADRAO, corPrim);
        corMapParte.put(CoresUI.COR_ESCURA, corPrimEscura);
        corMapParte.put(CoresUI.TEMA, R.style.AppTheme_Azul);
        // Coloca no corMapCompleto apenas as cores Primárias
        corMapCompleto.put(CoresUI.CORES_PRIMARIAS, corMapParte);

        // Limpa o Map para colocar as cores Secundárias
        corMapParte = new HashMap<>();
        Log.d(TAG, "corMapParte.size() = " + corMapParte.size());
        // Coloca cores Secundárias no corMapParte
        corMapParte.put(CoresUI.COR_CLARA, corSecClara);
        corMapParte.put(CoresUI.COR_PADRAO, corSec);
        corMapParte.put(CoresUI.COR_ESCURA, corSecEscura);
        corMapParte.put(CoresUI.TEMA, R.style.AppTheme_Verde);
        // Coloca no corMapCompleto apenas as cores Secundárias
        corMapCompleto.put(CoresUI.CORES_SECUNDARIAS, corMapParte);

        return corMapCompleto;
    }

    //// Métodos usados para atualizar a UI
    //

    /**
     * Atualiza as cores dos itens da BottomNavigationView
     * @param csl o novo seletor de cores
     */
    private void setBttNavColorStateList(ColorStateList csl) {
        refBttNavConteudos.setItemIconTintList(csl);
        refBttNavConteudos.setItemTextColor(csl);
    }

    //// Métodos de Observer
    //

    /**
     * Quando o nome da disciplina for alterado, atualiza o título da ActionBar
     * e a variável 'disciplina' no ViewModel, usada para Querys no FireStore
     * @param s o novo nome da Disciplina
     */
    private void onNomeDisciplinaChange(String s) {
        refTbMenu.setTitle(s);

        // Seta a disciplina ativa
        if (s.equals(discMat) || s.equals(discCie)) {
            vm.setDisciplinaAtiva(s);
        }
    }

    /**
     * Atualiza as cores de toda a UI a partir da cor setada em 'CoresUI', é chamada quando há
     * uma alteração nessa variável
     * @param constCoresUi o nome atualizado do tipo de cor atual da UI (ex.: cor_primaria, ...)
     */
    private void atualizaCoresUi(String constCoresUi) {
        Log.d(TAG, "constCoresUI = " + constCoresUi);
        // Obtém cores atuais da Ui
        Map<String, Integer> cores = vm.getCoresUI().getCoresAtuais();

        setTheme(cores.get(CoresUI.TEMA));

        if (primeiraVezIniciado) {
            refRootViewMainAct.setBackgroundColor(corBranco);
            primeiraVezIniciado = false;
        } else {
            refRootViewMainAct.setBackgroundColor(cores.get(CoresUI.COR_CLARA));
        }

        refPbQuestoes.getIndeterminateDrawable().setColorFilter(cores.get(CoresUI.COR_PADRAO), PorterDuff.Mode.SRC_IN );
        refTbMenu.setBackgroundColor(cores.get(CoresUI.COR_PADRAO));
        refBttNavConteudos.setBackgroundColor(cores.get(CoresUI.COR_PADRAO));
    }

    /**
     * Altera a quantidade de pontos de recompensa para a resposta certa da questão
     * @param qst a questão atualmente ativa
     */
    private void onQstFechadaChange(QuestaoFechada qst) {
        if(qst == null) {
            vm.setQtdPontosAtiva(0);
        } else {
             vm.setQtdPontosAtiva(qst.getQtdPontos());
        }
    }

    //
    //// Métodos de Observer

    //
    //// Métodos usados para atualizar a UI

    /**
     * @param TAG_FRAGMENT Tag referente ao 1° argumento, para ser colocado na BackStack,
     *                     para poder ser identificado posteriormente
     */
    private void iniciaFragment(final String TAG_FRAGMENT, boolean limpaBackStack) {
        mostraProgressBar();

        Fragment f = null;
        switch (TAG_FRAGMENT) {
            case TAG_QUESTOES_OU_MATERIAS_FRAGMENT:
                escondeHomeButton();
                mostraToolbar();
                mostraBttNav();
                f = QuestoesOuMateriasFragment.newInstance();
                break;
            case TAG_MATERIAS_FRAGMENT:
                vm.setQstAtiva(null);
                mostraHomeButton();
                mostraToolbar();
                mostraBttNav();
                f = MateriasFragment.newInstance();
                break;
            case TAG_CONTEUDOS_FRAGMENT:
                mostraHomeButton();
                mostraToolbar();
                mostraBttNav();
                f = ConteudosFragment.newInstance();
                break;
            case TAG_QUESTOES_FRAGMENT:
                mostraHomeButton();
                mostraToolbar();
                mostraBttNav();
                f = QuestoesFragment.newInstance();
                break;
            case TAG_VER_QUESTAO_FRAGMENT:
                mostraHomeButton();
                mostraToolbar();
                escondeBttNav();
                f = VerQuestaoFragment.newInstance();
                break;
            case TAG_VER_MATERIA_FRAGMENT:
                escondeToolbar();
                escondeBttNav();
                f = VerMateriaFragment.newInstance();
                break;
            case TAG_PONTUACAO_FRAGMENT:
                escondeToolbar();
                escondeBttNav();
                f = PontuacaoFragment.newInstance();
                break;
            case TAG_CONQUISTAS_FRAGMENT:
                escondeToolbar();
                f = ConquistasFragment.newInstance();
        }
        if(limpaBackStack) {
            // Limpa a pilha de Fragment abertos
            getSupportFragmentManager()
                    .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

        FragmentManager frgManager = getSupportFragmentManager();
        FragmentTransaction transaction = frgManager.beginTransaction();
        transaction.replace(FRAGMENT_CONTAINER_ID, f, TAG_FRAGMENT);
        transaction.addToBackStack(TAG_FRAGMENT);
        transaction.commit();
    }

    /**
     * Atualiza a UI para o estado inicial do app
     * @param conteudoAtivado indica se o estado inicial deve incluir a seleção de um conteúdo,
     *                        ou literalmente o estado inicial do aplicativo
     */
    private void setConteudoInicialUi(boolean conteudoAtivado) {
        // Ativa o estado Inicial do App sempre que o menu for chamado
        // Torna as barras superior e inferior visíveis, só por garantia
        mostraToolbar();
        mostraBttNav();

        // Seta o conteúdo e a questão atuais para 'null'
        // já que vai para a tela de seleção de conteúdos
        vm.setConteudoAtivo(null);
        vm.setQstAtiva(null);
        vm.setMateriaAtiva(null);
        vm.setQtdPontosAtiva(0);

        if (conteudoAtivado) {
            iniciaFragment(TAG_QUESTOES_OU_MATERIAS_FRAGMENT, true);
            escondeConteudoCentral();
        } else {
            vm.setDisciplinaAtiva(null);
            //Esconde o botão de voltar na ActionBar
            escondeHomeButton();
            ultimoItemClicado = 0;
            // Esconde a ProgressBar caso esteja visível
            escondeProgressBar();
            setBttNavColorStateList(
                    ColorStateList
                            .valueOf(vm.getCoresUI().getCoresAtuais().get(CoresUI.COR_CLARA)));
            vm.setTituloAtivo("Menu inicial");
            refRootViewMainAct.setBackgroundColor(corBranco);
            refTvToque.setTextColor(vm.getCoresUI().getCoresAtuais().get(CoresUI.COR_PADRAO));
            mostraConteudoCentral();
        }
    }

    /**
     * Simplemente mostra Toast, e caso um já seja visível, o bloqueia de aparecer
     * @param s o texto a ser mostrado no Toast
     * @param duracao a duração
     */
    public void mostraToast(String s, int duracao) {
        if (toast == null) {
            toast = Toast.makeText(this, s, duracao);
        } else {
            toast.setText(s);
        }
        toast.show();
    }

    private void atualizaPontosUsuario() {
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();

        int pontosDisciplina;
        if(vm.getDisciplinaAtiva().equals(discMat)) {
            pontosDisciplina = sp.getInt(ConquistasFragment.PONTOS_MATEMATICA, -1);

            if (pontosDisciplina == -1) {
                spEditor.putInt(ConquistasFragment.PONTOS_MATEMATICA, vm.getQtdPontosAtiva());
            } else {
                spEditor.putInt(
                        ConquistasFragment.PONTOS_MATEMATICA,
                        pontosDisciplina + vm.getQtdPontosAtiva());
            }
        } else if (vm.getDisciplinaAtiva().equals(discCie)) {
            pontosDisciplina = sp.getInt(ConquistasFragment.PONTOS_CIENCIAS, -1);

            if (pontosDisciplina == -1) {
                spEditor.putInt(ConquistasFragment.PONTOS_CIENCIAS, vm.getQtdPontosAtiva());
            } else {
                spEditor.putInt(
                        ConquistasFragment.PONTOS_CIENCIAS,
                        pontosDisciplina + vm.getQtdPontosAtiva());
            }
        }

        int pontosTotais = sp.getInt(ConquistasFragment.PONTOS_TOTAIS, -1);
        if (pontosTotais == -1) {
            spEditor.putInt(ConquistasFragment.PONTOS_TOTAIS, vm.getQtdPontosAtiva());
        } else {
            spEditor.putInt(
                    ConquistasFragment.PONTOS_TOTAIS,
                    pontosTotais + vm.getQtdPontosAtiva());
        }
    }

    //// Métodos que alteram Visibility das Views
    //

    // Altera visibilidade do conteúdo central da página
    private void escondeConteudoCentral() {
        refTvToque.setVisibility(View.GONE);
    }
    private void mostraConteudoCentral() {
        refTvToque.setVisibility(View.VISIBLE);
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

    // Altera visibilidade da ProgressBar
    @Override
    public void mostraProgressBar() {
        refPbQuestoes.setVisibility(View.VISIBLE);
    }
    @Override
    public void escondeProgressBar() {
        refPbQuestoes.setVisibility(View.GONE);
    }

    // Altera visibilidade da BottomNavigationBar
    private void mostraBttNav() {
        refSombraBttNav.setVisibility(View.VISIBLE);
        refBttNavConteudos.setVisibility(View.VISIBLE);
    }
    private void escondeBttNav() {
        refSombraBttNav.setVisibility(View.GONE);
        refBttNavConteudos.setVisibility(View.GONE);
    }

    // Altera visibilidade da ActionBar
    private void mostraToolbar() {
        refSombraTb.setVisibility(View.VISIBLE);
        refTbMenu.setVisibility(View.VISIBLE);
    }
    private void escondeToolbar() {
        refSombraTb.setVisibility(View.GONE);
        refTbMenu.setVisibility(View.GONE);
    }

    //
    //// Métodos que alteram Visibility das Views

    //// Métodos implementados de 'QuestoesFragment'
    //

    @Override
    public void onVerQuestaoInteraction(QuestaoFechada qst) {
        // Seta a questão ativa
        vm.setQstAtiva(qst);

        // Inicia o fragment
        iniciaFragment(TAG_VER_QUESTAO_FRAGMENT, false);
    }
    @Override
    public void onVerMateriaInteraction(QuestaoFechada qst) {
        // Seta a questão ativa
        vm.setQstAtiva(qst);

        iniciaFragment(TAG_VER_MATERIA_FRAGMENT, true);
    }

    //
    //// Métodos implementados de 'QuestoesFragment'

    //// Métodos implementados de 'ConteudosFragment'
    //

    @Override
    public void onConteudoInteraction(Conteudo conteudo) {
        // Seta o conteúdo que poderá ser buscado posteriormente
        vm.setConteudoAtivo(conteudo);

        iniciaFragment(TAG_QUESTOES_FRAGMENT, false);
    }

    //
    //// Métodos implementados de 'ConteudosFragment'

    //// Métodos implementados de 'VerQuestaoFragment'
    //

    @Override
    public void onAlternativaSelecionada(MaterialButton[] refMtrlBts, int altSelecionada) {
        boolean altCerta = altSelecionada == vm.getQstAtiva().getValue().getResposta();

        // Troca a cor do texto do botão para branco para melhorar a visualização
        String textoAlt = String.valueOf(refMtrlBts[altSelecionada].getText());
        refMtrlBts[altSelecionada].setText(textoAlt);
        refMtrlBts[altSelecionada].setTextColor(corBranco);

        // Verifica se o usuário tocou na alternativa certa e troca a cor do botão
        if(altCerta) {
            refMtrlBts[altSelecionada]
                    .setBackgroundTintList(ColorStateList.valueOf(corSecClara));

            // Impede que o usuário clique em outros botões e ative suas funções
            MaterialButtonUtils.impedeClicks(true, refMtrlBts);

            // Esconde a Actionbar
            escondeToolbar();

            // Atualiza os pontos do usuário
            atualizaPontosUsuario();
        } else {
            vm.setQtdPontosAtiva(vm.getQtdPontosAtiva() / 2);
            refMtrlBts[altSelecionada]
                    .setBackgroundTintList(ColorStateList.valueOf(corAltErrada));

            // Impede que o usuário clique em outros botões e ative suas funções
            MaterialButtonUtils.impedeClicks(false, refMtrlBts);

            mostraToast("Você não acertou dessa vez, tente denovo", Toast.LENGTH_SHORT);
        }

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (altCerta) {
                // Redireciona para a página de pontuação da questão
                iniciaFragment(TAG_PONTUACAO_FRAGMENT, true);
            } else {
                // Permite que o usuário toque novamente nos botões
                // e retorna o botão a sua cor original
                refMtrlBts[altSelecionada]
                        .setBackgroundTintList(ColorStateList.valueOf(corBranco));

                // Troca a cor do texto do botão para branco para melhorar a visualização
                refMtrlBts[altSelecionada]
                        .setTextColor(vm.getCoresUI().getCoresAtuais().get(CoresUI.COR_ESCURA));
                MaterialButtonUtils.permiteClicks(refMtrlBts);
            }
        }, 500);
    }

    //
    //// Métodos implementados de 'VerQuestaoFragment'

    //// Métodos implementados de 'PontuacaoFragment'
    //

    @Override
    public void onClaroQueSimInteraction() {
        iniciaFragment(TAG_VER_MATERIA_FRAGMENT, true);
    }

    @Override
    public void onVoltarParaOMenuSelecionado() {
        setConteudoInicialUi(true);
    }

    //
    //// Métodos implementados de 'PontuacaoFragment'

    //// Métodos implementados de 'VerMateriaFragment'
    //

    @Override
    public void onQroVoltarParaMenuInteraction() {
        onVoltarParaOMenuSelecionado();
    }
    @Override
    public void onQroVerOutrasMateriasInteraction() {
        iniciaFragment(TAG_MATERIAS_FRAGMENT, false);
    }

    //
    //// Métodos implementados de 'VerMateriaFragment'

    //// Métodos implementados de 'MateriasFragment'
    //

    @Override
    public void onMateriaInteraction(Materia m) {
        // Seta matéria atual
        vm.setMateriaAtiva(m);

        iniciaFragment(TAG_VER_MATERIA_FRAGMENT, true);
    }

    //
    //// Métodos implementados de 'MateriasFragment'

    //// Métodos implementados de 'QuestoesOuMateriasFragment'
    //

    @Override
    public void onQuestoesOuMateriasInteraction(int cvSelecionado) {
        if(cvSelecionado == QuestoesOuMateriasFragment.CARD_VIEW_VER_MATERIA_SELECIONADO) {
            iniciaFragment(TAG_MATERIAS_FRAGMENT, false);
        } else if (cvSelecionado == QuestoesOuMateriasFragment.CARD_VIEW_VER_QUESTOES_SELECIONADO) {
            iniciaFragment(TAG_CONTEUDOS_FRAGMENT, false);
        }
    }

    //
    //// Métodos implementados de 'QuestoesOuMateriasFragment'
}