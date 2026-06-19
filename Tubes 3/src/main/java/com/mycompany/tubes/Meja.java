/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes;

/**
 *
 * @author Louis Marchall
 */
public class Meja {
    private int noMeja;
    private int kapasitas;
    private boolean isTersedia;

    // Constructor
    public Meja(int noMeja, int kapasitas) {
        this.noMeja = noMeja;
        this.kapasitas = kapasitas;
        this.isTersedia = true; // Secara default, meja baru selalu kosong/tersedia
    }

    // Method operasional tanpa void (mengembalikan boolean sebagai penanda sukses)
    public boolean ubahKetersediaan(boolean status) {
        this.isTersedia = status;
        return true;
    }

    // --- GETTER & SETTER ---
    public int getNoMeja() {
        return noMeja;
    }

    public void setNoMeja(int noMeja) {
        this.noMeja = noMeja;
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    public boolean getIsTersedia() {
        return isTersedia;
    }

    public void setIsTersedia(boolean isTersedia) {
        this.isTersedia = isTersedia;
    }
}
