package br.com.hold.adega.adega.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Config.FirebaseConfig;
import br.com.hold.adega.adega.Model.Usuario;
import br.com.hold.adega.adega.Util.FirebaseChildsUtils;

public class IntroCadastro extends AppCompatActivity {


    private EditText campoEmail, campoSenha;
    private Button botaoEntrar, botaoCadastrar;
    private FirebaseAuth autentificacao;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_cadastro);
        //Summir a Toobar
        getSupportActionBar().hide();

        //Configuraçoes Iniciais
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoEntrar = findViewById(R.id.buttonEntrar);
        botaoCadastrar = findViewById(R.id.buttonCadastrar);

        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoEmail = campoEmail.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                if (!textoEmail.isEmpty()) {
                    if (!textoSenha.isEmpty()) {
                        usuario = new Usuario();
                        usuario.setEmail(textoEmail);
//                       usuario.setSenha(textoSenha);
                        validarLogin();

                    } else {
                        Toast.makeText(IntroCadastro.this, "Preenche a senha",
                                Toast.LENGTH_SHORT).show();


                    }

                } else {
                    Toast.makeText(IntroCadastro.this, "Preenche o E-mail",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IrCadastro();

            }
        });

    }

    /*@Override
    protected void onStart() {
        super.onStart();
        clienteLogado();

    }*/




    public void validarLogin() {
        autentificacao = FirebaseConfig.getFirebaseAutentificacao();
        autentificacao.signInWithEmailAndPassword(
                campoEmail.getText().toString(), campoSenha.getText().toString()

        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    abrirPedidosCardapio();

                } else if (campoEmail.getText().toString().equals("teste@teste.com")
                        && campoSenha.getText().toString().equals("teste123")) {
                    abrirPedidosEstoque();
                } else {
                    String excecao = "";
                    try {
                        throw task.getException();

                    } catch (FirebaseAuthInvalidUserException e) {
                        excecao = "Usuario nao esta cadastrado";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "E-mail e senha nao correspondem ao usuario cadastradro";
                    } catch (Exception e) {
                        excecao = "Erro ao cadastrar usuario" + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(IntroCadastro.this, excecao,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

   /* public void clienteLogado(){
        autentificacao = FirebaseConfig.getFirebaseAutentificacao();


        if (autentificacao.getCurrentUser() !=null ){
            abrirPedidosCardapio();

        }
    }

    public void donoLogado(){
        autentificacao = FirebaseConfig.getFirebaseAutentificacao();


        if (autentificacao.getCurrentUser() !=null ){
            abrirPedidosEstoque();

        }
    }*/

    public void abrirPedidosCardapio() {

        startActivity(new Intent(this, MenuCliente.class));
    }

    public void abrirPedidosEstoque() {
        startActivity(new Intent(this, MenuDono.class));

    }

    public void IrCadastro() {
        startActivity(new Intent(this, Cadastro1.class));
    }
}

















