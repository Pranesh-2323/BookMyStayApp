import java.util.*;

/**
 * AddOnServiceSystem
 *
 * Demonstrates how optional services can be added to a reservation
 * without modifying booking or inventory logic.
 */

class Service {

    private String serviceName;
    private double price;

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void displayService() {
        System.out.println(serviceName + " - $" + price);
    }
}

class AddOnServiceManager {

    // Map Reservation ID -> List of Services
    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add service to reservation
    public void addService(String reservationId, Service service) {

        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);

        System.out.println("Service added to Reservation " + reservationId);
    }

    // Display services for reservation
    public void displayServices(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId + ":");

        for (Service s : services) {
            s.displayService();
        }
    }

    // Calculate total additional cost
    public double calculateTotalCost(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        double total = 0;

        if (services != null) {
            for (Service s : services) {
                total += s.getPrice();
            }
        }

        return total;
    }
}

public class AddOnServiceSystem {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        AddOnServiceManager manager = new AddOnServiceManager();

        System.out.println("=== Add-On Service Selection ===");

        System.out.print("Enter Reservation ID: ");
        String reservationId = scanner.nextLine();

        System.out.print("How many services to add? ");
        int n = scanner.nextInt();

        scanner.nextLine();

        for (int i = 1; i <= n; i++) {

            System.out.println("\nService " + i);

            System.out.print("Enter Service Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Service Price: ");
            double price = scanner.nextDouble();

            scanner.nextLine();

            Service service = new Service(name, price);

            manager.addService(reservationId, service);
        }

        manager.displayServices(reservationId);

        double totalCost = manager.calculateTotalCost(reservationId);

        System.out.println("\nTotal Additional Service Cost: $" + totalCost);

        System.out.println("\nCore booking and inventory remain unchanged.");

        scanner.close();
    }
}