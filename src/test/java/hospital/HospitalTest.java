package hospital;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HospitalTest {
    /*
     * Karthik Muthukumar
     * ID: 016281915
     */
    private Hospital hospital;
    private Pharmacy pharmacy;

    //makes sure to create initial state before each function is executed so state changes are independent of each other
    @BeforeEach
    void setUp(){
        pharmacy = new Pharmacy("CVS-TEST");
        hospital = new Hospital("Dublin Hospital", pharmacy);
    }
    private Patient makePatient(String id, String name) {
        return new Patient(id, name, 22, "Cold");
    }
    private Doctor makeDoctor(String id, String name) {
        return new Doctor(
                id, name, "Cardiology", 150000,
                "Cardiologist", "LIC-" + id, "ROOM-" + id
        );
    }
    private Nurse makeNurse(String id, String name) {
        return new Nurse(
                id, name, "General", 80000,
                "Day", "Ward A"
        );
    }

    @Test
    void constructorInitializesFieldsCorrectly() {
        //makes sure the constructor initializes each field correctly
        assertEquals("Dublin Hospital", hospital.getHospitalName());
        assertEquals(0, hospital.getPatientCount());
        assertEquals(0, hospital.getDoctorCount());
        assertEquals(0, hospital.getNurseCount());
        assertEquals(0, hospital.getAppointmentCount());
        assertEquals(pharmacy, hospital.getPharmacy());
    }

    @Test
    void addPatientIncreasesPatientCount() {
        //makes sure adding the patient to the hospital increases the patient count
        Patient patient = makePatient("P001", "Karthik");

        hospital.addPatient(patient);
        assertEquals(1, hospital.getPatientCount());
        assertEquals(patient, hospital.getPatients()[0]);
    }

    @Test
    void addNullPatientDoesNotIncreaseCountTest() {
        //makes sure if we add a null patient it doesnt increase the count
        hospital.addPatient(null);
        assertEquals(0, hospital.getPatientCount());
    }

    @Test
    void addDuplicatePatientDoesNotIncreaseCount() {
        //makes sure that if we add 1 patient and then add another with the same ID it wont add the other one and state they arleady exists
        //also make sure number of patients isnt incremented
        Patient p1 = makePatient("P001", "Karthik");
        Patient p2 = makePatient("P001", "Karthik2");

        hospital.addPatient(p1);
        hospital.addPatient(p2);
        assertEquals(1, hospital.getPatientCount());
    }

    @Test
    void removePatientDecreasesPatientCount() {
        //makes sure if a patient is removed the total patient count decreases
        Patient patient = makePatient("P001", "Karthik");

        hospital.addPatient(patient);
        hospital.removePatient("P001");

        assertEquals(0, hospital.getPatientCount());
    }

    @Test
    void removeMissingPatientThrowsException() {
        //makes sure it throws an error if a nonexistent patient is removed
        assertThrows(PatientNotFoundException.class, () -> hospital.removePatient("Donovan Mitchell"));
    }

    @Test
    void addDoctorIncreasesDoctorCount() {
        //simply checks if add doctor works as intended and increases the doctor count for hospital
        Doctor doctor = makeDoctor("D001", "Newton");

        hospital.addDoctor(doctor);

        assertEquals(1, hospital.getDoctorCount());
    }

    @Test
    void removeDoctorWorksWhenSafe() {
        //simply checks if remove doctor works as intended and increases the doctor count for hospital
        Doctor doctor = makeDoctor("D001", "Newton");

        hospital.addDoctor(doctor);
        hospital.removeDoctor("D001");

        assertEquals(0, hospital.getDoctorCount());
    }

    @Test
    void addNurseIncreasesNurseCount() {
        //checks if when nurse is added the count of total nurses increases
        Nurse nurse = makeNurse("N001", "Maya");

        hospital.addNurse(nurse);

        assertEquals(1, hospital.getNurseCount());
    }

    @Test
    void removeNurseDecreasesNurseCount() {
        //checks if when nurse is removed the count of total nurses decreases
        Nurse nurse = makeNurse("N001", "Maya");

        hospital.addNurse(nurse);

        assertEquals(1, hospital.getNurseCount());
    }

    @Test
    void scheduleAppointmentIncreasesAppointmentCount() {
        //checks if scheduling appointment logic is correct and adds to appointment count
        Patient patient = makePatient("P001", "Karthik");
        Doctor doctor = makeDoctor("D001", "Newton");

        hospital.addPatient(patient);
        hospital.addDoctor(doctor);

        hospital.scheduleAppointment(patient, doctor, "03/28/2026");

        assertEquals(1, hospital.getAppointmentCount());
    }

    @Test
    void cancelAppointmentRemovesIt() {
        //checks if removing appointment logic is correct, sees if appointment count is decremented
        Patient patient = makePatient("P001", "Karthik");
        Doctor doctor = makeDoctor("D001", "Newton");

        hospital.addPatient(patient);
        hospital.addDoctor(doctor);
        hospital.scheduleAppointment(patient, doctor, "03/28/2026");

        int id = hospital.getAppointments()[0].getAppointmentId();

        hospital.cancelAppointment(id);

        assertEquals(0, hospital.getAppointmentCount());
        assertEquals(0, doctor.getAppointmentCount());
    }

    @Test
    void removePatientAlsoRemovesAppointments() {
        //makes sure if the patient is removed, their appointment is automatically removed (all appointment counts are 0)
        Patient patient = makePatient("P001", "Karthik");
        Doctor doctor = makeDoctor("D001", "Newton");

        hospital.addPatient(patient);
        hospital.addDoctor(doctor);
        hospital.scheduleAppointment(patient, doctor, "03/28/2026");

        hospital.removePatient("P001");

        assertEquals(0, hospital.getPatientCount());
        assertEquals(0, hospital.getAppointmentCount());
        assertEquals(0, doctor.getAppointmentCount());
    }

    @Test
    void payBillDecrementsBalance(){
        //Makes sure the paybill and add charge logic works properly by adding a charge and paying it and checking balance
        Patient patient = makePatient("P001", "Karthik");
        hospital.addPatient(patient);

        patient.addCharge(250);
        hospital.payPatientBill("P001", 100.0);

        assertEquals(150.0, patient.getOutstandingBalance());
    }

    @Test
    void assignDoctorToPatientWorks() {
        //makes sure that patient is assigned to the doctor
        Patient patient = makePatient("P001", "Karthik");
        Doctor doctor = makeDoctor("D001", "Newton");

        hospital.addPatient(patient);
        hospital.addDoctor(doctor);

        hospital.assignDoctorToPatient("P001", "D001");

        assertEquals(doctor, patient.getAssignedDoctor());
        assertEquals(1, doctor.getPatientCount());
    }

    //Below simply checks if we take into account the max instances limit of 100 posted on the assignment guidelines
    @Test
    void addingPatientBeyondCapacityThrowsException() {
        //Makes sure if we try to make more than 100 patients throws exception
        for (int i = 0; i < hospital.getPatients().length; i++) {
            hospital.addPatient(makePatient("P" + i, "Patient" + i));
        }

        assertThrows(MaxCapacityException.class, () -> {hospital.addPatient(makePatient("P_OVERLIMIT", "Patient overlimit"));});
    }

    @Test
    void addingDoctorBeyondCapacityThrowsException() {
        //Makes sure if we try to make more than 100 doctors throws exception
        for (int i = 0; i < hospital.getDoctors().length; i++) {
            hospital.addDoctor(makeDoctor("D" + i, "Doctor" + i));
        }

        assertThrows(MaxCapacityException.class, () -> {hospital.addDoctor(makeDoctor("D_OVERLIMIT", "Doctor overlimit"));});
    }

    @Test
    void addingNurseBeyondCapacityThrowsException() {
        //Makes sure if we try to make more than 100 nurses throws exception
        for (int i = 0; i < hospital.getNurses().length; i++) {
            hospital.addNurse(makeNurse("N" + i, "Nurse" + i));
        }

        assertThrows(MaxCapacityException.class, () -> {hospital.addNurse(makeNurse("N_OVERLIMIT", "Nurse overlimit"));});
    }


}
