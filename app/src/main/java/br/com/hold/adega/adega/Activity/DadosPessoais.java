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
import br.com.hold.adega.adega.Model.Usuario;
import br.com.hold.adega.adega.Util.FirebaseChildsUtils;
import br.com.hold.adega.adega.Util.FirebaseUsuarioUtils;

public class DadosPessoais extends AppCompatActivity {

    private EditText cpf, cep,endereco, numero, complemento, celular;
    private Button atualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_pessoais);

        //Sumir a ActionBar
        getSupportActionBar().hide();

        //Configurar toolbar
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Dados Pessoais");
        if (null != toolbar) {
            toolbar.setNavigationIcon(R.drawable.ic_back_24dp);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirMenuCliente();
                }
            });

        }

        //Inicializar Componentes
        cpf = findViewById(R.id.editTxtCpf);
        cep = findViewById(R.id.editTxtCep);
        endereco = findViewById(R.id.editTxtEndereco);
        numero = findViewById(R.id.editTxtNumero);
        complemento = findViewById(R.id.editTxtComplemento);
        celular = findViewById(R.id.editTxtCelular);
        atualizar = findViewById(R.id.bttAtualizarEndereco);

        //Trazer dados do Firebase
        FirebaseChildsUtils.getUsuario(FirebaseConfig.getFirebaseAutentificacao().getUid())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    cpf.setText(usuario.getCpf());
                    cep.setText(usuario.getCep());
                    endereco.setText(usuario.getEndereco());
                    complemento.setText(usuario.getComplemento());
                    numero.setText(usuario.getNumero());
                    celular.setText(usuario.getCelular());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

            atualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String editCpf = cpf.getText().toString();
                    String editCep = cep.getText().toString();
                    String editEndereco = endereco.getText().toString();
                    String editNumero = numero.getText().toString();
                    String editCelular = celular.getText().toString();



                    if (!editCpf.isEmpty()){
                        if (!editCep.isEmpty()){
                            if (!editEndereco.isEmpty()){
                                if (!editNumero.isEmpty()) {
                                  if (!editCelular.isEmpty()){

                                    Usuario usuario = new Usuario();
                                    usuario.setCpf(editCpf);
                                    usuario.setCep(editCep);
                                    usuario.setEndereco(editEndereco);
                                    usuario.setNumero(editNumero);
                                    usuario.setCelular(editCelular);
                                    update(usuario);
                                    finish();

                                    Toast.makeText(DadosPessoais.this, "Usuario Atualizado com sucesso", Toast.LENGTH_SHORT).show();

                                  }else{
                                      Toast.makeText(DadosPessoais.this,"Digite o seu celular",Toast.LENGTH_SHORT).show();

                                  }

                                }else{
                                     Toast.makeText(DadosPessoais.this,"Digite o numero de seu endereço",Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(DadosPessoais.this,"Digite e o seu endereço",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(DadosPessoais.this,"Digite o seu CEP",Toast.LENGTH_SHORT).show();
                        }

                    }else {

                        Toast.makeText(DadosPessoais.this,"Digite o seu CFP",Toast.LENGTH_SHORT).show();

                    }
                }
            });






    }

    private void abrirMenuCliente(){
        startActivity(new Intent(DadosPessoais.this, MenuCliente.class));
    }
    public  void update(Usuario usuario) {

        Map<String,Object> usuarioMap = new HashMap<String,Object>();
        usuarioMap.put("cpf", usuario.getCpf());
        usuarioMap.put("cep", usuario.getCep());
        usuarioMap.put("Endereco", usuario.getEndereco() );
        usuarioMap.put("numero",usuario.getNumero() );
        usuarioMap.put("complemento",usuario.getComplemento());
        usuarioMap.put("celular",usuario.getCelular());

        FirebaseConfig.getFirebase()
                .child("Usuarios")
                .child(FirebaseConfig.getFirebaseAutentificacao().getUid())
                .child("DadosPessoais")
                .updateChildren(usuarioMap);
    }
}
