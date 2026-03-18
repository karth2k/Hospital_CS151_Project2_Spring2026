package hospital;

/**
 * Thrown when a patient cannot be found in the hospital system.
 * author: Qingheng Fang
 * id: 015323763
 */
public class PatientNotFoundException extends RuntimeException {

    private final String patientId;

    /**
     * Constructs a PatientNotFoundException with a detail message.
     *
     * @param message description of the error
     */
    public PatientNotFoundException(String message) {
        super(message);
        this.patientId = "Unknown";
    }

    /**
     * Constructs a PatientNotFoundException for a specific patient ID.
     * Automatically generates a descriptive error message.
     *
     * @param patientId the ID of the patient that could not be found
     * @param isId      flag to distinguish this constructor from the message-only one
     */
    public PatientNotFoundException(String patientId, boolean isId) {
        super("Patient with ID \"" + patientId + "\" was not found in the system.");
        this.patientId = patientId;
    }

    /**
     * Returns the ID of the patient that triggered this exception.
     *
     * @return the patient ID string, or "Unknown" if not specified
     */
    public String getPatientId() {
        return patientId;
    }
}
