package hospital;

public class Appointment {

    /*********************************************
     * author: David Ayala
     * id: 018871489
     * username: @davidayala661-spec
     *********************************************/

    // below are the appointment fields
    private int appointmentId;
    private Patient patient;
    private Doctor doctor;
    private String date;
    private String status;

    // static counter to auto-generate appointment ids
    private static int idCounter = 1;

    // constructor to create a new appointment
    public Appointment(Patient patient, Doctor doctor, String date){
        this.appointmentId = idCounter++;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.status = "Pending";
    }

    // returns the appointment id
    public int getAppointmentId(){
        return appointmentId;
    }

    // returns the patient for this appointment
    public Patient getPatient(){
        return patient;
    }

    // returns the doctor for this appointment
    public Doctor getDoctor(){
        return doctor;
    }

    // returns the appointment date
    public String getDate(){
        return date;
    }

    // returns the appointment status
    public String getStatus(){
        return status;
    }

    // schedules the appointment and updates its status
    public void schedule(){
        status = "Scheduled!";
<<<<<<< HEAD
        System.out.println("Appointment " + appointmentId + " scheduled for " + patient.getName() + " with Dr. " + doctor.getName() + " on " + date);
=======
        System.out.println("Appointment " + appointmentId + " scheduled for "  + patient.getName()  + " with Dr. "  + doctor.getName()  + " on " + date);
>>>>>>> 1eece9b91c6d2bcc6ba819aa5e9768a4520fa0a7
    }

    // cancels the appointment and updates its status
    public void cancel(){
        status = "Canceled";
<<<<<<< HEAD
        System.out.println("Appointment " + appointmentId + " scheduled for " + patient.getName() + " with Dr. "  + doctor.getName() + " on " + date + " has been canceled!");
=======
        System.out.println("Appointment " + appointmentId + " scheduled for "  + patient.getName()  + " with Dr. "  + doctor.getName()  + " on " + date + " has been canceled!");
>>>>>>> 1eece9b91c6d2bcc6ba819aa5e9768a4520fa0a7
    }

    // changes the appointment date
    public void reschedule(String newDate){
        String oldDate = date;
        date = newDate;
        System.out.println("Appointment scheduled for " + oldDate + " has been rescheduled to " + date);
    }

    // marks the appointment as completed
    public void complete(){
        status = "Completed";
        System.out.println("Appointment " + appointmentId + " has been completed");
    }

    // displays all appointment details
    public void viewDetails(){
        System.out.println("\nAppointment Id: " + appointmentId);
        System.out.println("Patient: " + patient);
        System.out.println("Doctor: " + doctor);
        System.out.println("Date: " + date);
        System.out.println("Status: " + status);
        System.out.println();
    }

    @Override
    public String toString(){
        return "Appointment ID: " + appointmentId + ", Date: " + date + ", Status: " + status;
    }

}

