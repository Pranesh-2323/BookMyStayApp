import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * BookingRequestSystem
 * This program accepts booking requests from users
 * and stores them in a queue in arrival order (FIFO).
 */

class Reservation {
    String guestName;
    String roomType;

    Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    void display() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

public class BookingRequestSystem {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Queue<Reservation> bookingQueue = new LinkedList<>();

        System.out.println("=== Hotel Booking Request System ===");

        System.out.print("Enter number of booking requests: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= n; i++) {

            System.out.println("\nBooking Request " + i);

            System.out.print("Enter Guest Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Room Type (Single/Double/Suite): ");
            String room = scanner.nextLine();

            bookingQueue.add(new Reservation(name, room));

            System.out.println("Booking request added successfully!");
        }

        System.out.println("\n=== Booking Requests in Queue (FIFO Order) ===");

        for (Reservation r : bookingQueue) {
            r.display();
        }

        System.out.println("\nRequests are waiting for room allocation.");
        scanner.close();
    }
}