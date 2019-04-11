package br.com.hold.adega.adega.Activity;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Adapter.AdapterCarrinho;
import br.com.hold.adega.adega.Config.FirebaseConfig;
import br.com.hold.adega.adega.Listener.RecyclerItemClickListener;
import br.com.hold.adega.adega.Model.ItensCarrinho;
import br.com.hold.adega.adega.Model.Pedido;
import br.com.hold.adega.adega.Model.Produto;
import br.com.hold.adega.adega.Model.Usuario;
import br.com.hold.adega.adega.Model.ValoresPedido;
import br.com.hold.adega.adega.Util.FirebaseChildsUtils;
import br.com.hold.adega.adega.Util.FirebasePedidoUtils;
import br.com.hold.adega.adega.Util.FirebaseUsuarioUtils;

public class TelaCarrinho extends AppCompatActivity {

    private RecyclerView recyclerView;
    private static List<ItensCarrinho> itensCarrinho = new ArrayList<>();
    private TextView enderecoEntrega,valorTotal,cpf;
    private Button buttonCpf,buttonPedido;
    private static Pedido pedido;
    private static Produto produto;
    private static ItensCarrinho carrinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_carrinho);

        //sumir a toolbar
        getSupportActionBar().hide();

        //Inicializar os componentes
        recyclerView = findViewById(R.id.recyclerCarrinho);
        enderecoEntrega = findViewById(R.id.textEnderecoEntrega);
        valorTotal = findViewById(R.id.textValorTotal);
        cpf = findViewById(R.id.textCPFCarrinho);
        buttonPedido = findViewById(R.id.buttonFazerPedido);
        buttonCpf = findViewById(R.id.buttonTrocarCPF);


        //Configurando o Recycler
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final AdapterCarrinho adapterCarrinho = new AdapterCarrinho(itensCarrinho, this);
        recyclerView.setAdapter(adapterCarrinho);


        // Evento de click no Recycler
        recyclerView.addOnItemTouchListener( new RecyclerItemClickListener(this, recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                    }
                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }));


        //Recuperando dados
        FirebaseChildsUtils.getItensCarrinho(FirebaseConfig.getFirebaseAutentificacao().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        itensCarrinho.clear();
                        for (DataSnapshot child: dataSnapshot.getChildren()){
                            ItensCarrinho carrinho = child.getValue(ItensCarrinho.class);
                            itensCarrinho.add(carrinho);
                        }


                        adapterCarrinho.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        FirebaseChildsUtils.getUsuario(FirebaseConfig.getFirebaseAutentificacao().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Usuario usuario = dataSnapshot.getValue(Usuario.class);
                        cpf.setText(usuario.getCpf());
                        enderecoEntrega.setText(usuario.getEndereco() + usuario.getNumero() + usuario.getComplemento());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        FirebaseChildsUtils.getValoresPedido(FirebaseConfig.getFirebaseAutentificacao().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        ValoresPedido valoresPedido = dataSnapshot.getValue(ValoresPedido.class);
                        valorTotal.setText(String.valueOf(valoresPedido.getValorTotalProduto()));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        buttonPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertCarrinho();
                insertHistorico();
                finish();
                Toast.makeText(TelaCarrinho.this, " Pedido Realizado com sucesso ",
                        Toast.LENGTH_SHORT).show();
            }
        });

        buttonCpf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirAlert();


            }
        });



    }

    //Fazendo o AlertDialog
    public void abrirAlert(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Trocar CFP");
        dialog.setMessage("teste");
        dialog.setPositiveButton("sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(TelaCarrinho.this,"Produto Excluido com sucesso ",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    private static void insertCarrinho(){
        final DatabaseReference childPedido = FirebaseChildsUtils.getPedido();
        String keyPedido = childPedido.getKey();
        String uid = FirebaseConfig.getFirebaseAutentificacao().getUid();

        setUsuario(uid,childPedido.child("DadosCliente"));
        setItem(childPedido.child("Itens"),itensCarrinho);
        setValorAtualDoPedido(FirebaseChildsUtils.getValoresPedido(uid),childPedido.child("ValoresPedido"));
        childPedido.child("key").setValue(keyPedido);
    }

    private static void insertHistorico(){

        String uid = FirebaseConfig.getFirebaseAutentificacao().getUid();
        final DatabaseReference childPedido = FirebaseChildsUtils.getHistorico(uid);
        String keyPedido = childPedido.getKey();

        setUsuario(uid,childPedido.child("DadosCliente"));
        setItem(childPedido.child("Itens"),itensCarrinho);
        setValorAtualDoPedido(FirebaseChildsUtils.getValoresPedido(uid),childPedido.child("ValoresPedido"));
        childPedido.child("key").setValue(keyPedido);
    }

    public static void setUsuario(String uid, final DatabaseReference referenceKey){

       //Trazer dados do Firebase
       FirebaseChildsUtils.getUsuario(FirebaseConfig.getFirebaseAutentificacao().getUid())
               .addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       if (dataSnapshot.getValue() != null) {
                           Usuario usuario = dataSnapshot.getValue(Usuario.class);
                           referenceKey.setValue(usuario);
                       }
                   }
                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {
                   }
               });
    }


    public static void setItem(DatabaseReference referenceKey, List<ItensCarrinho> carrinho){

        for(ItensCarrinho item : carrinho){
            referenceKey.child(item.getKey()).setValue(item);
        }
    }

    public static void setValorAtualDoPedido(DatabaseReference refLeitura, final DatabaseReference refInsert){

        refLeitura.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ValoresPedido valoresPedido = dataSnapshot.getValue(ValoresPedido.class);
                refInsert.setValue(valoresPedido);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
