package id.ac.ub.filkom.perpustakaan.control;

/**
 * Alur alternatif fitur Tambah Buku (input tidak valid / ID sudah dipakai).
 * Dilempar oleh control, ditangkap boundary untuk ditampilkan sebagai pesan.
 */
public class TambahBukuGagalException extends RuntimeException {

    public TambahBukuGagalException(String pesan) {
        super(pesan);
    }
}
