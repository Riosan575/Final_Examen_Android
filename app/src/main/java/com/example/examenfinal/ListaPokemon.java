package com.example.examenfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.examenfinal.adapters.pokemonadapter;
import com.example.examenfinal.entities.Entranador;
import com.example.examenfinal.entities.Pokemon;
import com.example.examenfinal.services.EntrenadorService;
import com.example.examenfinal.services.PokemonService;

import org.w3c.dom.Text;

import java.security.Policy;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaPokemon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pokemon);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://upn.lumenes.tk/pokemons/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokemonService service = retrofit.create(PokemonService.class);
        Call<List<Pokemon>> pokemonCall = service.getAll();
        pokemonCall.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {

                List<Pokemon> pokemons = response.body();
                RecyclerView rv = findViewById(R.id.rvPalabras);
                rv.setHasFixedSize(true);
                rv.setLayoutManager(new LinearLayoutManager(ListaPokemon.this));
                pokemonadapter adapter = new pokemonadapter(pokemons);

                rv.setAdapter(adapter);



            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {

            }
        });


    }
}