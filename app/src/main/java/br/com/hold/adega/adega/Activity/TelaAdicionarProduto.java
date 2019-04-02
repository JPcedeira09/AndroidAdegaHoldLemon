package br.com.hold.adega.adega.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Config.FirebaseConfig;
import br.com.hold.adega.adega.Model.Produto;

public class TelaAdicionarProduto extends AppCompatActivity {
    private EditText editNomeProduto,editQuantidade,editDescricao,editValor;
    private Button adiciona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adicionar_produto);

        editNomeProduto = findViewById(R.id.editTextNomeProduto);
        editQuantidade = findViewById(R.id.editTextDescricao);
        editDescricao = findViewById(R.id.quantidadeProdutoEstoqueAdd);
        editValor = findViewById(R.id.editTextValorProduto);
        adiciona = findViewById(R.id.buttonAddEstoque);

        adiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = editNomeProduto.getText().toString();
                String descricao = editDescricao.getText().toString();
                String qtd = editQuantidade.getText().toString();
                String valor = editValor.getText().toString();


                if (!nome.isEmpty()){
                    if (!descricao.isEmpty()){
                        if (!qtd.isEmpty()){
                            if (!valor.isEmpty()){

                                Produto produto = new Produto();
                                produto.setNome(nome);
                                produto.setDescricao(descricao);
                                produto.setQuantidade(Integer.getInteger(qtd));
                                produto.setValor(Double.parseDouble(valor));
                                create(produto);
                                finish();

                                Toast.makeText(TelaAdicionarProduto.this,"Cadastro realizado com sucesso",Toast.LENGTH_SHORT).show();


                            }else{
                                Toast.makeText(TelaAdicionarProduto.this,"Digite o valor do produto",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(TelaAdicionarProduto.this,"Digite a quantidade do produto",Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(TelaAdicionarProduto.this,"Digite a descrição do produto",Toast.LENGTH_SHORT).show();
                    }

                }else {

                    Toast.makeText(TelaAdicionarProduto.this,"Digite o nome do produto",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public static void create(Produto produto){
        FirebaseConfig.getFirebase()
                .child("Adega")
                .child("Produtos")
                .child(produto.getNome())
                .setValue(produto);
    }
}
