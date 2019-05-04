package br.com.hold.adega.adega.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Model.Produto;

public class AdapterCardapio  extends RecyclerView.Adapter<AdapterCardapio.MyViewHolder> {


    private List<Produto> produtos;
    private Context context;

    public AdapterCardapio(List<Produto> pp, Context cc) {
        this.produtos = pp;
        this.context = cc;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cardapio, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        Produto produto = produtos.get(position);
        holder.nome.setText(produto.getNome());
        holder.valor.setText("R$"+ String.valueOf(produto.getValor()));
        String uriImagem = produto.getUrl();
        Picasso.get().load(uriImagem).into(holder.foto);


    }


    @Override
    public int getItemCount() {

        return produtos.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView valor;
        ImageView foto;


        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.textNProduto);
            valor = itemView.findViewById(R.id.textValorProduto);
            foto = itemView.findViewById(R.id.imageProduto);
        }
    }

}
