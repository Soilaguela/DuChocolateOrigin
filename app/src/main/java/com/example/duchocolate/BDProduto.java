package com.example.duchocolate;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class BDProduto implements BaseColumns {
    public static final String NOME_TABELA = "produto";
    public static final String CAMPO_PRODUTOESTOQUE = "Produtoestoque";
    public static final String CAMPO_QUANTIDADE = "Quantidade";


    public static final String[] TODAS_COLUNAS = new String[] {
            _ID, CAMPO_PRODUTOESTOQUE, CAMPO_QUANTIDADE};
    private SQLiteDatabase db;

    public BDProduto(SQLiteDatabase db){
        this.db =db;
    }

    public void cria(){
        db.execSQL(
                "CREATE TABLE " + NOME_TABELA + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CAMPO_PRODUTOESTOQUE + " INTEGER NOT NULL," +
                        CAMPO_QUANTIDADE + " INTEGER NOT NULL" +
                        ")"
        );
    }

    public Cursor query(String[]columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.query(NOME_TABELA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public long insert(ContentValues values) {
        return db.insert(NOME_TABELA, null, values);
    }

    public int delete (String whereClause, String[] whereArgs) {
        return db.delete(NOME_TABELA, whereClause, whereArgs);
    }

    public int update(ContentValues values, String whereClause, String [] whereArgs) {
        return db.update(NOME_TABELA, values, whereClause, whereArgs);
    }
}
