package br.com.hold.adega.adega.Activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



import java.io.ByteArrayOutputStream;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Config.FirebaseConfig;
import br.com.hold.adega.adega.Model.Produto;

public class TelaAdicionarProduto extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editNomeProduto, editQuantidade, editDescricao, editValor;
    private ImageView imagemProdutoAdd;
    private Button adiciona;
    private Produto produto = new Produto();

    private static final int SELECAO_GALERIA = 200;
    private String urlImagem = "";

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_adicionar_produto);

        //Sumir a ActionBar
        getSupportActionBar().hide();

        //Configurar toolbar
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Adicionar Produto");
        if (null != toolbar) {
            toolbar.setNavigationIcon(R.drawable.ic_back_24dp);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirMenuDono();
                }
            });

        }

        imagemProdutoAdd = findViewById(R.id.imagemProdutoEstoqueAdd);
        editNomeProduto = findViewById(R.id.editTextNomeProduto);
        editQuantidade = findViewById(R.id.quantidadeProdutoEstoqueAdd);
        editDescricao = findViewById(R.id.editTextDescricao);
        editValor = findViewById(R.id.editTextValorProduto);
        adiciona = findViewById(R.id.buttonAddEstoque);
        mStorageRef = FirebaseStorage.getInstance().getReference();



        imagemProdutoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                abrirImagemSelecionada();
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent,SELECAO_GALERIA);

                }
            }
        });


        adiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String nome = editNomeProduto.getText().toString();
                String descricao = editDescricao.getText().toString();
                String qtd = editQuantidade.getText().toString();
                String valor = editValor.getText().toString();


                if (!nome.isEmpty()) {
                    if (!descricao.isEmpty()) {
                        if (!qtd.isEmpty()) {
                            if (!valor.isEmpty()) {


                                produto.setNome(nome);
                                produto.setDescricao(descricao);
                                produto.setQuantidade(Integer.parseInt(qtd));
                                produto.setValor(Double.parseDouble(valor));
                                produto.setUrl(urlImagem);

                                    create(produto);
                                    finish();

                                    Toast.makeText(TelaAdicionarProduto.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();



                            } else {
                                Toast.makeText(TelaAdicionarProduto.this, "Digite o valor do produto", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(TelaAdicionarProduto.this, "Digite a quantidade do produto", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(TelaAdicionarProduto.this, "Digite a descrição do produto", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(TelaAdicionarProduto.this, "Digite o nome do produto", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }





    public static void create(Produto produto) {
        FirebaseConfig.getFirebase()
                .child("Adega")
                .child("Produtos")
                .child(produto.getNome())
                .setValue(produto);
    }

    private void abrirMenuDono() {
        startActivity(new Intent(TelaAdicionarProduto.this, MenuDono.class));
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
                    imagemProdutoAdd.setImageBitmap(imagem);
                    ByteArrayOutputStream baos =new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG,70,baos);
                    byte[] dadosimagem = baos.toByteArray();


                    final StorageReference imagemRef = mStorageRef
                            .child("produtos")
                            .child(editNomeProduto.getText() + ".jpeg");
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
