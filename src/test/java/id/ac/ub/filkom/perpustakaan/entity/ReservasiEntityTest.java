package id.ac.ub.filkom.perpustakaan.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Unit test method entity untuk UC-03 Ajukan Reservasi. */
class ReservasiEntityTest {

    @Test
    void buat_reservasi_berstatus_menunggu() {
        Anggota anggota = new Anggota("A001", "Budi Santoso", 2);
        Buku buku = new Buku("B002", "Rekayasa Perangkat Lunak", 0);

        Reservasi r = Reservasi.buat(anggota, buku);

        assertEquals(Reservasi.MENUNGGU, r.getStatus());
        assertTrue(r.isMenunggu());
        assertTrue(r.getIdReservasi().startsWith("R"));
        assertEquals(9, r.getIdReservasi().length());
        assertEquals(buku, r.getBuku());
        assertEquals(anggota, r.getAnggota());
    }

    @Test
    void sudahReservasi_hanya_true_untuk_buku_yang_direservasi() {
        Anggota anggota = new Anggota("A001", "Budi Santoso", 2);
        Buku bukuA = new Buku("B002", "Rekayasa Perangkat Lunak", 0);
        Buku bukuB = new Buku("B003", "Basis Data", 0);

        assertFalse(anggota.sudahReservasi(bukuA));
        Reservasi.buat(anggota, bukuA);
        assertTrue(anggota.sudahReservasi(bukuA));
        assertFalse(anggota.sudahReservasi(bukuB));
    }
}
