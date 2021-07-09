package com.example.examenfinal.services;

import com.example.examenfinal.entities.Entranador;
import com.example.examenfinal.entities.Pokemon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PokemonService {
    @GET("N00039379")
    Call<List<Pokemon>> getAll();

    @POST("N00039379/crear")
    Call<Pokemon> create(@Body Pokemon pokemon);
}
