package id.ac.ub.filkom.perpustakaan.entity;

import org.springframework.data.jpa.repository.JpaRepository;

/** Akses tabel anggota. findAll(), findById(), save() sudah tersedia otomatis. */
public interface AnggotaRepository extends JpaRepository<Anggota, String> {
}