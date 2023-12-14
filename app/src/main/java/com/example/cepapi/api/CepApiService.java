package com.example.cepapi.api;

import com.example.cepapi.model.CepResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CepApiService {
    @GET("{cep}/json/")
    Call<CepResponse> getCepDetails(@Path("cep") String cep);
}