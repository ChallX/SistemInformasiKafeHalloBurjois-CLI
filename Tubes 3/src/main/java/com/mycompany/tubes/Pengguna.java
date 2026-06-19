package com.mycompany.tubes;

public abstract class Pengguna {
    protected String id;
    protected String nama;
    protected String noHp;
    protected String username;
    protected String password;

    public Pengguna(String id, String nama, String noHp, String username, String password) {
        this.id = id;
        this.nama = nama;
        this.noHp = noHp;
        this.username = username;
        this.password = password;
    }

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void logout() {
        System.out.println(this.nama + " telah berhasil logout dari sistem.");
    }
}