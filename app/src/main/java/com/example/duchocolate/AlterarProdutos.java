package com.example.duchocolate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AlterarProdutos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_produtos);
    }
    public void cancelarAlteracaoProduto (View v){
        finish();
    }
    public void AlterarProduto (View V){
        EditText id_ADP = (EditText) findViewById(R.id.id_ADP);//Nome cliente
        String NP = id_ADP.getText().toString();
        if (NP.trim().length() == 0) {//nome cliente
            id_ADP.setError(getString(R.string.erro_ID_prodotosvendidos));
            id_ADP.requestFocus();
            return;
        }
        /////////////////////////////////////////

        EditText ID_AQ = (EditText) findViewById(R.id.ID_AQ);//valor

        double valor = 0;//validação valor para maior k 0

        try {
            valor = Double.parseDouble(ID_AQ.getText().toString());
        } catch (NumberFormatException e) {
            ID_AQ.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_AQ.requestFocus();
            return;
        }

        if (valor == 0) {
            ID_AQ.setError(getString(R.string.erro_0));
            ID_AQ.requestFocus();
            return;
        }

        Toast.makeText(AlterarProdutos.this,getString(R.string.AlterarSucesso), Toast.LENGTH_LONG).show();//mensagem de guardar
        finish();
    }
}
