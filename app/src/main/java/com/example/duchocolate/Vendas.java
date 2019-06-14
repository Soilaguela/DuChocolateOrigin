package com.example.duchocolate;

import android.content.ContentValues;
import android.database.Cursor;

public class Vendas {
    private long id;
    private String DescricaoProdutoV;
    private String Nomecliente;
    private String Data;
    private long cliente;
    private String nomeVenda; // Campo "externo"


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricaoProdutoV() {
        return DescricaoProdutoV;
    }

    public void setDescricaoProdutoV(String descricaoProdutoV) {
        DescricaoProdutoV = descricaoProdutoV;
    }

    public String getNomecliente() {
        return Nomecliente;
    }

    public void setNomecliente(String nomecliente) {
        Nomecliente = nomecliente;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public long getCliente() {
        return cliente;
    }

    public void setCliente(long cliente) {
        this.cliente = cliente;
    }

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();


        valores.put(BDVendas.CAMPO_DESCRICAOVENDA, DescricaoProdutoV);
        valores.put(BDVendas.CAMPO_NOMECLIENTE, Nomecliente);
        valores.put(BDVendas.CAMPO_DATA, Data);
        valores.put(BDVendas.CAMPO_CLIENTE, cliente);

        return valores;
    }
    public static Vendas fromCursor(Cursor cursor) {
        long id = cursor.getLong(
                cursor.getColumnIndex(BDVendas._ID)
        );

        String DescricaoProdutoV = cursor.getString(
                cursor.getColumnIndex(BDVendas.CAMPO_DESCRICAOVENDA)
        );

        String Nomecliente = cursor.getString(
                cursor.getColumnIndex(BDVendas.CAMPO_NOMECLIENTE)
        );

        long cliente = cursor.getLong(
                cursor.getColumnIndex(BDVendas.CAMPO_CLIENTE)
        );
        String Data = cursor.getString(
                cursor.getColumnIndex(BDVendas.CAMPO_DATA)
        );
        String nomeVenda = cursor.getString(
                cursor.getColumnIndex(BDVendas.ALIAS_NOME_VENDA)
        );

        Vendas vendas = new Vendas();

        vendas.setId(id);
        vendas.setDescricaoProdutoV(DescricaoProdutoV);
        vendas.setNomecliente(Nomecliente);
        vendas.setData(Data);
        vendas.setCliente(cliente);
        vendas.nomeVenda = nomeVenda;


        return vendas;
    }
}
