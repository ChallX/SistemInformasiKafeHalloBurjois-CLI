package com.mycompany.tubes;

public class Pelanggan extends Pengguna {

    public Pelanggan(String id, String nama, String noHp, String username, String password) {
        super(id, nama, noHp, username, password); 
    }

    public Pesanan buatPesanan() {
        System.out.println("Pelanggan " + this.nama + " sedang membuat daftar pesanan...");
        return new Pesanan(this); 
    }

    public boolean buatReservasi(Meja meja, String waktu) {
        System.out.println("Pelanggan " + this.nama + " mem-booking meja untuk jam: " + waktu);
        return true; 
    }
}