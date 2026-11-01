package id.ac.ub.filkom.perpustakaan.entity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Akses tabel reservasi.
 * save(reservasi) merealisasikan penyimpanan reservasi baru.
 */
public interface ReservasiRepository extends JpaRepository<Reservasi, String> {
}
