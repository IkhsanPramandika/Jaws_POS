# Jaws POS - Selenium Automation Framework (POM)

Project ini adalah kerangka kerja otomatisasi pengujian (Automation Testing Framework) untuk aplikasi **Point of Sales (POS)**. Framework ini dibangun menggunakan **Java**, **Selenium WebDriver**, dan **TestNG** dengan menerapkan desain pola **Page Object Model (POM)** untuk memastikan skalabilitas dan kemudahan pemeliharaan kode.

## 📌 Fitur Utama
* **Arsitektur POM**: Pemisahan yang jelas antara Page Objects (UI) dan Test Scripts (Logic).
* **Dynamic Data Flow (E2E)**: Mendukung estafet data antar modul, seperti penggunaan nomor PO dari modul Down Payment langsung ke modul Sales.
* **Multi-Payment Support**: Automasi pembayaran kombinasi (Cash, Debit, Credit) dalam satu transaksi.
* **Automated Allure Reports**: Laporan pengujian interaktif dengan langkah-langkah `@Step` dan screenshot otomatis saat terjadi kegagalan.
* **Centralized Configuration**: Pengaturan URL, kredensial, dan data master terpusat di satu file konfigurasi.

## 📂 Struktur Direktori Project
```text
src
├── main/java/dev1/pages          # Page Objects: Berisi locator (@FindBy) dan aksi UI
└── test/java/dev1
    ├── tests                     # Test Scripts: Berisi alur bisnis dan assertions
    │   ├── base                  # BaseTest (Setup/TearDown)
    │   └── [Modul Bisnis]        # DP, Sales, Repair, StockReceiving, dll
    ├── utils                     # Helper: TestListener, ConfigReader, ExtentManager
    └── resources/config.properties # File konfigurasi utama

🛠️ Prasyarat (Prerequisites)
1. Java JDK 11 atau lebih baru.
2. Maven (untuk manajemen dependensi).
3. Google Chrome browser.
4. Allure Commandline (opsional, untuk melihat report secara lokal).

🚀 Cara Menjalankan (Execution)
1. Konfigurasi Data
Sesuaikan data pengujian pada file src/test/resources/config.properties
2. Jalankan Suite Menggunakan TestNG
Gunakan perintah Maven berikut untuk menjalankan suite tertentu:
Menjalankan Master E2E Flow: mvn test -DsuiteXmlFile=src/test/resources/testng/testng.xml
Menjalankan Modul Sales Only: mvn test -DsuiteXmlFile=src/test/resources/testng/testng_pos_sales.xml
Menjalankan Modul Repair (E2E Step 1-5): mvn test -DsuiteXmlFile=src/test/resources/testng/testng-repair.xml

📊 Pelaporan (Reporting)
Framework ini terintegrasi dengan Allure Report. Untuk meninjau hasil test setelah eksekusi, jalankan:
1. Buka terminal di root project.
2. Jalankan perintah: allure serve allure-results

📝 Catatan Penting
1. Overlay Handling: Framework ini dilengkapi dengan waitForOverlayToDisappear() untuk menangani loading spinner yang sering muncul di aplikasi POS.
2. Tab Handling: Mendukung perpindahan tab otomatis (seperti saat print Invoice atau Sales Order).
3. Dynamic PO: Nomor PO yang dihasilkan oleh modul Down Payment disimpan secara statis di TestDataProviders agar bisa digunakan langsung oleh modul Sales Void atau Sales with DP.
