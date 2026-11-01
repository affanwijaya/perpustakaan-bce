package id.ac.ub.filkom.perpustakaan.control;

import id.ac.ub.filkom.perpustakaan.entity.TransaksiPinjam;

import java.time.LocalDate;

/**
 * [11] bukti peminjaman — data hasil yang dikembalikan control ke boundary.
 * Kelas biasa (bukan entity), sehingga template tidak mengakses entity secara langsung.
 */
public class Bukti {

    private final String idTransaksi;
    private final String namaAnggota;
    private final String judulBuku;
    private final LocalDate tanggalPinjam;
    private final String status;

    private Bukti(String idTransaksi, String namaAnggota, String judulBuku,
                  LocalDate tanggalPinjam, String status) {
        this.idTransaksi = idTransaksi;
        this.namaAnggota = namaAnggota;
        this.judulBuku = judulBuku;
        this.tanggalPinjam = tanggalPinjam;
        this.status = status;
    }

    public static Bukti dari(TransaksiPinjam transaksi) {
        return new Bukti(
                transaksi.getIdTransaksi(),
                transaksi.getAnggota().getNama(),
                transaksi.getBuku().getJudul(),
                transaksi.getTanggalPinjam(),
                transaksi.getStatus());
    }

    public String getIdTransaksi()      { return idTransaksi; }
    public String getNamaAnggota()      { return namaAnggota; }
    public String getJudulBuku()        { return judulBuku; }
    public LocalDate getTanggalPinjam() { return tanggalPinjam; }
    public String getStatus()           { return status; }
}
