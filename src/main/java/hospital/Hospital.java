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

    //Constructor
    public Hospital(String hospitalName, Pharmacy pharmacy){
        //Constructor initializes all values. checks if hospital/pharmacy name is valid and if not gives it a dummy name
        //initializes arrays to max object length of 100
        this.hospitalName = (hospitalName != null && !hospitalName.trim().isEmpty()) ? hospitalName.trim() : "Unknown Hospital";
        this.patients = new Patient[MAX_OBJECTS];
        this.doctors = new Doctor[MAX_OBJECTS];
        this.nurses = new Nurse[MAX_OBJECTS];
        this.appointments = new Appointment[MAX_OBJECTS];
        this.pharmacy = (pharmacy != null) ? pharmacy : new Pharmacy("CVS-001");
        this.patientCount = 0;
        this.doctorCount = 0;
        this.nurseCount = 0;
        this.appointmentCount = 0;
    }

    public Hospital(String hospitalName){
        //if only the hospital name is provided the constructor creates a random pharmacy name and uses attribute assignments of other
        //constructor using this keyword
        this(hospitalName, new Pharmacy("CVS-001"));
    }

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

    private void removeAppointmentsForPatient(Patient patient) { //removes appointments belonging to a specific patient

        // makes sure patient is valid
        if (patient == null) {
            return;
        }

        //loops through appointments array and removes matching appointments
        for (int i = 0; i < appointmentCount; i++) {
            if (appointments[i] != null && appointments[i].getPatient() == patient) {

                if (appointments[i].getDoctor() != null) {
                    appointments[i].getDoctor().removeAppointment(appointments[i]);
                }

                for (int j = i; j < appointmentCount - 1; j++) {
                    appointments[j] = appointments[j + 1];
                }

                appointments[appointmentCount - 1] = null;
                appointmentCount--;
                i--;
            }
        }
    }

    private void removeAppointmentFromHospital(Appointment appointment) {
        //removes one appointment from hospitals appointment array (clears history and allows for new appointments to be scheduled)
        //makes sure appointment is valid
        if(appointment == null) {
            return;
        }

        //searches for the appointment in hospital appointments array
        for(int i = 0; i < appointmentCount; i++) {
            if(appointments[i] != null &&
                    (appointments[i] == appointment || appointments[i].getAppointmentId() == appointment.getAppointmentId())) {

                if(appointments[i].getDoctor() != null) {
                    appointments[i].getDoctor().removeAppointment(appointments[i]);
                }

                for(int j = i; j < appointmentCount - 1; j++) {
                    appointments[j] = appointments[j + 1];
                }

                appointments[appointmentCount - 1] = null;
                appointmentCount--;
                return;
            }
        }
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
                removeAppointmentsForPatient(removedPatient); //new helper function to remove all appointments associated with patient

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
            throw new MaxCapacityException("Doctor", MAX_OBJECTS);
        }
        //checks if doctor already exists in doctor array
        if(hasDoctorId(doctor.getEmployeeID())) {
            System.out.println("Doctor with ID " + doctor.getEmployeeID() + " already exists.");
            return;
        }

        //Adds doctor to doctors array and increments doctor count
        doctors[doctorCount] = doctor;
        doctorCount++;

        System.out.println("Doctor " + doctor.getName() + " added to " + hospitalName + ".");
    }

    public void removeDoctor(String doctorId) { //removes doctor using doctor ID
        //makes sure doctor ID is not null or empty
        if(doctorId == null || doctorId.trim().isEmpty()) {
            System.out.println("Doctor ID cannot be empty.");
            return;
        }

        //searches for matching doctor in doctors array
        for(int i = 0; i < doctorCount; i++) {
            if(doctors[i] != null && doctors[i].getEmployeeID().equalsIgnoreCase(doctorId.trim())) {
                Doctor removedDoctor = doctors[i];

                //if doctor still has patients or appointments cant be removed
                if(removedDoctor.getPatientCount() > 0 || removedDoctor.getAppointmentCount() > 0) {
                    System.out.println("Cannot remove Dr. " + removedDoctor.getName()
                            + " because they still have assigned patients or scheduled appointments.");
                    return;
                }

                //shifts all doctors left to fill the removed spot
                for(int j = i; j < doctorCount - 1; j++) {
                    doctors[j] = doctors[j + 1];
                }

                doctors[doctorCount - 1] = null;
                doctorCount--;

                System.out.println("Doctor " + removedDoctor.getName() + " removed from the hospital.");
                return;
            }
        }
        //if doctor is not found
        System.out.println("Doctor with ID \"" + doctorId + "\" was not found.");
    }

    public void addNurse(Nurse nurse) {
        //Adds nurse, makes sure its not null and also makes sure to throw exception if there are 100 nurses already
        if(nurse == null) {
            System.out.println("Cannot add a null nurse.");
            return;
        }

        if(nurseCount >= MAX_OBJECTS) {
            throw new MaxCapacityException("Nurse", MAX_OBJECTS);
        }

        //checks if nurse already exists in nurse array
        if(hasNurseId(nurse.getEmployeeID())) {
            System.out.println("Nurse with ID " + nurse.getEmployeeID() + " already exists.");
            return;
        }

        //Adds nurse to nurses array and increments nurse count
        nurses[nurseCount] = nurse;
        nurseCount++;

        System.out.println("Nurse " + nurse.getName() + " added to " + hospitalName + ".");
    }

    public void removeNurse(String nurseId) { //removes nurse using nurse ID
        //makes sure nurse ID is not null or empty
        if(nurseId == null || nurseId.trim().isEmpty()) {
            System.out.println("Nurse ID cannot be empty.");
            return;
        }

        //searches for matching nurse in nurses array
        for(int i = 0; i < nurseCount; i++) {
            if(nurses[i] != null && nurses[i].getEmployeeID().equalsIgnoreCase(nurseId.trim())) {
                Nurse removedNurse = nurses[i];

                //shifts all nurses left to fill the removed spot
                for(int j = i; j < nurseCount - 1; j++) {
                    nurses[j] = nurses[j + 1];
                }

                nurses[nurseCount - 1] = null;
                nurseCount--;

                System.out.println("Nurse " + removedNurse.getName() + " removed from the hospital.");
                return;
            }
        }

        System.out.println("Nurse with ID \"" + nurseId + "\" was not found.");
    }

    public void scheduleAppointment(Patient patient, Doctor doctor, String date){
        //Validates patient doctor and date and then schedules and appoiintment
        if(patient == null) {
            System.out.println("Cannot schedule an appointment for a null patient.");
            return;
        }

        if(doctor == null) {
            System.out.println("Cannot schedule an appointment with a null doctor.");
            return;
        }

        if(date == null || date.trim().isEmpty()) {
            System.out.println("Appointment date cannot be empty.");
            return;
        }

        //if over 100 appointments exist then throw exception
        if(appointmentCount >= MAX_OBJECTS) {
            throw new MaxCapacityException("Appointment", MAX_OBJECTS);
        }

        //if doctor is unavailable throw error
        if(!doctor.isAvailable()) {
            throw new DoctorUnavailableException("Dr. " + doctor.getName() + " is currently unavailable.");
        }

        //if patient is not registered then do not schedule
        if(!hasPatientId(patient.getPatientId())) {
            System.out.println("Patient must be added to the hospital before scheduling.");
            return;
        }

        if(!hasDoctorId(doctor.getEmployeeID())) {
            System.out.println("Doctor must be added to the hospital before scheduling.");
            return;
        }

        Appointment appointment = new Appointment(patient, doctor, date.trim());

        //Doctor.takeAppointment() already checks availability, stores the appointment,
        //auto-assigns the patient if needed, and calls appointment.schedule().
        doctor.takeAppointment(appointment);

        appointments[appointmentCount] = appointment;
        appointmentCount++;

        System.out.println("Appointment " + appointment.getAppointmentId() + " added to the hospital schedule.");
    }

    public Patient findPatient(String patientId) {
        //finds patient and returns the patient
        if(patientId == null || patientId.trim().isEmpty()) {
            //if patient id does not exist or is empty then returns null and prints error
            System.out.println("Patient ID cannot be empty.");
            return null;
        }

        //finds patient in patient array and returns them
        for(int i = 0; i < patientCount; i++) {
            if(patients[i] != null && patients[i].getPatientId().equalsIgnoreCase(patientId.trim())) {
                return patients[i];
            }
        }

        //Throws error if patient not found
        throw new PatientNotFoundException(patientId, true);
    }

    public void displayAllPatients() {
        //Displays all the patients (through looping through patient array and printing their information using patient tostring)
        if(patientCount == 0) {
            System.out.println("No patients found.");
            return;
        }

        System.out.println("------- All Patients -------");
        for(int i = 0; i < patientCount; i++) {
            System.out.println((i + 1) + ". " + patients[i]);
        }
    }

    public void displayAllDoctors() {
        //Displays all the doctors (through looping through doctor array and printing their information using doctor tostring)
        if(doctorCount == 0) {
            System.out.println("No doctors found.");
            return;
        }

        System.out.println("------- All Doctors -------");
        for(int i = 0; i < doctorCount; i++) {
            System.out.println((i + 1) + ". " + doctors[i]);
        }
    }
    //Helpers for Main.java
    public void admitPatient(String patientId) {
        //finds and returns patient then admits them
        Patient patient = findPatient(patientId);
        if(patient != null) {
            patient.admitPatient();
        }
    }

    public void dischargePatient(String patientId) {
        //finds and returns patient then discharges them
        Patient patient = findPatient(patientId);
        if(patient != null) {
            patient.dischargePatient();
        }
    }

    public void payPatientBill(String patientId, double amount) {
        //finds and returns patient then pays the bill
        Patient patient = findPatient(patientId);
        if(patient != null) {
            patient.payBill(amount);
        }
    }

    public void assignDoctorToPatient(String patientId, String doctorId) {
        //finds the patient and doctor then returns them then assigns the doctor to the patient
        Patient patient = findPatient(patientId);
        Doctor doctor = findDoctor(doctorId);

        if(patient != null && doctor != null) {
            patient.assignDoctor(doctor);
        }
    }

    public void displayAllNurses() {
        //prints all nurses in the nurse array if more than 0
        if(nurseCount == 0) {
            System.out.println("No nurses found.");
            return;
        }

        System.out.println("------ All Nurses ------");
        for(int i = 0; i < nurseCount; i++) {
            System.out.println((i + 1) + ". " + nurses[i]);
        }
    }

    public void displayAllAppointments() {
        //prints all appointments in appointments array if there is more than 0
        if(appointmentCount == 0) {
            System.out.println("No appointments found.");
            return;
        }

        System.out.println("------ All Appointments ------");
        for(int i = 0; i < appointmentCount; i++) {
            appointments[i].viewDetails();
        }
    }

    public void cancelAppointment(int appointmentId) {
        //finds and validates the appointment and returns it. then cancels the appointment and removes from active records
        Appointment appointment = findAppointment(appointmentId);
        if(appointment != null) {
            appointment.cancel();
            removeAppointmentFromHospital(appointment);
        }
    }

    public void rescheduleAppointment(int appointmentId, String newDate) {
        //finds and validates the appointment and returns it. then reschedueles it
        Appointment appointment = findAppointment(appointmentId);
        if(appointment != null) {
            appointment.reschedule(newDate);
        }
    }

    public void completeAppointment(int appointmentId) {
        //finds and validates the appointment and returns it, then marks as complete and removes from active records
        Appointment appointment = findAppointment(appointmentId);
        if(appointment != null) {
            appointment.complete();
            removeAppointmentFromHospital(appointment);
        }
    }

    @Override
    public String toString() {
        //Overrides toString to print out the hospital details
        return "Hospital Name: " + hospitalName + ", Patients: " + patientCount + ", Doctors: " + doctorCount
                + ", Nurses: " + nurseCount + ", Appointments: " + appointmentCount + ", Pharmacy: " + pharmacy;
    }

}
