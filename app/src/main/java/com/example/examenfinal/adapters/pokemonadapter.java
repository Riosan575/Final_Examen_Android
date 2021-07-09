package com.example.examenfinal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examenfinal.R;
import com.example.examenfinal.entities.Pokemon;
import com.squareup.picasso.Picasso;

import java.util.List;

public class pokemonadapter extends RecyclerView.Adapter<pokemonadapter.PokemonViewHolder> {

    List<Pokemon> pokemons;

    public pokemonadapter(List<Pokemon> pokemons) {

        this.pokemons = pokemons;
    }
    @Override
    public PokemonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itempokemon,parent,false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(pokemonadapter.PokemonViewHolder holder, int position) {

        View view = holder.itemView;
        Pokemon pokemon = pokemons.get(position);

        TextView tvnombre = view.findViewById(R.id.tvNomPokemonss);
        TextView tvtipo = view.findViewById(R.id.tvtipoPokemonss);
        ImageView ivimage = view.findViewById(R.id.tvImagenPokemonss1);
        TextView tvlatitud = view.findViewById(R.id.tvlatitudePokemonss);
        TextView tvlogintud = view.findViewById(R.id.tvLongitudePokemonss);


        tvnombre.setText(pokemon.getNombre());
        tvtipo.setText(pokemon.getTipo());
        Picasso.get().load(pokemon.getImagen()).into(ivimage);
        tvlatitud.setText(pokemon.getLatitude());
        tvlogintud.setText(pokemon.getLongitude());




    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }


    public class PokemonViewHolder extends RecyclerView.ViewHolder {
        public PokemonViewHolder(View itemView) {
            super(itemView);
        }
    }
}
