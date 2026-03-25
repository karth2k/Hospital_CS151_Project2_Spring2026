package hospital;

public class Doctor extends Staff{
    /*********************************************
     * author: Sean Tadina
     * id: 018950802
     * username: @seantadina
     *********************************************/

    // below are the doctor fields
    private String specialization;
    private String licenseNumber;
    private String roomNumber;
    private boolean available;

    // the arrays to store patients and appointments
    private Patient[] assignedPatients;
    private Appointment[] appointments;

    // the counters for the arrays
    private int patientCount;
    private int appointmentCount;

    public Doctor(String employeeID, String name, String department, double salary,
                  String specialization, String licenseNumber, String roomNumber) {
        // initializes staff fields from parent class
        super(employeeID, name, department, salary);

        // initializes doctor fields
        this.specialization = specialization;
        this.licenseNumber = licenseNumber;
        this.roomNumber = roomNumber;
        this.available = true;

        // initializes arrays and counters
        this.assignedPatients = new Patient[MAX_OBJECTS];
        this.appointments = new Appointment[MAX_OBJECTS];
        this.patientCount = 0;
        this.appointmentCount = 0;
    }

    // returns the doctor's specialization
    public String getSpecialization() {
        return specialization;
    }

    // returns the doctor's license number
    public String getLicenseNumber() {
        return licenseNumber;
    }

    // returns the doctor's room number
    public String getRoomNumber() {
        return roomNumber;
    }

    // checks if doctor is available
    public boolean isAvailable() {
        return available;
    }

    // returns the patient array
    public Patient[] getAssignedPatients() {
        return assignedPatients;
    }

    // returns the appointment array
    public Appointment[] getAppointments() {
        return appointments;
    }

    // returns how many patients are assigned
    public int getPatientCount() {
        return patientCount;
    }

    // returns how many appointments are stored
    public int getAppointmentCount() {
        return appointmentCount;
    }

    // updates specialization if valid
    public void setSpecialization(String specialization) {
        if (specialization != null && !specialization.trim().isEmpty()) {
            this.specialization = specialization.trim();
        } else {
            System.out.println("Invalid specialization.");
        }
    }

    // updates license number if valid
    public void setLicenseNumber(String licenseNumber) {
        if (licenseNumber != null && !licenseNumber.trim().isEmpty()) {
            this.licenseNumber = licenseNumber.trim();
        } else {
            System.out.println("Invalid license number.");
        }
    }

    // updates room number if valid
    public void setRoomNumber(String roomNumber) {
        if (roomNumber != null && !roomNumber.trim().isEmpty()) {
            this.roomNumber = roomNumber.trim();
        } else {
            System.out.println("Invalid room number.");
        }
    }

    // changes doctor availability
    public void setAvailable(boolean available) {
        this.available = available;
    }

    // adds a patient to this doctor's list
    public void assignPatient(Patient patient) {
        if (patient == null) {
            System.out.println("Cannot assign a null patient.");
            return;
        }

        if (hasPatient(patient)) {
            System.out.println("This patient is already assigned to Dr. " + getName() + ".");
            return;
        }

        if (patientCount >= MAX_OBJECTS) {
            throw new MaxCapacityException("Patient", MAX_OBJECTS);
        }

        assignedPatients[patientCount] = patient;
        patientCount++;
        System.out.println("Patient assigned to Dr. " + getName() + ".");
    }

    // removes a patient from the doctor's list
    public void removePatient(Patient patient) {
        if (patient == null) {
            System.out.println("Cannot remove a null patient.");
            return;
        }

        for (int i = 0; i < patientCount; i++) {
            if (assignedPatients[i] == patient) {
                // shift everything left after removal
                for (int j = i; j < patientCount - 1; j++) {
                    assignedPatients[j] = assignedPatients[j + 1];
                }

                assignedPatients[patientCount - 1] = null;
                patientCount--;
                System.out.println("Patient removed from Dr. " + getName() + "'s list.");
                return;
            }
        }

        System.out.println("Patient is not assigned to Dr. " + getName() + ".");
    }

    // checks if this doctor already has the patient
    public boolean hasPatient(Patient patient) {
        if (patient == null) {
            return false;
        }

        for (int i = 0; i < patientCount; i++) {
            if (assignedPatients[i] == patient) {
                return true;
            }
        }
        return false;
    }

    // checks if this doctor already has the appointment
    public boolean hasAppointment(Appointment appointment) {
        if (appointment == null) {
            return false;
        }

        for (int i = 0; i < appointmentCount; i++) {
            if (appointments[i] == appointment
                    || appointments[i].getAppointmentId() == appointment.getAppointmentId()) {
                return true;
            }
        }
        return false;
    }

    // simple method for diagnosing a patient
    public void diagnosePatient(Patient patient) {
        if (patient == null) {
            System.out.println("Cannot diagnose a null patient.");
            return;
        }

        if (!hasPatient(patient)) {
            System.out.println("Patient must be assigned to Dr. " + getName() + " before diagnosis.");
            return;
        }

        System.out.println("Dr. " + getName() + " examined the patient and recorded a diagnosis.");
    }

    // simple method for prescribing medicine
    public void prescribeMedicine(Patient patient, String medicine) {
        if (patient == null) {
            System.out.println("Cannot prescribe medicine to a null patient.");
            return;
        }

        if (medicine == null || medicine.trim().isEmpty()) {
            System.out.println("Medicine name cannot be empty.");
            return;
        }

        if (!hasPatient(patient)) {
            System.out.println("Patient must be assigned to Dr. " + getName() + " before prescribing medicine.");
            return;
        }

        System.out.println("Dr. " + getName() + " prescribed " + medicine + " to the patient.");
    }

    // simple method for approving discharge
    public void approveDischarge(Patient patient) {
        if (patient == null) {
            System.out.println("Cannot approve discharge for a null patient.");
            return;
        }

        if (!hasPatient(patient)) {
            System.out.println("Patient must be assigned to Dr. " + getName() + " before discharge approval.");
            return;
        }

        System.out.println("Dr. " + getName() + " approved the patient's discharge.");
    }

    // adds an appointment to the doctor's schedule
    public void takeAppointment(Appointment appointment) {
        if (appointment == null) {
            System.out.println("Cannot take a null appointment.");
            return;
        }

        if (!available) {
            throw new DoctorUnavailableException("Dr. " + getName() + " is currently unavailable.");
        }

        if (appointment.getDoctor() != null && appointment.getDoctor() != this) {
            System.out.println("This appointment belongs to a different doctor.");
            return;
        }

        if (hasAppointment(appointment)) {
            System.out.println("Appointment " + appointment.getAppointmentId()
                    + " is already in Dr. " + getName() + "'s schedule.");
            return;
        }

        if (appointmentCount >= MAX_OBJECTS) {
            throw new MaxCapacityException("Appointment", MAX_OBJECTS);
        }

        // auto assign the patient if needed
        Patient patient = appointment.getPatient();
        if (patient != null && !hasPatient(patient)) {
            assignPatient(patient);
        }

        appointments[appointmentCount] = appointment;
        appointmentCount++;
        appointment.schedule();

        System.out.println("Dr. " + getName() + " accepted appointment "
                + appointment.getAppointmentId() + ".");
    }

    @Override
    public void viewStaffInfo() {
        // show parent info first
        super.viewStaffInfo();

        // then show doctor info
        System.out.println("Specialization: " + specialization);
        System.out.println("License Number: " + licenseNumber);
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Available: " + available);
        System.out.println("Assigned Patients: " + patientCount);
        System.out.println("Appointments: " + appointmentCount);
    }

    @Override
    public void performDuties() {
        // required method from staff (duties)
        System.out.println("Doctor " + getName() + " is performing duties.");
        System.out.println(" - diagnosing patients");
        System.out.println(" - prescribing medicine");
        System.out.println(" - reviewing appointments");
        System.out.println(" - approving discharges");
    }

    @Override
    public String getRoleDescription() {
        // other required method from staff (short description of special role)
        return "Doctor specializing in " + specialization + ".";
    }

    @Override
    public String toString() {
        // returns doctor info as one string
        return super.toString()
                + ", Specialization: " + specialization
                + ", License Number: " + licenseNumber
                + ", Room Number: " + roomNumber
                + ", Available: " + available
                + ", Assigned Patients: " + patientCount
                + ", Appointments: " + appointmentCount;
    }
}
