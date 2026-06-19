/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes;

/**
 *
 * @author Louis Marchall
 */
public class Reservasi {
    private String idReservasi;
    private Pelanggan pelanggan;
    private Meja meja;
    private String waktu;

    // Constructor
    public Reservasi(String idReservasi, Pelanggan pelanggan, Meja meja, String waktu) {
        this.idReservasi = idReservasi;
        this.pelanggan = pelanggan;
        this.meja = meja;
        this.waktu = waktu;
    }

    // Method menggunakan klausa 'throws' untuk melempar Exception jika meja sudah dibooking
    public boolean konfirmasiReservasi() throws MejaPenuhException {
        // Cek apakah meja sedang dipakai
        if (!this.meja.getIsTersedia()) {
            throw new MejaPenuhException("Maaf, Meja nomor " + meja.getNoMeja() + " sudah di-booking atau tidak tersedia!");
        }
        
        // Jika lolos pengecekan (meja tersedia), kunci meja tersebut
        this.meja.ubahKetersediaan(false);
        System.out.println("\n[BERHASIL] Reservasi Dikonfirmasi!");
        System.out.println("Meja " + meja.getNoMeja() + " telah di-booking atas nama: " + pelanggan.nama);
        System.out.println("Waktu Reservasi: " + this.waktu);
        
        return true;
    }

    // --- GETTER & SETTER ---
    public String getIdReservasi() {
        return idReservasi;
    }

    public void setIdReservasi(String idReservasi) {
        this.idReservasi = idReservasi;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public void setPelanggan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }

    public Meja getMeja() {
        return meja;
    }

    public void setMeja(Meja meja) {
        this.meja = meja;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
