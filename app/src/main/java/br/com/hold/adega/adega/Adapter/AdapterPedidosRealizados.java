package br.com.hold.adega.adega.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Model.Pedido;

public class AdapterPedidosRealizados  extends RecyclerView.Adapter<AdapterPedidosRealizados.MyViewHolder> {

    private List<Pedido> listaPedidos;
    private Context context;



    public AdapterPedidosRealizados(List<Pedido> pp, Context cc) {
        this.listaPedidos = pp;
        this.context = cc;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pedidos_realizados, parent,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Pedido pedido = listaPedidos.get(position);
//        holder.textTempoEntrega.setText(pedido.getValoresPedido().getDataPedido());
        holder.textPedidoN.setText(position + pedido.getUsuario().getNome());
        holder.textValor.setText("R$ " + String.valueOf(pedido.getValoresPedido().getValorTotalProduto()));
        holder.textStatus.setText(pedido.getValoresPedido().getStatusPedido());
//        holder.textDistancia.setText(pedido.);

//        holder.buttonStatus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context.getApplicationContext(),PopActivity.class);
//                context.getApplicationContext().startActivity(i);
//            }
//        });


    }

    @Override
    public int getItemCount() {


        return listaPedidos.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textPedidoN;
        TextView textValor;
        TextView textStatus;
        TextView textTempoEntrega;
        TextView textDistancia;

        public MyViewHolder(View itemView) {
            super(itemView);

            textPedidoN = itemView.findViewById(R.id.textNumeroNome);
            textValor = itemView.findViewById(R.id.textValorPedido);
            textStatus = itemView.findViewById(R.id.textStatusPedido);
            textTempoEntrega = itemView.findViewById(R.id.textTempoEntrega);
            textDistancia = itemView.findViewById(R.id.textDistancia);
        }
    }















































}
