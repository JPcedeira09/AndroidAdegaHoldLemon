package br.com.hold.adega.adega.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Adapter.AdapterTelaPedido;
import br.com.hold.adega.adega.Config.FirebaseConfig;
import br.com.hold.adega.adega.Model.ItensCarrinho;
import br.com.hold.adega.adega.Model.Pedido;
import br.com.hold.adega.adega.Model.Usuario;
import br.com.hold.adega.adega.Model.ValoresPedido;
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

        //Sumir a ActionBar
        getSupportActionBar().hide();

        //Configurar toolbar
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Detalhe do Pedido");
        if (null != toolbar) {
            toolbar.setNavigationIcon(R.drawable.ic_back_24dp);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirMenuDono();
                }
            });

        }

        Intent intent = getIntent();
        pedido = (Pedido) intent.getSerializableExtra("selecionado");



        //Inicializar Componentes
        editNome = findViewById(R.id.txtNomeClientePedido);
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
        FirebaseChildsUtils.getOPedidoItens(pedido.getKey()).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        itensCarrinho.clear();

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            ItensCarrinho carrinho = child.getValue(ItensCarrinho.class);
                            itensCarrinho.add(carrinho);
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        FirebaseChildsUtils.getOPedidoDados(pedido.getKey())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                        Usuario usuario = dataSnapshot.getValue(Usuario.class);
                        editNome.setText(usuario.getNome());
                        editEndereco.setText(usuario.getEndereco() + usuario.getNumero() + usuario.getComplemento());
                        editCpf.setText(usuario.getCpf());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        FirebaseChildsUtils.getOPedidoValores(pedido.getKey())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        ValoresPedido valoresPedido = dataSnapshot.getValue(ValoresPedido.class);
                        editTotal.setText(String.valueOf("R$" + valoresPedido.getValorTotalProduto()));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





    }




    private void abrirMenuDono(){
        startActivity(new Intent(TelaPedidos.this, MenuDono.class));
    }
}
