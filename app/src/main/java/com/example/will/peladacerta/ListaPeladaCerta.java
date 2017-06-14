package com.example.will.peladacerta;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.loopj.android.http.PersistentCookieStore;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListaPeladaCerta extends AppCompatActivity {

    RecyclerView recyclerView;
    PeladaAdapter peladaAdapter;
    List<Pelada> peladaList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pelada_certa);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_layout_recycler);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        baixarImagem();
    }

    public void baixarImagem(){

        progressDialog = ProgressDialog.show(ListaPeladaCerta.this, "Pelada Certa", "Carregando...");

        //criando a nova Thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //transformando url em objeto
                    URL url = new URL("https://amocaes.files.wordpress.com/2011/10/cachorro-fofo-ff23a.jpg");
                    //abrindo a conexão
                    HttpURLConnection http = (HttpURLConnection) url.openConnection();
                    //pegando a imagem em byte code
                    final InputStream inputStream = http.getInputStream();
                    //transformando em bitmap
                    //final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            //imgDownload.setImageBitmap(bitmap);
                            peladaList = new ArrayList<>();
                            peladaList.add(new Pelada(1,"Pelada do Fragoso", "", "","","Muribeca", "", "",2,1, inputStream));
                            peladaList.add(new Pelada(2,"Pelada do Jonh", "", "","","Recife", "", "",2,1, inputStream));
                            peladaList.add(new Pelada(3,"Pelada do Carlos", "", "","","Suvado da Cobra", "", "",2,1, inputStream));
                            peladaList.add(new Pelada(4,"Pelada do Cadena", "", "","","EntraPusso", "", "",2,1, inputStream));
                            peladaList.add(new Pelada(5,"Pelada do Adriel", "", "","","Gameleira", "", "",2,1, inputStream));

                            peladaAdapter = new PeladaAdapter(peladaList);
                            recyclerView.setAdapter(peladaAdapter);

                        }
                    });
                    Log.i("Main", "Baixou a imagem");

                } catch (Exception e) {
                    progressDialog.dismiss();
                    Log.i("Main", "Não baixou a imagem");
                    e.printStackTrace();

                }


            }
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        (new PersistentCookieStore(this)).clear(); // limpa os cookies
    }
}
