package br.com.hold.adega.adega.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Config.FirebaseConfig;
import br.com.hold.adega.adega.Model.ItensCarrinho;
import br.com.hold.adega.adega.Model.Produto;
import br.com.hold.adega.adega.Model.Usuario;
import br.com.hold.adega.adega.Model.ValoresPedido;
import br.com.hold.adega.adega.Util.FirebaseChildsUtils;

public class TelaProduto extends AppCompatActivity {

    private TextView descricao, numero, total, nomeProduto ;
    private ImageView mais,menos;
    private Button adicionarCarrinho;
    private static Produto produto;
    private static Integer quantidadePedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_produto);

        Intent intent = getIntent();
        produto = (Produto) intent.getSerializableExtra("selecionado");
        System.out.println(produto.toString());

        //Sumir a Toobar
        getSupportActionBar().hide();

        descricao = findViewById(R.id.textDescricaoProduto);
        nomeProduto = findViewById(R.id.textNomeProduto);
        numero = findViewById(R.id.textNumero);
        total = findViewById(R.id.textTotal);
        mais = findViewById(R.id.imageMais);
        menos = findViewById(R.id.imageMenos);
        adicionarCarrinho = findViewById(R.id.buttonAdicionarCarrinho);
        quantidadePedido = 1;


        descricao.setText(produto.getDescricao());
        numero.setText(""+1);

        total.setText("Total R$ " + produto.getValor());
        nomeProduto.setText(produto.getNome());

        //Fazer evento de Click na Imagem
        mais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(quantidadePedido < produto.getQuantidade()){
                    quantidadePedido = quantidadePedido + 1;
                    numero.setText(""+ quantidadePedido );
                    atualizaValorTotal();
                }

            }
        });

        //Fazer evento de Click na Imagem
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(quantidadePedido > 1 ){
                    quantidadePedido = quantidadePedido - 1;
                    numero.setText(""+ quantidadePedido );
                    atualizaValorTotal();
                }
            }
        });

        //Adicionar o Produto para o carrinho
        adicionarCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String uid = FirebaseConfig.getFirebaseAutentificacao().getUid();

                DatabaseReference itemRefKey = FirebaseChildsUtils.getItemCarrinho(uid);

                setItemPedido(uid,itemRefKey);

                finish();

                Toast.makeText(TelaProduto.this,"Produto adicionado com sucesso",Toast.LENGTH_SHORT).show();



            }
        });
    }

    public void atualizaValorTotal(){
        Double qtd = Double.valueOf(quantidadePedido);
        DecimalFormat df2 = new DecimalFormat("#.##");
        df2.setRoundingMode(RoundingMode.DOWN);
        df2.format(produto.getValor());
        df2.setRoundingMode(RoundingMode.UP);
        df2.format(produto.getValor());
        total.setText("Total R$ "+ df2.format(produto.getValor() * qtd)  );
    }

    public static void setItemPedido(String uid , DatabaseReference referenceKey){

        String key = referenceKey.getKey();
        Integer qtd = quantidadePedido.intValue();
        DecimalFormat df2 = new DecimalFormat("#.##");
        df2.setRoundingMode(RoundingMode.DOWN);
        df2.format(produto.getValor());
        df2.setRoundingMode(RoundingMode.UP);
        df2.format(produto.getValor());
        Double valorTotal = Double.valueOf(df2.format(produto.getValor() * qtd));

        ItensCarrinho item = new ItensCarrinho(key, qtd, produto.getNome(), valorTotal);


        referenceKey.setValue(item);
        getValorAtualDoPedido(uid,valorTotal);
    }


    public static void getValorAtualDoPedido(String uid, final Double valorTotalDosItensAtuais){
        final DatabaseReference childValoresPedido = FirebaseChildsUtils.getValoresPedido(uid);

        childValoresPedido.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ValoresPedido valorAtaul = dataSnapshot.getValue(ValoresPedido.class);
                Double valorTotalPedido = valorAtaul.getValorTotalProduto();
                Double valorAtualizadoPedido = (valorTotalPedido + valorTotalDosItensAtuais);

                valorAtaul.setValorTotalProduto(valorAtualizadoPedido);

                childValoresPedido.setValue(valorAtaul);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}