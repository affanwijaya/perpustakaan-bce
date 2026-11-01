# Perpustakaan BCE — Implementasi RSI (Spring Boot)

Pengerjaan **Latihan** modul *10a. Implementasi RSI* (slide 57), mata kuliah Rekayasa Sistem Informasi, FILKOM Universitas Brawijaya.

- Dosen: Dr. Achmad Arwan
- Dikerjakan oleh: Affan Herlino Tazakha Wijaya (255150400111041)
- Dikembangkan dari repo contoh dosen: [arwan07/Book_Springboot](https://github.com/arwan07/Book_Springboot)

## Latihan dan letak pengerjaannya

| No | Latihan (slide 57) | Letak di repo |
|---|---|---|
| 1 | Use case **Kelola Stok Buku** (aktor Pustakawan) dengan pola BCE: `HalamanStokController`, `StokService`, entity `Buku` | `boundary/HalamanStokController.java`, `templates/stok.html`, `control/StokService.java`, `entity/Buku.java` |
| 2 | Use case «extend» **Ajukan Reservasi**: entity `Reservasi`, tabel `reservasi`, tautan pada `pinjam.html` | `entity/Reservasi.java`, `entity/ReservasiRepository.java`, tabel `reservasi` di `schema.sql`, `control/ReservasiService.java`, `boundary/HalamanReservasiController.java`, `templates/reservasi.html`, `templates/bukti-reservasi.html`, tautan *Ajukan Reservasi* di `templates/pinjam.html` |
| 3 | Sequence diagram tiap use case dengan nomor pesan, nomornya ditulis sebagai komentar di kode | [`docs/sequence-diagram.md`](docs/sequence-diagram.md); nomor `[n]` di diagram = komentar `// [n]` di kode |
| 4 | Unit test untuk setiap method entity baru | `src/test/java/.../entity/StokEntityTest.java`, `ReservasiEntityTest.java` |

## Use case

| Use case | Aktor | Halaman | Boundary | Control | Entity |
|---|---|---|---|---|---|
| UC-01 Pinjam Buku (contoh modul Bab 5) | Anggota | `/pinjam` | `HalamanPinjamController`, `pinjam.html`, `bukti.html` | `PinjamService` | `Buku`, `Anggota`, `TransaksiPinjam` |
| UC-02 Kelola Stok Buku | Pustakawan | `/stok` | `HalamanStokController`, `stok.html` | `StokService` | `Buku` |
| UC-03 Ajukan Reservasi («extend» Pinjam Buku) | Anggota | `/reservasi?idBuku=...` (dibuka dari tautan di `/pinjam`) | `HalamanReservasiController`, `reservasi.html`, `bukti-reservasi.html` | `ReservasiService` | `Reservasi`, `Anggota`, `Buku` |

Alur alternatif yang ditangani:

- **UC-01:** stok habis (muncul tautan *Ajukan Reservasi*), melewati batas pinjam, buku tidak ditemukan.
- **UC-02:** jumlah ≤ 0 ditolak; pengurangan yang membuat stok negatif ditolak.
- **UC-03:** reservasi hanya untuk buku yang stoknya habis; reservasi ganda untuk buku yang sama ditolak.

## Struktur package (pola BCE)

```
id.ac.ub.filkom.perpustakaan
├── boundary/   @Controller + template Thymeleaf (tanpa query dan aturan bisnis)
├── control/    @Service, satu kelas per use case
└── entity/     @Entity + repository
```

Skema tabel ada di `src/main/resources/schema.sql` dan data awal di `data.sql`. `spring.jpa.hibernate.ddl-auto=validate` memastikan entity cocok dengan skema tabel.

## Unit test

| Kelas test | Method yang diuji |
|---|---|
| `PinjamEntityTest` | `Buku.cekStok()`, `Buku.kurangiStok()`, `Anggota.cekBatasPinjam()` |
| `StokEntityTest` | `Buku.tambahStok(jumlah)`, `Buku.kurangiStok(jumlah)` |
| `ReservasiEntityTest` | `Reservasi.buat(...)`, `Anggota.sudahReservasi(buku)` |

## Menjalankan aplikasi

Prasyarat: Java 17 atau lebih baru.

1. Buka folder proyek di VS Code / IntelliJ.
2. Jalankan kelas `PerpustakaanApplication` (tombol **Run**), atau `mvn spring-boot:run` bila Maven terpasang.
3. Buka http://localhost:8080 lalu akses halaman berikut:

| Halaman | Fungsi |
|---|---|
| `/buku` | Daftar dan cari buku |
| `/buku/tambah` | Tambah buku |
| `/anggota` | Daftar anggota |
| `/pinjam` | UC-01 Pinjam Buku |
| `/stok` | UC-02 Kelola Stok Buku |
| `/h2-console` | Melihat database (JDBC URL `jdbc:h2:mem:perpustakaan`, user `sa`, password kosong) |

UC-03 dibuka dengan meminjam buku yang stoknya habis di `/pinjam`, lalu menekan tautan *Ajukan Reservasi*.

Data awal:

| Buku | Judul | Stok |
|---|---|---|
| B001 | Pemrograman Java | 3 |
| B002 | Rekayasa Perangkat Lunak | 0 |
| B003 | Basis Data | 2 |

| Anggota | Nama | Batas pinjam |
|---|---|---|
| A001 | Budi Santoso | 2 |
| A002 | Sari Dewi | 1 |

## Batasan

- Belum ada login; anggota yang aktif selalu `A001`.
- Reservasi hanya dicatat dengan status `MENUNGGU`.
- Database H2 in-memory, sehingga data kembali ke awal setiap aplikasi dijalankan ulang.
