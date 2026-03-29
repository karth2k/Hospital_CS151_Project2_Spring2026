package hospital;

public class Nurse extends Staff{

    /*********************************************
     * author: David Ayala
     * id: 018871489
     * username: @davidayala661-spec
     *********************************************/

    // below are the nurse fields
    private String shift;
    private String ward;

    // constructor to initialize nurse and inherited staff fields
    public Nurse(String employeeId, String name, String department, double salary, String shift, String ward){
        super(employeeId, name, department, salary);
        this.shift = shift;
        this.ward = ward;
    }

    // returns the nurse's shift
    public String getShift(){
        return shift;
    }

    // returns the nurse's ward
    public String getWard(){
        return ward;
    }

    // updates shift if input is valid
    public void setShift(String shift){
        //makes sure shift is a valid nonempty string
        if(shift != null && !shift.trim().isEmpty()){
            this.shift = shift;
        }else{
            System.out.println("Invalid input!");
        }
    }

    // updates ward if input is valid
    public void assignWard(String ward){
        //makes sure ward is a valid nonempty string
        if(ward != null && !ward.trim().isEmpty()){
            this.ward = ward;
        }else{
            System.out.println("Invalid input!");
        }
    }

    // checks a patient's vitals
    public void checkVitals(Patient patient){
        System.out.println("Nurse "  + getName() + " is checking vitals for patient " + patient.getName());
        System.out.println("Vitals are stable.");
    }

    // helps a doctor during a procedure
    public void assistDoctor(Doctor doctor){
        System.out.println("Nurse " + getName() + " assists Doctor " + doctor.getName() + " with procedure");
        System.out.println("Doctor successfully assisted!");
    }

    // gives medicine to a patient
    public void administerMedicine(Patient patient, String medicine){
<<<<<<< HEAD
        System.out.println("Nurse " + getName() + " administers " + medicine + " for patient " + patient.getName() );
=======
        System.out.println("Nurse " + getName() + " administers " + medicine + " for patient " + patient.getName());
>>>>>>> 1eece9b91c6d2bcc6ba819aa5e9768a4520fa0a7
        System.out.println("Medicine administered!");
    }

    @Override
    public void viewStaffInfo(){
        super.viewStaffInfo();
        System.out.println("Shift: " + shift);
        System.out.println("Ward: " + ward);
    } 

    @Override
    public void performDuties(){
        System.out.println("Nurse " + getName() + " is performing duties: ");
        System.out.println(" - Checking patient vitals");
        System.out.println(" - Administering medication");
        System.out.println(" - Assisting doctor with procedures");
        System.out.println(" - Monitoring patient conditions");
    }

    @Override
    public String getRoleDescription(){
        return "Nurse responsible for monitoring patients, administering medication, and assisting doctors.";
    }
}
