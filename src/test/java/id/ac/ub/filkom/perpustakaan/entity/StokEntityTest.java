package id.ac.ub.filkom.perpustakaan.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Unit test method entity untuk UC-02 Kelola Stok Buku. */
class StokEntityTest {

    @Test
    void tambahStok_menambah_jumlah_stok() {
        Buku buku = new Buku("B002", "Rekayasa Perangkat Lunak", 0);
        buku.tambahStok(5);
        assertEquals(5, buku.getStok());
    }

    @Test
    void tambahStok_menolak_jumlah_nol_atau_negatif() {
        Buku buku = new Buku("B001", "Pemrograman Java", 3);
        assertThrows(IllegalArgumentException.class, () -> buku.tambahStok(0));
        assertThrows(IllegalArgumentException.class, () -> buku.tambahStok(-2));
        assertEquals(3, buku.getStok());
    }

    @Test
    void kurangiStok_jumlah_mengurangi_stok() {
        Buku buku = new Buku("B001", "Pemrograman Java", 3);
        buku.kurangiStok(2);
        assertEquals(1, buku.getStok());
    }

    @Test
    void kurangiStok_jumlah_menolak_bila_stok_menjadi_negatif() {
        Buku buku = new Buku("B003", "Basis Data", 2);
        assertThrows(IllegalStateException.class, () -> buku.kurangiStok(3));
        assertThrows(IllegalArgumentException.class, () -> buku.kurangiStok(0));
        assertEquals(2, buku.getStok());
    }
}
