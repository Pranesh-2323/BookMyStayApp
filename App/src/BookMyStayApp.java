import java.util.HashMap;
import java.util.Map;

/**
 * HotelSearchApplication
 *
 * Demonstrates guest room search with read-only access.
 * The system retrieves room availability from centralized inventory
 * and displays only available room types without modifying system state.
 *
 * @author Pranesh Raj
 * @version 1.0
 */

// ---------- ROOM DOMAIN MODEL ----------
class Room {

    private String roomType;
    private int beds;
    private double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price per Night: $" + price);
    }
}

// ---------- INVENTORY (STATE HOLDER) ----------
class RoomInventory {

    private HashMap<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();

        availability.put("Single Room", 5);
        availability.put("Double Room", 3);
        availability.put("Suite Room", 0); // unavailable example
    }

    public int getAvailability(String roomType) {
        return availability.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllAvailability() {
        return availability;
    }
}

// ---------- SEARCH SERVICE ----------
class SearchService {

    private RoomInventory inventory;

    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms(Room[] rooms) {

        System.out.println("===== Available Rooms =====");

        for (Room room : rooms) {

            int available = inventory.getAvailability(room.getRoomType());

            // Validation: show only rooms with availability > 0
            if (available > 0) {

                room.displayDetails();
                System.out.println("Available Rooms: " + available);
                System.out.println("---------------------------");
            }
        }
    }
}

// ---------- APPLICATION ENTRY ----------
public class HotelSearchApplication {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Create room domain objects
        Room single = new Room("Single Room", 1, 100);
        Room doubleRoom = new Room("Double Room", 2, 180);
        Room suite = new Room("Suite Room", 3, 350);

        Room[] rooms = {single, doubleRoom, suite};

        // Create search service
        SearchService searchService = new SearchService(inventory);

        // Guest searches available rooms
        searchService.searchAvailableRooms(rooms);

        System.out.println("Search completed. System state unchanged.");
    }
}