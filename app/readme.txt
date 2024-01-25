1) Manifests Dosyasi

- burada uygulamanin ana configurasyonu yer alir. Biz de alinacak uygulama izinlerini
 bu configurasyon dosyasina, application taginin uzerine ekliyoruz:

```
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE"
        tools:ignore="ScopedStorage" />
    <uses-permission android:name="android.permission.READ_MEDIA_AUDIO" />
```


2) Main Activity
- Uygulamanin ana kismidir, diger komponentleri burada cagririz.

- BottomSheetScaffold: alttan bottom sheet cikmasini saglayan yapi
- LaunchedEffect: uygulama acilinca yapilmasini istedigimiz ui isleri burada yapilir
- Request Permission vs: bunlar izinleri almak icin gerekli olan kutuphanedir. en ustte ", EasyPermissions.PermissionCallbacks"
interfaceini implemente etmesi de izinleri almak icin.

3) Build gradle Module App (2 tane build gradle var bizi module app olan ilgilendirir)
- Burasi uygulama ile alakali kutuphaneleri vs yukledigimiz yer.
- Easy permission kutuphanesini ekledik. izinleri almak kolay olsun diye.
- Material design 2 surumunun kutuphanesini ekledik tasarim icin. Material3 de degisen seyler oldugundan Material2 kullandik.


3) Music sinifi:
- sadece music adi ve dosya yolunu tutan bir class tanimladik kolaylik olsun diye

4) Music Helper sinifi
- Music dosyalarini okumaya yarayan kodlar var.
- Companion object denen seyin icindeki de yardimci bir fonksiyon. Companion icine koyarsak o metodlar degiskenler falan
statik olurlar ve direkt Class.method diye erisilirler. Diger turlu olsa new Class() gibi yapip class olusturmak gerekirdi.

5) MusicListUI
- music listesi ve her bir music list item i icerisinde barindirir.
- alttaki yer alan @Preview ile kodunu yazarken ayni zamanda yaptigimiz seyi goruruz.

6) MusicPlayerUI
- music player controllerini vs barindirir (prev next play pause falan).
- alttaki yer alan @Preview ile kodunu yazarken ayni zamanda yaptigimiz seyi goruruz.

7) MusicViewModel
- tum stateler, business logic (yani mantiksal seyler, ileri geri, oynat durdur, filtreleme falan) vs burada yer alir.

8) PermissionHelper
- yetkileri almak icin yardimci sinif. MAin activityden bu cagrilir.








