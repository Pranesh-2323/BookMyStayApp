import java.util.*;

/**
 * Use Case 11: Concurrent Booking Simulation
 *
 * Demonstrates thread-safe booking processing using synchronization.
 * Multiple threads process booking requests concurrently.
 *
 * @version 11.0
 */

/* ---------------- RESERVATION ---------------- */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/* ---------------- BOOKING REQUEST QUEUE ---------------- */
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

/* ---------------- ROOM INVENTORY ---------------- */
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public boolean isAvailable(String roomType) {
        return inventory.get(roomType) > 0;
    }

    public void allocateRoom(String roomType) {
        inventory.put(roomType,
                inventory.get(roomType) - 1);
    }

    public int getAvailability(String roomType) {
        return inventory.get(roomType);
    }

    public void displayInventory() {

        System.out.println("\nRemaining Inventory:");

        for (String type : inventory.keySet()) {
            System.out.println(
                    type + ": " +
                            inventory.get(type)
            );
        }
    }
}

/* ---------------- ROOM ALLOCATION SERVICE ---------------- */
class RoomAllocationService {

    private Map<String, Integer> counters =
            new HashMap<>();

    public RoomAllocationService() {

        counters.put("Single", 0);
        counters.put("Double", 0);
        counters.put("Suite", 0);
    }

    public void allocateRoom(
            Reservation reservation,
            RoomInventory inventory
    ) {

        String roomType =
                reservation.getRoomType();

        if (inventory.isAvailable(roomType)) {

            inventory.allocateRoom(roomType);

            int count =
                    counters.get(roomType) + 1;

            counters.put(roomType, count);

            String roomId =
                    roomType + "-" + count;

            System.out.println(
                    "Booking confirmed for Guest: "
                            + reservation.getGuestName()
                            + ", Room ID: "
                            + roomId
            );

        } else {

            System.out.println(
                    "No rooms available for "
                            + roomType
            );
        }
    }
}

/* ---------------- THREAD PROCESSOR ---------------- */
class ConcurrentBookingProcessor
        implements Runnable {

    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService
    ) {

        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {

        while (true) {

            Reservation reservation;

            /* synchronize queue access */
            synchronized (bookingQueue) {

                if (bookingQueue.isEmpty())
                    break;

                reservation =
                        bookingQueue.getNextRequest();
            }

            /* synchronize inventory update */
            synchronized (inventory) {

                allocationService.allocateRoom(
                        reservation,
                        inventory
                );
            }
        }
    }
}

/* ---------------- MAIN CLASS ---------------- */
public class UseCase11ConcurrentBookingSimulation {

    public static void main(String[] args) {

        System.out.println(
                "Concurrent Booking Simulation"
        );

        BookingRequestQueue bookingQueue =
                new BookingRequestQueue();

        RoomInventory inventory =
                new RoomInventory();

        RoomAllocationService allocationService =
                new RoomAllocationService();

        /* Add booking requests */
        bookingQueue.addRequest(
                new Reservation("Abhi", "Single")
        );

        bookingQueue.addRequest(
                new Reservation("Vanmathi", "Double")
        );

        bookingQueue.addRequest(
                new Reservation("Kural", "Suite")
        );

        bookingQueue.addRequest(
                new Reservation("Subha", "Single")
        );

        /* Create threads */
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue,
                        inventory,
                        allocationService
                )
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(
                        bookingQueue,
                        inventory,
                        allocationService
                )
        );

        /* Start concurrent processing */
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        }
        catch (InterruptedException e) {

            System.out.println(
                    "Thread execution interrupted."
            );
        }

        inventory.displayInventory();
    }
}