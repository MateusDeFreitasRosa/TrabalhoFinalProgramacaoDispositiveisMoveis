package com.example.stack10.control;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "datas";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String CHAVE_IDENTIFICADOR = "logado";

    public Preferencias(Context contextoParametro){

        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = preferences.edit();
    }
    public void salvarDados(String uid) {

        editor.clear();
        editor.putString(CHAVE_IDENTIFICADOR, uid);
        editor.commit();
    }
    public String getIdentificador(){
        return preferences.getString(CHAVE_IDENTIFICADOR, "");
    }

}
