package hospital;

/**
 * Manages the hospital's medicine inventory.
 * Handles dispensing, restocking, and querying medicines.
 * author: Qingheng Fang
 * id: 015323763
 */
public class Pharmacy implements Billable {



    private String pharmacyId;
    private String[] medicineNames;
    private int[] medicineQuantities;
    private double[] medicinePrices;
    private int medicineCount;
    private double totalRevenue;
    private double totalExpenses;

    /**
     * Constructs a Pharmacy with the given ID and empty inventory.
     *
     * @param pharmacyId unique identifier for this pharmacy
     */
    public Pharmacy(String pharmacyId) {
        this.pharmacyId = pharmacyId;
        this.medicineNames = new String[Hospital.MAX_OBJECTS];
        this.medicineQuantities = new int[Hospital.MAX_OBJECTS];
        this.medicinePrices = new double[Hospital.MAX_OBJECTS];
        this.medicineCount = 0;
        this.totalRevenue = 0.0;
        this.totalExpenses = 0.0;
    }

    // -------------------------------------------------------------------------
    // Billable Interface Methods
    // -------------------------------------------------------------------------

    /**
     * Records income earned when dispensing medicine to a patient.
     *
     * @param amount the price charged for the dispensed medicine
     */
    @Override
    public void addCharge(double amount) {
        totalRevenue += amount;
    }

    /**
     * Records an expense paid when restocking medicine from a supplier.
     *
     * @param amount the cost of the restock
     */
    @Override
    public void payBill(double amount) {
        totalExpenses += amount;
    }

    /**
     * Returns the net balance: total revenue minus total expenses.
     *
     * @return totalRevenue - totalExpenses
     */
    @Override
    public double getOutstandingBalance() {
        return totalRevenue - totalExpenses;
    }

    // -------------------------------------------------------------------------
    // Core Methods
    // -------------------------------------------------------------------------

    /**
     * Restocks a medicine by the given quantity and price.
     * If the medicine already exists, increments its quantity and updates its price.
     * If the medicine is new, adds it as a new entry.
     * Throws MaxCapacityException if the inventory is already full.
     *
     * @param medicine name of the medicine to restock
     * @param quantity number of units to add (must be greater than 0)
     * @param price    unit price of the medicine
     * @throws MaxCapacityException     if inventory has reached MAX_OBJECTS
     * @throws IllegalArgumentException if quantity is not positive
     */
    public void restockMedicine(String medicine, int quantity, double price) {
        if (medicine == null || medicine.isBlank()) {
            throw new IllegalArgumentException("Medicine name cannot be null or empty.");
        }
        medicine = medicine.trim();
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        for (int i = 0; i < medicineCount; i++) {
            if (medicineNames[i].equalsIgnoreCase(medicine)) {
                medicineQuantities[i] += quantity;
                medicinePrices[i] = price;
                payBill(quantity * price);
                System.out.println("Restocked " + medicine + ". New quantity: "
                        + medicineQuantities[i] + ", price updated to $"
                        + String.format("%.2f", price));
                return;
            }
        }
        if (medicineCount >= Hospital.MAX_OBJECTS) {
            throw new MaxCapacityException("Medicine", Hospital.MAX_OBJECTS);
        }
        medicineNames[medicineCount] = medicine;
        medicineQuantities[medicineCount] = quantity;
        medicinePrices[medicineCount] = price;
        medicineCount++;
        payBill(quantity * price);
        System.out.println("Added new medicine: " + medicine + " (quantity: " + quantity
                + ", price: $" + String.format("%.2f", price) + ")");
    }

    /**
     * Dispenses one unit of a medicine to a patient.
     * Decrements stock by 1, charges the patient the unit price,
     * and records the income as pharmacy revenue.
     * Throws OutOfStockException if the medicine is unavailable.
     *
     * @param patient  the patient receiving the medicine
     * @param medicine name of the medicine to dispense
     * @throws OutOfStockException if the medicine is out of stock or not found
     */
    public void dispenseMedicine(Patient patient, String medicine) {
        if (patient == null) {
            throw new IllegalArgumentException("Patient cannot be null.");
        }
        if (medicine == null || medicine.isBlank()) {
            throw new IllegalArgumentException("Medicine name cannot be null or empty.");
        }
        medicine = medicine.trim();
        for (int i = 0; i < medicineCount; i++) {
            if (medicineNames[i].equalsIgnoreCase(medicine)) {
                if (medicineQuantities[i] <= 0) {
                    throw new OutOfStockException(medicine, true);
                }
                medicineQuantities[i]--;
                double price = medicinePrices[i];
                patient.addCharge(price);
                addCharge(price);
                System.out.println("Dispensed " + medicine + " to " + patient.getName()
                        + ". Charge: $" + String.format("%.2f", price));
                if (medicineQuantities[i] == 0) {
                    System.out.println("Warning: " + medicine + " is now out of stock.");
                }
                return;
            }
        }
        throw new OutOfStockException(medicine, true);
    }

    /**
     * Returns the unit price of the given medicine.
     *
     * @param medicine name of the medicine
     * @return the unit price, or -1 if the medicine is not found
     */
    public double findMedicinePrice(String medicine) {
        if (medicine == null || medicine.isBlank()) {
            return -1;
        }
        for (int i = 0; i < medicineCount; i++) {
            if (medicineNames[i].equalsIgnoreCase(medicine)) {
                return medicinePrices[i];
            }
        }
        return -1;
    }

    /**
     * Prints a formatted table of all medicines in inventory,
     * showing name, quantity, and unit price.
     */
    public void viewInventory() {
        if (medicineCount == 0) {
            System.out.println("Pharmacy inventory is empty.");
            return;
        }
        System.out.println("=== Pharmacy Inventory [" + pharmacyId + "] ===");
        System.out.printf("%-25s %-12s %-10s%n", "Medicine", "Quantity", "Price");
        System.out.println("-".repeat(50));
        for (int i = 0; i < medicineCount; i++) {
            System.out.printf("%-25s %-12d $%-9.2f%n",
                    medicineNames[i], medicineQuantities[i], medicinePrices[i]);
        }
        System.out.println("-".repeat(50));
        System.out.println("Total medicines: " + medicineCount);
    }

    /**
     * Checks whether a medicine is available (quantity > 0).
     * Prints the current quantity to the console.
     *
     * @param medicine name of the medicine to check
     * @return true if available, false if out of stock or not found
     */
    public boolean checkAvailability(String medicine) {
        if (medicine == null || medicine.isBlank()) {
            System.out.println("Invalid medicine name.");
            return false;
        }
        for (int i = 0; i < medicineCount; i++) {
            if (medicineNames[i].equalsIgnoreCase(medicine)) {
                System.out.println(medicine + " — available quantity: "
                        + medicineQuantities[i]);
                return medicineQuantities[i] > 0;
            }
        }
        System.out.println(medicine + " is not in the pharmacy inventory.");
        return false;
    }

    /**
     * Removes a medicine entirely from the inventory by shifting the arrays.
     *
     * @param medicine name of the medicine to remove
     * @return true if the medicine was found and removed, false otherwise
     */
    public boolean removeMedicine(String medicine) {
        if (medicine == null || medicine.isBlank()) {
            System.out.println("Invalid medicine name.");
            return false;
        }
        for (int i = 0; i < medicineCount; i++) {
            if (medicineNames[i].equalsIgnoreCase(medicine)) {
                for (int j = i; j < medicineCount - 1; j++) {
                    medicineNames[j] = medicineNames[j + 1];
                    medicineQuantities[j] = medicineQuantities[j + 1];
                    medicinePrices[j] = medicinePrices[j + 1];
                }
                medicineNames[medicineCount - 1] = null;
                medicineQuantities[medicineCount - 1] = 0;
                medicinePrices[medicineCount - 1] = 0.0;
                medicineCount--;
                System.out.println(medicine + " has been removed from inventory.");
                return true;
            }
        }
        System.out.println(medicine + " not found in inventory.");
        return false;
    }

    // -------------------------------------------------------------------------
    // Getters & Setters
    // -------------------------------------------------------------------------

    public String getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(String pharmacyId) {
        if (pharmacyId != null && !pharmacyId.isBlank()) {
            this.pharmacyId = pharmacyId;
        }
    }

    public int getMedicineCount() {
        return medicineCount;
    }

    /**
     * Updates the price of an existing medicine.
     *
     * @param medicine name of the medicine
     * @param price    new unit price
     * @return true if updated, false if medicine not found
     */
    public boolean setMedicinePrice(String medicine, double price) {
        for (int i = 0; i < medicineCount; i++) {
            if (medicineNames[i].equalsIgnoreCase(medicine)) {
                medicinePrices[i] = price;
                return true;
            }
        }
        return false;
    }

    // -------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------

    /**
     * Returns a summary of the pharmacy including ID and medicine count.
     *
     * @return formatted pharmacy summary string
     */
    @Override
    public String toString() {
        return "Pharmacy{id='" + pharmacyId + "', medicineCount=" + medicineCount + "}";
    }
}
