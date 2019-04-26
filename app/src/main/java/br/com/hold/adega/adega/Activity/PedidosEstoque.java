package br.com.hold.adega.adega.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import br.com.hold.adega.R;

public class PedidosEstoque extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_estoque);

        //Configuraçao de Objetos
//        autenticacao = FirebaseConfig.getFirebaseAutentificacao();
//
//        //Configurar o bottom navigation view
//        configuraBottomNavigation();
//
    }
//
//    /**
//     * Metodo responsavel  por criar o BottomNavigation
//     */
//    public void configuraBottomNavigation() {
//        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavigation);
//        bottomNavigationViewEx.enableAnimation(true);
//        bottomNavigationViewEx.enableItemShiftingMode(false);
//        bottomNavigationViewEx.enableShiftingMode(false);
//        bottomNavigationViewEx.setTextVisibility(true);
//
//        //Habilitar Navegaçao
//        habilitarNavegaçao(bottomNavigationViewEx);
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.viewPage, new PedidosRealizadosFragment()).commit();
//
//
//    }
//
//    /**
//     * Metodo responsavel por tratar eventos de click na BottomNavigation
//     *
//     * @param viewEx
//     */
//
//    private void habilitarNavegaçao(BottomNavigationViewEx viewEx) {
//        viewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//                switch (item.getItemId()) {
//                    case R.id.ic_pedidos:
//                        fragmentTransaction.replace(R.id.viewPage, new PedidosRealizadosFragment()).commit();
//                        return true;
//                    case R.id.ic_estoque:
//                        fragmentTransaction.replace(R.id.viewPage, new EstoqueFragment()).commit();
//                        return true;
//
//                }
//                return false;
//            }
//        });


//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_dono, menu);
//
//        return super.onCreateOptionsMenu(menu);
//
//    }




    }

