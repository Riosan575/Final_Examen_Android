package com.example.examenfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.examenfinal.entities.Entranador;
import com.example.examenfinal.services.EntrenadorService;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaEntrenadores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_entrenadores);

        ImageView ivimage = findViewById(R.id.ivEntre);
        TextView tvnombre = findViewById(R.id.tvNombreEntre);
        TextView tvpueblo = findViewById(R.id.tvPuebloEntre);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://upn.lumenes.tk/entrenador/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EntrenadorService service = retrofit.create(EntrenadorService.class);
        Call<Entranador> entrenadorCall = service.getAll();
        entrenadorCall.enqueue(new Callback<Entranador>() {
            @Override
            public void onResponse(Call<Entranador> call, Response<Entranador> response) {
                Entranador entranador = response.body();

                tvnombre.setText(entranador.getNombres());
                tvpueblo.setText(entranador.getPueblo());
                Picasso.get().load(entranador.getImagen()).into(ivimage);
            }

            @Override
            public void onFailure(Call<Entranador> call, Throwable t) {

            }
        });

        Button btnPoke = findViewById(R.id.btnCrearPokemonss);
        btnPoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaEntrenadores.this,CrearPokemon.class);
                startActivity(intent);
            }
        });

        Button btnverpoke = findViewById(R.id.btnVerPokemon);
        btnverpoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaEntrenadores.this,ListaPokemon.class);
                startActivity(intent);
            }
        });
    }
}