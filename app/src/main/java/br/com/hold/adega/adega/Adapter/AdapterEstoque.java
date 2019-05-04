package br.com.hold.adega.adega.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Config.FirebaseConfig;
import br.com.hold.adega.adega.Model.Produto;

public class AdapterEstoque extends RecyclerView.Adapter<AdapterEstoque.MyViewHolder>  {

    private List<Produto> produtos;
    private Context context;

    public AdapterEstoque(List<Produto> produtos, Context context) {
        this.produtos = produtos;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterEstoque.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_estoque, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {
        Produto produto = produtos.get(i);
        holder.nome.setText(produto.getNome());
        holder.valor.setText("R$ " + produto.getValor());

        String uriImagem = produto.getImagemProduto();
        Picasso.get().load(uriImagem).into(holder.imagemProduto);

//        if(produto.isDisponivel() == true){
//            holder.disponivel.setText("Disponivel");
//        }else{
//            holder.disponivel.setText("Indisponivel");
//        }
    }



    @Override
    public int getItemCount() {

        return produtos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView valor;
        TextView disponivel;
        ImageView imagemProduto;

        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txtNomeProdutoPedido);
            valor = itemView.findViewById(R.id.txtValorProdutoPedido);
            disponivel = itemView.findViewById(R.id.txtDisponivelProdutoPedido);
            imagemProduto = itemView.findViewById(R.id.imageProduto);

        }




    }






























}
