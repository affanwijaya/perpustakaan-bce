package id.ac.ub.filkom.perpustakaan.control;

/**
 * Alur alternatif use case Ajukan Reservasi.
 * Dilempar oleh control, ditangkap boundary untuk ditampilkan sebagai pesan.
 */
public class ReservasiGagalException extends RuntimeException {

    private ReservasiGagalException(String pesan) {
        super(pesan);
    }

    /** Validasi input: data anggota / buku tidak terdaftar. */
    public static ReservasiGagalException tidakDitemukan(String apa) {
        return new ReservasiGagalException(apa + " tidak ditemukan");
    }

    /** Reservasi hanya untuk buku yang stoknya habis («extend» dari Pinjam Buku). */
    public static ReservasiGagalException stokMasihAda() {
        return new ReservasiGagalException("Stok buku masih tersedia, silakan pinjam langsung");
    }

    /** Anggota sudah punya reservasi aktif untuk buku yang sama. */
    public static ReservasiGagalException sudahReservasi() {
        return new ReservasiGagalException("Anda sudah mengajukan reservasi untuk buku ini");
    }
}
