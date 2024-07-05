import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("Sistem modunu seçin (a: asenkron, s: senkron, q: çıkış): ");
            input = scanner.nextLine();

            if (input.equals("q")) {
                break;
            }

            boolean isSynchronized = input.equals("s");

            AirlineReservationSystem system = new AirlineReservationSystem(isSynchronized);
            String[] seats = {"koltuk_1", "koltuk_2", "koltuk_3", "koltuk_4", "koltuk_5"};

            System.out.println(isSynchronized ? "Senkron sistem başlıyor..." : "Asenkron sistem başlıyor...");

            // Tüm koltuklar için okuma thread'leri oluştur
            for (String seat : seats) {
                new Reader(system, seat).start();
            }

            // Her koltuk için yazma thread'leri oluştur (makeReservation ve cancelReservation)
            for (String seat : seats) {
                for (int i = 0; i < 3; i++) {  // Yarış durumunu göstermek için yazma thread'lerini arttır
                    new Writer(system, seat, true).start(); // makeReservation
                    new Writer(system, seat, false).start(); // cancelReservation
                }
            }

            try {
                Thread.sleep(5000); // İşlemlerin tamamlanması için bekleyelim
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isSynchronized) {
                System.out.println("Senkron sistem tamamlandı. Koltukların durumu:");
            } else {
                System.out.println("Asenkron sistem tamamlandı. Koltukların durumu:");
            }
            System.out.println(system.getSeatStatus());
            System.out.println("------------------------------------------------------");
        }

        scanner.close();
        System.out.println("Program sonlandı.");
    }
}
