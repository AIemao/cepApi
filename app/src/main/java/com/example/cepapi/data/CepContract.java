package com.example.cepapi.data;

import android.provider.BaseColumns;

public class CepContract {
    private CepContract() {
        // Evita que a classe seja instanciada acidentalmente.
    }

    public static class CepEntry implements BaseColumns {
        public static final String TABLE_NAME = "cep";
        public static final String COLUMN_CEP = "cep";
        public static final String COLUMN_LOGRADOURO = "logradouro";
        public static final String COLUMN_BAIRRO = "bairro";
        public static final String COLUMN_LOCALIDADE = "localidade";
        public static final String COLUMN_UF = "uf";
    }
}
