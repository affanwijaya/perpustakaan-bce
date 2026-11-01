package id.ac.ub.filkom.perpustakaan.boundary;

import id.ac.ub.filkom.perpustakaan.control.StokGagalException;
import id.ac.ub.filkom.perpustakaan.control.StokService;
import id.ac.ub.filkom.perpustakaan.entity.Buku;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** «boundary» HalamanStok — UC-02 Kelola Stok Buku, aktor Pustakawan (template: stok.html). */
@Controller
@RequestMapping("/stok")
public class HalamanStokController {

    private final StokService stokService;               // «control»

    public HalamanStokController(StokService stokService) {
        this.stokService = stokService;
    }

    /** Langkah 1 scenario: tampilkan daftar buku beserta stok. */
    @GetMapping
    public String tampilHalaman(Model model) {
        model.addAttribute("daftarBuku", stokService.daftarBuku());
        return "stok";                            // templates/stok.html
    }

    /** [1] tambahStok(idBuku, jumlah) dari aktor Pustakawan. */
    @PostMapping("/tambah")
    public String tambahStok(@RequestParam String idBuku, @RequestParam int jumlah,
                             RedirectAttributes redirect) {
        try {
            // [2] tambahStok(idBuku, jumlah)
            Buku buku = stokService.tambahStok(idBuku, jumlah);
            tampilBerhasil(buku, redirect);       // [11] tampilkan stok terbaru
        } catch (StokGagalException e) {          // [10] pesan gagal
            redirect.addFlashAttribute("pesan", e.getMessage()); // [12] tampilkan pesan
        }
        return "redirect:/stok";                  // pola Post-Redirect-Get
    }

    /** [1] kurangiStok(idBuku, jumlah) dari aktor Pustakawan. */
    @PostMapping("/kurangi")
    public String kurangiStok(@RequestParam String idBuku, @RequestParam int jumlah,
                              RedirectAttributes redirect) {
        try {
            // [2] kurangiStok(idBuku, jumlah)
            Buku buku = stokService.kurangiStok(idBuku, jumlah);
            tampilBerhasil(buku, redirect);       // [11] tampilkan stok terbaru
        } catch (StokGagalException e) {          // [10] pesan gagal
            redirect.addFlashAttribute("pesan", e.getMessage()); // [12] tampilkan pesan
        }
        return "redirect:/stok";                  // pola Post-Redirect-Get
    }

    private void tampilBerhasil(Buku buku, RedirectAttributes redirect) {
        redirect.addFlashAttribute("pesanSukses",
                "Stok \"" + buku.getJudul() + "\" sekarang " + buku.getStok());
    }
}
