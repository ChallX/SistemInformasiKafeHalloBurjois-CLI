package com.mycompany.tubes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Main {

    static ArrayList<Pengguna> daftarPengguna = new ArrayList<>();
    static ArrayList<Menu> daftarMenu = new ArrayList<>();
    static ArrayList<Meja> daftarMeja = new ArrayList<>();
    static ArrayList<Pesanan> daftarPesananAktif = new ArrayList<>();

    private static JFrame frame;
    private static CardLayout cardLayout;
    private static JPanel cardPanel;
    private static Pengguna penggunaAktif;

    public static void main(String[] args) {
        siapkanDataAwal();
        SwingUtilities.invokeLater(Main::tampilkanGUI);
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

    // ===================== GUI UTAMA =====================

    private static void tampilkanGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        frame = new JFrame("Sistem Informasi Kafe Hallo Burjois");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 620);
        frame.setMinimumSize(new Dimension(820, 560));
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(buatPanelLogin(), "login");
        cardPanel.add(buatPanelPelanggan(), "pelanggan");
        cardPanel.add(buatPanelKasir(), "kasir");
        cardPanel.add(buatPanelWaiter(), "waiter");
        cardPanel.add(buatPanelOwner(), "owner");

        frame.setContentPane(cardPanel);
        cardLayout.show(cardPanel, "login");
        frame.setVisible(true);
    }

    private static JPanel buatPanelDasar(String judul, String subjudul) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.add(new JLabel(judul));
        header.add(new JLabel(subjudul));
        panel.add(header, BorderLayout.NORTH);

        return panel;
    }

    private static JButton buatTombol(String teks, boolean utama) {
        return new JButton(teks);
    }

    private static void navigasiKeLogin() {
        penggunaAktif = null;
        cardLayout.show(cardPanel, "login");
    }

    private static String formatRupiah(double angka) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        nf.setMaximumFractionDigits(0);
        return nf.format(angka);
    }

    // ===================== LOGIN =====================

    private static JPanel buatPanelLogin() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(new JLabel("SISTEM INFORMASI KAFE HALLO BURJOIS"), gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("Silakan login"), gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        JTextField tfUser = new JTextField(15);
        panel.add(tfUser, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        JPasswordField tfPass = new JPasswordField(15);
        panel.add(tfPass, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JPanel aksi = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton btnLogin = buatTombol("Login", true);
        JButton btnKeluar = buatTombol("Keluar", false);
        aksi.add(btnLogin);
        aksi.add(btnKeluar);
        panel.add(aksi, gbc);

        btnLogin.addActionListener(e -> {
            String user = tfUser.getText().trim();
            String pass = new String(tfPass.getPassword());
            Pengguna akun = prosesLogin(user, pass);
            if (akun != null) {
                penggunaAktif = akun;
                tfPass.setText("");
                JOptionPane.showMessageDialog(frame,
                        "Login berhasil! Selamat datang, " + akun.nama + ".",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
                if (akun instanceof Pelanggan) {
                    cardLayout.show(cardPanel, "pelanggan");
                } else if (akun instanceof Kasir) {
                    cardLayout.show(cardPanel, "kasir");
                } else if (akun instanceof Waiter) {
                    cardLayout.show(cardPanel, "waiter");
                } else if (akun instanceof Owner) {
                    cardLayout.show(cardPanel, "owner");
                }
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Username atau password salah!",
                        "Login Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnKeluar.addActionListener(e -> System.exit(0));
        tfPass.addActionListener(e -> btnLogin.doClick());

        return panel;
    }

    // ===================== PELANGGAN =====================

    private static JPanel buatPanelPelanggan() {
        JPanel panel = buatPanelDasar("Menu Pelanggan",
                "Pesan makanan atau booking meja di Hallo Burjois");

        JPanel konten = new JPanel(new GridLayout(1, 2, 10, 0));

        JPanel kartuPesanan = buatKartuAksi("Buat Pesanan Baru",
                "Pilih menu, tentukan porsi, lalu kirim pesanan ke dapur.");
        JButton btnPesanan = buatTombol("Buat Pesanan", true);
        kartuPesanan.add(btnPesanan, BorderLayout.SOUTH);

        JPanel kartuReservasi = buatKartuAksi("Booking Meja",
                "Lihat ketersediaan meja dan reservasi untuk waktu kedatangan.");
        JButton btnReservasi = buatTombol("Booking Meja", true);
        kartuReservasi.add(btnReservasi, BorderLayout.SOUTH);

        konten.add(kartuPesanan);
        konten.add(kartuReservasi);
        panel.add(konten, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLogout = buatTombol("Logout", false);
        footer.add(btnLogout);
        panel.add(footer, BorderLayout.SOUTH);

        btnPesanan.addActionListener(e -> {
            if (penggunaAktif instanceof Pelanggan pel) {
                tampilkanDialogPesanan(pel);
            }
        });

        btnReservasi.addActionListener(e -> {
            if (penggunaAktif instanceof Pelanggan pel) {
                tampilkanDialogReservasi(pel);
            }
        });

        btnLogout.addActionListener(e -> {
            penggunaAktif.logout();
            navigasiKeLogin();
        });

        return panel;
    }

    private static JPanel buatKartuAksi(String judul, String deskripsi) {
        JPanel kartu = new JPanel(new BorderLayout(0, 8));
        kartu.setBorder(BorderFactory.createTitledBorder(judul));

        JTextArea area = new JTextArea(deskripsi);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);

        kartu.add(area, BorderLayout.CENTER);
        return kartu;
    }

    private static void tampilkanDialogPesanan(Pelanggan pel) {
        JDialog dialog = new JDialog(frame, "Buat Pesanan Baru", true);
        dialog.setSize(720, 520);
        dialog.setLocationRelativeTo(frame);
        dialog.setLayout(new BorderLayout(12, 12));

        Pesanan pesananBaru = new Pesanan(pel);

        String[] kolomMenu = {"No", "Menu", "Kategori", "Harga"};
        DefaultTableModel modelMenu = new DefaultTableModel(kolomMenu, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        for (int i = 0; i < daftarMenu.size(); i++) {
            Menu m = daftarMenu.get(i);
            modelMenu.addRow(new Object[]{i + 1, m.getNamaMenu(), m.getKategori(), formatRupiah(m.getHarga())});
        }
        JTable tabelMenu = new JTable(modelMenu);

        String[] kolomKeranjang = {"Menu", "Porsi", "Catatan", "Subtotal"};
        DefaultTableModel modelKeranjang = new DefaultTableModel(kolomKeranjang, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        JTable tabelKeranjang = new JTable(modelKeranjang);

        JLabel lblTotal = new JLabel("Total: Rp0");

        Runnable refreshTotal = () -> {
            lblTotal.setText("Total: " + formatRupiah(pesananBaru.hitungTotal()));
        };

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 99, 1);
        JSpinner spnQty = new JSpinner(spinnerModel);
        spnQty.setPreferredSize(new Dimension(60, 30));
        JTextField tfCatatan = new JTextField(18);
        form.add(new JLabel("Porsi:"));
        form.add(spnQty);
        form.add(new JLabel("Catatan:"));
        form.add(tfCatatan);

        JButton btnTambah = buatTombol("Tambah ke Pesanan", true);
        form.add(btnTambah);

        btnTambah.addActionListener(ev -> {
            int baris = tabelMenu.getSelectedRow();
            if (baris < 0) {
                JOptionPane.showMessageDialog(dialog, "Pilih menu terlebih dahulu!", "Peringatan",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            Menu menuDipilih = daftarMenu.get(baris);
            int qty = (Integer) spnQty.getValue();
            String cat = tfCatatan.getText().trim();
            if (cat.isEmpty()) {
                cat = "-";
            }
            itemPesanan item = new itemPesanan(menuDipilih, qty, cat);
            pesananBaru.tambahItem(item);
            modelKeranjang.addRow(new Object[]{
                    menuDipilih.getNamaMenu(), qty, cat, formatRupiah(item.hitungSubtotal())
            });
            refreshTotal.run();
            tfCatatan.setText("");
            spnQty.setValue(1);
        });

        JPanel bawah = new JPanel(new BorderLayout());
        bawah.add(lblTotal, BorderLayout.WEST);

        JPanel aksiBawah = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton btnBatal = buatTombol("Batal", false);
        JButton btnSelesai = buatTombol("Kirim Pesanan", true);
        aksiBawah.add(btnBatal);
        aksiBawah.add(btnSelesai);
        bawah.add(aksiBawah, BorderLayout.EAST);

        btnBatal.addActionListener(ev -> dialog.dispose());

        btnSelesai.addActionListener(ev -> {
            if (pesananBaru.getDaftarItem().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Pesanan kosong. Tambahkan menu terlebih dahulu.",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            daftarPesananAktif.add(pesananBaru);
            JOptionPane.showMessageDialog(dialog,
                    "Pesanan berhasil dibuat!\nID: " + pesananBaru.getIdPesanan()
                            + "\nTotal: " + formatRupiah(pesananBaru.hitungTotal()),
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        JPanel atas = new JPanel(new BorderLayout(0, 4));
        atas.add(new JLabel("Daftar Menu Hallo Burjois"), BorderLayout.NORTH);
        atas.add(new JScrollPane(tabelMenu), BorderLayout.CENTER);

        JPanel tengah = new JPanel(new BorderLayout(0, 4));
        tengah.add(form, BorderLayout.NORTH);
        tengah.add(new JLabel("Keranjang Pesanan"), BorderLayout.CENTER);
        tengah.add(new JScrollPane(tabelKeranjang), BorderLayout.SOUTH);
        tengah.setPreferredSize(new Dimension(0, 180));

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, atas, tengah);
        split.setResizeWeight(0.5);
        split.setDividerLocation(220);

        dialog.add(split, BorderLayout.CENTER);
        dialog.add(bawah, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private static void tampilkanDialogReservasi(Pelanggan pel) {
        JDialog dialog = new JDialog(frame, "Booking Meja", true);
        dialog.setSize(520, 420);
        dialog.setLocationRelativeTo(frame);
        dialog.setLayout(new BorderLayout(12, 12));

        String[] kolom = {"No Meja", "Kapasitas", "Status"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        for (Meja m : daftarMeja) {
            String status = m.getIsTersedia() ? "Tersedia" : "Penuh";
            model.addRow(new Object[]{m.getNoMeja(), m.getKapasitas() + " orang", status});
        }
        JTable tabel = new JTable(model);

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.add(new JLabel("Nomor Meja:"));
        JTextField tfMeja = new JTextField();
        form.add(tfMeja);
        form.add(new JLabel("Waktu Kedatangan:"));
        JTextField tfWaktu = new JTextField();
        tfWaktu.setToolTipText("Contoh: 19:00");
        form.add(tfWaktu);

        JPanel aksi = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        JButton btnBatal = buatTombol("Batal", false);
        JButton btnKonfirmasi = buatTombol("Konfirmasi Reservasi", true);
        aksi.add(btnBatal);
        aksi.add(btnKonfirmasi);

        btnBatal.addActionListener(ev -> dialog.dispose());

        btnKonfirmasi.addActionListener(ev -> {
            int noMeja;
            try {
                noMeja = Integer.parseInt(tfMeja.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Nomor meja harus berupa angka!",
                        "Input Salah", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String jam = tfWaktu.getText().trim();
            if (jam.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Waktu kedatangan wajib diisi!",
                        "Input Salah", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Meja mejaDipilih = null;
            for (Meja m : daftarMeja) {
                if (m.getNoMeja() == noMeja) {
                    mejaDipilih = m;
                    break;
                }
            }
            if (mejaDipilih == null) {
                JOptionPane.showMessageDialog(dialog, "Nomor meja tidak ditemukan!",
                        "Gagal", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Reservasi res = new Reservasi("RES-" + System.currentTimeMillis(), pel, mejaDipilih, jam);
            try {
                res.konfirmasiReservasi();
                JOptionPane.showMessageDialog(dialog,
                        "Reservasi berhasil!\nMeja " + mejaDipilih.getNoMeja()
                                + " untuk " + pel.nama + " pukul " + jam,
                        "Berhasil", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } catch (MejaPenuhException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(new JScrollPane(tabel), BorderLayout.CENTER);
        dialog.add(form, BorderLayout.NORTH);
        dialog.add(aksi, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    // ===================== KASIR =====================

    private static DefaultTableModel modelPesananKasir;

    private static JPanel buatPanelKasir() {
        JPanel panel = buatPanelDasar("Dashboard Kasir",
                "Kelola dan proses pembayaran pesanan pelanggan");

        String[] kolom = {"No", "ID Pesanan", "Pelanggan", "Status", "Total"};
        modelPesananKasir = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        JTable tabel = new JTable(modelPesananKasir);

        JPanel aksi = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton btnRefresh = buatTombol("Refresh", false);
        JButton btnBayar = buatTombol("Proses Pembayaran", true);
        JButton btnLogout = buatTombol("Logout", false);
        aksi.add(btnRefresh);
        aksi.add(btnBayar);
        aksi.add(btnLogout);

        panel.add(new JScrollPane(tabel), BorderLayout.CENTER);
        panel.add(aksi, BorderLayout.SOUTH);

        Runnable refresh = () -> muatPesananKeTabel(modelPesananKasir, false);

        btnRefresh.addActionListener(e -> refresh.run());
        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                refresh.run();
            }
        });

        btnBayar.addActionListener(e -> {
            if (!(penggunaAktif instanceof Kasir ksr)) {
                return;
            }
            int baris = tabel.getSelectedRow();
            if (baris < 0) {
                JOptionPane.showMessageDialog(frame, "Pilih pesanan yang akan dibayar!",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Pesanan pesananBayar = daftarPesananAktif.get(baris);
            tampilkanDialogPembayaran(ksr, pesananBayar, refresh);
        });

        btnLogout.addActionListener(e -> {
            penggunaAktif.logout();
            navigasiKeLogin();
        });

        return panel;
    }

    private static void muatPesananKeTabel(DefaultTableModel model, boolean filterWaiter) {
        model.setRowCount(0);
        for (int i = 0; i < daftarPesananAktif.size(); i++) {
            Pesanan p = daftarPesananAktif.get(i);
            if (filterWaiter && p.getStatus() != StatusPesanan.DIPROSES
                    && p.getStatus() != StatusPesanan.SIAP_ANTAR) {
                continue;
            }
            model.addRow(new Object[]{
                    i + 1, p.getIdPesanan(), p.getPelanggan().nama,
                    p.getStatus(), formatRupiah(p.hitungTotal())
            });
        }
    }

    private static void tampilkanDialogPembayaran(Kasir ksr, Pesanan pesanan, Runnable refresh) {
        double totalTagihan = pesanan.hitungTotal();

        String[] opsi = {"Cash / Tunai", "QRIS"};
        int metode = JOptionPane.showOptionDialog(frame,
                "Total Tagihan: " + formatRupiah(totalTagihan) + "\nPilih metode pembayaran:",
                "Pembayaran - " + pesanan.getIdPesanan(),
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opsi, opsi[0]);

        if (metode == JOptionPane.CLOSED_OPTION) {
            return;
        }

        MetodePembayaran paymentMethod = null;

        if (metode == 0) {
            String input = JOptionPane.showInputDialog(frame,
                    "Masukkan jumlah uang pelanggan (Rp):", totalTagihan);
            if (input == null) {
                return;
            }
            try {
                double uang = Double.parseDouble(input.trim());
                paymentMethod = new PembayaranCash(uang);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Jumlah uang tidak valid!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } else if (metode == 1) {
            paymentMethod = new PembayaranQRIS("TX-QR-" + System.currentTimeMillis());
        }

        if (paymentMethod != null) {
            try {
                boolean sukses = paymentMethod.prosesBayar(totalTagihan);
                if (sukses) {
                    ksr.updateStatusPesanan(pesanan, StatusPesanan.DIPROSES);
                    String pesan = metode == 0
                            ? "Pembayaran tunai berhasil!\nKembalian: "
                            + formatRupiah(((PembayaranCash) paymentMethod).getUangDibayar() - totalTagihan)
                            : "Pembayaran QRIS berhasil diverifikasi!";
                    JOptionPane.showMessageDialog(frame, pesan, "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    refresh.run();
                }
            } catch (PembayaranGagalException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Pembayaran Gagal",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ===================== WAITER =====================

    private static DefaultTableModel modelPesananWaiter;
    private static ArrayList<Pesanan> pesananWaiterCache = new ArrayList<>();

    private static JPanel buatPanelWaiter() {
        JPanel panel = buatPanelDasar("Dashboard Waiter",
                "Pantau pesanan siap antar dan tandai selesai");

        String[] kolom = {"No", "ID Pesanan", "Pelanggan", "Status", "Detail Item"};
        modelPesananWaiter = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        JTable tabel = new JTable(modelPesananWaiter);

        JTextArea detailArea = new JTextArea(6, 40);
        detailArea.setEditable(false);
        detailArea.setLineWrap(true);
        detailArea.setWrapStyleWord(true);

        tabel.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            int baris = tabel.getSelectedRow();
            if (baris >= 0 && baris < pesananWaiterCache.size()) {
                Pesanan p = pesananWaiterCache.get(baris);
                StringBuilder sb = new StringBuilder();
                sb.append("Pelanggan: ").append(p.getPelanggan().nama).append("\n");
                sb.append("Status: ").append(p.getStatus()).append("\n\nDetail:\n");
                for (itemPesanan item : p.getDaftarItem()) {
                    sb.append(" - ").append(item.getKuantitas()).append("x ")
                            .append(item.getMenu().getNamaMenu())
                            .append(" (").append(item.getCatatan()).append(")\n");
                }
                detailArea.setText(sb.toString());
            }
        });

        JPanel aksi = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton btnRefresh = buatTombol("Refresh", false);
        JButton btnSelesai = buatTombol("Tandai Selesai", true);
        JButton btnLogout = buatTombol("Logout", false);
        aksi.add(btnRefresh);
        aksi.add(btnSelesai);
        aksi.add(btnLogout);

        JPanel bawah = new JPanel(new BorderLayout());
        bawah.add(new JLabel("Detail Pesanan Terpilih:"), BorderLayout.NORTH);
        bawah.add(new JScrollPane(detailArea), BorderLayout.CENTER);
        bawah.add(aksi, BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(tabel), bawah);
        split.setResizeWeight(0.55);
        split.setDividerLocation(260);

        panel.add(split, BorderLayout.CENTER);

        Runnable refresh = () -> muatPesananWaiter();

        btnRefresh.addActionListener(e -> refresh.run());
        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                refresh.run();
            }
        });

        btnSelesai.addActionListener(e -> {
            if (!(penggunaAktif instanceof Waiter wtr)) {
                return;
            }
            if (pesananWaiterCache.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Belum ada pesanan siap antar.",
                        "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int baris = tabel.getSelectedRow();
            if (baris < 0) {
                JOptionPane.showMessageDialog(frame, "Pilih pesanan terlebih dahulu!",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Pesanan p = pesananWaiterCache.get(baris);
            if (p.getStatus() != StatusPesanan.DIPROSES && p.getStatus() != StatusPesanan.SIAP_ANTAR) {
                JOptionPane.showMessageDialog(frame, "Pesanan ini belum siap diantar!",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            wtr.updateStatusSelesai(p);
            JOptionPane.showMessageDialog(frame,
                    "Pesanan " + p.getIdPesanan() + " telah diserahkan (SELESAI).",
                    "Sukses", JOptionPane.INFORMATION_MESSAGE);
            refresh.run();
        });

        btnLogout.addActionListener(e -> {
            penggunaAktif.logout();
            navigasiKeLogin();
        });

        return panel;
    }

    private static void muatPesananWaiter() {
        modelPesananWaiter.setRowCount(0);
        pesananWaiterCache.clear();
        for (Pesanan p : daftarPesananAktif) {
            if (p.getStatus() == StatusPesanan.DIPROSES || p.getStatus() == StatusPesanan.SIAP_ANTAR) {
                pesananWaiterCache.add(p);
                StringBuilder items = new StringBuilder();
                for (itemPesanan item : p.getDaftarItem()) {
                    if (items.length() > 0) {
                        items.append(", ");
                    }
                    items.append(item.getKuantitas()).append("x ").append(item.getMenu().getNamaMenu());
                }
                modelPesananWaiter.addRow(new Object[]{
                        pesananWaiterCache.size(), p.getIdPesanan(),
                        p.getPelanggan().nama, p.getStatus(), items.toString()
                });
            }
        }
        if (pesananWaiterCache.isEmpty()) {
            modelPesananWaiter.addRow(new Object[]{"-", "-", "-", "-", "Belum ada pesanan siap antar"});
        }
    }

    // ===================== OWNER =====================

    private static JPanel buatPanelOwner() {
        JPanel panel = buatPanelDasar("Dashboard Owner",
                "Pantau laporan keuangan dan aktivitas meja");

        JPanel konten = new JPanel(new GridLayout(1, 2, 10, 0));

        JPanel kartuLaporan = buatKartuAksi("Laporan Transaksi",
                "Lihat daftar transaksi sukses dan total pendapatan kafe.");
        JButton btnLaporan = buatTombol("Lihat Laporan", true);
        kartuLaporan.add(btnLaporan, BorderLayout.SOUTH);

        JPanel kartuMeja = buatKartuAksi("Rekap Aktivitas Meja",
                "Pantau meja yang kosong, dipakai, atau sedang dibooking.");
        JButton btnMeja = buatTombol("Lihat Rekap Meja", true);
        kartuMeja.add(btnMeja, BorderLayout.SOUTH);

        konten.add(kartuLaporan);
        konten.add(kartuMeja);

        JTextArea areaLaporan = new JTextArea(12, 50);
        areaLaporan.setEditable(false);
        JScrollPane scrollLaporan = new JScrollPane(areaLaporan);
        scrollLaporan.setBorder(BorderFactory.createTitledBorder("Hasil Laporan"));

        JPanel tengah = new JPanel(new BorderLayout(0, 10));
        tengah.add(konten, BorderLayout.NORTH);
        tengah.add(scrollLaporan, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLogout = buatTombol("Logout", false);
        footer.add(btnLogout);

        panel.add(tengah, BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);

        btnLaporan.addActionListener(e -> {
            if (penggunaAktif instanceof Owner own) {
                areaLaporan.setText(buatTeksLaporanTransaksi(own));
            }
        });

        btnMeja.addActionListener(e -> {
            if (penggunaAktif instanceof Owner own) {
                areaLaporan.setText(buatTeksRekapMeja(own));
            }
        });

        btnLogout.addActionListener(ev -> {
            penggunaAktif.logout();
            navigasiKeLogin();
        });

        return panel;
    }

    private static String buatTeksLaporanTransaksi(Owner own) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- LAPORAN KEUANGAN KAFE ---\n\n");
        double totalPendapatan = 0;
        int jumlahTransaksi = 0;

        for (Pesanan p : daftarPesananAktif) {
            if (p.getStatus() != StatusPesanan.DITERIMA) {
                sb.append("ID: ").append(p.getIdPesanan())
                        .append(" | Pelanggan: ").append(p.getPelanggan().nama)
                        .append(" | Pemasukan: ").append(formatRupiah(p.hitungTotal())).append("\n");
                totalPendapatan += p.hitungTotal();
                jumlahTransaksi++;
            }
        }
        sb.append("\n---------------------------------\n");
        sb.append("Total Transaksi Sukses : ").append(jumlahTransaksi).append("\n");
        sb.append("Total Pendapatan Kas   : ").append(formatRupiah(totalPendapatan)).append("\n");

        own.lihatLaporanTransaksi(daftarPesananAktif);
        return sb.toString();
    }

    private static String buatTeksRekapMeja(Owner own) {
        StringBuilder sb = new StringBuilder();
        sb.append("--- REKAP AKTIVITAS MEJA ---\n\n");
        int mejaTerpakai = 0;

        for (Meja m : daftarMeja) {
            String status = m.getIsTersedia() ? "Kosong" : "Dipakai / Dibooking";
            sb.append("Meja ").append(m.getNoMeja())
                    .append(" (Kapasitas ").append(m.getKapasitas()).append(" orang) -> ")
                    .append(status).append("\n");
            if (!m.getIsTersedia()) {
                mejaTerpakai++;
            }
        }
        sb.append("\nTotal Meja Terpakai: ").append(mejaTerpakai)
                .append(" dari ").append(daftarMeja.size()).append(" meja.\n");

        own.lihatRekapAktivitas(daftarMeja);
        return sb.toString();
    }
}
