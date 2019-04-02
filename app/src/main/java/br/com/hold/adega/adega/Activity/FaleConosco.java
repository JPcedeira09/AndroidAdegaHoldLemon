package br.com.hold.adega.adega.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.hold.adega.R;

public class FaleConosco extends AppCompatActivity {

    private Button botaoEnviarMensagem;
    private TextView textTituloFC, textDescricaoFC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fale_conosco);

        inicializarComponentes();

        botaoEnviarMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = textTituloFC.getText().toString();
                String descricao = textDescricaoFC.getText().toString();

                if(!titulo.isEmpty()){
                    if(!descricao.isEmpty()){

                        exibirMensagem("Sua mensagem foi enviada com sucesso!");

                    }else {
                        exibirMensagem("Preencha o campo de descrição!");
                    }
                } else{
                    exibirMensagem("Informe o assunto!");
                }

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL  , new String[] { "houseBeer@gmail.com" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "oi ");

                startActivity(Intent.createChooser(intent, "Email via..."));

            }
        });
    }


    private void exibirMensagem (String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT)
                .show();
    }



    private void inicializarComponentes(){
        textTituloFC = findViewById(R.id.textTituloFC);
        textDescricaoFC = findViewById(R.id.textDescricaoFC);
        botaoEnviarMensagem = findViewById(R.id.bttnEnviarMensagemFC);

    }



}
