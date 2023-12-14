package com.example.cepapi.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CepDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cep.db";
    private static final int DATABASE_VERSION = 1;

    public CepDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_CEP_TABLE =
                "CREATE TABLE " + CepContract.CepEntry.TABLE_NAME + " (" +
                        CepContract.CepEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        CepContract.CepEntry.COLUMN_CEP + " TEXT NOT NULL, " +
                        CepContract.CepEntry.COLUMN_LOGRADOURO + " TEXT, " +
                        CepContract.CepEntry.COLUMN_BAIRRO + " TEXT, " +
                        CepContract.CepEntry.COLUMN_LOCALIDADE + " TEXT, " +
                        CepContract.CepEntry.COLUMN_UF + " TEXT);";

        db.execSQL(SQL_CREATE_CEP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Atualizar o banco, proximas atualizações!!!
    }
}