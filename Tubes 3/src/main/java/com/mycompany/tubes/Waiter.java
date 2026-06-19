package com.mycompany.tubes;

import java.util.ArrayList; 

public class Waiter extends Pengguna {
    private String idWaiter;

    public Waiter(String id, String nama, String noHp, String username, String password, String idWaiter) {
        super(id, nama, noHp, username, password);
        this.idWaiter = idWaiter;
    }
    
    public void lihatPesananSiapAntar(ArrayList<Pesanan> daftarPesanan) {
        System.out.println("\n--- CEK PESANAN UNTUK WAITER ---");
        boolean adaPesanan = false;
        
        for (Pesanan p : daftarPesanan) {
            if (p.getStatus() == StatusPesanan.DIPROSES || p.getStatus() == StatusPesanan.SIAP_ANTAR) {
                System.out.println("ID Pesanan : " + p.getIdPesanan());
                System.out.println("Pelanggan  : " + p.getPelanggan().nama);
                System.out.println("Status     : " + p.getStatus());
                System.out.println("Detail Makanan:");
                for (itemPesanan item : p.getDaftarItem()) {
                    System.out.println(" - " + item.getKuantitas() + "x " + item.getMenu().getNamaMenu() + " (" + item.getCatatan() + ")");
                }
                System.out.println("---------------------------------");
                adaPesanan = true;
            }
        }
        
        if (!adaPesanan) {
            System.out.println("Belum ada pesanan yang sedang diproses oleh dapur.");
        }
    }
    
    public void updateStatusSelesai(Pesanan p) {
        p.setStatus(StatusPesanan.SELESAI);
        System.out.println("Pesanan " + p.getIdPesanan() + " telah diserahkan ke pelanggan (Status: SELESAI).");
    }

    public String getIdWaiter() { return idWaiter; }
    public void setIdWaiter(String idWaiter) { this.idWaiter = idWaiter; }
}