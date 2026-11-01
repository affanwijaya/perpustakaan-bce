package id.ac.ub.filkom.perpustakaan.control;

import id.ac.ub.filkom.perpustakaan.entity.Anggota;
import id.ac.ub.filkom.perpustakaan.entity.AnggotaRepository;
import id.ac.ub.filkom.perpustakaan.entity.Buku;
import id.ac.ub.filkom.perpustakaan.entity.BukuRepository;
import id.ac.ub.filkom.perpustakaan.entity.Reservasi;
import id.ac.ub.filkom.perpustakaan.entity.ReservasiRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * «control» ReservasiController — UC-03 Ajukan Reservasi («extend» dari Pinjam Buku).
 * Direalisasikan sebagai @Service, bukan @Controller.
 */
@Service
public class ReservasiService {

    private final AnggotaRepository anggotaRepository;
    private final BukuRepository bukuRepository;
    private final ReservasiRepository reservasiRepository;

    public ReservasiService(AnggotaRepository anggotaRepository,
                            BukuRepository bukuRepository,
                            ReservasiRepository reservasiRepository) {
        this.anggotaRepository = anggotaRepository;
        this.bukuRepository = bukuRepository;
        this.reservasiRepository = reservasiRepository;
    }

    /** [2] buku yang akan direservasi, untuk ditampilkan di halaman konfirmasi. */
    public Buku cariBuku(String idBuku) {
        return bukuRepository.findById(idBuku)
                .orElseThrow(() -> ReservasiGagalException.tidakDitemukan("Buku"));
    }

    /** +ajukanReservasi(idAnggota : String, idBuku : String) : BuktiReservasi */
    @Transactional
    public BuktiReservasi ajukanReservasi(String idAnggota, String idBuku) {
        // Persiapan: ambil objek entity dari database
        Anggota anggota = anggotaRepository.findById(idAnggota)
                .orElseThrow(() -> ReservasiGagalException.tidakDitemukan("Anggota"));
        Buku buku = cariBuku(idBuku);

        // [5] cekStok()  -> [6] stok masih tersedia?
        if (buku.cekStok()) {
            throw ReservasiGagalException.stokMasihAda();   // [13] pesan gagal
        }

        // [7] sudahReservasi(buku)  -> [8] sudah punya reservasi aktif?
        if (anggota.sudahReservasi(buku)) {
            throw ReservasiGagalException.sudahReservasi(); // [13] pesan gagal
        }

        // [9] buat(anggota, buku)  -> [10] reservasi tersimpan
        Reservasi reservasi = Reservasi.buat(anggota, buku);
        reservasiRepository.save(reservasi);

        return BuktiReservasi.dari(reservasi);              // [11] bukti reservasi
    }
}
