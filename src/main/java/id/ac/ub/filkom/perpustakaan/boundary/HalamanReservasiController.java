package id.ac.ub.filkom.perpustakaan.boundary;

import id.ac.ub.filkom.perpustakaan.control.BuktiReservasi;
import id.ac.ub.filkom.perpustakaan.control.ReservasiGagalException;
import id.ac.ub.filkom.perpustakaan.control.ReservasiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * «boundary» HalamanReservasi — UC-03 Ajukan Reservasi («extend» dari Pinjam Buku).
 * Template: reservasi.html (konfirmasi), bukti-reservasi.html.
 */
@Controller
@RequestMapping("/reservasi")
public class HalamanReservasiController {

    private final ReservasiService reservasiService;     // «control»

    public HalamanReservasiController(ReservasiService reservasiService) {
        this.reservasiService = reservasiService;
    }

    /** [1] klik tautan "Ajukan Reservasi" di pinjam.html -> [2] tampilkan konfirmasi. */
    @GetMapping
    public String tampilKonfirmasi(@RequestParam String idBuku,
                                   HttpSession session, Model model) {
        model.addAttribute("idAnggota", SesiAnggota.idAnggotaLogin(session));
        try {
            model.addAttribute("buku", reservasiService.cariBuku(idBuku));
        } catch (ReservasiGagalException e) {
            model.addAttribute("pesan", e.getMessage());
        }
        return "reservasi";                       // templates/reservasi.html
    }

    /** [3] ajukanReservasi(idBuku) dari aktor Anggota. */
    @PostMapping
    public String ajukanReservasi(@RequestParam String idBuku,
                                  HttpSession session, Model model) {
        String idAnggota = SesiAnggota.idAnggotaLogin(session);
        try {
            // [4] ajukanReservasi(idAnggota, idBuku)
            BuktiReservasi bukti = reservasiService.ajukanReservasi(idAnggota, idBuku);
            return tampilBukti(bukti, model);     // [12] tampilkan bukti reservasi
        } catch (ReservasiGagalException e) {     // [13] pesan gagal
            model.addAttribute("pesan", e.getMessage());
            return tampilKonfirmasi(idBuku, session, model); // [14] tampilkan pesan
        }
    }

    private String tampilBukti(BuktiReservasi bukti, Model model) {
        model.addAttribute("bukti", bukti);
        return "bukti-reservasi";                 // templates/bukti-reservasi.html
    }
}
