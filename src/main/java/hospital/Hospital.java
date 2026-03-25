package hospital;

public class Hospital {
    /*
     * Class Implemented by Karthik Muthukumar
     * ID: 016281915
     */

    //Max objects limit
    public static final int MAX_OBJECTS = 100;

    //Attributes
    private String hospitalName;                    //Name of hospital
    private Patient[] patients;                     //List of patients
    private Doctor[] doctors;                       //List of doctors
    private Nurse[] nurses;                         //List of nurses
    private Appointment[] appointments;             //List of appointments
    private Pharmacy pharmacy;                      //pharmacy object

    //Counters
    private int patientCount;                       //Amount of patients
    private int doctorCount;                        //Amount of doctors
    private int nurseCount;                         //Amount of nurses
    private int appointmentCount;                   //Amount of appointments

    //Getters
    public String getHospitalName(){
        //gets the name of the hospital
        return hospitalName;
    }
    public Patient[] getPatients() {
        //Gets list of patients
        return patients;
    }
    public Doctor[] getDoctors(){
        //Gets list of doctors
        return doctors;
    }
    public Nurse[] getNurses(){
        //Gets list of nurses
        return nurses;
    }
    public Appointment[] getAppointments(){
        //Gets list of appointments
        return appointments;
    }
    public Pharmacy getPharmacy(){
        //Gets pharmacy
        return pharmacy;
    }
    public int getPatientCount(){
        //Gets patient count
        return patientCount;
    }
    public int getDoctorCount(){
        //Gets doctor count
        return doctorCount;
    }
    public int getNurseCount(){
        //Gets nurse count
        return nurseCount;
    }
    public int getAppointmentCount(){
        //Gets appointment count
        return appointmentCount;
    }

    //Setters with validation
    public void setHospitalName(String hospitalName){
        //Makes sure hospital name isnt null or empty space
        if(hospitalName != null && !hospitalName.trim().isEmpty()){
            this.hospitalName= hospitalName.trim();
        }
        else{
            System.out.println("Hospital name cannot be empty.");
        }
    }
    public void setPharmacy(Pharmacy pharmacy){
        if(pharmacy != null){
            this.pharmacy = pharmacy;
        }
        else{
            System.out.println("Pharmacy cant be null.");
        }
    }

    //Helper functions
    private boolean hasPatientId(String patientId){
        //Checks if a patient has a valid patient id and exists in the patient array
        if(patientId == null || patientId.trim().isEmpty()) {
            return false;
        }

        for(int i = 0; i < patientCount; i++) {
            if(patients[i] != null && patients[i].getPatientId().equalsIgnoreCase(patientId.trim())) {
                return true;
            }
        }
        return false;
    }

    private boolean hasDoctorId(String doctorId) {
        //Checks if a doctor has a valid doctor id and exists in the doctor array
        if(doctorId == null || doctorId.trim().isEmpty()) {
            return false;
        }

        for(int i = 0; i < doctorCount; i++) {
            if(doctors[i] != null && doctors[i].getEmployeeID().equalsIgnoreCase(doctorId.trim())) {
                return true;
            }
        }
        return false;
    }

    private boolean hasNurseId(String nurseId) {
        //Checks if a nurse has a valid nurse id and exists in the nurse array
        if(nurseId == null || nurseId.trim().isEmpty()) {
            return false;
        }

        for(int i = 0; i < nurseCount; i++) {
            if(nurses[i] != null && nurses[i].getEmployeeID().equalsIgnoreCase(nurseId.trim())) {
                return true;
            }
        }
        return false;
    }

    public Doctor findDoctor(String doctorId) {
        //Searches for and returns doctor object with matching doctor ID and if not found prints not found.
        //Also makes sure ID isnt null
        if(doctorId == null || doctorId.trim().isEmpty()) {
            System.out.println("Doctor ID cannot be empty.");
            return null;
        }

        for(int i = 0; i < doctorCount; i++) {
            if(doctors[i] != null && doctors[i].getEmployeeID().equalsIgnoreCase(doctorId.trim())) {
                return doctors[i];
            }
        }

        System.out.println("Doctor with ID \"" + doctorId + "\" was not found.");
        return null;
    }

    public Nurse findNurse(String nurseId) {
        //Searches for and returns nurse object with matching nurse ID and if not found prints not found.
        //Also makes sure ID isnt null
        if(nurseId == null || nurseId.trim().isEmpty()) {
            System.out.println("Nurse ID cannot be empty.");
            return null;
        }

        for(int i = 0; i < nurseCount; i++) {
            if(nurses[i] != null && nurses[i].getEmployeeID().equalsIgnoreCase(nurseId.trim())) {
                return nurses[i];
            }
        }

        System.out.println("Nurse with ID \"" + nurseId + "\" was not found.");
        return null;
    }

    public Appointment findAppointment(int appointmentId) {
        //Searches and returns appointment object with matching appointment id and prints not found if it doesnt exist
        for(int i = 0; i < appointmentCount; i++) {
            if(appointments[i] != null && appointments[i].getAppointmentId() == appointmentId) {
                return appointments[i];
            }
        }

        System.out.println("Appointment " + appointmentId + " was not found.");
        return null;
    }

    //Core functionality
    public void addPatient(Patient patient){
        //Adds patient, makes sure its not null and also makes sure to throw exception if there are 100 patients already
        if(patient == null){
            System.out.println("Cant add null patient.");
            return;
        }
        if(patientCount >= MAX_OBJECTS) {
            throw new MaxCapacityException("Patient", MAX_OBJECTS);
        }
        //checks if patient already exists in patient array
        if(hasPatientId(patient.getPatientId())) {
            System.out.println("Patient with ID " + patient.getPatientId() + " already exists.");
            return;
        }

        //next open slot in array is allocatd to patient and patient count is increased
        patients[patientCount] = patient;
        patientCount++;

        System.out.println("Patient " + patient.getName() + " added to " + hospitalName + ".");
    }

    public void removePatient(String patientId) {
        //removes a patient given a valid patient ID (checks for null/empty spaces)
        if(patientId == null || patientId.trim().isEmpty()) {
            System.out.println("Patient ID cannot be empty.");
            return;
        }

        //Removes patient from the patients array if valid id is found/matched for the patient in patient array
        for(int i = 0; i < patientCount; i++) {
            if(patients[i] != null && patients[i].getPatientId().equalsIgnoreCase(patientId.trim())) {
                Patient removedPatient = patients[i];

                if(removedPatient.getAssignedDoctor() != null) {
                    removedPatient.getAssignedDoctor().removePatient(removedPatient);
                }

                //fixes array slots starting from slot patient was deletd
                for(int j = i; j < patientCount - 1; j++) {
                    patients[j] = patients[j + 1];
                }

                //decrements patient count and makes the open spot equal to null so it can be used later on if we do add patient
                patients[patientCount - 1] = null;
                patientCount--;

                System.out.println("Patient " + removedPatient.getName() + " removed from the hospital.");
                return;
            }
        }
        //if patient isnt found then throws patient not found exception
        throw new PatientNotFoundException(patientId, true);
    }

    public void addDoctor(Doctor doctor){
        //Adds doctor, makes sure its not null and also makes sure to throw exception if there are 100 doctors already
        if(doctor == null){
            System.out.println("Cannot add a null doctor");
            return;
        }
        if(doctorCount >= MAX_OBJECTS) {
            throw new MaxCapacityException("Patient", MAX_OBJECTS);
        }
        //checks if doctor already exists in doctor array
        if(hasDoctorId(doctor.getEmployeeID())) {
            System.out.println("Patient with ID " + doctor.getEmployeeID() + " already exists.");
            return;
        }

        //Adds doctor to doctors array and increments doctor count
        doctors[doctorCount] = doctor;
        doctorCount++;

        System.out.println("Doctor " + doctor.getName() + " added to " + hospitalName + ".");
    }

    public void addNurse(Nurse nurse) {
        //Adds nurse, makes sure its not null and also makes sure to throw exception if there are 100 nurses already
        if (nurse == null) {
            System.out.println("Cannot add a null nurse.");
            return;
        }

        if (nurseCount >= MAX_OBJECTS) {
            throw new MaxCapacityException("Nurse", MAX_OBJECTS);
        }

        //checks if nurse already exists in nurse array
        if (hasNurseId(nurse.getEmployeeID())) {
            System.out.println("Nurse with ID " + nurse.getEmployeeID() + " already exists.");
            return;
        }

        //Adds nurse to nurses array and increments nurse count
        nurses[nurseCount] = nurse;
        nurseCount++;

        System.out.println("Nurse " + nurse.getName() + " added to " + hospitalName + ".");
    }

}
