package com.mycompany.tubes;

public class Kasir extends Pengguna {
    private String idKaryawan; 

    public Kasir(String id, String nama, String noHp, String username, String password, String idKaryawan) {
        super(id, nama, noHp, username, password);
        this.idKaryawan = idKaryawan;
    }

    public boolean updateStatusPesanan(Pesanan pesanan, StatusPesanan statusbaru) {
        pesanan.setStatus(statusbaru);
        System.out.println("Kasir " + this.nama + " telah memperbarui pesanan ID " + pesanan.getIdPesanan() + " menjadi: " + statusbaru);
        return true;
    }

    public String getIdKaryawan() { return idKaryawan; }
    public void setIdKaryawan(String idKaryawan) { this.idKaryawan = idKaryawan; }
}