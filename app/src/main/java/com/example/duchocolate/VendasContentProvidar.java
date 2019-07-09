package com.example.duchocolate;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class VendasContentProvidar extends ContentProvider {
    public static final String AUTHORITY = "com.example.duchocolate";
    public static final String CLIENTE = "cliente";
    public static final String VENDAS = "vendas";
    public static final String PRODUTOS = "produto";


    private static final Uri ENDERECO_BASE = Uri.parse("content://" + AUTHORITY);
    public static final Uri ENDERECO_CLIENTE = Uri.withAppendedPath(ENDERECO_BASE, CLIENTE);
    public static final Uri ENDERECO_VENDAS = Uri.withAppendedPath(ENDERECO_BASE, VENDAS);
     public static final Uri ENDERECO_PRODUTOS = Uri.withAppendedPath(ENDERECO_BASE, PRODUTOS);



    public static final int URI_CLIENTE = 100;
    public static final int URI_UNICA_CLIENTE  = 101;
    public static final int URI_VENDAS = 200;
    public static final int URI_UNICA_VENDAS  = 201;
    public static final int URI_PRODUTOS = 300;
    public static final int URI_UNICA_PRODUTOS = 301;

    public static final String UNICO_ITEM = "vnd.android.cursor.item/";
    public static final String MULTIPLOS_ITEMS = "vnd.android.cursor.dir/";

    private  BDVendasOpenHelper bdVendasOpenHelper;

    private UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, CLIENTE, URI_CLIENTE);
        uriMatcher.addURI(AUTHORITY, CLIENTE + "/#", URI_UNICA_CLIENTE);
        uriMatcher.addURI(AUTHORITY, VENDAS, URI_VENDAS);
        uriMatcher.addURI(AUTHORITY, VENDAS + "/#", URI_UNICA_VENDAS);
        uriMatcher.addURI(AUTHORITY, PRODUTOS, URI_PRODUTOS);
        uriMatcher.addURI(AUTHORITY, PRODUTOS + "/#", URI_UNICA_PRODUTOS);


        return uriMatcher;
    }


    /**
     * Implement this to initialize your content provider on startup.
     * This method is called for all registered content providers on the
     * application main thread at application launch time.  It must not perform
     * lengthy operations, or application startup will be delayed.
     *
     * <p>You should defer nontrivial initialization (such as opening,
     * upgrading, and scanning databases) until the content provider is used
     * (via {@link #query}, {@link #insert}, etc).  Deferred initialization
     * keeps application startup fast, avoids unnecessary work if the provider
     * turns out not to be needed, and stops database errors (such as a full
     * disk) from halting application launch.
     *
     * <p>If you use SQLite, {@link SQLiteOpenHelper}
     * is a helpful utility class that makes it easy to manage databases,
     * and will automatically defer opening until first use.  If you do use
     * SQLiteOpenHelper, make sure to avoid calling
     * {@link SQLiteOpenHelper#getReadableDatabase} or
     * {@link SQLiteOpenHelper#getWritableDatabase}
     * from this method.  (Instead, override
     * {@link SQLiteOpenHelper#onOpen} to initialize the
     * database when it is first opened.)
     *
     * @return true if the provider was successfully loaded, false otherwise
     */
    @Override
    public boolean onCreate() {
        bdVendasOpenHelper = new BDVendasOpenHelper(getContext());

        return true;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase bd = bdVendasOpenHelper.getReadableDatabase();
        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)){
            case  URI_CLIENTE:
                return new BDCliente(bd).query(projection, selection, selectionArgs,null, null,sortOrder);
            case URI_UNICA_CLIENTE:
                return  new BDCliente(bd).query(projection, "clientes." + BDCliente._ID + "=?",new String[]{id},null,null, null);
            case URI_PRODUTOS:
                return new BDProduto(bd).query(projection,selection, selectionArgs, null,null,sortOrder);
            case URI_UNICA_PRODUTOS:
                return new  BDProduto(bd).query(projection, BDProduto._ID + "=?",new String[]{id},null ,null,null);
            case URI_VENDAS:
                return  new BDVendas(bd).query(projection,selection, selectionArgs, null,null,sortOrder);
            case URI_UNICA_VENDAS:
                return new BDVendas(bd).query(projection,  "vendas._id=?", new String[]{id},null, null,null);
//BDVendas._ID +
            default:
                throw new UnsupportedOperationException("URI inválida (QUERY): " + uri.toString());
        }

    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (getUriMatcher().match(uri)) {

            case URI_CLIENTE:
                return MULTIPLOS_ITEMS + CLIENTE;
            case URI_UNICA_CLIENTE:
                return UNICO_ITEM + CLIENTE;

            case URI_PRODUTOS:
                return MULTIPLOS_ITEMS + PRODUTOS;
            case URI_UNICA_PRODUTOS:
                return UNICO_ITEM + PRODUTOS;

            case URI_VENDAS:
                return UNICO_ITEM + VENDAS;
            case URI_UNICA_VENDAS:
                return UNICO_ITEM + VENDAS;
            default:
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase bd= bdVendasOpenHelper.getWritableDatabase();
        long id= -1;

       switch (getUriMatcher().match(uri)) {
            case URI_CLIENTE:
                id = new BDCliente(bd).insert(values);
                break;

            case URI_VENDAS:
                id = new BDVendas(bd).insert(values);
                break;

            case URI_PRODUTOS:
                id = new BDProduto(bd).insert(values);
                break;

            default:
                throw new UnsupportedOperationException("URI inválida (INSERT):" + uri.toString());
        }

        if (id == -1) {
            throw new SQLException("Não foi possível inserir o registo");
        }


        return Uri.withAppendedPath(uri, String.valueOf(id));
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
      SQLiteDatabase bd = bdVendasOpenHelper.getWritableDatabase();

        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_UNICA_VENDAS:
                return new BDVendas(bd).delete(BDVendas._ID + "=?", new String[]{id});
            case URI_UNICA_PRODUTOS:
                return new BDProduto(bd).delete(BDProduto._ID + "=?", new String[]{id});
            case URI_UNICA_CLIENTE:
                return new BDCliente(bd).delete(BDCliente._ID + "=?", new String[]{id});
            default:
                throw new UnsupportedOperationException("URI inválida (DELETE): " + uri.toString());

        }
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase bd = bdVendasOpenHelper.getWritableDatabase();

        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_UNICA_VENDAS:
                return new BDVendas(bd).update(values,BDVendas._ID + "=?", new String[]{id});
            case URI_UNICA_PRODUTOS:
                return new BDProduto(bd).update(values,BDProduto._ID + "=?", new String[]{id});
            case URI_UNICA_CLIENTE:
                return new BDCliente(bd).update(values,BDCliente._ID + "=?", new String[]{id});
            default:
                throw new UnsupportedOperationException("URI inválida (DELETE): " + uri.toString());

        }

    }


}
