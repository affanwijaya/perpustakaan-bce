package id.ac.ub.filkom.perpustakaan.control;

/**
 * Alur alternatif use case Pinjam Buku.
 * Dilempar oleh control, ditangkap boundary untuk ditampilkan sebagai pesan.
 */
public class PinjamGagalException extends RuntimeException {

    /** true bila anggota boleh mengajukan reservasi («extend» Ajukan Reservasi). */
    private final boolean bisaReservasi;

    private PinjamGagalException(String pesan, boolean bisaReservasi) {
        super(pesan);
        this.bisaReservasi = bisaReservasi;
    }

    /** Validasi input: data anggota / buku tidak terdaftar. */
    public static PinjamGagalException tidakDitemukan(String apa) {
        return new PinjamGagalException(apa + " tidak ditemukan", false);
    }

    /** Alur 4b: anggota sudah mencapai batas pinjam. */
    public static PinjamGagalException melewatiBatas() {
        return new PinjamGagalException("Melewati batas pinjam", false);
    }

    /** [13] pesanHabis — alur 4a / alt [stok habis]. */
    public static PinjamGagalException stokHabis() {
        return new PinjamGagalException("Stok buku habis", true);
    }

    public boolean isBisaReservasi() { return bisaReservasi; }
}
