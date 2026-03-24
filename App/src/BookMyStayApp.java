import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * UseCase8BookingHistoryReport
 *
 * Demonstrates booking history tracking and reporting.
 * Confirmed reservations are stored in insertion order
 * and can be reviewed or summarized by an admin.
 *
 * No external storage is used; data is kept in memory.
 *
 * @author Pranesh Raj
 * @version 1.0
 */

// ---------------- RESERVATION ----------------
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println(
                "Reservation ID: " + reservationId +
                        " | Guest: " + guestName +
                        " | Room Type: " + roomType
        );
    }
}

// ---------------- BOOKING HISTORY ----------------
class BookingHistory {

    // List preserves insertion order
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    // Store confirmed reservation
    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Reservation stored in booking history.");
    }

    // Retrieve stored reservations
    public List<Reservation> getAllReservations() {
        return history;
    }

    // Display history
    public void displayHistory() {
        System.out.println("\n===== Booking History =====");

        if (history.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : history) {
            r.display();
        }
    }
}

// ---------------- REPORT SERVICE ----------------
class BookingReportService {

    // Generate summary report
    public void generateReport(List<Reservation> reservations) {

        System.out.println("\n===== Booking Summary Report =====");

        int total = reservations.size();

        int singleCount = 0;
        int doubleCount = 0;
        int suiteCount = 0;

        for (Reservation r : reservations) {

            switch (r.getRoomType().toLowerCase()) {
                case "single":
                    singleCount++;
                    break;
                case "double":
                    doubleCount++;
                    break;
                case "suite":
                    suiteCount++;
                    break;
            }
        }

        System.out.println("Total Bookings: " + total);
        System.out.println("Single Room Bookings: " + singleCount);
        System.out.println("Double Room Bookings: " + doubleCount);
        System.out.println("Suite Room Bookings: " + suiteCount);

        System.out.println("\nReport generated without modifying booking data.");
    }
}

// ---------------- MAIN APPLICATION ----------------
public class UseCase8BookingHistoryReport {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        BookingHistory bookingHistory = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        System.out.println("=== Booking History & Reporting System ===");

        System.out.print("Enter number of confirmed bookings: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        // Simulate confirmed bookings
        for (int i = 1; i <= n; i++) {

            System.out.println("\nBooking " + i);

            System.out.print("Enter Reservation ID: ");
            String id = scanner.nextLine();

            System.out.print("Enter Guest Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Room Type (Single / Double / Suite): ");
            String room = scanner.nextLine();

            Reservation reservation =
                    new Reservation(id, name, room);

            bookingHistory.addReservation(reservation);
        }

        // Admin views booking history
        bookingHistory.displayHistory();

        // Admin generates report
        reportService.generateReport(
                bookingHistory.getAllReservations()
        );

        scanner.close();

        System.out.println("\nProgram completed successfully.");
    }
}