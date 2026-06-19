package com.mycompany.tubes;

public interface MetodePembayaran {
    boolean prosesBayar(double total) throws PembayaranGagalException;
}