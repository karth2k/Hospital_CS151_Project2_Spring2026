package hospital;

public class Appointment {
    private int appointmentId;
    private Patient patient;
    private Doctor doctor;
    private String date;
    private String status;

    private static int idCounter = 1;

    public Appointment(Patient patient, Doctor doctor, String date){
        this.appointmentId = idCounter++;
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
        this.status = "Pending";
    }

    public int getAppointmentId(){
        return appointmentId;
    }

    public Patient getPatient(){
        return patient;
    }

    public Doctor getDoctor(){
        return doctor;
    }

    public String getDate(){
        return date;
    }

    public String getStatus(){
        return status;
    }

    public void schedule(){
        status = "Scheduled!";
        System.out.println("Appointment " + appointmentId + " scheduled for "  + patient.getName()  + " with Dr. "  + doctor.getName()  + " on " + date);
    }

    public void cancel(){
        status = "Canceled";
        System.out.println("Appointment " + appointmentId + " scheduled for "  + patient.getName()  + " with Dr. "  + doctor.getName()  + " on " + date + " has been canceled!");
    }

    public void reschedule(String newDate){
        String oldDate = date;
        date = newDate;
        System.out.println("Appointment scheduled for " + oldDate + " has been rescheduled to " + date);
    }

    public void complete(){
        status = "Completed";
        System.out.println("Appointment " + appointmentId + " has been completed");
    }

    public void viewDetails(){
        System.out.println("\nAppointment Id: " + appointmentId);
        System.out.println("Patient: " + patient);
        System.out.println("Doctor: " + doctor);
        System.out.println("Date: " + date);
        System.out.println("Status: " + status);
        System.out.println();
    }

}

