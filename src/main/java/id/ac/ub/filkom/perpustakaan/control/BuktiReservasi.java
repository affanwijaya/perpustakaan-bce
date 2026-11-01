package id.ac.ub.filkom.perpustakaan.control;

import id.ac.ub.filkom.perpustakaan.entity.Reservasi;

import java.time.LocalDate;

/**
 * [11] bukti reservasi — data hasil yang dikembalikan control ke boundary.
 * Kelas biasa (bukan entity), sehingga template tidak mengakses entity secara langsung.
 */
public class BuktiReservasi {

    private final String idReservasi;
    private final String namaAnggota;
    private final String judulBuku;
    private final LocalDate tanggalReservasi;
    private final String status;

    private BuktiReservasi(String idReservasi, String namaAnggota, String judulBuku,
                           LocalDate tanggalReservasi, String status) {
        this.idReservasi = idReservasi;
        this.namaAnggota = namaAnggota;
        this.judulBuku = judulBuku;
        this.tanggalReservasi = tanggalReservasi;
        this.status = status;
    }

    public static BuktiReservasi dari(Reservasi reservasi) {
        return new BuktiReservasi(
                reservasi.getIdReservasi(),
                reservasi.getAnggota().getNama(),
                reservasi.getBuku().getJudul(),
                reservasi.getTanggalReservasi(),
                reservasi.getStatus());
    }

    public String getIdReservasi()         { return idReservasi; }
    public String getNamaAnggota()         { return namaAnggota; }
    public String getJudulBuku()           { return judulBuku; }
    public LocalDate getTanggalReservasi() { return tanggalReservasi; }
    public String getStatus()              { return status; }
}
