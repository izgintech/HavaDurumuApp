package com.example.havadurumuapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class GecmisActivity extends AppCompatActivity {

    private ListView listViewGecmis;
    private VeritabaniYardimcisi veritabani;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gecmis);

        listViewGecmis = findViewById(R.id.listViewGecmis);
        veritabani = new VeritabaniYardimcisi(this);

        // 1. Veri tabanımızdaki son 5 aramayı bir liste olarak çekiyoruz
        ArrayList<String> gecmisListesi = veritabani.sonBesVeriyiGetir();

        // Adapter: Java'daki ham verileri (ArrayList), XML'deki tasarıma (ListView) bağlayan köprüdür.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, // Android'in kendi içindeki standart, basit satır tasarımı
                gecmisListesi
        );

        // 3. Köprüyü listemize takıyoruz ve veriler ekranda beliriyor
        listViewGecmis.setAdapter(adapter);
    }
}