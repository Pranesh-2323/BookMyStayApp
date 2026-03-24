import java.io.*;
import java.util.*;

/**
 * Use Case 12: Data Persistence & System Recovery
 *
 * Demonstrates saving and loading system state
 * using a plain text file.
 *
 * @version 12.0
 */

/* ---------------- ROOM INVENTORY ---------------- */
class RoomInventory {

    private Map<String, Integer> inventory;

    public RoomInventory() {

        inventory = new HashMap<>();

        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void setAvailability(
            String roomType,
            int count
    ) {
        inventory.put(roomType, count);
    }

    public void displayInventory() {

        System.out.println("\nCurrent Inventory:");

        for (String type : inventory.keySet()) {

            System.out.println(
                    type + ": " +
                            inventory.get(type)
            );
        }
    }
}

/* ---------------- FILE PERSISTENCE SERVICE ---------------- */
class FilePersistenceService {

    /**
     * Saves inventory state to a file.
     * Format:
     * roomType=availableCount
     */
    public void saveInventory(
            RoomInventory inventory,
            String filePath
    ) {

        try {

            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(filePath)
                    );

            for (Map.Entry<String, Integer> entry
                    : inventory.getInventory().entrySet()) {

                writer.write(
                        entry.getKey()
                                + "="
                                + entry.getValue()
                );

                writer.newLine();
            }

            writer.close();

            System.out.println(
                    "Inventory saved successfully."
            );

        }
        catch (IOException e) {

            System.out.println(
                    "Error saving inventory: "
                            + e.getMessage()
            );
        }
    }

    /**
     * Loads inventory state from a file.
     */
    public void loadInventory(
            RoomInventory inventory,
            String filePath
    ) {

        File file = new File(filePath);

        if (!file.exists()) {

            System.out.println(
                    "No valid inventory data found. Starting fresh."
            );

            return;
        }

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(file)
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts =
                        line.split("=");

                if (parts.length == 2) {

                    String roomType =
                            parts[0];

                    int count =
                            Integer.parseInt(parts[1]);

                    inventory.setAvailability(
                            roomType,
                            count
                    );
                }
            }

            reader.close();

            System.out.println(
                    "Inventory restored from file."
            );

        }
        catch (Exception e) {

            System.out.println(
                    "Error loading inventory: "
                            + e.getMessage()
            );
        }
    }
}

/* ---------------- MAIN CLASS ---------------- */
public class UseCase12DataPersistenceRecovery {

    public static void main(String[] args) {

        System.out.println("System Recovery");

        String filePath = "inventory.txt";

        RoomInventory inventory =
                new RoomInventory();

        FilePersistenceService persistenceService =
                new FilePersistenceService();

        /* Load saved data */
        persistenceService.loadInventory(
                inventory,
                filePath
        );

        /* Display inventory */
        inventory.displayInventory();

        /* Save data before shutdown */
        persistenceService.saveInventory(
                inventory,
                filePath
        );
    }
}