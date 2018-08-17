package br.cefetmg.inf.medidaexata.view.activities.fragments;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cefetmg.inf.android.medidaexata.activities.R;
import com.google.android.material.button.MaterialButton;

import br.cefetmg.inf.medidaexata.model.domain.QuestaoFechada;
import br.cefetmg.inf.medidaexata.view.activities.fragments.QuestoesFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * TODO: Replace the implementation with code for your data type.
 */
public class QuestaoAdapter extends RecyclerView.Adapter<QuestaoAdapter.QuestaoHolder> {

    private final List<QuestaoFechada> questoes;
    private final OnListFragmentInteractionListener mListener;

    public QuestaoAdapter(List<QuestaoFechada> items, OnListFragmentInteractionListener listener) {
        questoes = items;
        mListener = listener;
    }

    @Override
    public QuestaoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(
                        R.layout.item_questao,
                        parent,
                        false);
        return new QuestaoHolder(view);
    }

    @Override
    public void onBindViewHolder(final QuestaoHolder holder, int position) {
        holder.questao = questoes.get(position);
        holder.refTvEnunciado.setText(holder.questao.getEnunciado());
        holder.refTvObjCon.setText(holder.questao.getObjCon());

        holder.refBtVerQuestao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.questao);
                }
            }
        });
        holder.refBtVerMateria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.questao);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return questoes.size();
    }

    public class QuestaoHolder extends RecyclerView.ViewHolder {
        final View v;
        final TextView refTvEnunciado;
        final TextView refTvObjCon;
        final MaterialButton refBtVerQuestao;
        final MaterialButton refBtVerMateria;
        QuestaoFechada questao;

        public QuestaoHolder(View view) {
            super(view);
            v = view;
            refTvEnunciado = view.findViewById(R.id.tv_enunciado);
            refTvObjCon = view.findViewById(R.id.tv_obj_con);
            refBtVerQuestao = view.findViewById(R.id.bt_ver_questao);
            refBtVerMateria = view.findViewById(R.id.bt_ver_materia);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + refTvObjCon.getText() + "'";
        }
    }
}
