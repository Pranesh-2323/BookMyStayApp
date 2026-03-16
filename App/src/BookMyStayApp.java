import java.util.HashMap;
import java.util.Map;

/**
 * HotelInventoryApplication
 *
 * Demonstrates centralized inventory management using HashMap.
 * All room availability is maintained in one place to avoid scattered variables.
 *
 * @author Pranesh Raj
 * @version 1.0
 */
public class HotelInventoryApplication {

    /**
     * RoomInventory manages room availability using a HashMap.
     */
    static class RoomInventory {

        // HashMap storing room type -> available count
        private HashMap<String, Integer> roomAvailability;

        /**
         * Constructor initializes room inventory.
         */
        public RoomInventory() {
            roomAvailability = new HashMap<>();

            roomAvailability.put("Single Room", 5);
            roomAvailability.put("Double Room", 3);
            roomAvailability.put("Suite Room", 2);
        }

        /**
         * Returns the number of available rooms for a given type.
         */
        public int getAvailability(String roomType) {
            return roomAvailability.getOrDefault(roomType, 0);
        }

        /**
         * Updates availability for a given room type.
         */
        public void updateAvailability(String roomType, int count) {
            roomAvailability.put(roomType, count);
        }

        /**
         * Displays the current inventory.
         */
        public void displayInventory() {
            System.out.println("===== Current Room Inventory =====");

            for (Map.Entry<String, Integer> entry : roomAvailability.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
            }
        }
    }

    /**
     * Application entry point.
     */
    public static void main(String[] args) {

        // Initialize centralized inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        inventory.displayInventory();

        System.out.println("\nChecking availability of Single Room:");
        System.out.println("Available Rooms: " + inventory.getAvailability("Single Room"));

        // Simulate booking (update inventory)
        System.out.println("\nBooking a Single Room...");
        int updatedCount = inventory.getAvailability("Single Room") - 1;
        inventory.updateAvailability("Single Room", updatedCount);

        // Display updated inventory
        System.out.println("\nUpdated Inventory:");
        inventory.displayInventory();

        System.out.println("\nApplication Terminated.");
    }
}
