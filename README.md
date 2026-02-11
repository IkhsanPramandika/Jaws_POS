Dokumentasi kamu sudah sangat bagus dan mencakup poin-poin krusial arsitektur project-nya. Saya telah merapikan formatnya agar lebih profesional, mudah dibaca (scannable), dan memberikan hierarki informasi yang lebih jelas bagi pengguna lain yang melihat GitHub kamu.

Berikut adalah versi yang sudah dirapikan:

---

# 💎 Jaws POS - Selenium Automation Framework (POM)

Project ini merupakan framework **Automation Testing** untuk aplikasi **Point of Sales (POS)**. Arsitektur dibangun menggunakan desain pola **Page Object Model (POM)** untuk menjamin skalabilitas, keterbacaan, dan kemudahan pemeliharaan kode dalam jangka panjang.

---

## 🚀 Fitur Utama

* **Arsitektur POM**: Pemisahan tegas antara logika UI (Page Objects) dan skrip pengujian (Test Scripts).
* **Dynamic Data Flow (E2E)**: Mendukung estafet data antar modul secara otomatis, seperti penggunaan nomor PO hasil transaksi *Down Payment* ke dalam modul *Sales*.
* **Multi-Payment Support**: Automasi alur pembayaran kombinasi (*Cash, Debit, Credit*) dalam satu sesi transaksi.
* **Automated Allure Reports**: Laporan interaktif yang dilengkapi dengan anotasi `@Step` dan tangkapan layar otomatis jika terjadi kegagalan.
* **Centralized Configuration**: Seluruh pengaturan URL, kredensial, dan data master dikelola secara terpusat melalui satu file konfigurasi.

---

## 📂 Struktur Direktori Project

```text
src
├── main/java/dev1/pages            # Page Objects: Berisi locator (@FindBy) dan aksi UI
└── test/java/dev1
    ├── tests                       # Test Scripts: Berisi alur bisnis dan assertions
    │   ├── base                    # BaseTest (Setup, TearDown, & Common Helpers)
    │   └── [Modul Bisnis]          # Skenario: DP, Sales, Repair, StockReceiving, dll
    ├── utils                       # Helpers: TestListener, ConfigReader, ExtentManager
    └── resources
        ├── config.properties       # File konfigurasi utama
        └── testng                  # File XML untuk eksekusi Test Suite

```

---

## 🛠️ Prasyarat (Prerequisites)

1. **Java JDK 11** atau versi terbaru.
2. **Maven** (Manajemen dependensi).
3. **Google Chrome** (Browser eksekusi).
4. **Allure Commandline** (Opsional, untuk visualisasi laporan secara lokal).

---

## ⚙️ Cara Menjalankan (Execution)

### 1. Konfigurasi Data

Sesuaikan data pengujian pada file:

`src/test/resources/config.properties`

### 2. Jalankan Suite via Maven

Gunakan perintah berikut di terminal untuk menjalankan suite tertentu:

* **Full E2E Flow**: `mvn test -DsuiteXmlFile=src/test/resources/testng/testng.xml`
* **Sales Module Only**: `mvn test -DsuiteXmlFile=src/test/resources/testng/testng_pos_sales.xml`
* **Repair E2E (Step 1-5)**: `mvn test -DsuiteXmlFile=src/test/resources/testng/testng-repair.xml`

---

## 📊 Pelaporan (Reporting)

Framework ini terintegrasi penuh dengan **Allure Report**. Untuk meninjau hasil eksekusi:

1. Buka terminal pada root project.
2. Jalankan perintah:
`allure serve allure-results`

---

## 📝 Catatan Penting

* **Overlay Handling**: Menggunakan method `waitForOverlayToDisappear()` untuk menangani *loading spinner* yang sering muncul secara dinamis.
* **Tab Handling**: Mendukung perpindahan tab otomatis secara stabil, khususnya pada proses cetak *Invoice* atau *Sales Order*.
* **Dynamic PO**: Nomor PO hasil modul *Down Payment* disimpan secara statis di `TestDataProviders` agar dapat dikonsumsi langsung oleh modul *Sales Void* atau *Sales with DP*.

---
