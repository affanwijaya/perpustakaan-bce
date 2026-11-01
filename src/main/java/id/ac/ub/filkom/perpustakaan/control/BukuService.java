package id.ac.ub.filkom.perpustakaan.control;

import id.ac.ub.filkom.perpustakaan.entity.Buku;
import id.ac.ub.filkom.perpustakaan.entity.BukuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Service: tempat logika bisnis/aturan aplikasi. */
@Service
public class BukuService {

    private final BukuRepository bukuRepository;

    // Dependency injection lewat constructor
    public BukuService(BukuRepository bukuRepository) {
        this.bukuRepository = bukuRepository;
    }

    public List<Buku> cariBuku(String kata) {
        if (kata == null || kata.isBlank()) {
            return bukuRepository.findAll();
        }
        return bukuRepository.findByJudulContainingIgnoreCase(kata.trim());
    }

    @Transactional
    public Buku tambahBuku(String idBuku, String judul, Integer stok) {
        String id = idBuku == null ? "" : idBuku.trim();
        String judulBersih = judul == null ? "" : judul.trim();

        // Defensive programming: tolak setiap input tidak valid secara eksplisit
        if (id.isEmpty()) {
            throw new TambahBukuGagalException("ID buku wajib diisi");
        }
        if (id.length() > 10) {
            throw new TambahBukuGagalException("ID buku maksimal 10 karakter");
        }
        if (judulBersih.isEmpty()) {
            throw new TambahBukuGagalException("Judul wajib diisi");
        }
        if (judulBersih.length() > 200) {
            throw new TambahBukuGagalException("Judul maksimal 200 karakter");
        }
        if (stok == null) {
            throw new TambahBukuGagalException("Stok wajib diisi");
        }
        if (stok < 0) {
            throw new TambahBukuGagalException("Stok tidak boleh negatif");
        }
        // save() akan menimpa data lama bila ID sudah ada, jadi cek dulu
        if (bukuRepository.existsById(id)) {
            throw new TambahBukuGagalException("ID buku " + id + " sudah dipakai");
        }
        return bukuRepository.save(new Buku(id, judulBersih, stok));
    }
}
