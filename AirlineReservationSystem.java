import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AirlineReservationSystem {
    private final Map<String, Boolean> seats;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final boolean isSynchronized;

    public AirlineReservationSystem(boolean isSynchronized) {
        this.isSynchronized = isSynchronized;
        seats = new LinkedHashMap<>();
        for (int i = 1; i <= 5; i++) {
            seats.put("koltuk_" + i, false); // false = boş, true = rezerve
        }
    }

    public boolean isSeatAvailable(String seat) {
        if (isSynchronized) {
            lock.readLock().lock();
            try {
                return !seats.getOrDefault(seat, true);
            } finally {
                lock.readLock().unlock();
            }
        } else {
            return !seats.getOrDefault(seat, true);
        }
    }

    public boolean makeReservation(String seat) {
        if (isSynchronized) {
            lock.writeLock().lock();
            try {
                if (!isSeatAvailable(seat)) {
                    return false;
                }
                seats.put(seat, true);
                return true;
            } finally {
                lock.writeLock().unlock();
            }
        } else {
            if (!seats.getOrDefault(seat, true)) {
                seats.put(seat, true);
                return true;
            } else {
                seats.put(seat, true);
                return true;
            }
        }
    }

    public boolean cancelReservation(String seat) {
        if (isSynchronized) {
            lock.writeLock().lock();
            try {
                if (isSeatAvailable(seat)) {
                    return false;
                }
                seats.put(seat, false);
                return true;
            } finally {
                lock.writeLock().unlock();
            }
        } else {
            if (seats.getOrDefault(seat, false)) {
                seats.put(seat, false);
                return true;
            } else {
                seats.put(seat, false);
                return true;
            }
        }
    }

    public String getSeatStatus() {
        StringBuilder status = new StringBuilder();
        seats.forEach((seat, reserved) -> {
            status.append(seat).append(": ").append(reserved ? "1" : "0").append("\n");
        });
        return status.toString();
    }
}
