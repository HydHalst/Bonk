package com.ncs.projecteranking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ncs.projecteranking.R;
import com.ncs.projecteranking.models.Ranking;
import com.ncs.projecteranking.models.RankingList;
import com.ncs.projecteranking.models.User;
import com.squareup.picasso.Picasso;

public class RankingUserListAdapter
        extends RecyclerView.Adapter<RankingUserListAdapter.ViewHolder> {

    private RankingList rankingList;

    public RankingUserListAdapter(RankingList rankingList) {
        this.rankingList = rankingList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        User user;
        ImageView imgUser;
        TextView tvUserId, tvFullName, tvUsername, tvScore;

        public ViewHolder(View view) {
            super(view);
            imgUser = view.findViewById(R.id.imgUser);
            tvUserId = view.findViewById(R.id.tvUserId);
            tvFullName = view.findViewById(R.id.tvFullName);
            tvUsername = view.findViewById(R.id.tvUsername);
            tvScore = view.findViewById(R.id.tvScore);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user == null) return;
                    if (listener != null) listener.itemClicked(v, user);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void itemClicked(View view, User user);
    }

    private OnItemClickListener listener;

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_ranking_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RankingUserListAdapter.ViewHolder holder, int position) {
        Ranking ranking = rankingList.getRankings()[position];
        User user = ranking.getUser();
        Context context = holder.tvUserId.getContext();
        holder.user = user;
        holder.tvUserId.setText(context.getString(R.string.strId, ranking.getId()));
        Log.d("ncs", "onBindViewHolder: USERNAME: " + user.getUsername());
        holder.tvUsername.setText(user.getUsername());
        holder.tvFullName.setText(context.getString(R.string.fullName, user.getFirstname(), user.getLastname()));
        holder.tvScore.setText(context.getString(R.string.position, position + 1, Integer.parseInt(ranking.getScore())));
        Picasso.with(context).load(user.getAvatarPath()).into(holder.imgUser);
    }

    @Override
    public int getItemCount() {
        return rankingList.getCount();
    }
}
