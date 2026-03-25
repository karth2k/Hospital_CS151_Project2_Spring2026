package hospital;

public class Patient implements Billable {
    /*********************************************
     * author: Sean Tadina
     * id: 018950802
     * username: @seantadina
     *********************************************/

    private String patientId;
    private String name;
    private int age;
    private String condition;
    private double billAmount;
    private boolean admitted;
    private Doctor assignedDoctor;
    private String diagnosis;
    private String prescribedMedicine;


    public Patient(String patientId, String name, int age, String condition) {
        this.patientId = (patientId != null && !patientId.trim().isEmpty()) ? patientId.trim() : "Unknown";
        this.name = (name != null && !name.trim().isEmpty()) ? name.trim() : "Unknown";
        this.age = Math.max(age, 0);
        this.condition = (condition != null && !condition.trim().isEmpty()) ? condition.trim() : "Not provided";
        this.billAmount = 0.0;
        this.admitted = false;
        this.assignedDoctor = null;
        this.diagnosis = "Not diagnosed";
        this.prescribedMedicine = "None";
    }
}