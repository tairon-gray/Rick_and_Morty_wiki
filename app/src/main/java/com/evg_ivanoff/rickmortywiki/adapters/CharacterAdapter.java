package com.evg_ivanoff.rickmortywiki.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evg_ivanoff.rickmortywiki.R;
import com.evg_ivanoff.rickmortywiki.pojo.Character;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {

    private List<Character> characters;
    private OnCharacterClickListener onCharacterClickListener;
    private OnReachEndListener onReachEndListener;

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
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
        if(position==characters.size() - 1 && onReachEndListener != null){
            onReachEndListener.OnReachEnd();
        }
        Character character = characters.get(position);
        holder.textViewCharacterName.setText(character.getName());
        Picasso.get().load(character.getImage()).into(holder.imageViewCharacter);
    }

    @Override
    public int getItemCount() {
        return characters.size();
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
