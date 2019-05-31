package com.example.duchocolate;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BDVendas implements BaseColumns {
    public static final String NOME_TABELA = "vendas";


    public static final String CAMPO_DESCRICAOVENDA = "DescricaoProdutoV";
    public static final String CAMPO_NOMECLIENTE = "Nomecliente";
    public static final String CAMPO_DATA="Data";
    public static final String CAMPO_PRODUTOS = "produtos";

    public static final String[] TODAS_COLUNAS = new String[] {
            _ID, CAMPO_DESCRICAOVENDA , CAMPO_NOMECLIENTE, CAMPO_DATA, CAMPO_PRODUTOS};


    private SQLiteDatabase db;

    public BDVendas(SQLiteDatabase db){
        this.db =db;
    }

    public void cria(){
        db.execSQL( "CREATE TABLE " + NOME_TABELA + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CAMPO_DESCRICAOVENDA + " TEXT NOT NULL," +
                CAMPO_DATA+ " TEXT NOT NULL," +
                CAMPO_NOMECLIENTE + " TEXT NOT NULL," +
                CAMPO_PRODUTOS + " INTEGER  NOT NULL,"+
                "FOREIGN KEY (" + CAMPO_PRODUTOS + ") REFERENCES " + BDProduto.NOME_TABELA + "(" + BDProduto._ID + ")" +
                ")"
        );
    }

    public Cursor query(String[]columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public long insert(ContentValues values) {
        return  db.insert(NOME_TABELA, null, values);
    }

    public int update(ContentValues values, String whereClause, String [] whereArgs) {
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }

    public int delete(String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }
}
