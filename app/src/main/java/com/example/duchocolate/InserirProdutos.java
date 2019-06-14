package com.example.duchocolate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class InserirProdutos extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_produtos);
    }
    public void cancelarProduto (View v){
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guardar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_guardar) {
            GuardarProdutos();
            return true;
        } else if (id == R.id.action_cancelar) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void GuardarProdutos (){//Inserir e as suas validaçes

        EditText ID_NP = (EditText) findViewById(R.id.ID_NP);//Nome cliente
        String NP = ID_NP.getText().toString();
        if (NP.trim().length() == 0) {//nome cliente
            ID_NP.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_NP.requestFocus();
            return;
        }
        /////////////////////////////////////////

        EditText ID_QuantE = (EditText) findViewById(R.id.ID_QuantE);//valor

        double valor = 0;//validação valor para maior k 0

        try {
            valor = Double.parseDouble(ID_QuantE.getText().toString());
        } catch (NumberFormatException e) {
            ID_QuantE.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_QuantE.requestFocus();
            return;
        }

        if (valor == 0) {
            ID_QuantE.setError(getString(R.string.erro_0));
            ID_QuantE.requestFocus();
            return;
        }

        /////////////////////////////////////////
        Toast.makeText(InserirProdutos.this,getString(R.string.GuardadoSucesso), Toast.LENGTH_LONG).show();//mensagem de guardar
        finish();
    }
    public void GuardarProdutos (View v){//Inserir e as suas validaçes

        EditText ID_NP = (EditText) findViewById(R.id.ID_NP);//Nome cliente
        String NP = ID_NP.getText().toString();
        if (NP.trim().length() == 0) {//nome cliente
            ID_NP.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_NP.requestFocus();
            return;
        }
        /////////////////////////////////////////

        EditText ID_QuantE = (EditText) findViewById(R.id.ID_QuantE);//valor

        double valor = 0;//validação valor para maior k 0

        try {
            valor = Double.parseDouble(ID_QuantE.getText().toString());
        } catch (NumberFormatException e) {
            ID_QuantE.setError(getString(R.string.erro_ID_prodotosvendidos));
            ID_QuantE.requestFocus();
            return;
        }

        if (valor == 0) {
            ID_QuantE.setError(getString(R.string.erro_0));
            ID_QuantE.requestFocus();
            return;
        }

        /////////////////////////////////////////
        Toast.makeText(InserirProdutos.this,getString(R.string.GuardadoSucesso), Toast.LENGTH_LONG).show();//mensagem de guardar
        finish();
    }
    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param id   The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    /**
     * Called when a previously created loader has finished its load.  Note
     * that normally an application is <em>not</em> allowed to commit fragment
     * transactions while in this call, since it can happen after an
     * activity's state is saved.  See {@link FragmentManager#beginTransaction()
     * FragmentManager.openTransaction()} for further discussion on this.
     *
     * <p>This function is guaranteed to be called prior to the release of
     * the last data that was supplied for this Loader.  At this point
     * you should remove all use of the old data (since it will be released
     * soon), but should not do your own release of the data since its Loader
     * owns it and will take care of that.  The Loader will take care of
     * management of its data so you don't have to.  In particular:
     *
     * <ul>
     * <li> <p>The Loader will monitor for changes to the data, and report
     * them to you through new calls here.  You should not monitor the
     * data yourself.  For example, if the data is a {@link Cursor}
     * and you place it in a {@link CursorAdapter}, use
     * the {@link CursorAdapter#CursorAdapter(Context,
     * Cursor, int)} constructor <em>without</em> passing
     * in either {@link CursorAdapter#FLAG_AUTO_REQUERY}
     * or {@link CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
     * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
     * from doing its own observing of the Cursor, which is not needed since
     * when a change happens you will get a new Cursor throw another call
     * here.
     * <li> The Loader will release the data once it knows the application
     * is no longer using it.  For example, if the data is
     * a {@link Cursor} from a {@link CursorLoader},
     * you should not call close() on it yourself.  If the Cursor is being placed in a
     * {@link CursorAdapter}, you should use the
     * {@link CursorAdapter#swapCursor(Cursor)}
     * method so that the old Cursor is not closed.
     * </ul>
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that has finished.
     * @param data   The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.  The application should at this point
     * remove any references it has to the Loader's data.
     *
     * <p>This will always be called from the process's main thread.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
