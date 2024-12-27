# Manajemen Pegawai

Aplikasi Manajemen Pegawai adalah sebuah aplikasi berbasis Java yang dirancang untuk mengelola data pegawai. Aplikasi ini menyediakan antarmuka grafis untuk memudahkan pengguna dalam melihat, menambahkan, menghapus, atau memperbarui informasi pegawai.

## Fitur Utama

- **Manajemen Data Pegawai**: Menambahkan, menghapus, memperbarui, dan melihat data pegawai.
- **Antarmuka Pengguna Grafis**: Menyediakan GUI untuk interaksi yang lebih mudah.
- **Penyimpanan Data**: Data pegawai disimpan dalam file teks (`pegawai.txt`).
- **Struktur Modular**: Kode dibagi menjadi beberapa kelas untuk mempermudah pengelolaan dan pengembangan.

## Struktur Proyek

```
managemen-pegawai/
|-- bin/                  # Folder output build
|-- data/                 # Folder untuk file data
|   |-- pegawai.txt       # File data pegawai
|-- images/               # Folder untuk aset gambar (opsional)
|-- lib/                  # Folder untuk library eksternal
|-- src/                  # Folder kode sumber
|   |-- FileHandler.java  # Kelas untuk pengelolaan file
|   |-- fileTest.java     # File untuk pengujian file handling
|   |-- GUI.java          # Kelas untuk antarmuka pengguna grafis
|   |-- Pegawai.java      # Kelas untuk model data Pegawai
|-- README.md             # File dokumentasi (ini)
```

## Cara Menjalankan

1. **Persiapan Lingkungan**
   - Pastikan Anda memiliki Java Development Kit (JDK) terinstal di komputer Anda.
   - Gunakan editor seperti Visual Studio Code atau IntelliJ IDEA untuk pengalaman terbaik.

2. **Kompilasi dan Eksekusi**
   - Buka terminal atau command prompt.
   - Navigasikan ke folder `src`.
   - Jalankan perintah berikut untuk mengompilasi:
     ```
     javac *.java
     ```
   - Jalankan program dengan perintah berikut:
     ```
     java GUI
     ```

## Project oleh
- **Nama**: [Farhan Kurnia]
- **NIM**: [202310370311486]

- **Nama**: [Widya Kusuma]
- **NIM**: [202310370311???]

---