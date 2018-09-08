package br.cefetmg.inf.medidaexata.view.adapters;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import br.cefetmg.inf.medidaexata.model.CoresUI;
import br.cefetmg.inf.medidaexata.model.Materia;
import br.cefetmg.inf.medidaexata.view.IAlteraProgressBar;
import br.cefetmg.inf.medidaexata.view.fragments.MateriasFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.Map;

public class MateriaAdapter
        extends FirestoreRecyclerAdapter<Materia, MateriaAdapter.MateriaHolder> {

    // Listeners
    private final MateriasFragment.OnMateriasFragmentInteractionListener fListener;
    private IAlteraProgressBar altPbListener;
    // Cores dos textos mostrados
    private Map<String, Integer> coresTexto;

    public MateriaAdapter(FirestoreRecyclerOptions<Materia> options,
                           MateriasFragment.OnMateriasFragmentInteractionListener fListener,
                           IAlteraProgressBar altPbListener,
                           Map<String, Integer> coresTexto) {
        super(options);
        this.fListener = fListener;
        this.altPbListener = altPbListener;
        this.coresTexto = coresTexto;
    }

    @Override
    public MateriaAdapter.MateriaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        altPbListener.escondeProgressBar();

        View view = LayoutInflater
                .from(
                        parent.getContext())
                .inflate(
                        R.layout.list_item_conteudo,
                        parent,
                        false);

        return new MateriaAdapter.MateriaHolder(view);
    }

    @Override
    protected void onBindViewHolder(final MateriaAdapter.MateriaHolder materiaHolder,
                                    int posicao,
                                    final Materia materia) {
        int corEscura = coresTexto.get(CoresUI.COR_ESCURA);

        // Altera o texto e sua cor a ser mostrada no nome de cada Conteúdo
        materiaHolder.refTvNomeMateria.setTextColor(corEscura);
        materiaHolder.refTvNomeMateria.setText(materia.getTitulo());

        // Seta um novo ClickListener à CardView
        materiaHolder.cv.setOnClickListener(v -> {
            if (fListener != null) {
                fListener.onMateriaInteraction(materia);
            }
        });
    }

    public class MateriaHolder extends RecyclerView.ViewHolder {
        final CardView cv;
        @BindView(R.id.tv_nome_materia)
        TextView refTvNomeMateria;

        MateriaHolder(View v) {
            super(v);
            try {
                ButterKnife.bind(this, v);
            } catch (Exception e ) {
                e.printStackTrace();
            }
            cv = (CardView) v;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + refTvNomeMateria.getText() + "'";
        }
    }
}
