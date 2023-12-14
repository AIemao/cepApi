package com.example.cepapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.cepapi.api.ApiClient;
import com.example.cepapi.model.CepResponse;
import com.example.cepapi.repository.CepRepository;

public class MainActivity extends AppCompatActivity {

    private EditText editTextCep;
    private Button buttonSearch;    private TextView textViewCep, textViewLogradouro, textViewBairro, textViewLocalidade, textViewUf;

    private CepRepository cepRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCep = findViewById(R.id.editTextCep);
        buttonSearch = findViewById(R.id.buttonSearch);
        textViewCep = findViewById(R.id.textViewCep);
        textViewLogradouro = findViewById(R.id.textViewLogradouro);
        textViewBairro = findViewById(R.id.textViewBairro);
        textViewLocalidade = findViewById(R.id.textViewLocalidade);
        textViewUf = findViewById(R.id.textViewUf);

        cepRepository = new CepRepository(this);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cep = editTextCep.getText().toString();
                if (!cep.isEmpty()) {
                    searchCep(cep);
                }
            }
        });
    }

    private void searchCep(String cep) {
        cepRepository.getCepDetailsViaCep(cep, new CepRepository.CepCallback() {
            @Override
            public void onSuccess(CepResponse cepResponse) {
                updateUi(cepResponse);
            }

            @Override
            public void onError(String errorMessage) {
                showErrorMessage(errorMessage);
            }
        });
    }

    private void updateUi(CepResponse cepResponse) {
        if (cepResponse != null && cepResponse.getCep() != null && !cepResponse.getCep().isEmpty()) {
            textViewCep.setText("CEP: " + cepResponse.getCep());
            textViewLogradouro.setText("Logradouro: " + cepResponse.getLogradouro());
            textViewBairro.setText("Bairro: " + cepResponse.getBairro());
            textViewLocalidade.setText("Localidade: " + cepResponse.getLocalidade());
            textViewUf.setText("UF: " + cepResponse.getUf());
        } else {
            showErrorMessage("Erro na resposta do serviço");
        }
    }

    private void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}