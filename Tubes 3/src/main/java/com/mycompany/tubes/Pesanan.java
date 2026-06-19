package com.mycompany.tubes;

import java.util.ArrayList;

public class Pesanan {
    private Pelanggan pelanggan;
    private String idPesanan;
    private StatusPesanan status; 
    private ArrayList<itemPesanan> daftarItem; 

    public Pesanan(Pelanggan pelanggan) {
        this.pelanggan = pelanggan;
        this.daftarItem = new ArrayList<>();
        this.status = StatusPesanan.DITERIMA; 
        this.idPesanan = "ORDER-" + System.currentTimeMillis();
    }

    public Pesanan(Pelanggan pelanggan, String idPesanan) {
        this.pelanggan = pelanggan;
        this.idPesanan = idPesanan;
        this.daftarItem = new ArrayList<>();
        this.status = StatusPesanan.DITERIMA;
    }

    public boolean tambahItem(itemPesanan item) {
        return this.daftarItem.add(item);
    }

    public double hitungTotal() {
        double total = 0;
        for (itemPesanan item : daftarItem) {
            total += item.hitungSubtotal(); 
        }
        return total;
    }
    
    public String getIdPesanan(){ return idPesanan; }
    public StatusPesanan getStatus() { return status; }
    public void setStatus(StatusPesanan status) { this.status = status; }
    public ArrayList<itemPesanan> getDaftarItem() { return daftarItem; }
    public Pelanggan getPelanggan() { return pelanggan; }
}