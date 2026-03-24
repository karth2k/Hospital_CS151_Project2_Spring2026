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
