import java.time.LocalDateTime;

public class Reader extends Thread {
    private final AirlineReservationSystem system;
    private final String seat;

    public Reader(AirlineReservationSystem system, String seat) {
        this.system = system;
        this.seat = seat;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((int) (Math.random() * 1000));
            LocalDateTime currentTime = LocalDateTime.now();
            System.out.println("Date: " + currentTime.toLocalDate() + " Time: " + currentTime.toLocalTime() + " " + getName() + " kontrol ediyor: " + seat + " - Durumu: " +
                    (system.isSeatAvailable(seat) ? "Boş" : "Dolu"));
            System.out.println();
            System.out.println("Koltukların durumu:");
            System.out.println(system.getSeatStatus());
            System.out.println("------------------------------------------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
