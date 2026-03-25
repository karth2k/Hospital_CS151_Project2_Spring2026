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

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCondition() {
        return condition;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public boolean isAdmitted() {
        return admitted;
    }

    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getPrescribedMedicine() {
        return prescribedMedicine;
    }

    public void setPatientId(String patientId) {
        if (patientId != null && !patientId.trim().isEmpty()) {
            this.patientId = patientId.trim();
        } else {
            System.out.println("Invalid patient ID.");
        }
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.trim();
        } else {
            System.out.println("Invalid patient name.");
        }
    }

    public void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        } else {
            System.out.println("Age cannot be negative.");
        }
    }

    public void setCondition(String condition) {
        if (condition != null && !condition.trim().isEmpty()) {
            this.condition = condition.trim();
        } else {
            System.out.println("Invalid condition.");
        }
    }

    public void setBillAmount(double billAmount) {
        if (billAmount >= 0) {
            this.billAmount = billAmount;
        } else {
            System.out.println("Bill amount cannot be negative.");
        }
    }

    public void setAdmitted(boolean admitted) {
        this.admitted = admitted;
    }
}