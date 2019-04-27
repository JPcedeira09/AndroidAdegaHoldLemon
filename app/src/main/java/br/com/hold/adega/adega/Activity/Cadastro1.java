package br.com.hold.adega.adega.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import br.com.hold.adega.R;
import br.com.hold.adega.adega.Model.Usuario;

public class Cadastro1 extends AppCompatActivity {

    private EditText nome, cpf , cep , endereco , numero , complemento , telefone;
    private Button proximo;
    private Usuario usuario;
    private FirebaseAuth autenticaçao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro1);

        //Summir a Toobar
        getSupportActionBar().hide();

        //Inicializar componentes
        nome = findViewById(R.id.editNome);
        cpf = findViewById(R.id.editCpf);
        cep = findViewById(R.id.editCep);
        endereco = findViewById(R.id.editEndereco);
        numero = findViewById(R.id.editNumero);
        complemento = findViewById(R.id.editComplemento);
        telefone = findViewById(R.id.editTelefone);
        proximo = findViewById(R.id.buttonProximo);


        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoNome  = nome.getText().toString();
                String textoCpf = cpf.getText().toString();
                String textoCep = cep.getText().toString();
                String textoEndereco = endereco.getText().toString();
                String textoNumero = numero.getText().toString();
                String textoComplemento = complemento.getText().toString();
                String textoTelefone = telefone.getText().toString();
                //Validar se os campos foram preenchidos
                if (!textoNome.isEmpty()){
                    if (!textoCpf.isEmpty()){
                        if (!textoCep.isEmpty()){
                        if (!textoEndereco.isEmpty()) {
                        if (!textoNumero.isEmpty()) {
                        if (!textoTelefone.isEmpty()) {
                            usuario.setNome(textoNome);
                            usuario.setCpf(textoCpf);
                            usuario.setCep(textoCep);
                            usuario.setEndereco(textoEndereco);
                            usuario.setNumero(textoNumero);
                            usuario.setComplemento(textoComplemento);
                            usuario.setCelular(textoTelefone);
                            proximaPagina();


                        } else {
                            Toast.makeText(Cadastro1.this,"Preenche o numero de seu telefone ",
                                    Toast.LENGTH_SHORT).show(); }

                        } else {
                            Toast.makeText(Cadastro1.this,"Preenche o numero de sua recidencia",
                                    Toast.LENGTH_SHORT).show(); }

                        } else{
                            Toast.makeText(Cadastro1.this,"Preenche o endereço",
                                    Toast.LENGTH_SHORT).show(); }

                        } else {
                            Toast.makeText(Cadastro1.this,"Preenche o CEP",
                                    Toast.LENGTH_SHORT).show(); }

                    } else {
                        Toast.makeText(Cadastro1.this,"Preenche o CPF",
                                Toast.LENGTH_SHORT).show(); }

                } else {
                    Toast.makeText(Cadastro1.this,"Preenche o nome",
                            Toast.LENGTH_SHORT).show(); }
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void proximaPagina(){
        Intent intent = new Intent(Cadastro1.this, Cadastro2.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);

}

}

