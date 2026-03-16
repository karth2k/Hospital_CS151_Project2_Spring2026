package hospital;

public class Nurse extends Staff{
    private String shift;
    private String ward;

    public Nurse(String employeeId, String name, String department, double salary, String shift, String ward){
        super(employeeId, name, department, salary);
        this.shift = shift;
        this.ward = ward;
    }

    public String getShift(){
        return shift;
    }

    public String getWard(){
        return ward;
    }

    public void setShift(String shift){
        //makes sure shift is a valid nonempty string
        if(shift != null && !shift.trim().isEmpty()){
            this.shift = shift;
        }else{
            System.out.println("Invalid input!");
        }
    }

    public void assignWard(String ward){
        //makes sure ward is a valid nonempty string
        if(ward != null && !ward.trim().isEmpty()){
            this.ward = ward;
        }else{
            System.out.println("Invalid input!");
        }
    }

    public void checkVitals(Patient patient){
        System.out.println("Nurse "  + getName() + " is checking vitals for patient " /*+ patient.getName()*/);
        System.out.println("Vitals are stable.");
    }

    public void assistDoctor(Doctor doctor){
        System.out.println("Nurse " + getName() + " assists Doctor " /*+ doctor.getName()*/ + " with procedure");
        System.out.println("Doctor successfully assisted!");
    }

    public void administerMedicine(Patient patient, String medicine){
        System.out.println("Nurse " + getName() + " administers " + medicine + " for patient " /*+ patient.getName() */);
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
