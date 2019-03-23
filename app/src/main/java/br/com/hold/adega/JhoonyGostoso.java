package br.com.hold.adega;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.hold.adega.adega.Model.Usuario;
import br.com.hold.adega.adega.Util.FirebaseUsuarioUtils;

public class JhoonyGostoso extends AppCompatActivity {


    private static FirebaseUsuarioUtils usuarioUtils = new FirebaseUsuarioUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jhoony_gostoso);

        Usuario usuario = new Usuario("kaue@teste.com", "Kaue", "333.333.333", "09540500",  "73737373",  "444",
                 "82",  "3232-3232" );


//        DatabaseReference child = FirebaseConfig.getFirebase().child("1111111111");
//        FirebaseChildsUtils.getUsuario("1111").setValue(usuario);
//          usuarioUtils.createUsuario(usuario,"1212121212");
                //FirebaseChildsUtils.getUsuario("11111111111111111"));
    }
}
