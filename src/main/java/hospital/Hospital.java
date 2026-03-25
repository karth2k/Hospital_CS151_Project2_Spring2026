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


}
