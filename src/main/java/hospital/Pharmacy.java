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
}
