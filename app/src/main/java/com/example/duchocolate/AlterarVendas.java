package com.example.duchocolate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AlterarVendas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_vendas);
    }
    public void canselarAlteracao (View v){
        finish();
    }
    public void Alterarvendas (View V){
        EditText id_descProduto = (EditText) findViewById(R.id.id_descProduto);//Nome cliente
        String nomecliente = id_descProduto.getText().toString();

        if (nomecliente.trim().length() == 0) {  //nome cliente
            id_descProduto.setError(getString(R.string.erro_ID_prodotosvendidos));
            id_descProduto.requestFocus();
            return ;
        }

        //////////////////////////////////
        EditText id_ClienteNome = (EditText) findViewById(R.id.id_ClienteNome);//Nome cliente
        String ClienteNome = id_descProduto.getText().toString();

        if (ClienteNome.trim().length() == 0) {  //nome cliente
            id_descProduto.setError(getString(R.string.erro_ID_prodotosvendidos));
            id_descProduto.requestFocus();
            return ;
        }

        Toast.makeText(AlterarVendas.this,getString(R.string.AlterarSucesso), Toast.LENGTH_LONG).show();//mensagem de guardar
        finish();
    }
}
