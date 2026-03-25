package hospital;

public class Patient implements Billable {
    /*********************************************
     * author: Sean Tadina
     * id: 018950802
     * username: @seantadina
     *********************************************/

    // fields
    private String patientId;
    private String name;
    private int age;
    private String condition;
    private double billAmount;
    private boolean admitted;
    private Doctor assignedDoctor;
    private String diagnosis;
    private String prescribedMedicine;


    // constructor
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

    // getters
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

    // setters
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

    // admits the patient
    public void admitPatient() {
        if (admitted) {
            System.out.println(name + " is already admitted.");
            return;
        }

        admitted = true;
        System.out.println(name + " has been admitted.");
    }

    // discharges the patient
    public void dischargePatient() {
        if (!admitted) {
            System.out.println(name + " is not currently admitted.");
            return;
        }

        admitted = false;
        System.out.println(name + " has been discharged.");
    }

    // updates diagnosis
    public void updateDiagnosis(String diagnosis) {
        if (diagnosis == null || diagnosis.trim().isEmpty()) {
            System.out.println("Diagnosis cannot be empty.");
            return;
        }

        this.diagnosis = diagnosis.trim();
        System.out.println("Diagnosis updated for " + name + ".");
    }

    // updates prescription
    public void updatePrescription(String prescribedMedicine) {
        if (prescribedMedicine == null || prescribedMedicine.trim().isEmpty()) {
            System.out.println("Prescription cannot be empty.");
            return;
        }

        this.prescribedMedicine = prescribedMedicine.trim();
        System.out.println("Prescription updated for " + name + ".");
    }

    // assigns a doctor to the patient
    public void assignDoctor(Doctor doctor) {
        if (doctor == null) {
            System.out.println("Cannot assign a null doctor.");
            return;
        }

        if (assignedDoctor == doctor) {
            System.out.println("Dr. " + doctor.getName() + " is already assigned to " + name + ".");
            return;
        }

        Doctor previousDoctor = assignedDoctor;

        if (!doctor.hasPatient(this)) {
            doctor.assignPatient(this);
        }

        assignedDoctor = doctor;

        if (previousDoctor != null && previousDoctor != doctor && previousDoctor.hasPatient(this)) {
            previousDoctor.removePatient(this);
        }

        System.out.println("Dr. " + doctor.getName() + " has been assigned to patient " + name + ".");
    }

    // adds money to patient's bill
    @Override
    public void addCharge(double amount) {
        if (amount <= 0) {
            System.out.println("Charge amount must be greater than 0.");
            return;
        }

        billAmount += amount;
        System.out.println("$" + String.format("%.2f", amount)
                + " added to " + name + "'s bill. New balance: $"
                + String.format("%.2f", billAmount));
    }

    // gives option to pay full or partial
    @Override
    public void payBill(double amount) {
        if (amount <= 0) {
            System.out.println("Payment amount must be greater than 0.");
            return;
        }

        if (billAmount <= 0) {
            System.out.println(name + " has no outstanding balance.");
            return;
        }

        if (amount > billAmount) {
            System.out.println("Payment cannot be greater than the current balance.");
            return;
        }

        billAmount -= amount;
        System.out.println(name + " paid $" + String.format("%.2f", amount)
                + ". Remaining balance: $" + String.format("%.2f", billAmount));
    }

    // returns unpaid balance
    @Override
    public double getOutstandingBalance() {
        return billAmount;
    }

    // menu view for patient info
    public void viewPatientInfo() {
        System.out.println("----- Patient Information -----");
        System.out.println("Patient ID: " + patientId);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Condition: " + condition);
        System.out.println("Admitted: " + admitted);
        System.out.println("Assigned Doctor: " + (assignedDoctor != null ? assignedDoctor.getName() : "None"));
        System.out.println("Diagnosis: " + diagnosis);
        System.out.println("Prescribed Medicine: " + prescribedMedicine);
        System.out.println("Outstanding Balance: $" + String.format("%.2f", billAmount));
    }

    @Override
    public String toString() {
        return "Patient ID: " + patientId
                + ", Name: " + name
                + ", Age: " + age
                + ", Condition: " + condition
                + ", Admitted: " + admitted
                + ", Assigned Doctor: " + (assignedDoctor != null ? assignedDoctor.getName() : "None")
                + ", Diagnosis: " + diagnosis
                + ", Prescribed Medicine: " + prescribedMedicine
                + ", Bill Amount: $" + String.format("%.2f", billAmount);
    }
}