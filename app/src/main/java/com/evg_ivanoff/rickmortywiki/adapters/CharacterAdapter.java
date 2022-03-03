package com.evg_ivanoff.rickmortywiki.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evg_ivanoff.rickmortywiki.R;
import com.evg_ivanoff.rickmortywiki.pojo.CharacterOne;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {

    private List<CharacterOne> characterOnes;
    private OnCharacterClickListener onCharacterClickListener;
    private OnReachEndListener onReachEndListener;

    public List<CharacterOne> getCharacters() {
        return characterOnes;
    }

    public void setCharacters(List<CharacterOne> characterOnes) {
        this.characterOnes = characterOnes;
        notifyDataSetChanged();
    }

    public void setOnCharacterClickListener(OnCharacterClickListener onCharacterClickListener) {
        this.onCharacterClickListener = onCharacterClickListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    public interface OnCharacterClickListener{
        void onCharacterClick(int position);
    }

    public interface OnReachEndListener{
        void OnReachEnd();
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_item_grid, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        if(position== characterOnes.size() - 1 && onReachEndListener != null){
            onReachEndListener.OnReachEnd();
        }
        CharacterOne characterOne = characterOnes.get(position);
        holder.textViewCharacterName.setText(characterOne.getName());
        Picasso.get().load(characterOne.getImage()).into(holder.imageViewCharacter);
    }

    @Override
    public int getItemCount() {
        return characterOnes.size();
    }

    class CharacterViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewCharacterName;
        private ImageView imageViewCharacter;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCharacterName = itemView.findViewById(R.id.textViewCharacterName);
            imageViewCharacter = itemView.findViewById(R.id.imageViewCharacter);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onCharacterClickListener != null){
                        onCharacterClickListener.onCharacterClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
