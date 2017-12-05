package com.example.pinarmnkl.kullanici_giris;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Button kullaniciSil, cikisYap;
    private TextView textView;
    private Toolbar toolbar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // kullaniciSil = (Button) findViewById(R.id.kullaniciSil);
        textView = (TextView) findViewById(R.id.text);
       // cikisYap = (Button) findViewById(R.id.cikis_yap);

        //FirebaseAuth sınıfının referans olduğu nesneleri kullanabilmek için getInstance metodunu kullanıyoruz.
        auth = FirebaseAuth.getInstance();

        //Bir AuthStateListener dinleyicisi oluşturuyoruz ve bu dinleyici onAuthStateChanged bölümünü çalıştırır.
        // Buradaki getCurrentUser metodu ile kullanıcı verilerine ulaşabilmekteyiz.
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                // Eğer geçerli bir kullanıcı oturumu yoksa LoginActivity e geçilir.
                // Oturum kapatma işlemi yapıldığında bu sayede LoginActivity e geçilir.
                if (user == null) {

                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        //getCurrentUser metodu üzerinden ulaştığımız kullanıcı verilerinde getEmail ile de kullanıcının mailini kullanarak,
        // kullanıcıya bir text gösteriyoruz.
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        textView.setText("Merhaba, " + user.getEmail());


        kullaniciSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //Silme işlemi başarılı oldugunda kullanıcıya bir mesaj gösterilip UyeOlActivity e geçiliyor.
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Hesabın silindi.Yeni bir hesap oluştur!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this, UyeOlActivity.class));
                                        finish();

                                    } else {
                                        //İşlem başarısız olursa kullanıcı bilgilendiriliyor.
                                        Toast.makeText(MainActivity.this, "Hesap silinemedi!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });

        cikisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //FirebaseAuth.getInstance().signOut ile oturumu kapatabilmekteyiz.
                auth.signOut();

                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });



    setUpToolbar();

    setUpDrawer();
    recyclerView= (RecyclerView) findViewById(R.id.recyclerview);

    CardViewAdapter cardViewAdapter=new CardViewAdapter(this, Esya.getData());
        recyclerView.setAdapter(cardViewAdapter);

    LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);


}

    private void setUpToolbar(){
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Visualhome");
        toolbar.setSubtitle("Anasayfa");


    }

    private void setUpDrawer(){

        NavigationDrawerFragment navFragment= (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment);
        DrawerLayout drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);

        navFragment.setUpNavigationDrawer(drawerLayout, toolbar);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.layout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.linearViewHorizontal:
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                break;

            case R.id.linearViewVertical:
                LinearLayoutManager linearLayoutManagerVertical = new LinearLayoutManager(this);
                linearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManagerVertical);
                break;

            case R.id.gridView:
                GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 3); // (Context context, int spanCount)
                recyclerView.setLayoutManager(mGridLayoutManager);
                break;
            case R.id.staggeredViewHorizontal:
                StaggeredGridLayoutManager mStaggeredHorizontalLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL); // (int spanCount, int orientation)
                recyclerView.setLayoutManager(mStaggeredHorizontalLayoutManager);
                break;
            case R.id.staggeredViewVertical:
                StaggeredGridLayoutManager mStaggeredVerticalLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL); // (int spanCount, int orientation)
                recyclerView.setLayoutManager(mStaggeredVerticalLayoutManager);
                break;


        }


        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }

    }
}
