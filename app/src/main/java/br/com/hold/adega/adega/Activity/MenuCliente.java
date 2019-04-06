package br.com.hold.adega.adega.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.hold.adega.JhoonyGostoso;
import br.com.hold.adega.R;
import br.com.hold.adega.adega.Adapter.AdapterCardapio;
import br.com.hold.adega.adega.Config.FirebaseConfig;
import br.com.hold.adega.adega.Listener.RecyclerItemClickListener;
import br.com.hold.adega.adega.Model.Produto;
import br.com.hold.adega.adega.Util.FirebaseChildsUtils;


public class MenuCliente extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private List<Produto> produtos = new ArrayList<>();
    private RecyclerView recyclerCardapio;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);

        autenticacao = FirebaseAuth.getInstance();
        recyclerCardapio = findViewById(R.id.recyclerCardapio);



        //Configura recyclerView
        recyclerCardapio.setLayoutManager(new LinearLayoutManager(this));
        recyclerCardapio.setHasFixedSize(true);
        final AdapterCardapio adapterCardapio = new AdapterCardapio(produtos,this);
        recyclerCardapio.setAdapter(adapterCardapio);

        //Evento de click no RecylerView
        recyclerCardapio.addOnItemTouchListener( new RecyclerItemClickListener(this, recyclerCardapio,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Produto p = produtos.get(position);
                        Intent intent = new Intent(MenuCliente.this,TelaProduto .class);
                        intent.putExtra("selecionado", p);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {


                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }));

        //Trazer dados do firebase
        FirebaseChildsUtils.getProdutos()
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produtos.clear();
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Produto produto = child.getValue(Produto.class);
                    produtos.add(produto);
                }
                adapterCardapio.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cliente, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.carrinho ){
            irCarrinho();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void irCarrinho(){
        startActivity(new Intent(this, TelaCarrinho.class));
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(this, DadosPessoais.class));



        } else if (id == R.id.nav_gallery) {
            startActivity(new Intent(this, HistoricoPedidosCliente.class));

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            autenticacao.signOut();
            startActivity(new Intent(getApplicationContext(), IntroCadastro.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
