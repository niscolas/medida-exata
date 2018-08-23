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

import br.cefetmg.inf.medidaexata.model.Conteudo;
import br.cefetmg.inf.medidaexata.view.fragments.ConteudosFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ConteudoAdapter
        extends FirestoreRecyclerAdapter<Conteudo, ConteudoAdapter.ConteudoHolder> {

    // Listeners
    private final ConteudosFragment.OnConteudoInteractionListener frgListener;
    private IAlteraProgressBar altPbListener;
    // Cores dos textos mostrados
    private int[] coresTexto;

    public ConteudoAdapter(FirestoreRecyclerOptions<Conteudo> options,
                           ConteudosFragment.OnConteudoInteractionListener frgListener,
                           IAlteraProgressBar altPbListener,
                           int[] coresTexto) {
        super(options);
        this.frgListener = frgListener;
        this.altPbListener = altPbListener;
        this.coresTexto = coresTexto;
    }

    @Override
    public ConteudoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        altPbListener.escondeProgressBar();

        View view = LayoutInflater
                .from(
                        parent.getContext())
                .inflate(
                        R.layout.list_item_conteudo,
                        parent,
                        false);

        return new ConteudoHolder(view);
    }

    @Override
    protected void onBindViewHolder(final ConteudoHolder conteudoHolder,
                                    int posicao,
                                    final Conteudo conteudo) {
        // Altera o texto e sua cor a ser mostrada no nome de cada Conteúdo
        conteudoHolder.refTvNomeConteudo.setTextColor(coresTexto[2]);
        conteudoHolder.refTvNomeConteudo.setText(conteudo.getNome());
        // Altera o texto e sua cor a ser mostrada na descrição de cada Conteúdo
        conteudoHolder.refTvDesConteudo.setTextColor(coresTexto[0]);
        conteudoHolder.refTvDesConteudo.setText(conteudo.getDescricao());
        // Seta um novo ClickListener à CardView
        conteudoHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frgListener != null) {
                    frgListener.onConteudoInteraction(conteudo, coresTexto);
                }
            }
        });
    }

    public class ConteudoHolder extends RecyclerView.ViewHolder {
        final CardView cardView;
        @BindView(R.id.tv_nome_conteudo)
        TextView refTvNomeConteudo;
        @BindView(R.id.tv_des_conteudo)
        TextView refTvDesConteudo;

        ConteudoHolder(View v) {
            super(v);
            try {
                ButterKnife.bind(this, v);
            } catch (Exception e ) {
                e.printStackTrace();
            }
            cardView = (CardView) v;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + refTvDesConteudo.getText() + "'";
        }
    }

    public interface IAlteraProgressBar {
        void mostraProgressBar();
        void escondeProgressBar();
    }
}
