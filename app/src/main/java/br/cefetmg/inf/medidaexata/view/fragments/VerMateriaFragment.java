package br.cefetmg.inf.medidaexata.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import br.cefetmg.inf.medidaexata.model.enums.TonalidadeCor;
import br.cefetmg.inf.medidaexata.model.Materia;
import br.cefetmg.inf.medidaexata.view.IAlteraProgressBar;
import br.cefetmg.inf.medidaexata.view.IOnSemResultados;
import br.cefetmg.inf.medidaexata.view.activities.MainActivity;
import br.cefetmg.inf.medidaexata.view.dialogs.SemResultadosDialog;
import br.cefetmg.inf.medidaexata.viewmodel.MedidaExataViewModel;
import br.cefetmg.inf.util.ImageViewUtils;
import br.cefetmg.inf.util.StringUtils;
import br.cefetmg.inf.util.TextViewUtils;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VerMateriaFragment extends Fragment {

    //// Campos static final
    //

    private static final String TAG = VerMateriaFragment.class.getSimpleName();

    //
    //// Campos static final

    // A matéria a ser disposta na tela por esse Fragment
    private Materia materia;
    // Listener do Fragment
    private OnVerMateriaFragmentInteractionListener frgListener;
    private IAlteraProgressBar altPbListener;
    private IOnSemResultados semResultadosListener;
    // ViewModel
    private MedidaExataViewModel vm;

    //// Binding
    //

    // Views
    @BindView(R.id.tv_materia) LinearLayout refLlMateria;
    @BindView(R.id.tv_nome_materia) TextView refTvTituloMat;
    @BindView(R.id.bt_qro_ver_materia) MaterialButton refBtQroVerMat;
    @BindView(R.id.bt_gostei_mas_quero_voltar) MaterialButton refBtQroVoltar;

    // Cores
    @BindColor(R.color.azul_escuro) int azulEscuro;

    //
    //// Binding

    public VerMateriaFragment() {
    }

    public static Fragment newInstance() {
        return new VerMateriaFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnVerMateriaFragmentInteractionListener) {
            frgListener = (OnVerMateriaFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnVerMateriaFragmentInteractionListener");
        }
        if (context instanceof IAlteraProgressBar) {
            altPbListener = (IAlteraProgressBar) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IAlteraProgressBar");
        }
        if (context instanceof IOnSemResultados) {
            semResultadosListener = (IOnSemResultados) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IOnSemResultados");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Obtém o ViewModel
        vm = ViewModelProviders.of(getActivity()).get(MedidaExataViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ver_materia, container, false);
        ButterKnife.bind(this, v);

        refTvTituloMat.setTextColor(vm.getCorContextualEspecifica(TonalidadeCor.ESCURA));

        if(vm.getQstAtiva().getValue() != null) {
            // Obtém os dados da matéria
            if(vm.getQstAtiva().getValue() != null && vm.getQstAtiva().getValue().getMateriaRel() != null) {
                getDadosMateria();
            } else {
                DialogFragment df = SemResultadosDialog.newInstance(SemResultadosDialog.SEM_MATERIA);
                semResultadosListener.onSemQuestoes(df);
            }
        } else {
            materia = vm.getMateriaAtiva();
            refTvTituloMat.setText(materia.getTitulo());
            populaFragmentComMateria();
        }
        altPbListener.escondeProgressBar();

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        frgListener = null;
    }

    private void getDadosMateria() {
        DocumentReference docMateria = vm.getQstAtiva().getValue().getMateriaRel();
        docMateria.get().addOnSuccessListener(documentSnapshot -> {
            materia = documentSnapshot.toObject(Materia.class);

            if(materia != null) {
                refTvTituloMat.setText(materia.getTitulo());
                populaFragmentComMateria();
            } else {
                DialogFragment df = SemResultadosDialog.newInstance(SemResultadosDialog.SEM_MATERIA);
                semResultadosListener.onSemQuestoes(df);
            }
        });
    }

    private void populaFragmentComMateria() {
        populaLinearLayoutComTextoEImagens
                (refLlMateria,
                        1,
                        materia.getMateria(),
                        vm.getCorContextualEspecifica(TonalidadeCor.ESCURA),
                        azulEscuro,
                        16,
                        0,
                        0,
                        0,
                        0,
                        this.getContext());

        // Seta o ClickListener no botão de retorno para o Menu e torna o botão visível e clicável
        refBtQroVoltar.setOnClickListener(view ->
                frgListener.onQroVoltarParaMenuInteraction());
        refBtQroVoltar.setVisibility(View.VISIBLE);

        // Seta o ClickListener no botão de trigger para a tela de visualização de matérias
        // e torna o botão visível e clicável
        refBtQroVerMat.setOnClickListener(view ->
                frgListener.onQroVerOutrasMateriasInteraction());
        refBtQroVerMat.setVisibility(View.VISIBLE);
    }

    /**
     * Popula um LinearLayout com texto e imagens de um List<String>
     * @param container o LinearLayout onde serão dispostos os textos e imagens
     * @param posViewInicial a posição do Layout onde começará a ser colocado o conteúdo
     * @param textoEImagens um List com texto e URLs de imagens
     *                      URLs devem começar e terminar com '$$' (sem as aspas)
     * @param corTexto a cor do Texto
     * @param context o contexto
     */

    static void populaLinearLayoutComTextoEImagens(
            LinearLayout container,
            int posViewInicial,
            List<String> textoEImagens,
            int corTexto,
            int corLink,
            int tamanhoFonte,
            int paddingImagem,
            int paddingTexto,
            int margemTexto,
            int margemImagem,
            Context context) {

        for (String item : textoEImagens) {

            // Caso seja uma imagem

            if(item.startsWith("$$") && item.endsWith("$$")) {
                // Cria uma nova ImageView com os parâmetros passados
                ImageView img = ImageViewUtils.criaImageView(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        -1,
                        -1,
                        margemImagem,
                        paddingImagem,
                        context);

                // Obtém a Url da imagem, que foi padronizada como sendo as Strings
                // que começam e terminam com "$$", ou seja, tirando essa parte da String, obtém-se
                // a Url
                String urlImagem
                        = item
                        .substring(
                                2,
                                item.length() - 2);

                // Carrega a Url existente em 'pedacoConteudo' numa ImageView e dispõe na tela
                Picasso.get()
                        .load(urlImagem)
                        .resize(1800, 500).centerInside()
                        .into(img);
                container.addView(img, posViewInicial);
            }

            // Caso seja na verdade um link

            else if (item.startsWith("##") && item.endsWith("##")){
                String novoTexto = item.substring(2, item.length() - 2);

                TextView novoTv = TextViewUtils.criaTextView(
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                        novoTexto + "\n\n",
                        tamanhoFonte,
                        margemTexto,
                        paddingTexto,
                        corLink,
                        context);

                StringUtils.tornaTextoLink(novoTv);

                novoTv.setOnClickListener(view -> {
                    Intent viewIntent =
                            new Intent("android.intent.action.VIEW",
                                    Uri.parse(novoTexto));
                    context.startActivity(viewIntent);
                });

                container.addView(novoTv, posViewInicial);
            }

            // Caso seja texto puro

            else {
                boolean negrito = false;
                int tamanhoFonte2 = tamanhoFonte;

                // Verifica se o texto deve ser em negrito
                if(item.startsWith("@b@")) {
                    negrito = true;

                    // Corta o marcador que indica o negrito da String
                    item = item.replace("@b@", "");
                }

                // Verifica se a fonte do texto deve ser maior
                if(item.endsWith("@@")) {
                    // Obtém o tamanho que o texto deve ser em forma de int
                    tamanhoFonte2 = Integer
                            .parseInt(item.substring(
                                    item.length() - 4,
                                    item.length() - 2));

                    // Corta o marcador que indica o tamanho do texto da String
                    item = item.replace(tamanhoFonte2 + "@@", "");
                }

                // Troca os literais \n e \t por seus caracteres próprios
                item = item.replace("\\n", "\n  ");
                item = item.replace("\\t", "\t  ");

                // Adiciona um Tab antes da String
                item = "   " + item;

                // Cria uma nova TextView com os parâmetros passados
                TextView tv = TextViewUtils.criaTextView(
                        LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                        item,
                        tamanhoFonte2,
                        margemTexto,
                        paddingTexto,
                        corTexto,
                        context);

                if (negrito) {
                    tv.setTypeface(null, Typeface.BOLD);
                }
                container.addView(tv, posViewInicial);
            }
            posViewInicial++;
        }
    }

    public interface OnVerMateriaFragmentInteractionListener {
        void onQroVoltarParaMenuInteraction();
        void onQroVerOutrasMateriasInteraction();
    }
}