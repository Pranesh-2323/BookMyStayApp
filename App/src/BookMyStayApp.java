/**
 * HotelBookingApplication
 *
 * Demonstrates abstraction, inheritance, polymorphism,
 * and simple room availability display.
 *
 * @author Pranesh Raj
 * @version 1.0
 */

abstract class Room {

    private int beds;
    private int size;
    private double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public int getBeds() {
        return beds;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public abstract String getRoomType();

    public void displayRoomDetails() {
        System.out.println("Room Type: " + getRoomType());
        System.out.println("Beds: " + beds);
        System.out.println("Room Size: " + size + " sq ft");
        System.out.println("Price per Night: $" + price);
    }
}

class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 200, 100);
    }

    public String getRoomType() {
        return "Single Room";
    }
}

class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 350, 180);
    }

    public String getRoomType() {
        return "Double Room";
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 600, 350);
    }

    public String getRoomType() {
        return "Suite Room";
    }
}

public class HotelBookingApplication {

    public static void main(String[] args) {

        // Create room objects
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Availability variables
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("===== Hotel Room Availability =====\n");

        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + singleAvailable);
        System.out.println();

        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleAvailable);
        System.out.println();

        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteAvailable);

        System.out.println("\nApplication Terminated.");
    }
}