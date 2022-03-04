package com.evg_ivanoff.rickmortywiki.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evg_ivanoff.rickmortywiki.R;
import com.evg_ivanoff.rickmortywiki.pojo.Episode;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder> {

    private List<Episode> episodes;

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episode_item, parent, false);
        return new EpisodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        Episode episode = episodes.get(position);
        holder.textEpisodeName.setText(episode.getId() + ". " + episode.getName());
        holder.textEpisodeDate.setText(episode.getAirDate());
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    class EpisodeViewHolder extends RecyclerView.ViewHolder {
        private TextView textEpisodeName;
        private TextView textEpisodeDate;

        public EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
            textEpisodeName = itemView.findViewById(R.id.textEpisodeName);
            textEpisodeDate = itemView.findViewById(R.id.textEpisodeDate);
        }
    }
}
