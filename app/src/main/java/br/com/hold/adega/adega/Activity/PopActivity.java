package br.com.hold.adega.adega.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import br.com.hold.adega.R;

public class PopActivity extends Activity {
    private RadioGroup status;
    private Button alterarStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        //inicializar Componentes
        status = findViewById(R.id.RadioGroupStatus);
        alterarStatus = findViewById(R.id.buttonAlterarStatus);




        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int) (height*.7));


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
        verificaRadioButton();


        alterarStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(PopActivity.this, " Status alterado com sucesso  ",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    public void verificaRadioButton(){
        status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                if (i == R.id.radioButtonConfirmado){
                    Toast.makeText(PopActivity.this, " Status alterado para Confirmado  ",
                            Toast.LENGTH_SHORT).show();

                }
                if (i == R.id.radioButtonPreparo){
                    Toast.makeText(PopActivity.this, " Status alterado para Em preparo  ",
                            Toast.LENGTH_SHORT).show();

                }
                if (i == R.id.radioButtonSaindoEntrega){
                    Toast.makeText(PopActivity.this, " Status alterado para Saindo para Entrega  ",
                            Toast.LENGTH_SHORT).show();

                }
                if (i == R.id.radioButtonRecusado){
                    Toast.makeText(PopActivity.this, " Status alterado para Recusado  ",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    }
