package com.example.cepapi.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String VIA_CEP_BASE_URL = "https://viacep.com.br/ws/";
    private static final String APICEP_BASE_URL = "https://apicep.com/api-de-consulta/";
    private static final String AWESOME_API_BASE_URL = "https://api.awesomeapi.com.br/";

    private static Retrofit viaCepRetrofit = null;
    private static Retrofit apicepRetrofit = null;
    private static Retrofit awesomeApiRetrofit = null;

    public static CepApiService getViaCepClient() {
        if (viaCepRetrofit == null) {
            viaCepRetrofit = new Retrofit.Builder()
                    .baseUrl(VIA_CEP_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return viaCepRetrofit.create(CepApiService.class);
    }

    public static CepApiService getApicepClient() {
        if (apicepRetrofit == null) {
            apicepRetrofit = new Retrofit.Builder()
                    .baseUrl(APICEP_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return apicepRetrofit.create(CepApiService.class);
    }

    public static CepApiService getAwesomeApiClient() {
        if (awesomeApiRetrofit == null) {
            awesomeApiRetrofit = new Retrofit.Builder()
                    .baseUrl(AWESOME_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return awesomeApiRetrofit.create(CepApiService.class);
    }
}