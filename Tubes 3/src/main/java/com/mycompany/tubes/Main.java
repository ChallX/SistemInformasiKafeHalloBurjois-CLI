package com.mycompany.tubes;

import java.util.*;

public class Main {

    static ArrayList<Pengguna> daftarPengguna = new ArrayList<>();
    static ArrayList<Menu> daftarMenu = new ArrayList<>();
    static ArrayList<Meja> daftarMeja = new ArrayList<>();
    static ArrayList<Pesanan> daftarPesananAktif = new ArrayList<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        siapkanDataAwal(); 

        boolean isAplikasiBerjalan = true;

        while (isAplikasiBerjalan) {
            System.out.println("\n==============================================");
            System.out.println("   SISTEM INFORMASI KAFE HALLO BURJOIS        ");
            System.out.println("==============================================");
            System.out.println("1. Login");
            System.out.println("0. Keluar Aplikasi");
            System.out.print("Pilih menu (0/1): ");
            
            int pilihanUtama = input.nextInt();
            input.nextLine();

            if (pilihanUtama == 1) {
                System.out.println("\n--- SILAKAN LOGIN ---");
                System.out.print("Username : ");
                String usInput = input.nextLine();
                System.out.print("Password : ");
                String pwInput = input.nextLine();

                Pengguna akunLogin = prosesLogin(usInput, pwInput);

                if (akunLogin != null) {
                    System.out.println("\n[SUKSES] Login Berhasil! Selamat datang, " + akunLogin.nama + ".");
                    
                    if (akunLogin instanceof Pelanggan) {
                        menuPelanggan(input, (Pelanggan) akunLogin); 
                    } else if (akunLogin instanceof Kasir) {
                        menuKasir(input, (Kasir) akunLogin);
                    } else if (akunLogin instanceof Waiter) {
                        menuWaiter(input, (Waiter) akunLogin);
                    } else if (akunLogin instanceof Owner) {
                        menuOwner(input, (Owner) akunLogin);
                    }
                    
                } else {
                    System.out.println("\n[GAGAL] Username atau Password salah! Silakan coba lagi.");
                }

            } else if (pilihanUtama == 0) {
                isAplikasiBerjalan = false;
                System.out.println("\nSistem dimatikan. Terima kasih!");
            } else {
                System.out.println("\n[!] Pilihan tidak valid.");
            }
        }
        
        input.close();
    }

    static Pengguna prosesLogin(String username, String password) {
        for (Pengguna p : daftarPengguna) {
            if (p.login(username, password)) {
                return p;
            }
        }
        return null;
    }

    static void siapkanDataAwal() {
        daftarPengguna.add(new Pelanggan("C01", "Gley", "0811", "gley123", "pass123"));
        daftarPengguna.add(new Pelanggan("C02", "Louis", "0812", "louis123", "pass123"));
        daftarPengguna.add(new Kasir("K01", "Nabilah", "0822", "kasir_nab", "kasir123", "EMP-01"));
        daftarPengguna.add(new Waiter("W01", "Wahyu", "0833", "waiter_why", "waiter123", "WT-01"));
        daftarPengguna.add(new Owner("O01", "Bos Besar", "0899", "admin", "admin123", "OWN-01"));

        daftarMenu.add(new Menu("M01", "Kopi Susu Burjois", 26000, "Kopi"));
        daftarMenu.add(new Menu("M02", "Rice Beef Bulgogi", 35000, "Makanan"));
        daftarMenu.add(new Menu("M03", "French Fries", 20000, "Snack"));

        daftarMeja.add(new Meja(1, 4));
        daftarMeja.add(new Meja(2, 2));
        daftarMeja.add(new Meja(3, 6));
    }

    static void menuPelanggan(Scanner input, Pelanggan pel) {
        boolean isAktif = true;
        while (isAktif) {
            System.out.println("\n--- MENU PELANGGAN (" + pel.nama + ") ---");
            System.out.println("1. Buat Pesanan Baru");
            System.out.println("2. Booking Meja (Reservasi)");
            System.out.println("0. Logout");
            System.out.print("Pilih aksi (0-2): ");
            int aksi = input.nextInt();
            input.nextLine();

            if (aksi == 1) {
                Pesanan pesananBaru = new Pesanan(pel);
                boolean nambahMenu = true;

                while (nambahMenu) {
                    System.out.println("\n--- DAFTAR MENU HALLO BURJOIS ---");
                    for (int i = 0; i < daftarMenu.size(); i++) {
                        System.out.println((i + 1) + ". " + daftarMenu.get(i).getNamaMenu() + " - Rp" + daftarMenu.get(i).getHarga());
                    }
                    System.out.println("0. Selesai Memesan");
                    System.out.print("Pilih menu (angka): ");
                    int pilMenu = input.nextInt();
                    input.nextLine();

                    if (pilMenu > 0 && pilMenu <= daftarMenu.size()) {
                        Menu menuDipilih = daftarMenu.get(pilMenu - 1);
                        System.out.print("Berapa porsi? ");
                        int qty = input.nextInt();
                        input.nextLine();
                        System.out.print("Catatan (opsional): ");
                        String cat = input.nextLine();

                        itemPesanan item = new itemPesanan(menuDipilih, qty, cat);
                        pesananBaru.tambahItem(item);
                        System.out.println(">> " + menuDipilih.getNamaMenu() + " ditambahkan ke pesanan!");
                    } else if (pilMenu == 0) {
                        nambahMenu = false;
                    } else {
                        System.out.println("Pilihan tidak valid!");
                    }
                }

                if (!pesananBaru.getDaftarItem().isEmpty()) {
                    daftarPesananAktif.add(pesananBaru);
                    System.out.println("\n[SUKSES] Pesanan berhasil dibuat dengan ID: " + pesananBaru.getIdPesanan());
                    System.out.println("Total Tagihan: Rp" + pesananBaru.hitungTotal());
                } else {
                    System.out.println("Pesanan dibatalkan (Kosong).");
                }

            } else if (aksi == 2) {
                System.out.println("\n--- BOOKING MEJA ---");
                for (Meja m : daftarMeja) {
                    String statusM = m.getIsTersedia() ? "Tersedia" : "Penuh";
                    System.out.println("Meja " + m.getNoMeja() + " (Kapasitas: " + m.getKapasitas() + ") - " + statusM);
                }
                
                System.out.print("Pilih Nomor Meja: ");
                int noMeja = input.nextInt();
                input.nextLine();
                System.out.print("Rencana Kedatangan (Cth: 19:00): ");
                String jam = input.nextLine();

                Meja mejaDipilih = null;
                for (Meja m : daftarMeja) {
                    if (m.getNoMeja() == noMeja) {
                        mejaDipilih = m;
                        break;
                    }
                }

                if (mejaDipilih != null) {
                    Reservasi res = new Reservasi("RES-" + System.currentTimeMillis(), pel, mejaDipilih, jam);
                    try {
                        res.konfirmasiReservasi(); 
                    } catch (MejaPenuhException e) {
                        System.out.println("\n[GAGAL] " + e.getMessage());
                    }
                } else {
                    System.out.println("Nomor meja tidak ditemukan.");
                }

            } else if (aksi == 0) {
                pel.logout();
                isAktif = false;
            }
        }
    }

    static void menuKasir(Scanner input, Kasir ksr) {
        boolean isAktif = true;
        while (isAktif) {
            System.out.println("\n--- DASHBOARD KASIR (" + ksr.nama + ") ---");
            System.out.println("1. Tampilkan & Proses Pembayaran Pesanan");
            System.out.println("0. Logout");
            System.out.print("Pilih aksi (0-1): ");
            int aksi = input.nextInt();
            input.nextLine();

            if (aksi == 1) {
                if (daftarPesananAktif.isEmpty()) {
                    System.out.println("Tidak ada pesanan aktif saat ini.");
                    continue;
                }

                System.out.println("\n--- DAFTAR PESANAN AKTIF ---");
                for (int i = 0; i < daftarPesananAktif.size(); i++) {
                    Pesanan p = daftarPesananAktif.get(i);
                    System.out.println((i + 1) + ". ID: " + p.getIdPesanan() + " | Pelanggan: " + p.getPelanggan().nama + " | Status: " + p.getStatus() + " | Total: Rp" + p.hitungTotal());
                }
                
                System.out.print("Pilih pesanan yang akan dibayar (0 untuk batal): ");
                int pilPesanan = input.nextInt();
                input.nextLine();

                if (pilPesanan > 0 && pilPesanan <= daftarPesananAktif.size()) {
                    Pesanan pesananBayar = daftarPesananAktif.get(pilPesanan - 1);
                    double totalTagihan = pesananBayar.hitungTotal();

                    System.out.println("\nTotal Tagihan: Rp" + totalTagihan);
                    System.out.println("Metode Pembayaran:");
                    System.out.println("1. Cash / Tunai");
                    System.out.println("2. QRIS");
                    System.out.print("Pilih metode (1-2): ");
                    int metode = input.nextInt();
                    input.nextLine();

                    MetodePembayaran paymentMethod = null;

                    if (metode == 1) {
                        System.out.print("Masukkan jumlah uang pelanggan: Rp");
                        double uang = input.nextDouble();
                        input.nextLine();
                        paymentMethod = new PembayaranCash(uang);
                    } else if (metode == 2) {
                        System.out.println("Silakan scan QRIS. Menunggu verifikasi...");
                        paymentMethod = new PembayaranQRIS("TX-QR-" + System.currentTimeMillis());
                    }

                    if (paymentMethod != null) {
                        try {
                            boolean sukses = paymentMethod.prosesBayar(totalTagihan);
                            if (sukses) {
                                ksr.updateStatusPesanan(pesananBayar, StatusPesanan.DIPROSES);
                            }
                        } catch (PembayaranGagalException e) {
                            System.out.println("\n[ERROR PEMBAYARAN] " + e.getMessage());
                        }
                    }
                }
            } else if (aksi == 0) {
                ksr.logout();
                isAktif = false;
            }
        }
    }

static void menuWaiter(Scanner input, Waiter wtr) {
        boolean isAktif = true;
        while (isAktif) {
            System.out.println("\n--- DASHBOARD WAITER (" + wtr.nama + ") ---");
            System.out.println("1. Lihat Daftar Pesanan (Masak / Siap Antar)");
            System.out.println("2. Tandai Pesanan Menjadi SELESAI");
            System.out.println("0. Logout");
            System.out.print("Pilih aksi (0-2): ");
            int aksi = input.nextInt();
            input.nextLine();

            if (aksi == 1) {
                wtr.lihatPesananSiapAntar(daftarPesananAktif);
            } else if (aksi == 2) {
                System.out.println("\n--- PILIH PESANAN YANG SUDAH DIANTAR ---");
                for (int i = 0; i < daftarPesananAktif.size(); i++) {
                    Pesanan p = daftarPesananAktif.get(i);
                    if (p.getStatus() == StatusPesanan.DIPROSES || p.getStatus() == StatusPesanan.SIAP_ANTAR) {
                        System.out.println((i + 1) + ". ID: " + p.getIdPesanan() + " | Pelanggan: " + p.getPelanggan().nama);
                    }
                }
                
                System.out.print("Pilih nomor urut pesanan (0 batal): ");
                int pil = input.nextInt();
                input.nextLine();
                if (pil > 0 && pil <= daftarPesananAktif.size()) {
                    wtr.updateStatusSelesai(daftarPesananAktif.get(pil - 1));
                }
            } else if (aksi == 0) {
                wtr.logout();
                isAktif = false;
            }
        }
    }

    static void menuOwner(Scanner input, Owner own) {
        boolean isAktif = true;
        while (isAktif) {
            System.out.println("\n--- DASHBOARD OWNER (" + own.nama + ") ---");
            System.out.println("1. Lihat Laporan Transaksi");
            System.out.println("2. Lihat Rekap Aktivitas Meja");
            System.out.println("0. Logout");
            System.out.print("Pilih aksi (0-2): ");
            int aksi = input.nextInt();
            input.nextLine();

            if (aksi == 1) {
                own.lihatLaporanTransaksi(daftarPesananAktif); 
            } else if (aksi == 2) {
                own.lihatRekapAktivitas(daftarMeja);
            } else if (aksi == 0) {
                own.logout();
                isAktif = false;
            }
        }
    }
}