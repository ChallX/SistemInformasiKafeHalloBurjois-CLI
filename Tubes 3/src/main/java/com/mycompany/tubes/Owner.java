package com.mycompany.tubes;

import java.util.ArrayList; 

public class Owner extends Pengguna {
    private String idOwner;

    public Owner(String id, String nama, String noHp, String username, String password, String idOwner) {
        super(id, nama, noHp, username, password);
        this.idOwner = idOwner;
    }
    
    public void lihatLaporanTransaksi(ArrayList<Pesanan> daftarPesanan) {
        System.out.println("\n--- LAPORAN KEUANGAN KAFE ---");
        double totalPendapatan = 0;
        int jumlahTransaksi = 0;

        for (Pesanan p : daftarPesanan) {
            if (p.getStatus() != StatusPesanan.DITERIMA) {
                System.out.println("ID: " + p.getIdPesanan() + " | Pelanggan: " + p.getPelanggan().nama + " | Pemasukan: Rp" + p.hitungTotal());
                totalPendapatan += p.hitungTotal();
                jumlahTransaksi++;
            }
        }
        System.out.println("---------------------------------");
        System.out.println("Total Transaksi Sukses : " + jumlahTransaksi);
        System.out.println("Total Pendapatan Kas   : Rp" + totalPendapatan);
    }

    public boolean lihatRekapAktivitas(ArrayList<Meja> daftarMeja) {
        System.out.println("\n--- REKAP AKTIVITAS MEJA ---");
        int mejaTerpakai = 0;
        
        for (Meja m : daftarMeja) {
            String status = m.getIsTersedia() ? "Kosong" : "Dipakai / Dibooking";
            System.out.println("Meja " + m.getNoMeja() + " (Kapasitas " + m.getKapasitas() + " orang) -> " + status);
            
            if (!m.getIsTersedia()) {
                mejaTerpakai++;
            }
        }
        System.out.println("\nTotal Meja Terpakai: " + mejaTerpakai + " dari " + daftarMeja.size() + " meja.");
        return true;
    }

    public String getIdOwner() { return idOwner; }
    public void setIdOwner(String idOwner) { this.idOwner = idOwner; }
}