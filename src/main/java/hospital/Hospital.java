package hospital;

public class Hospital {
    /*
     * Class Implemented by Karthik Muthukumar
     * ID: 016281915
     */

    //Max objects limit
    public static final int MAX_OBJECTS = 100;

    //Attributes
    private String hospitalName;
    private Patient[] patients;
    private Doctor[] doctors;
    private Nurse[] nurses;
    private Appointment[] appointments;
    private Pharmacy pharmacy;

    //Counters
    private int patientCount;
    private int doctorCount;
    private int nurseCount;
    private int appointmentCount;

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


}
