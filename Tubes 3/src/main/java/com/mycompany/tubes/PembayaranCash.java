package com.mycompany.tubes;

public class PembayaranCash implements MetodePembayaran {
    private double uangDibayar;

    public PembayaranCash(double uangDibayar) {
        this.uangDibayar = uangDibayar;
    }

    @Override
    public boolean prosesBayar(double total) throws PembayaranGagalException {
        if (uangDibayar >= total) {
            System.out.println("Pembayaran Tunai Berhasil. Kembalian: Rp" + (uangDibayar - total));
            return true;
        } else {
            throw new PembayaranGagalException("Uang tunai kurang! Tagihan: Rp" + total + ", Uang dibayar: Rp" + uangDibayar);
        }
    }
    
    public double getUangDibayar() { return uangDibayar; }
    public void setUangDibayar(double uang) { this.uangDibayar = uang; }
}