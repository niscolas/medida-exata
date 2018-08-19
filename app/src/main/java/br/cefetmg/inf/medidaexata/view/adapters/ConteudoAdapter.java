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
import br.cefetmg.inf.medidaexata.view.activities.fragments.ConteudosFragment;
import butterknife.BindView;

public class ConteudoAdapter
        extends FirestoreRecyclerAdapter<Conteudo, ConteudoAdapter.ConteudoHolder> {

    private final ConteudosFragment.OnConteudoInteractionListener frgListener;

    public ConteudoAdapter(FirestoreRecyclerOptions<Conteudo> options,
                           ConteudosFragment.OnConteudoInteractionListener frgListener) {
        super(options);
        this.frgListener = frgListener;
    }


    @Override
    public ConteudoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
                                    int i,
                                    Conteudo conteudo) {

//        conteudoHolder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != frgListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    frgListener.onConteudoInteraction(conteudoHolder.mItem);
//                }
//            }
//        });
    }

    public class ConteudoHolder extends RecyclerView.ViewHolder {
        final CardView cardView;
        @BindView(R.id.item_number)
        TextView mIdView;
        @BindView(R.id.content)
        TextView mContentView;

        ConteudoHolder(View v) {
            super(v);
            cardView = (CardView) v;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
