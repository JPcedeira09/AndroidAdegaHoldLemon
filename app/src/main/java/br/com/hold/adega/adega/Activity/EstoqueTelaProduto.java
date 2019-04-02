package br.com.hold.adega.adega.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Config.FirebaseConfig;
import br.com.hold.adega.adega.Model.Produto;
import br.com.hold.adega.adega.Util.FirebaseChildsUtils;

public class EstoqueTelaProduto extends AppCompatActivity {

    private EditText alteraNome, alteraDescricao, alteraQuantidade,alteraValor;
    private Button altera;
    private static Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estoque_tela_produto);
        //Chama o item que foi  clicado
        Intent intent = getIntent();
        produto = (Produto) intent.getSerializableExtra("selecionado");
        System.out.println(produto.toString());

        //Inicializar componentes
        alteraNome = findViewById(R.id.editTextAlteraNomeProduto);
        alteraDescricao = findViewById(R.id.editTextAlteraDescricao);
        alteraQuantidade = findViewById(R.id.quantidadeAlteraProdutoEstoqueAdd);
        alteraValor = findViewById(R.id.editTextAlteraValorProduto);
        altera = findViewById(R.id.buttonAlterarEstoque);

        //Recupera dados
        FirebaseChildsUtils.getProduto(produto.getNome()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    Produto produto = dataSnapshot.getValue(Produto.class);
                    alteraNome.setText(produto.getNome());
                    alteraDescricao.setText(produto.getDescricao());
                    alteraQuantidade.setText(String.valueOf(produto.getQuantidade()));
                    alteraValor.setText(String.valueOf(produto.getValor()) );

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Evento de click
        altera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = alteraNome.getText().toString();
                String descricao = alteraDescricao.getText().toString();
                String qtd = alteraQuantidade.getText().toString();
                String valor = alteraValor.getText().toString();

                if (!nome.isEmpty()){
                  if (!descricao.isEmpty()){
                   if (!qtd.isEmpty()){
                    if (!valor.isEmpty()){

                        Produto produto = new Produto();
                        produto.setNome(nome);
                        produto.setDescricao(descricao);
                        produto.setQuantidade(Integer.getInteger(qtd));
                        produto.setValor(Double.parseDouble(valor));
                        update(produto);
                        finish();

                        Toast.makeText(EstoqueTelaProduto.this,"Produto alterado com sucesso ",Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(EstoqueTelaProduto.this,"Digite o valor do produto ",Toast.LENGTH_SHORT).show();
                    }

                   }else {
                       Toast.makeText(EstoqueTelaProduto.this,"Digite a quantidade do produto ",Toast.LENGTH_SHORT).show();
                   }


                  }else {
                      Toast.makeText(EstoqueTelaProduto.this,"Digite a descrição do produto ",Toast.LENGTH_SHORT).show();
                  }

                }else {
                    Toast.makeText(EstoqueTelaProduto.this,"Digite o nome  do produto ",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    public  void update(Produto produto) {

        Map<String,Object> usuarioMap = new HashMap<String,Object>();
        usuarioMap.put("nome", produto.getNome());
        usuarioMap.put("descricao", produto.getDescricao());
        usuarioMap.put("quantidade", produto.getQuantidade());
        usuarioMap.put("valor",produto.getValor());


        FirebaseConfig.getFirebase()
                .child("Adega")
                .child("Produtos")
                .child(produto.getNome())
                .updateChildren(usuarioMap);
    }
}
