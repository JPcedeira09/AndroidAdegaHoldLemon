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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Config.FirebaseConfig;
import br.com.hold.adega.adega.Model.Usuario;
import br.com.hold.adega.adega.Model.UsuarioAdega;
import br.com.hold.adega.adega.Util.FirebaseChildsUtils;

public class DadosPessoaisDono extends AppCompatActivity {

    private EditText editTxtNome, editTxtRazao, editTxtCNPJ, editTxtEndereco, editTxtContato, editCep, editNumero;
    private Button atualizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_pessoais_dono);

        //Sumir a ActionBar
        getSupportActionBar().hide();

        //Configurar toolbar
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Dados da Empresa");
        if (null != toolbar) {
            toolbar.setNavigationIcon(R.drawable.ic_back_24dp);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirMenuDono();
                }
            });

        }

        //Inicializar Componentes
        editTxtNome = findViewById(R.id.editNomeLoja);
        editTxtRazao = findViewById(R.id.editRazaoLoja);
        editTxtCNPJ = findViewById(R.id.editCNPJLoja);
        editTxtEndereco = findViewById(R.id.editEnderecoLoja);
        editTxtContato = findViewById(R.id.editContatoLoja);
        editCep = findViewById(R.id.editCEPLoja);
        editNumero = findViewById(R.id.editNumeroLoja);
        atualizar = findViewById(R.id.buttonAtualizarDadosDono);

        //Recupera Dados do Firebase
        FirebaseChildsUtils.getDadosAdega().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){
                    UsuarioAdega usuario = dataSnapshot.getValue(UsuarioAdega.class);
                    editTxtNome.setText(usuario.getNome());
                    editTxtRazao.setText(usuario.getNome());
                    editTxtEndereco.setText(usuario.getEndereco());
                    editTxtContato.setText(usuario.getTelefone1());
                    editTxtCNPJ.setText(usuario.getCNPJ());
                    editCep.setText(usuario.getCEP());
                    editNumero.setText(usuario.getNumero());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = editTxtNome.getText().toString();
                String razao = editTxtRazao.getText().toString();
                String cnpj = editTxtCNPJ.getText().toString();
                String cep = editCep.getText().toString();
                String endereço = editTxtEndereco.getText().toString();
                String numero =  editNumero.getText().toString();
                String contato = editTxtContato.getText().toString();


                if (!nome.isEmpty()){
                    if (!razao.isEmpty()){
                        if (!cnpj.isEmpty()){
                            if (!cep.isEmpty()){
                                if (!endereço.isEmpty()) {
                                    if (!numero.isEmpty()) {
                                        if (!contato.isEmpty()){

                                    UsuarioAdega usuario = new UsuarioAdega();
                                    usuario.setNome(nome);
                                    usuario.setNome(razao);
                                    usuario.setCNPJ(cnpj);
                                    usuario.setCEP(cep);
                                    usuario.setEndereco(endereço);
                                    usuario.setNumero(numero);
                                    usuario.setTelefone1(contato);
                                    update(usuario);
                                    finish();

                                    Toast.makeText(DadosPessoaisDono.this, "Usuario Atualizado com sucesso", Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(DadosPessoaisDono.this,"Digite o seu celular",Toast.LENGTH_SHORT).show();

                                }
                                }else{
                                    Toast.makeText(DadosPessoaisDono.this,"Digite o numero de seu endereço",Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(DadosPessoaisDono.this,"Digite o  seu endereço",Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(DadosPessoaisDono.this,"Digite e o seu CNPJ",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(DadosPessoaisDono.this,"Digite e o seu CEP",Toast.LENGTH_SHORT).show();
                    }

                    }else{
                        Toast.makeText(DadosPessoaisDono.this,"Digite a razão social",Toast.LENGTH_SHORT).show();
                    }

                }else {

                    Toast.makeText(DadosPessoaisDono.this,"Digite o nome",Toast.LENGTH_SHORT).show();

                }

            }

            public void update(UsuarioAdega usuario) {


                Map<String,Object> usuarioMap = new HashMap<String,Object>();
                usuarioMap.put("nome", usuario.getNome());
                usuarioMap.put("endereco", usuario.getEndereco() );
                usuarioMap.put("numero", usuario.getNumero());
                usuarioMap.put("CNPJ", usuario.getCNPJ() );
                usuarioMap.put("CEP",usuario.getCEP());
                usuarioMap.put("Razao",usuario.getNome());
                usuarioMap.put("Telefone",usuario.getTelefone1());

                FirebaseConfig.getFirebase()
                        .child("Adega")
                        .child("DadosAdega")
                        .updateChildren(usuarioMap);

            }
        });


    }

    private void abrirMenuDono(){
        startActivity(new Intent(DadosPessoaisDono.this, MenuDono.class));
    }
}

