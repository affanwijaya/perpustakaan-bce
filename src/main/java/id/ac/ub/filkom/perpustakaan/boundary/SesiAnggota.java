package id.ac.ub.filkom.perpustakaan.boundary;

import jakarta.servlet.http.HttpSession;

/**
 * Bantuan boundary: id anggota yang sedang memakai halaman.
 * Disimpan per pengguna di session (bukan di field controller yang singleton).
 */
final class SesiAnggota {

    /** Anggota default selama fitur login belum dibuat (skenario uji memakai A001). */
    private static final String ANGGOTA_DEFAULT = "A001";

    private SesiAnggota() { }

    static String idAnggotaLogin(HttpSession session) {
        Object idAnggota = session.getAttribute("idAnggota");
        if (idAnggota == null) {
            session.setAttribute("idAnggota", ANGGOTA_DEFAULT);
            return ANGGOTA_DEFAULT;
        }
        return idAnggota.toString();
    }
}
