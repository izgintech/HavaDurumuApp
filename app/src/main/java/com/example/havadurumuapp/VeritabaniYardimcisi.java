package com.example.havadurumuapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class VeritabaniYardimcisi extends SQLiteOpenHelper {

    // Veri tabanı adını ve sürümünü belirliyoruz
    private static final String VERITABANI_ADI = "HavaDurumu.db";
    private static final int SURUM = 1;

    public VeritabaniYardimcisi(Context context) {
        super(context, VERITABANI_ADI, null, SURUM);
    }

    // Tablo ilk kez oluşturulurken çalışır
    @Override
    public void onCreate(SQLiteDatabase db) {
        // "aramalar" adında, id, sehir ve derece sütunları olan bir tablo oluşturuyoruz
        db.execSQL("CREATE TABLE aramalar (id INTEGER PRIMARY KEY AUTOINCREMENT, sehir TEXT, derece TEXT);");
    }

    // Veri tabanı güncellendiğinde çalışır
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS aramalar");
        onCreate(db);
    }

    // Yeni aranan şehri ve dereceyi veri tabanına kaydetme metodu
    public void veriEkle(String sehir, String derece) {
        SQLiteDatabase db = this.getWritableDatabase(); // Yazma modunda aç
        ContentValues degerler = new ContentValues();

        degerler.put("sehir", sehir);
        degerler.put("derece", derece);

        // Veriyi "aramalar" tablosuna ekle
        db.insert("aramalar", null, degerler);
        db.close(); // İş bitince veri tabanını kapat ki telefon yorulmasın
    }

    // Son 5 aramayı listelemek için veri çekme metodu
    public ArrayList<String> sonBesVeriyiGetir() {
        ArrayList<String> liste = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase(); // Okuma modunda aç

        // Son eklenenleri en üstte görmek için (ORDER BY id DESC) ve sadece 5 tane almak için (LIMIT 5) sorgu atıyoruz
        Cursor cursor = db.rawQuery("SELECT * FROM aramalar ORDER BY id DESC LIMIT 5", null);

        if (cursor.moveToFirst()) {
            do {
                String sehir = cursor.getString(1); // 1. indeks "sehir" sütunu
                String derece = cursor.getString(2); // 2. indeks "derece" sütunu
                liste.add(sehir + " - " + derece); // "İstanbul - +25°C" şeklinde listeye ekliyoruz
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return liste;
    }
}