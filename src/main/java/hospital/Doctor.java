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
        this.assignedPatients = new Patient[100];
        this.appointments = new Appointment[100];
        this.patientCount = 0;
        this.appointmentCount = 0;
    }

    @Override
    public void performDuties() {
        // required method from staff
        System.out.println("Doctor is performing duties.");
    }

    @Override
    public String getRoleDescription() {
        // other required method from staff
        return "Doctor";
    }
}
