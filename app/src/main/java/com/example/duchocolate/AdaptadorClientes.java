package com.example.duchocolate;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorClientes extends RecyclerView.Adapter<AdaptadorClientes.ViewHolderClientes> {
    private Cursor cursor;
    private Context context;

   // public AdaptadorClientes(MainClientes Context) {
    //}


    public AdaptadorClientes(Context context) { this.context =context; }
    public void setCursor(Cursor cursor){
        if (this.cursor != cursor) {
            this.cursor= cursor;
        notifyDataSetChanged();


        }
    }
    @NonNull
    @Override
    public ViewHolderClientes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemcliento = LayoutInflater.from(context).inflate(R.layout.item_cliente, parent, false);
        return new ViewHolderClientes(itemcliento);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderClientes holder, int position) {
        cursor.moveToPosition(position);
        Cliente cliente= Cliente.fromCursor(cursor);
        holder.setCliente(cliente);

    }


    @Override
    public int getItemCount() {
        if (cursor ==null) return 0;
        return cursor.getCount ();
    }
    public Cliente getClientSelecionado() {
        if (viewHolderClientesSelecionado == null) return null;

        return viewHolderClientesSelecionado.cliente;
    }

    private static  ViewHolderClientes viewHolderClientesSelecionado= null;

    public class ViewHolderClientes extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView textViewNomeCliente;
        private TextView textViewPreço;
        private TextView textViewTelefone;
        private TextView textViewdata;
        private TextView textViewEmail;
        private TextView textViewEmpresa;
        private TextView textViewproduto;

        private  Cliente cliente;

        public ViewHolderClientes(@NonNull View itemView) {
            super(itemView);
            textViewNomeCliente = (TextView)itemView.findViewById(R.id.textViewNomeCliente);
            textViewPreço =  (TextView)itemView.findViewById(R.id.textViewPreço);
            textViewTelefone =  (TextView)itemView.findViewById(R.id.textViewTelefone);
            textViewdata = (TextView)itemView.findViewById(R.id.textViewdata);
            textViewEmail =  (TextView)itemView.findViewById(R.id.textViewEmail);
            textViewEmpresa =  (TextView)itemView.findViewById(R.id.textViewEmpresa);
            textViewproduto=(TextView) itemView.findViewById(R.id.textViewproduto);
            itemView.setOnClickListener(this);
        }

        public void setCliente(Cliente cliente){
            this.cliente= cliente;
            textViewNomeCliente.setText(cliente.getNomeCliente());
            textViewPreço.setText(String.valueOf(cliente.getPreço()));
            textViewTelefone.setText(String.valueOf(cliente.getTelefone()));
            textViewdata.setText(String.valueOf(cliente.getData()));
            textViewEmail.setText(String.valueOf(cliente.getEmail()));
            textViewEmpresa.setText(String.valueOf(cliente.getEmpresa()));
            textViewproduto.setText(String.valueOf(cliente.getProdutos()));

        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if (viewHolderClientesSelecionado != null) {
                viewHolderClientesSelecionado.DesSeleciona();
            }

            viewHolderClientesSelecionado = this;
            ((MainClientes) context).atualizaOpcoesMenu();
            Seleciona();

        }

        private void DesSeleciona() {
            itemView.setBackgroundResource(android.R.color.white);
        }

        private void Seleciona() {
            itemView.setBackgroundResource(R.color.endcastanho);
        }
    }
}
