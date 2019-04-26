package br.com.hold.adega.adega.Activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Config.FirebaseConfig;
import br.com.hold.adega.adega.Fragment.EstoqueFragment;
import br.com.hold.adega.adega.Model.Produto;

public class TelaAdicionarProduto extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editNomeProduto, editQuantidade, editDescricao, editValor;
    private ImageView imagemProdutoAdd;
    private Button adiciona;


    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

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
        editQuantidade = findViewById(R.id.editTextDescricao);
        editDescricao = findViewById(R.id.quantidadeProdutoEstoqueAdd);
        editValor = findViewById(R.id.editTextValorProduto);
        adiciona = findViewById(R.id.buttonAddEstoque);

        mStorageRef = FirebaseStorage.getInstance().getReference("produtos");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("produtos");

        imagemProdutoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirImagemSelecionada();
            }
        });


        adiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = editNomeProduto.getText().toString();
                String descricao = editDescricao.getText().toString();
                String qtd = editQuantidade.getText().toString();
                String valor = editValor.getText().toString();


                if (!nome.isEmpty()) {
                    if (!descricao.isEmpty()) {
                        if (!qtd.isEmpty()) {
                            if (!valor.isEmpty()) {
                                if (mImageUri != null) {


                                    Produto produto = new Produto();
                                    produto.setNome(nome);
                                    produto.setDescricao(descricao);
                                    produto.setQuantidade(Integer.getInteger(qtd));
                                    produto.setValor(Double.parseDouble(valor));
                                    StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "."
                                    + getFileExtension(mImageUri));

                                    mUploadTask = fileReference.putFile(mImageUri)
                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(TelaAdicionarProduto.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                    create(produto);
                                    finish();

                                    Toast.makeText(TelaAdicionarProduto.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(TelaAdicionarProduto.this, "Insira a imagem", Toast.LENGTH_SHORT).show();
                                }

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

    /*public void loadWithGlide(){

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    }*/

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

    private void abrirImagemSelecionada() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).into(imagemProdutoAdd);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
