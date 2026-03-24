import java.util.*;

/**
 * BookingConfirmationSystem
 *
 * This program processes booking requests from a queue,
 * assigns unique room IDs, prevents duplicate assignments,
 * and updates inventory immediately after confirmation.
 */

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class InventoryService {

    private Map<String, Integer> inventory;

    public InventoryService() {
        inventory = new HashMap<>();

        // Initial room availability
        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decreaseAvailability(String roomType) {
        int count = inventory.get(roomType);
        inventory.put(roomType, count - 1);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Available: " + entry.getValue());
        }
    }
}

class BookingService {

    private Queue<Reservation> requestQueue;
    private InventoryService inventoryService;

    // Store allocated room IDs
    private Set<String> allocatedRoomIds;

    // Map room type -> assigned room IDs
    private Map<String, Set<String>> assignedRooms;

    public BookingService(Queue<Reservation> requestQueue,
                          InventoryService inventoryService) {

        this.requestQueue = requestQueue;
        this.inventoryService = inventoryService;

        allocatedRoomIds = new HashSet<>();
        assignedRooms = new HashMap<>();
    }

    public void processBookings() {

        System.out.println("\n=== Processing Booking Requests ===");

        while (!requestQueue.isEmpty()) {

            Reservation reservation = requestQueue.poll();

            String roomType = reservation.roomType;

            if (inventoryService.getAvailability(roomType) > 0) {

                String roomId = generateRoomId(roomType);

                // Ensure uniqueness
                while (allocatedRoomIds.contains(roomId)) {
                    roomId = generateRoomId(roomType);
                }

                allocatedRoomIds.add(roomId);

                assignedRooms
                        .computeIfAbsent(roomType, k -> new HashSet<>())
                        .add(roomId);

                // Update inventory immediately
                inventoryService.decreaseAvailability(roomType);

                System.out.println(
                        "Booking Confirmed: "
                                + reservation.guestName
                                + " -> "
                                + roomType
                                + " Room ID: "
                                + roomId
                );

            } else {

                System.out.println(
                        "Booking Failed: "
                                + reservation.guestName
                                + " -> "
                                + roomType
                                + " (No rooms available)"
                );
            }
        }
    }

    private String generateRoomId(String roomType) {

        int number = (int) (Math.random() * 1000);

        return roomType.substring(0, 1).toUpperCase() + number;
    }
}

public class BookingConfirmationSystem {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Queue<Reservation> bookingQueue = new LinkedList<>();

        System.out.println("=== Hotel Booking Confirmation System ===");

        System.out.print("Enter number of booking requests: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= n; i++) {

            System.out.println("\nBooking Request " + i);

            System.out.print("Enter Guest Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Room Type (Single / Double / Suite): ");
            String roomType = scanner.nextLine();

            bookingQueue.add(new Reservation(name, roomType));
        }

        InventoryService inventoryService = new InventoryService();

        BookingService bookingService =
                new BookingService(bookingQueue, inventoryService);

        bookingService.processBookings();

        inventoryService.displayInventory();

        scanner.close();
    }
}