import java.util.*;

/**
 * Use Case 9: Error Handling & Validation
 *
 * Demonstrates validation of booking input before processing.
 * The system:
 * - Accepts user input
 * - Validates input centrally
 * - Handles errors gracefully
 *
 * @version 9.0
 */

// ---------------- CUSTOM EXCEPTION ----------------
/**
 * This custom exception represents invalid booking scenarios.
 */
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

// ---------------- ROOM INVENTORY ----------------
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single", 2);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public boolean isRoomTypeValid(String roomType) {

        return inventory.containsKey(roomType);
    }

    public int getAvailability(String roomType) {

        return inventory.getOrDefault(roomType, 0);
    }
}

// ---------------- BOOKING REQUEST QUEUE ----------------
class BookingRequestQueue {

    private Queue<String> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(String guestName) {

        queue.offer(guestName);

        System.out.println("Booking request added to queue.");
    }
}

// ---------------- VALIDATOR ----------------
/**
 * This class validates booking requests before processing.
 * All validation rules are centralized.
 */
class ReservationValidator {

    public void validate(
            String guestName,
            String roomType,
            RoomInventory inventory
    ) throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException(
                    "Guest name cannot be empty."
            );
        }

        if (!inventory.isRoomTypeValid(roomType)) {
            throw new InvalidBookingException(
                    "Invalid room type selected."
            );
        }

        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException(
                    "No rooms available for selected type."
            );
        }
    }
}

// ---------------- MAIN APPLICATION ----------------
public class UseCase9ErrorHandlingValidation {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Booking Validation");

        // Initialize required components
        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue =
                new BookingRequestQueue();

        try {

            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print(
                    "Enter room type (Single/Double/Suite): "
            );
            String roomType = scanner.nextLine();

            // Validate booking
            validator.validate(
                    guestName,
                    roomType,
                    inventory
            );

            // If valid, add request to queue
            bookingQueue.addRequest(guestName);

            System.out.println("Booking request accepted.");

        } catch (InvalidBookingException e) {

            // Graceful error handling
            System.out.println(
                    "Booking failed: " + e.getMessage()
            );

        } finally {

            scanner.close();
        }
    }
}