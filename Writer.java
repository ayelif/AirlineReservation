import java.time.LocalDateTime;

public class Writer extends Thread {
    private final AirlineReservationSystem system;
    private final String seat;
    private final boolean makeReservation;

    public Writer(AirlineReservationSystem system, String seat, boolean makeReservation) {
        this.system = system;
        this.seat = seat;
        this.makeReservation = makeReservation;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((int) (Math.random() * 1000));
            LocalDateTime currentTime = LocalDateTime.now();
            if (makeReservation) {
                System.out.println("Date: " + currentTime.toLocalDate() + " Time: " + currentTime.toLocalTime() + " " + getName() + " " + seat + " koltuğunu rezerve etmeye çalışıyor.");
                if (system.makeReservation(seat)) {
                    System.out.println("Date: " + currentTime.toLocalDate() + " Time: " + currentTime.toLocalTime() + " " + getName() + " " + seat + " koltuğunu başarıyla rezerve etti.");
                } else {
                    System.out.println("Date: " + currentTime.toLocalDate() + " Time: " + currentTime.toLocalTime() + " " + getName() + " " + seat + " koltuğunu rezerve edemedi çünkü zaten rezerve edilmiş.");
                }
            } else {
                System.out.println("Date: " + currentTime.toLocalDate() + " Time: " + currentTime.toLocalTime() + " " + getName() + " " + seat + " koltuğu rezervasyonunu iptal etmeye çalışıyor.");
                if (system.cancelReservation(seat)) {
                    System.out.println("Date: " + currentTime.toLocalDate() + " Time: " + currentTime.toLocalTime() + " " + getName() + " " + seat + " koltuğu rezervasyonunu başarıyla iptal etti.");
                } else {
                    System.out.println("Date: " + currentTime.toLocalDate() + " Time: " + currentTime.toLocalTime() + " " + getName() + " " + seat + " koltuğu rezervasyonunu iptal edemedi çünkü zaten boş.");
                }
            }
            System.out.println();
            System.out.println("Koltukların durumu:");
            System.out.println(system.getSeatStatus());
            System.out.println("------------------------------------------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
