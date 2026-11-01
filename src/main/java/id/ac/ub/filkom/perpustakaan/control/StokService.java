package id.ac.ub.filkom.perpustakaan.control;

import id.ac.ub.filkom.perpustakaan.entity.Buku;
import id.ac.ub.filkom.perpustakaan.entity.BukuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * «control» StokController — UC-02 Kelola Stok Buku (aktor Pustakawan).
 * Direalisasikan sebagai @Service, bukan @Controller.
 */
@Service
public class StokService {

    private final BukuRepository bukuRepository;

    public StokService(BukuRepository bukuRepository) {
        this.bukuRepository = bukuRepository;
    }

    /** Langkah 1 scenario: tampilkan daftar buku beserta stok. */
    public List<Buku> daftarBuku() {
        return bukuRepository.findAll();
    }

    /** +tambahStok(idBuku : String, jumlah : int) : Buku */
    @Transactional
    public Buku tambahStok(String idBuku, int jumlah) {
        // [3] cari buku  -> [4] buku ditemukan / tidak ditemukan
        Buku buku = cariBuku(idBuku);
        try {
            // [5] tambahStok(jumlah)  -> [6] ok / jumlah tidak valid
            buku.tambahStok(jumlah);
        } catch (IllegalArgumentException e) {
            throw new StokGagalException(e.getMessage());   // [10] pesan gagal
        }
        // [7] simpan  -> [8] stok tersimpan
        bukuRepository.save(buku);
        return buku;                                        // [9] stok terbaru
    }

    /** +kurangiStok(idBuku : String, jumlah : int) : Buku */
    @Transactional
    public Buku kurangiStok(String idBuku, int jumlah) {
        // [3] cari buku  -> [4] buku ditemukan / tidak ditemukan
        Buku buku = cariBuku(idBuku);
        try {
            // [5] kurangiStok(jumlah)  -> [6] ok / jumlah tidak valid / stok jadi negatif
            buku.kurangiStok(jumlah);
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new StokGagalException(e.getMessage());   // [10] pesan gagal
        }
        // [7] simpan  -> [8] stok tersimpan
        bukuRepository.save(buku);
        return buku;                                        // [9] stok terbaru
    }

    private Buku cariBuku(String idBuku) {
        return bukuRepository.findById(idBuku)
                .orElseThrow(StokGagalException::tidakDitemukan);
    }
}
