package hospital;

/**
 * Manages the hospital's medicine inventory.
 * Handles dispensing, restocking, and querying medicines.
 * author: Qingheng Fang
 * id: 015323763
 */
public class Pharmacy {

    public static final int MAX_OBJECTS = 100;

    private String pharmacyId;
    private String[] medicineNames;
    private int[] medicineQuantities;
    private double[] medicinePrices;
    private int medicineCount;

    /**
     * Constructs a Pharmacy with the given ID and empty inventory.
     *
     * @param pharmacyId unique identifier for this pharmacy
     */
    public Pharmacy(String pharmacyId) {
        this.pharmacyId = pharmacyId;
        this.medicineNames = new String[MAX_OBJECTS];
        this.medicineQuantities = new int[MAX_OBJECTS];
        this.medicinePrices = new double[MAX_OBJECTS];
        this.medicineCount = 0;
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
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
        for (int i = 0; i < medicineCount; i++) {
            if (medicineNames[i].equalsIgnoreCase(medicine)) {
                medicineQuantities[i] += quantity;
                medicinePrices[i] = price;
                System.out.println("Restocked " + medicine + ". New quantity: "
                        + medicineQuantities[i] + ", price updated to $"
                        + String.format("%.2f", price));
                return;
            }
        }
        if (medicineCount >= MAX_OBJECTS) {
            throw new MaxCapacityException("Medicine", MAX_OBJECTS);
        }
        medicineNames[medicineCount] = medicine;
        medicineQuantities[medicineCount] = quantity;
        medicinePrices[medicineCount] = price;
        medicineCount++;
        System.out.println("Added new medicine: " + medicine + " (quantity: " + quantity
                + ", price: $" + String.format("%.2f", price) + ")");
    }

    /**
     * Checks whether a medicine is available (quantity > 0).
     * Prints the current quantity to the console.
     *
     * @param medicine name of the medicine to check
     * @return true if available, false if out of stock or not found
     */
    public boolean checkAvailability(String medicine) {
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
