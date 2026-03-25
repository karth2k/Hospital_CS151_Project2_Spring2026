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


}
