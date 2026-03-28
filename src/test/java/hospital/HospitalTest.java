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







}
