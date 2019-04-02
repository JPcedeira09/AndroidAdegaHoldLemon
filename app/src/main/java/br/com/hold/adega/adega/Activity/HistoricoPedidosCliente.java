package br.com.hold.adega.adega.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Adapter.AdapterPedidosCliente;
import br.com.hold.adega.adega.Config.FirebaseConfig;
import br.com.hold.adega.adega.Model.ItensCarrinho;
import br.com.hold.adega.adega.Model.Pedido;
import br.com.hold.adega.adega.Model.Usuario;
import br.com.hold.adega.adega.Model.ValoresPedido;
import br.com.hold.adega.adega.Util.FirebaseChildsUtils;

public class HistoricoPedidosCliente extends AppCompatActivity {

    private RecyclerView recyclerPedidos;
    private List<Pedido> pedidos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_pedidos_cliente);

        //sumir a toolbar
        getSupportActionBar().hide();

        //Inicializar os compomentes
        recyclerPedidos = findViewById(R.id.recyclerPedidos);


        //Configura recyclerView
        recyclerPedidos.setLayoutManager(new LinearLayoutManager(this));
        recyclerPedidos.setHasFixedSize(true);
       final AdapterPedidosCliente adapterPedidos = new AdapterPedidosCliente(pedidos,this);
        recyclerPedidos.setAdapter(adapterPedidos);


        //Trazer dados do Firebase
        FirebaseChildsUtils.getRetornoHistorico(FirebaseConfig.getFirebaseAutentificacao().getUid())
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pedidos.clear();
                System.out.println(dataSnapshot);


                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Pedido pedido = new Pedido();

                    final DataSnapshot itensDS = ds.child("Itens");
                    List<ItensCarrinho> itensCarrinhos= new ArrayList<ItensCarrinho>();
                    for(DataSnapshot dsItem : itensDS.getChildren()){
                        ItensCarrinho itensCarrinho = dsItem.getValue(ItensCarrinho.class);
                        itensCarrinhos.add(itensCarrinho);
                        pedido.setItensCarrinho(itensCarrinhos);
                        System.out.println(ds);
                        System.out.println(itensCarrinho);

                    }

                    final DataSnapshot valoresPedidoDS = ds.child("ValoresPedido");
                    ValoresPedido valoresPedido = valoresPedidoDS.getValue(ValoresPedido.class);
                    pedido.setValoresPedido(valoresPedido);

                    final DataSnapshot usuarioDS = ds.child("DadosCliente");
                    Usuario usuario = usuarioDS.getValue(Usuario.class);
                    pedido.setUsuario(usuario);

                    pedidos.add(pedido);



                }
                adapterPedidos.notifyDataSetChanged();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
