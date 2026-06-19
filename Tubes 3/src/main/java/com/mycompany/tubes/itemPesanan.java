/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes;

import com.mycompany.tubes.Menu;

/**
 *
 * @author vcl
 */
public class itemPesanan {

    private Menu menu;
    private int kuantitas;
    private String catatan;

    public itemPesanan(Menu menu, int qty, String catatan) {
        this.menu = menu;
        this.kuantitas = qty;
        this.catatan = catatan;

    }

    public double hitungSubtotal() {
        return menu.getHarga() * kuantitas; 
    }

    public Menu getMenu() {
        return menu;

    }

    public void setMenu(Menu menu) {
        this.menu = menu;

    }

    public int getKuantitas() {
        return kuantitas;
    }

    public void setKuantitas(int kuantitas) {
        this.kuantitas = kuantitas;

    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;

    }
}
