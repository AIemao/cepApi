package com.example.cepapi.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cepapi.api.ApiClient;
import com.example.cepapi.api.CepApiService;
import com.example.cepapi.data.CepContract;
import com.example.cepapi.data.CepDbHelper;
import com.example.cepapi.model.CepResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CepRepository {
    private CepApiService viaCepService;
    private CepApiService apicepService;
    private CepApiService awesomeApiService;
    private CepDbHelper dbHelper;

    public CepRepository(Context context) {
        viaCepService = ApiClient.getViaCepClient();
        apicepService = ApiClient.getApicepClient();
        awesomeApiService = ApiClient.getAwesomeApiClient();
        dbHelper = new CepDbHelper(context);
    }

    public void getCepDetailsViaCep(String cep, final CepCallback callback) {
        Call<CepResponse> call = viaCepService.getCepDetails(cep);
        call.enqueue(new Callback<CepResponse>() {
            @Override
            public void onResponse(Call<CepResponse> call, Response<CepResponse> response) {
                if (response.isSuccessful()) {
                    CepResponse cepResponse = response.body();
                    if (cepResponse != null) {
                        saveCepToDatabase(cepResponse);
                        callback.onSuccess(cepResponse);
                    } else {
                        callback.onError("Resposta nula do serviço");
                    }
                } else {
                    callback.onError("Erro na resposta do serviço CEP Inválido.");
                }
            }

            @Override
            public void onFailure(Call<CepResponse> call, Throwable t) {
                callback.onError("Falha na requisição");
            }
        });
    }

    public void getCepDetailsApicep(String cep, final CepCallback callback) {
        Call<CepResponse> call = apicepService.getCepDetails(cep);
        call.enqueue(new Callback<CepResponse>() {
            @Override
            public void onResponse(Call<CepResponse> call, Response<CepResponse> response) {
                if (response.isSuccessful()) {
                    CepResponse cepResponse = response.body();
                    if (cepResponse != null) {
                        saveCepToDatabase(cepResponse);
                        callback.onSuccess(cepResponse);
                    } else {
                        callback.onError("Resposta nula do serviço");
                    }
                } else {
                    callback.onError("Erro na resposta do serviço CEP Inválido.");
                }
            }

            @Override
            public void onFailure(Call<CepResponse> call, Throwable t) {
                callback.onError("Falha na requisição");
            }
        });
    }

    public void getCepDetailsAwesomeApi(String cep, final CepCallback callback) {
        Call<CepResponse> call = awesomeApiService.getCepDetails(cep);
        call.enqueue(new Callback<CepResponse>() {
            @Override
            public void onResponse(Call<CepResponse> call, Response<CepResponse> response) {
                if (response.isSuccessful()) {
                    CepResponse cepResponse = response.body();
                    if (cepResponse != null) {
                        saveCepToDatabase(cepResponse);
                        callback.onSuccess(cepResponse);
                    } else {
                        callback.onError("Resposta nula do serviço");
                    }
                } else {
                    callback.onError("Erro na resposta do serviço CEP Inválido.");
                }
            }

            @Override
            public void onFailure(Call<CepResponse> call, Throwable t) {
                callback.onError("Falha na requisição");
            }
        });
    }

    private long saveCepToDatabase(CepResponse cepResponse) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CepContract.CepEntry.COLUMN_CEP, cepResponse.getCep());
        values.put(CepContract.CepEntry.COLUMN_LOGRADOURO, cepResponse.getLogradouro());
        values.put(CepContract.CepEntry.COLUMN_BAIRRO, cepResponse.getBairro());
        values.put(CepContract.CepEntry.COLUMN_LOCALIDADE, cepResponse.getLocalidade());
        values.put(CepContract.CepEntry.COLUMN_UF, cepResponse.getUf());

        return db.insert(CepContract.CepEntry.TABLE_NAME, null, values);
    }

    public List<CepResponse> getAllSavedCeps() {
        List<CepResponse> cepList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                CepContract.CepEntry.COLUMN_CEP,
                CepContract.CepEntry.COLUMN_LOGRADOURO,
                CepContract.CepEntry.COLUMN_BAIRRO,
                CepContract.CepEntry.COLUMN_LOCALIDADE,
                CepContract.CepEntry.COLUMN_UF
        };

        Cursor cursor = db.query(
                CepContract.CepEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            CepResponse cepResponse = new CepResponse();
            cepResponse.setCep(cursor.getString(cursor.getColumnIndexOrThrow(CepContract.CepEntry.COLUMN_CEP)));
            cepResponse.setLogradouro(cursor.getString(cursor.getColumnIndexOrThrow(CepContract.CepEntry.COLUMN_LOGRADOURO)));
            cepResponse.setBairro(cursor.getString(cursor.getColumnIndexOrThrow(CepContract.CepEntry.COLUMN_BAIRRO)));
            cepResponse.setLocalidade(cursor.getString(cursor.getColumnIndexOrThrow(CepContract.CepEntry.COLUMN_LOCALIDADE)));
            cepResponse.setUf(cursor.getString(cursor.getColumnIndexOrThrow(CepContract.CepEntry.COLUMN_UF)));

            cepList.add(cepResponse);
        }

        cursor.close();
        return cepList;
    }

    public interface CepCallback {
        void onSuccess(CepResponse cepResponse);

        void onError(String errorMessage);
    }
}
