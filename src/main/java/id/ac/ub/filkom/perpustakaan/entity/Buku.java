package id.ac.ub.filkom.perpustakaan.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** «entity» Buku — tabel buku (skema 8a). */
@Entity
@Table(name = "buku")
public class Buku {

    @Id
    @Column(name = "id_buku", length = 10)
    private String idBuku;

    @Column(name = "judul", length = 200, nullable = false)
    private String judul;

    @Column(name = "stok")
    private int stok;

    protected Buku() { }               // wajib ada untuk JPA

    public Buku(String idBuku, String judul, int stok) {
        this.idBuku = idBuku;
        this.judul = judul;
        this.stok = stok;
    }

    /** +cekStok() : boolean */
    public boolean cekStok() {
        return stok > 0;
    }

    /** +kurangiStok() : void */
    public void kurangiStok() {
        if (stok <= 0) {
            throw new IllegalStateException("Stok sudah habis");
        }
        stok--;
    }

    /** +tambahStok(jumlah : int) : void — UC-02 Kelola Stok Buku. */
    public void tambahStok(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih dari 0");
        }
        stok += jumlah;
    }

    /** +kurangiStok(jumlah : int) : void — UC-02 Kelola Stok Buku (mis. buku rusak/hilang). */
    public void kurangiStok(int jumlah) {
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah harus lebih dari 0");
        }
        if (jumlah > stok) {
            throw new IllegalStateException("Stok tidak boleh negatif (stok saat ini " + stok + ")");
        }
        stok -= jumlah;
    }

    public String getIdBuku() { return idBuku; }
    public String getJudul()  { return judul; }
    public int getStok()      { return stok; }
}
