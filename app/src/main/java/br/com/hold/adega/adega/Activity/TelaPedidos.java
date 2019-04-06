package br.com.hold.adega.adega.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Adapter.AdapterTelaPedido;
import br.com.hold.adega.adega.Model.ItensCarrinho;
import br.com.hold.adega.adega.Model.Pedido;
import br.com.hold.adega.adega.Util.FirebaseChildsUtils;

public class TelaPedidos extends AppCompatActivity {

    private TextView editNome,editNumeroPedido,editEndereco,editTotal,editCpf;
    private RecyclerView recyclerPedidos;
    private Button confitmaPedido;
    private static List<ItensCarrinho> itensCarrinho = new ArrayList<>();
    private static Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_pedidos);

        Intent intent = getIntent();
        pedido = (Pedido) intent.getSerializableExtra("selecionado");

        System.out.println(pedido.toString());
        System.out.println("___________________________________________________________________________--");
        System.out.println("-----------------------------------------------------");
        System.out.println(pedido.toString());


        //Inicializar Componentes
        editNome = findViewById(R.id.txtNomeClientePedido);
        editNumeroPedido = findViewById(R.id.txtNumeroPedido);
        editEndereco = findViewById(R.id.txtEnderecoEntregaPedido);
        editTotal = findViewById(R.id.textValorTotal);
        editCpf = findViewById(R.id.txtCPFClientePedido);
        recyclerPedidos = findViewById(R.id.recyclerItemPedido);
        confitmaPedido = findViewById(R.id.buttonConfirmarPedidoLoja);

        //Configura o RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerPedidos.setLayoutManager(layoutManager);
        recyclerPedidos.setHasFixedSize(true);
        final AdapterTelaPedido adapterTelaPedido = new AdapterTelaPedido(itensCarrinho,this);
        recyclerPedidos.setAdapter(adapterTelaPedido);

        //Recupera dados do Firebase
        FirebaseChildsUtils.getOPedido(pedido.getKey()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        itensCarrinho.clear();
                        for (DataSnapshot child: dataSnapshot.getChildren()){
                            ItensCarrinho carrinho = child.getValue(ItensCarrinho.class);
                            itensCarrinho.add(carrinho);
                        }


                        adapterTelaPedido.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


//        FirebaseChildsUtils.getUsuario(FirebaseConfig.getFirebaseAutentificacao().getUid())
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        Usuario usuario = dataSnapshot.getValue(Usuario.class);
//                        editNome.setText(usuario.getNome());
//                        editCpf.setText(usuario.getCpf());
//                        editEndereco.setText(usuario.getEndereco() + usuario.getNumero() + usuario.getComplemento());
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//
//        FirebaseChildsUtils.getValoresPedido(FirebaseConfig.getFirebaseAutentificacao().getUid())
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        ValoresPedido valoresPedido = dataSnapshot.getValue(ValoresPedido.class);
//                        editTotal.setText(String.valueOf(valoresPedido.getValorTotalProduto()));
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });


    }
}
