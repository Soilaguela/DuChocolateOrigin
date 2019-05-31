package com.example.duchocolate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EleminarProdutos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eleminar_produtos);
    }
    public void cancelarEleminarProduto (View v){
        finish();
    }

    public void EleminarProdutos (View v){//Inserir e as suas validaçes

        EditText id_eDP = (EditText) findViewById(R.id.id_eDP);//Nome cliente
        String NP = id_eDP.getText().toString();
        if (NP.trim().length() == 0) {//nome cliente
            id_eDP.setError(getString(R.string.erro_ID_prodotosvendidos));
            id_eDP.requestFocus();
            return;
        }
        /////////////////////////////////////////

        EditText ID_EQ = (EditText) findViewById(R.id.ID_EQ);//valor

        double valor = 0;//validação valor para maior k 0

        try {
            valor = Double.parseDouble(ID_EQ.getText().toString());
        } catch (NumberFormatException e) {
            ID_EQ.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_EQ.requestFocus();
            return;
        }

        if (valor == 0) {
            ID_EQ.setError(getString(R.string.erro_0));
            ID_EQ.requestFocus();
            return;
        }

        /////////////////////////////////////////
        Toast.makeText(EleminarProdutos.this,getString(R.string.EleminarSucesso), Toast.LENGTH_LONG).show();//mensagem de guardar
        finish();
    }
}
