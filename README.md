# 🌤️ Hava Durumu Uygulaması - Dönem Ödevi

Bu proje, istenen tüm dönem ödevi kriterlerini eksiksiz karşılayacak şekilde geliştirilmiş bir Android mobil uygulamasıdır.

## 🛠️ Uygulanan Ödev Kriterleri

* **Çoklu Ekran Yapısı (En az 3 Activity):** Projede `SplashActivity` (Açılış Ekranı), `MainActivity` (Ana Sorgu Ekranı) ve `GecmisActivity` (Arama Geçmişi Ekranı) olmak üzere toplam **3 adet Activity** kullanılmıştır.
* **Veri Tabanı Yönetimi (SQLite):** `VeriTabaniYardimcisi` sınıfı aracılığıyla yerel SQLite veri tabanı entegre edilmiştir. Kullanıcının yaptığı son 5 başarılı arama geçmişi veri tabanına kaydedilmekte ve listelenmektedir.
* **Dış Servis Entegrasyonu (API):** Canlı hava durumu verileri, güncel ve güvenilir bir dış servis API'si üzerinden anlık olarak çekilmektedir.
* **Kod Okunabilirliği:** Proje içerisindeki tüm kritik metotlar ve fonksiyonlar, okunabilirliği artırmak adına kısa açıklama satırları (comment line) ile desteklenmiştir.

## 🚀 Çalıştırma Talimatı
1. Projeyi ZIP olarak indirin veya bilgisayarınıza clone edin.
2. Android Studio ile projeyi açın.
3. Gradle senkronizasyonunun tamamlanmasını bekledikten sonra doğrudan emülatör veya gerçek cihazda çalıştırabilirsiniz.
