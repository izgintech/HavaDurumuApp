package com.example.havadurumuapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText editTextSehir;
    private Button buttonGetir;
    private TextView textViewSonuc;
    private Button buttonGecmis;

    private VeritabaniYardimcisi veritabani;

    // Otomatik güncelleme için bir Zamanlayıcı (Handler) oluşturuyoruz
    private Handler zamanlayici = new Handler();
    private Runnable guncellemeGorevi;
    private String sonArananSehir = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextSehir = findViewById(R.id.editTextSehir);
        buttonGetir = findViewById(R.id.buttonGetir);
        textViewSonuc = findViewById(R.id.textViewSonuc);
        buttonGecmis = findViewById(R.id.buttonGecmis);

        // Veri tabanını bu ekranda başlatıyoruz
        veritabani = new VeritabaniYardimcisi(this);

        // Hava Durumunu Getir Butonu Tıklaması
        buttonGetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sehir = editTextSehir.getText().toString().trim();
                if (!sehir.isEmpty()) {
                    sonArananSehir = sehir; // Zamanlayıcı için şehri hafızaya al
                    havaDurumunuGetir(sehir);
                    otomatikGuncellemeyiBaslat(); // Otomatik döngüyü tetikle
                }
            }
        });

        // Geçmiş Butonu Tıklaması (3. Ekrana Geçiş)
        buttonGecmis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GecmisActivity.class);
                startActivity(intent);
            }
        });
    }

    private void havaDurumunuGetir(String sehir) {
        textViewSonuc.setText("Bekleniyor...");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String urlString = "https://wttr.in/" + sehir + "?format=%t+%C";
                    URL url = new URL(urlString);
                    HttpURLConnection baglanti = (HttpURLConnection) url.openConnection();
                    baglanti.setRequestMethod("GET");

                    BufferedReader reader = new BufferedReader(new InputStreamReader(baglanti.getInputStream()));
                    StringBuilder sonuc = new StringBuilder();
                    String satir;
                    while ((satir = reader.readLine()) != null) {
                        sonuc.append(satir);
                    }
                    reader.close();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String havaDurumu = sonuc.toString();
                            textViewSonuc.setText(havaDurumu);

                            // Veriyi veri tabanına kaydediyoruz.
                            veritabani.veriEkle(sehir, havaDurumu);
                        }
                    });

                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewSonuc.setText("Hata: Veri çekilemedi!");
                        }
                    });
                }
            }
        }).start();
    }

    // 30 Saniyede bir otomatik yenileme yapan metot
    private void otomatikGuncellemeyiBaslat() {
        // Eğer arkada zaten çalışan bir kronometre varsa durdur.
        if (guncellemeGorevi != null) {
            zamanlayici.removeCallbacks(guncellemeGorevi);
        }

        guncellemeGorevi = new Runnable() {
            @Override
            public void run() {
                if (!sonArananSehir.isEmpty()) {
                    havaDurumunuGetir(sonArananSehir); // Arka planda sessizce tekrar çeker.
                }
                // Kendini 30 saniye sonra tekrar çağırarak döngüye sokar.
                zamanlayici.postDelayed(this, 30000);
            }
        };

        // İlk döngüyü 30 saniye sonrası için başlatır.
        zamanlayici.postDelayed(guncellemeGorevi, 30000);
    }
}