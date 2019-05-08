package br.com.hold.adega.adega.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Config.FirebaseConfig;
import br.com.hold.adega.adega.Fragment.EstoqueFragment;
import br.com.hold.adega.adega.Model.Produto;
import br.com.hold.adega.adega.Util.FirebaseChildsUtils;

public class EstoqueTelaProduto extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText alteraNome, alteraDescricao, alteraQuantidade,alteraValor;
    private Button altera;
    private static Produto produto;

    private ImageView imagemProdutoEstoque;

    private static final int SELECAO_GALERIA = 200;
    private String urlImagem = "";

    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estoque_tela_produto);
        //Chama o item que foi  clicado
        Intent intent = getIntent();
        produto = (Produto) intent.getSerializableExtra("selecionado");

        System.out.println(produto.toString());


        //Sumir a ActionBar
        getSupportActionBar().hide();


        //Configurar toolbar
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Atualizar Produto");
        if (null != toolbar) {
            toolbar.setNavigationIcon(R.drawable.ic_back_24dp);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

        //Inicializar componentes
        alteraNome = findViewById(R.id.editTextAlteraNomeProduto);
        alteraDescricao = findViewById(R.id.editTextAlteraDescricao);
        alteraQuantidade = findViewById(R.id.quantidadeAlteraProdutoEstoqueAdd);
        alteraValor = findViewById(R.id.editTextAlteraValorProduto);
        altera = findViewById(R.id.buttonAlterarEstoque);
        imagemProdutoEstoque = findViewById(R.id.imagemProdutoEstoqueAdd);
        mStorageRef = FirebaseStorage.getInstance().getReference();

         String uriImagem1 = produto.getUrl();
        Picasso.get().load(uriImagem1).into(imagemProdutoEstoque);



        imagemProdutoEstoque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent,SELECAO_GALERIA);

                }
            }
        });

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

                        int result = Integer.parseInt(qtd);



                        produto.setNome(nome);
                        produto.setDescricao(descricao);
                        produto.setQuantidade(result);
                        produto.setValor(Double.parseDouble(valor));


                        System.out.print(produto.toString());
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

    private void abrirMenuDono(){
        startActivity(new Intent(EstoqueTelaProduto.this, MenuDono.class));
    }

    public static void update(Produto p) {

        Map<String,Object> produtoMap = new HashMap<String,Object>();

        produtoMap.put("nome", p.getNome());
        produtoMap.put("descricao", p.getDescricao());

        if ((p.getQuantidade() == null) || p.getQuantidade().toString().isEmpty()) {
            produtoMap.put("quantidade", produto.getQuantidade());
        } else {
            produtoMap.put("quantidade", p.getQuantidade());
        }

        produtoMap.put("valor",p.getValor());

        System.out.println(produtoMap.toString());
        FirebaseConfig.getFirebase()
                .child("Adega")
                .child("Produtos")
                .child(produto.getNome())
                .updateChildren(produtoMap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Produto produto = new Produto();
        if (resultCode == RESULT_OK){
            Bitmap imagem = null;

            try {
                switch (requestCode){
                    case SELECAO_GALERIA:
                        Uri localImagem = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(),localImagem);

                        break;
                }
                if (imagem != null){
                    imagemProdutoEstoque.setImageBitmap(imagem);
                    ByteArrayOutputStream baos =new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG,70,baos);
                    byte[] dadosimagem = baos.toByteArray();


                    final StorageReference imagemRef = mStorageRef
                            .child("produtos")
                            .child(alteraNome.getText() + ".jpeg");
                    UploadTask uploadTask = imagemRef.putBytes(dadosimagem);


                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            return imagemRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                urlImagem = downloadUri.toString();

                            } else {
                            }
                        }
                    });

                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }


    }


}
