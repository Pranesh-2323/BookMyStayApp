import java.util.*;

/**
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Demonstrates how confirmed bookings can be cancelled safely.
 * Inventory is restored and rollback history is maintained using Stack.
 *
 * @version 10.0
 */

// ---------------- ROOM INVENTORY ----------------
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public void increaseAvailability(String roomType) {

        int count = inventory.get(roomType);

        inventory.put(roomType, count + 1);
    }

    public int getAvailability(String roomType) {

        return inventory.getOrDefault(roomType, 0);
    }
}

// ---------------- CANCELLATION SERVICE ----------------
class CancellationService {

    // Tracks recently released reservation IDs (LIFO)
    private Stack<String> rollbackStack;

    // Maps reservation ID -> room type
    private Map<String, String> reservationRoomTypeMap;

    public CancellationService() {

        rollbackStack = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    /**
     * Registers a confirmed booking.
     * This simulates storing confirmation data.
     */
    public void registerBooking(String reservationId,
                                String roomType) {

        reservationRoomTypeMap.put(
                reservationId,
                roomType
        );
    }

    /**
     * Cancels a confirmed booking
     * and restores inventory safely.
     */
    public void cancelBooking(String reservationId,
                              RoomInventory inventory) {

        if (!reservationRoomTypeMap
                .containsKey(reservationId)) {

            System.out.println(
                    "Cancellation failed: Reservation not found."
            );
            return;
        }

        String roomType =
                reservationRoomTypeMap.get(reservationId);

        // Restore inventory
        inventory.increaseAvailability(roomType);

        // Track rollback
        rollbackStack.push(reservationId);

        // Remove reservation
        reservationRoomTypeMap.remove(reservationId);

        System.out.println(
                "Booking cancelled successfully. "
                        + "Inventory restored for room type: "
                        + roomType
        );
    }

    /**
     * Displays rollback history
     */
    public void showRollbackHistory() {

        System.out.println(
                "\nRollback History (Most Recent First):"
        );

        if (rollbackStack.isEmpty()) {

            System.out.println("No cancellations yet.");
            return;
        }

        for (String id : rollbackStack) {

            System.out.println(
                    "Released Reservation ID: " + id
            );
        }
    }
}

// ---------------- MAIN CLASS ----------------
public class UseCase10BookingCancellation {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory =
                new RoomInventory();

        CancellationService cancellationService =
                new CancellationService();

        System.out.println("Booking Cancellation");

        // Simulate confirmed booking
        System.out.print(
                "Enter reservation ID to register: "
        );

        String reservationId =
                scanner.nextLine();

        System.out.print(
                "Enter room type (Single/Double/Suite): "
        );

        String roomType =
                scanner.nextLine();

        cancellationService.registerBooking(
                reservationId,
                roomType
        );

        // Cancel booking
        System.out.print(
                "\nEnter reservation ID to cancel: "
        );

        String cancelId =
                scanner.nextLine();

        cancellationService.cancelBooking(
                cancelId,
                inventory
        );

        cancellationService.showRollbackHistory();

        System.out.println(
                "\nUpdated "
                        + roomType
                        + " Room Availability: "
                        + inventory.getAvailability(roomType)
        );

        scanner.close();
    }
}