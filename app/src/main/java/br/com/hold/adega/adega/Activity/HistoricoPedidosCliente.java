package br.com.hold.adega.adega.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Adapter.AdapterPedidosCliente;
import br.com.hold.adega.adega.Model.ValoresPedido;

public class HistoricoPedidosCliente extends AppCompatActivity {

    private RecyclerView recyclerPedidos;
    private List<ValoresPedido> pedidos = new ArrayList<>();
    private AdapterPedidosCliente adapterPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_pedidos_cliente);

        //Inicializar os compomentes
        recyclerPedidos = findViewById(R.id.recyclerPedidos);


        //Configura recyclerView
        recyclerPedidos.setLayoutManager(new LinearLayoutManager(this));
        recyclerPedidos.setHasFixedSize(true);
        adapterPedidos = new AdapterPedidosCliente(pedidos,this);
        recyclerPedidos.setAdapter(adapterPedidos);
    }
}
