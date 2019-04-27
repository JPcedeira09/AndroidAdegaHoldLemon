package br.com.hold.adega.adega.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Config.FirebaseConfig;
import br.com.hold.adega.adega.Model.Usuario;
import br.com.hold.adega.adega.Model.ValoresPedido;
import br.com.hold.adega.adega.Util.FirebaseUsuarioUtils;
import br.com.hold.adega.adega.Util.FirebaseValoresPedidoUtils;

public class Cadastro2 extends AppCompatActivity {

    private EditText email, nome, senha;
    private Usuario usuario;
    private Button casdastrar;
    private static FirebaseUsuarioUtils utilsUsuario = new FirebaseUsuarioUtils();
    private static FirebaseValoresPedidoUtils pedidoUtils = new FirebaseValoresPedidoUtils();
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro2);

        Intent intent = getIntent();
        usuario = (Usuario)intent.getSerializableExtra("usuario");
        System.out.println(usuario);

        //Summir a Toobar
        getSupportActionBar().hide();

        //Inicializar Componetes
        nome = findViewById(R.id.editNome);
        email = findViewById(R.id.editEmail);
        senha = findViewById(R.id.editSenha);
        casdastrar = findViewById(R.id.Cadastrar);

        casdastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textNome = nome.getText().toString();
                String textEmail = email.getText().toString();
                String textSenha = senha.getText().toString();

                if (!textNome.isEmpty()) {
                    if (!textEmail.isEmpty()) {
                        if (!textSenha.isEmpty()) {

                            usuario.setEmail(textEmail);
                            cadastrarUsuario(usuario);

                        } else {
                            Toast.makeText(Cadastro2.this, "Preenche a senha ",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(Cadastro2.this, "Preenche o email",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Cadastro2.this, "Preenche o nome",
                            Toast.LENGTH_SHORT).show();
                }
            }


        });

    }

    public void menuCliente() {
        startActivity(new Intent(this, MenuCliente.class));
    }

    public void cadastrarUsuario(final Usuario usuarioParaCadastro){
        autenticacao = FirebaseConfig.getFirebaseAutentificacao();
        autenticacao.createUserWithEmailAndPassword(
                email.getText().toString(),senha.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    String UID = task.getResult().getUser().getUid();
                            utilsUsuario.createUsuario(usuarioParaCadastro,UID);
                            pedidoUtils.createValoresPedido(new ValoresPedido( 0.0, getCurrentDate(),  "Sem Status",  false),UID);
                            menuCliente();
                            finish();
                        } else {
                            Toast.makeText(Cadastro2.this,"Erro ao cadastrar o usuario",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    private static String getCurrentDate() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return cal.toString();
    }

}


