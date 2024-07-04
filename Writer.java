import java.time.LocalDateTime;

public class Writer extends Thread {
    private final AirlineReservationSystem system;
    private final String seat;

    public Writer(AirlineReservationSystem system, String seat) {
        this.system = system;
        this.seat = seat;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((int) (Math.random() * 1000));
            LocalDateTime currentTime = LocalDateTime.now();
            System.out.println("Date: " + currentTime.toLocalDate() + " Time: " + currentTime.toLocalTime() + " " + getName() + " " + seat + " koltuğunu rezerve etmeye çalışıyor.");
            if (system.reserveSeat(seat)) {
                System.out.println("Date: " + currentTime.toLocalDate() + " Time: " + currentTime.toLocalTime() + " " + getName() + " " + seat + " koltuğunu başarıyla rezerve etti.");
            } else {
                System.out.println("Date: " + currentTime.toLocalDate() + " Time: " + currentTime.toLocalTime() + " " + getName() + " " + seat + " koltuğunu rezerve edemedi çünkü zaten rezerve edilmiş.");
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