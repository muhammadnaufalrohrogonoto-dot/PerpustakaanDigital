import java.util.ArrayList;
import java.util.Scanner;

class Buku {
    private final String judul;
    private final String pengarang;
    private final int tahunTerbit;
    private boolean dipinjam;
    
    public Buku(String judul, String pengarang, int tahunTerbit) {
        this.judul = judul;
        this.pengarang = pengarang;
        this.tahunTerbit = tahunTerbit;
        this.dipinjam = false;
    }
    
    public String getJudul() {
        return judul;
    }
    
    public boolean isDipinjam() {
        return dipinjam;
    }
    
    public void setDipinjam(boolean dipinjam) {
        this.dipinjam = dipinjam;
    }
    
    @Override
    public String toString() {
        String status = dipinjam ? "[DIPINJAM]" : "[TERSEDIA]";
        return String.format("%s - %s (%d) %s", judul, pengarang, tahunTerbit, status);
    }
}

public class Perpus_Online {
    private static final ArrayList<Buku> daftarBuku = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        // Inisialisasi dengan minimal 5 buku
        try (scanner) {
            // Inisialisasi dengan minimal 5 buku
            daftarBuku.add(new Buku("Laskar Pelangi", "Andrea Hirata", 2005));
            daftarBuku.add(new Buku("Bumi Manusia", "Pramoedya Ananta Toer", 1980));
            daftarBuku.add(new Buku("Negeri 5 Menara", "Ahmad Fuadi", 2009));
            daftarBuku.add(new Buku("Perahu Kertas", "Dee Lestari", 2009));
            daftarBuku.add(new Buku("Sang Pemimpi", "Andrea Hirata", 2006));
            
            boolean running = true;
            
            while (running) {
                tampilkanMenu();
                int pilihan = inputAngka();
                
                switch (pilihan) {
                    case 1 -> lihatDaftarBuku();
                    case 2 -> pinjamBuku();
                    case 3 -> kembalikanBuku();
                    case 4 -> {
                        running = false;
                        System.out.println("\nTerima kasih telah menggunakan aplikasi perpustakaan!");
                    }
                    default -> System.out.println("\nPilihan tidak valid. Silakan coba lagi.");
                }

                if (running) {
                    System.out.println("\nTekan Enter untuk melanjutkan...");
                    scanner.nextLine();
                }
            }
        }
    }
    
    private static void tampilkanMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("         APLIKASI PERPUSTAKAAN SEDERHANA");
        System.out.println("=".repeat(50));
        System.out.println("Menu Utama:");
        System.out.println("1. Lihat Daftar Buku");
        System.out.println("2. Pinjam Buku");
        System.out.println("3. Kembalikan Buku");
        System.out.println("4. Keluar");
        System.out.println("=".repeat(50));
        System.out.print("Pilih menu (1-4): ");
    }
    
    private static void lihatDaftarBuku() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("              DAFTAR BUKU PERPUSTAKAAN");
        System.out.println("=".repeat(50));
        
        if (daftarBuku.isEmpty()) {
            System.out.println("Tidak ada buku dalam perpustakaan.");
        } else {
            for (int i = 0; i < daftarBuku.size(); i++) {
                System.out.printf("%d. %s\n", i + 1, daftarBuku.get(i));
            }
        }
        System.out.println("=".repeat(50));
    }
    
    private static void pinjamBuku() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                  PINJAM BUKU");
        System.out.println("=".repeat(50));
        
        lihatDaftarBuku();
        
        System.out.print("\nMasukkan nomor buku yang ingin dipinjam (0 untuk batal): ");
        int nomor = inputAngka();
        
        if (nomor == 0) {
            System.out.println("Peminjaman dibatalkan.");
            return;
        }
        
        if (nomor < 1 || nomor > daftarBuku.size()) {
            System.out.println("Nomor buku tidak valid!");
            return;
        }
        
        Buku buku = daftarBuku.get(nomor - 1);
        
        if (buku.isDipinjam()) {
            System.out.println("Buku \"" + buku.getJudul() + "\" sudah dipinjam!");
        } else {
            buku.setDipinjam(true);
            System.out.println("Buku \"" + buku.getJudul() + "\" berhasil dipinjam!");
        }
    }
    
    private static void kembalikanBuku() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                KEMBALIKAN BUKU");
        System.out.println("=".repeat(50));
        
        // Tampilkan hanya buku yang sedang dipinjam
        ArrayList<Integer> indexDipinjam = new ArrayList<>();
        System.out.println("\nDaftar Buku yang Sedang Dipinjam:");
        System.out.println("-".repeat(50));
        
        boolean adaBukuDipinjam = false;
        for (int i = 0; i < daftarBuku.size(); i++) {
            if (daftarBuku.get(i).isDipinjam()) {
                indexDipinjam.add(i);
                System.out.printf("%d. %s\n", indexDipinjam.size(), daftarBuku.get(i));
                adaBukuDipinjam = true;
            }
        }
        
        if (!adaBukuDipinjam) {
            System.out.println("Tidak ada buku yang sedang dipinjam.");
            return;
        }
        
        System.out.println("-".repeat(50));
        System.out.print("\nMasukkan nomor buku yang ingin dikembalikan (0 untuk batal): ");
        int nomor = inputAngka();
        
        if (nomor == 0) {
            System.out.println("Pengembalian dibatalkan.");
            return;
        }
        
        if (nomor < 1 || nomor > indexDipinjam.size()) {
            System.out.println("Nomor buku tidak valid!");
            return;
        }
        
        Buku buku = daftarBuku.get(indexDipinjam.get(nomor - 1));
        buku.setDipinjam(false);
        System.out.println("Buku \"" + buku.getJudul() + "\" berhasil dikembalikan!");
    }
    
    private static int inputAngka() {
        while (true) {
            try {
                int angka = Integer.parseInt(scanner.nextLine());
                return angka;
            } catch (NumberFormatException e) {
                System.out.print("Input tidak valid! Masukkan angka: ");
            }
        }
    }
}