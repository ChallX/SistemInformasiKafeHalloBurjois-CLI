package com.mycompany.tubes;

public class PembayaranQRIS implements MetodePembayaran {
    private String transactionId;

    public PembayaranQRIS(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public boolean prosesBayar(double total) throws PembayaranGagalException {
        System.out.println("Memproses QRIS dengan TX-ID: " + transactionId + " sejumlah Rp" + total);
        boolean apiSuccess = true; 
        
        if (apiSuccess) {
            System.out.println("Pembayaran QRIS Berhasil Diverifikasi!");
            return true;
        } else {
            throw new PembayaranGagalException("Koneksi QRIS Gagal atau Timeout.");
        }
    }
    
    public String getTransactionId() { return transactionId; }
}