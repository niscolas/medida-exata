package br.cefetmg.inf.medidaexata.view.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.button.MaterialButton;

import br.cefetmg.inf.medidaexata.model.QuestaoFechada;
import br.cefetmg.inf.medidaexata.view.activities.fragments.QuestoesFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestaoAdapter
        extends FirestoreRecyclerAdapter<QuestaoFechada, QuestaoAdapter.QuestaoHolder> {

    private final static String TAG = QuestaoAdapter.class.getSimpleName();
    private final QuestoesFragment.IProgressBarShower frgListener;

    public QuestaoAdapter(FirestoreRecyclerOptions<QuestaoFechada> options,
                          QuestoesFragment.IProgressBarShower frgListener) {
        super(options);
        this.frgListener = frgListener;
    }

    @Override
    public QuestaoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        frgListener.escondeProgressBar();

        View v = LayoutInflater
                .from(
                        parent.getContext())
                .inflate(
                        R.layout.list_item_questao,
                        parent,
                        false);

        return new QuestaoHolder(v);
    }

    @Override
    protected void onBindViewHolder(final QuestaoHolder qstHolder,
                                    int position,
                                    final QuestaoFechada qst) {
        qstHolder.refTvEnunciado.setText(qst.getEnunciado());
        qstHolder.refTvObjCon.setText(qst.getObjCon());

        qstHolder.refBtVerQuestao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != frgListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
//                    frgListener.escondeProgressBar(qst);
                }
            }
        });
        qstHolder.refBtVerMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != frgListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
//                    frgListener.escondeProgressBar(qst);
                }
            }
        });
    }

    public class QuestaoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_enunciado)
        TextView refTvEnunciado;
        @BindView(R.id.tv_obj_con)
        TextView refTvObjCon;
        @BindView(R.id.bt_ver_questao)
        MaterialButton refBtVerQuestao;
        @BindView(R.id.bt_ver_materia)
        MaterialButton refBtVerMateria;

        QuestaoHolder(View v) {
            super(v);
            try {
                ButterKnife.bind(this, v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return super.toString() + " '" + refTvObjCon.getText() + "'";
        }
    }
}
