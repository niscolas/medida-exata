package br.cefetmg.inf.medidaexata.view.fragments;

import android.content.Context;
import android.os.Bundle;
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

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import br.cefetmg.inf.medidaexata.model.CoresUI;
import br.cefetmg.inf.medidaexata.model.Materia;
import br.cefetmg.inf.medidaexata.view.IAlteraProgressBar;
import br.cefetmg.inf.medidaexata.viewmodel.MedidaExataViewModel;
import br.cefetmg.inf.util.ImageViewUtils;
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
    // ViewModel
    private MedidaExataViewModel vm;

    //// Binding
    //

    // Views
    @BindView(R.id.tv_materia) LinearLayout refLlMateria;
    @BindView(R.id.tv_materia_titulo) TextView refTvMatTitulo;
    @BindView(R.id.tv_materia_abordada) TextView refTvMatAbordada;
    @BindView(R.id.bt_qro_ver_materia) MaterialButton refBtQroVerMat;
    @BindView(R.id.bt_gostei_mas_quero_voltar) MaterialButton refBtQroVoltar;

    // Cores
    @BindColor(R.color.branco) int corBranco;

    //
    //// Binding

    public VerMateriaFragment() {
    }

    public static Fragment newInstance() {
        VerMateriaFragment frg = new VerMateriaFragment();
        return frg;
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
            altPbListener= (IAlteraProgressBar) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement IAlteraProgressBar");
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
        altPbListener.escondeProgressBar();

        View v = inflater.inflate(R.layout.fragment_ver_materia, container, false);
        ButterKnife.bind(this, v);
        // Obtém os dados da matéria
        getDadosMateria();

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        frgListener = null;
    }

    private void getDadosMateria() {
        DocumentReference docMateria = vm.getQst().getValue().getMateriaRel();
        docMateria.get().addOnSuccessListener(documentSnapshot -> {
            materia = documentSnapshot.toObject(Materia.class);

            // Atribui ao TextView de MatériaAbordada, texto e cor
            refTvMatAbordada.setTextColor(vm.getCoresUI().getCoresAtuais().get(CoresUI.COR_ESCURA));
            refTvMatAbordada.append(vm.getQst().getValue().getMateriaAbordada());
            refTvMatAbordada.append(":");

            // Atribui ao TextView de Título da Matéria, texto e cor
            refTvMatTitulo.setTextColor(vm.getCoresUI().getCoresAtuais().get(CoresUI.COR_ESCURA));
            refTvMatTitulo.setText(materia.getTitulo());
            populaFragmentComMateria();
        });
    }

    private void populaFragmentComMateria() {
        int i = 2;
        for (String pedacoMateria : materia.getMateria()) {
            if(pedacoMateria.startsWith("$$") && pedacoMateria.endsWith("$$")) {
                // Cria uma nova ImageView com os parâmetros passados
                ImageView img = ImageViewUtils.criaImageView(
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                        -1,
                        -1,
                        0,
                        0,
                        this.getContext());

                // Obtém a Url da imagem, que foi padronizada como sendo as Strings
                // que começam e terminam com "$$", ou seja, tirando essa parte da String, obtém-se
                // a Url
                String urlImagem
                        = pedacoMateria
                        .substring(
                                2,
                                pedacoMateria.length() - 2);

                // Carrega a Url existente em 'pedacoMateria' numa ImageView e dispõe na tela
                Picasso.get()
                        .load(urlImagem)
                        .into(img);
                refLlMateria.addView(img, i);
            } else {
                // Cria uma nova ImageView com os parâmetros passados
                refLlMateria
                        .addView(
                                TextViewUtils.criaTextView(
                                        LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                                        pedacoMateria,
                                        16,
                                        0,
                                        0,
                                        corBranco,
                                        this.getContext()),
                                i);
            }
            i++;
        }
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

    public interface OnVerMateriaFragmentInteractionListener {
        void onQroVoltarParaMenuInteraction();
        void onQroVerOutrasMateriasInteraction();
    }
}