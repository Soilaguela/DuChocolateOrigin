package com.example.duchocolate;

import android.content.ContentValues;
import android.database.Cursor;

public class Vendas {
    private long id;
    private String DescricaoProdutoV;
    private String Nomecliente;
    private String Data;
    private long produtos;

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

    public long getProdutos() {
        return produtos;
    }

    public void setProdutos(long produtos) {
        this.produtos = produtos;
    }

    public ContentValues getContentValues() {
        ContentValues valores = new ContentValues();


        valores.put(BDVendas.CAMPO_DESCRICAOVENDA, DescricaoProdutoV);
        valores.put(BDVendas.CAMPO_NOMECLIENTE, Nomecliente);
        valores.put(BDVendas.CAMPO_DATA, Data);
        valores.put(BDVendas.CAMPO_PRODUTOS, produtos);

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

        long produtos = cursor.getLong(
                cursor.getColumnIndex(BDVendas.CAMPO_PRODUTOS)
        );
        String Data = cursor.getString(
                cursor.getColumnIndex(BDVendas.CAMPO_DATA)
        );

        Vendas vendas = new Vendas();

        vendas.setId(id);
        vendas.setDescricaoProdutoV(DescricaoProdutoV);
        vendas.setNomecliente(Nomecliente);
        vendas.setData(Data);
        vendas. setProdutos(produtos);

        return vendas;
    }
}
