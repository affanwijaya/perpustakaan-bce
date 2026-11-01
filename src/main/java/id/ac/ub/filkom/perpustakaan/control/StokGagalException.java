package id.ac.ub.filkom.perpustakaan.control;

/**
 * Alur alternatif use case Kelola Stok Buku.
 * Dilempar oleh control, ditangkap boundary untuk ditampilkan sebagai pesan.
 */
public class StokGagalException extends RuntimeException {

    public StokGagalException(String pesan) {
        super(pesan);
    }

    /** Validasi input: buku tidak terdaftar. */
    public static StokGagalException tidakDitemukan() {
        return new StokGagalException("Buku tidak ditemukan");
    }
}
