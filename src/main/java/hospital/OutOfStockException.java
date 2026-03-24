package hospital;

/**
 * Thrown when a requested medicine is out of stock in the pharmacy.
 * author: Qingheng Fang
 * id: 015323763
 */
public class OutOfStockException extends RuntimeException {

    private final String medicineName;

    /**
     * Constructs an OutOfStockException with a detail message.
     *
     * @param message description of the stock error
     */
    public OutOfStockException(String message) {
        super(message);
        this.medicineName = "Unknown";
    }

    /**
     * Constructs an OutOfStockException for a specific medicine.
     * Automatically generates a descriptive error message.
     *
     * @param medicineName the name of the medicine that is out of stock
     * @param isName       flag to distinguish this constructor from the message-only one
     */
    public OutOfStockException(String medicineName, boolean isName) {
        super("Medicine \"" + medicineName + "\" is out of stock in the pharmacy.");
        this.medicineName = medicineName;
    }

    /**
     * Returns the name of the medicine that triggered this exception.
     *
     * @return the medicine name, or "Unknown" if not specified
     */
    public String getMedicineName() {
        return medicineName;
    }

    /**
     * Returns a formatted summary of the exception.
     *
     * @return a descriptive string of this exception
     */
    @Override
    public String toString() {
        return "OutOfStockException: [" + medicineName + "] " + getMessage();
    }
}
