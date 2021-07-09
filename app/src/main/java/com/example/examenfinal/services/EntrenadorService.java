package com.example.examenfinal.services;

import com.example.examenfinal.entities.Entranador;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EntrenadorService {

    @GET("N00039379")
    Call<Entranador> getAll();

    @POST("N00039379")
    Call<Entranador> create(@Body Entranador entranador);
}
