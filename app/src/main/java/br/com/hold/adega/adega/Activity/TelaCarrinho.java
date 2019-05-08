package br.com.hold.adega.adega.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


public class TelaCarrinho extends AppCompatActivity {

    private RecyclerView recyclerView;
    private static List<ItensCarrinho> itensCarrinho = new ArrayList<>();
    private TextView enderecoEntrega,valorTotal,cpf;
    private Button buttonPedido;
    private static Integer quantidadePedido;
    private static Produto produto;
    private static Double produtoValor = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_carrinho);

        //sumir a toolbar
        getSupportActionBar().hide();

        //Configurar toolbar
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Carrinho");
        if (null != toolbar) {
            toolbar.setNavigationIcon(R.drawable.ic_back_24dp);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    voltarMenuCliente();
                }
            });

        }

        //Inicializar os componentes
        recyclerView = findViewById(R.id.recyclerCarrinho);
        enderecoEntrega = findViewById(R.id.textEnderecoEntrega);
        valorTotal = findViewById(R.id.textValorTotal);
        cpf = findViewById(R.id.textCPFCarrinho);
        buttonPedido = findViewById(R.id.buttonFazerPedido);
        quantidadePedido=1;



        //Configurando o Recycler
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        final AdapterCarrinho adapterCarrinho = new AdapterCarrinho(itensCarrinho, this);
        recyclerView.setAdapter(adapterCarrinho);

        // Evento de click no Recycler
        recyclerView.addOnItemTouchListener( new RecyclerItemClickListener(this, recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {


                        AlertDialog.Builder dialog = new AlertDialog.Builder(TelaCarrinho.this);

                        dialog.setTitle("Confirma e exclusão do produto?");
                        dialog.setPositiveButton("sim", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                remover(itensCarrinho.get(position).getKey());
                                getValorDoProduto(itensCarrinho.get(position).getNome());

                                final double v = itensCarrinho.get(position).getTotalItem() / itensCarrinho.get(position).getQtd();

                                getValorAtualDoPedido(FirebaseConfig.getFirebaseAutentificacao().getUid(),v);
                            }
                        });

                        dialog.create();
                        dialog.show();


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
                        DecimalFormat df2 = new DecimalFormat("#.##");
                        df2.setRoundingMode(RoundingMode.DOWN);
                        df2.format(valoresPedido.getValorTotalProduto());
                        df2.setRoundingMode(RoundingMode.UP);
                        df2.format(valoresPedido.getValorTotalProduto());
                        valorTotal.setText("R$" +(df2.format(valoresPedido.getValorTotalProduto())));
                        System.out.println(valorTotal);
                        System.out.print("============================================================");
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

    }

    public static void remover(String key){

        DatabaseReference firebaseRef = FirebaseConfig.getFirebase();
        DatabaseReference  produtoRef = firebaseRef.child("Usuarios")
                .child(FirebaseConfig.getFirebaseAutentificacao().getUid())
                .child("MeusPedidos")
                .child(key);

        produtoRef.removeValue();
    }

    public static void getValorDoProduto(String nome) {

        DatabaseReference produtoRef = FirebaseChildsUtils.getProduto(nome);
        produtoRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Produto prod = dataSnapshot.getValue(Produto.class);

                produtoValor = prod.getValor();
                System.out.println(produtoValor.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void voltarMenuCliente () {
                    startActivity(new Intent(TelaCarrinho.this, MenuCliente.class));
                }


                private static void insertCarrinho () {
                    DatabaseReference childPedido = FirebaseChildsUtils.getPedido();
                    String keyPedido = childPedido.getKey();
                    String uid = FirebaseConfig.getFirebaseAutentificacao().getUid();

                    setUsuario(uid, childPedido.child("DadosCliente"));
                    setItem(childPedido.child("Itens"), itensCarrinho);
                    setValorAtualDoPedido(FirebaseChildsUtils.getValoresPedido(uid), childPedido.child("ValoresPedido"));
                    childPedido.child("key").setValue(keyPedido);
                }

                private static void insertHistorico () {

                    String uid = FirebaseConfig.getFirebaseAutentificacao().getUid();
                    DatabaseReference childPedido = FirebaseChildsUtils.getHistorico(uid);
                    String keyPedido = childPedido.getKey();

                    setUsuario(uid, childPedido.child("DadosCliente"));
                    setItem(childPedido.child("Itens"), itensCarrinho);
                    setValorAtualDoPedido(FirebaseChildsUtils.getValoresPedido(uid), childPedido.child("ValoresPedido"));
                    childPedido.child("key").setValue(keyPedido);
                }

                public static void setUsuario (String uid,final DatabaseReference referenceKey){

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

                public static void setItem (DatabaseReference
                referenceKey, List < ItensCarrinho > carrinho){

                    for (ItensCarrinho item : carrinho) {

                        referenceKey.child(item.getKey()).setValue(item);


                    }
                }

                public static void setValorAtualDoPedido (DatabaseReference refLeitura,
                final DatabaseReference refInsert){

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

    public static void getValorAtualDoPedido (String uid , final Double valorTotalDosItensAtuais){

        final DatabaseReference childValoresPedido = FirebaseChildsUtils.getValoresPedido(uid);

        childValoresPedido.addListenerForSingleValueEvent(new ValueEventListener() {
            ValoresPedido valorAtaul = null;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                valorAtaul = dataSnapshot.getValue(ValoresPedido.class);
                Double valorTotalPedido = valorAtaul.getValorTotalProduto();
                Double valorAtualizadoPedido = valorTotalPedido - valorTotalDosItensAtuais;

                valorAtaul.setValorTotalProduto(valorAtualizadoPedido);
                System.out.print(valorAtaul);
                System.out.print(valorTotalDosItensAtuais);
                System.out.print(valorTotalPedido);
                update(valorAtaul);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public static void update(ValoresPedido valoresPedido) {

        Map<String,Object> valoresMap = new HashMap<String,Object>();
        valoresMap.put("valorTotalProduto", valoresPedido.getValorTotalProduto());
        valoresMap.put("dataPedido", valoresPedido.getDataPedido());
        valoresMap.put("pedidoAceite",valoresPedido.getPedidoAceite());
        valoresMap.put("statusPedido",valoresPedido.getStatusPedido());

        FirebaseConfig.getFirebase()
                .child("Usuarios")
                .child(FirebaseConfig.getFirebaseAutentificacao().getUid())
                .child("ValoresPedido")
                .updateChildren(valoresMap);
    }
            }