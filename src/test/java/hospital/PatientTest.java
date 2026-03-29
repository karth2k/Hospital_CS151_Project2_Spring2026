package hospital;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {
    /*********************************************
     * author: Sean Tadina
     * id: 018950802
     * username: @seantadina
     *********************************************/

    // shared test objects used before each test
    private Patient patient;
    private Doctor doctor;

    // creates a fresh patient and doctor before every test
    @BeforeEach
    void setUp() {
        patient = makePatient("P001", "Sean");
        doctor = makeDoctor("D001", "Robinavitch");
    }

    // helper method to build a patient object quickly
    private Patient makePatient(String id, String name) {
        return new Patient(id, name, 22, "Cold");
    }

    // helper method to build a doctor object quickly
    private Doctor makeDoctor(String id, String name) {
        return new Doctor(
                id,
                name,
                "Cardiology",
                150000,
                "Cardiologist",
                "LIC-" + id,
                "ROOM-" + id
        );
    }

    // tests that the constructor sets up the default patient state correctly
    @Test
    void constructorInitializesFieldsCorrectly() {
        assertEquals("P001", patient.getPatientId());
        assertEquals("Sean", patient.getName());
        assertEquals(22, patient.getAge());
        assertEquals("Cold", patient.getCondition());
        assertEquals(0.0, patient.getBillAmount());
        assertFalse(patient.isAdmitted());
        assertNull(patient.getAssignedDoctor());
        assertEquals("Not diagnosed", patient.getDiagnosis());
        assertEquals("None", patient.getPrescribedMedicine());
    }

    // tests that the main valid setters update the patient fields correctly
    @Test
    void validSettersUpdatePatientFields() {
        patient.setPatientId("P777");
        patient.setName("Amir");
        patient.setAge(30);
        patient.setCondition("Stable");
        patient.setBillAmount(125.50);
        patient.setAdmitted(true);

        assertEquals("P777", patient.getPatientId());
        assertEquals("Amir", patient.getName());
        assertEquals(30, patient.getAge());
        assertEquals("Stable", patient.getCondition());
        assertEquals(125.50, patient.getBillAmount());
        assertTrue(patient.isAdmitted());
    }

    // tests the normal admit then discharge workflow
    @Test
    void admitAndDischargeUpdatePatientStatus() {
        patient.admitPatient();
        assertTrue(patient.isAdmitted());

        patient.dischargePatient();
        assertFalse(patient.isAdmitted());
    }

    // tests that diagnosis and prescription updates store the new patient info
    @Test
    void updateDiagnosisAndPrescriptionChangePatientRecord() {
        patient.updateDiagnosis("Flu");
        patient.updatePrescription("Ibuprofen");

        assertEquals("Flu", patient.getDiagnosis());
        assertEquals("Ibuprofen", patient.getPrescribedMedicine());
    }

    // tests billing logic including a valid payment and an invalid overpayment edge case
    @Test
    void billingUpdatesBalanceAndRejectsOverpayment() {
        patient.addCharge(200.0);
        assertEquals(200.0, patient.getOutstandingBalance());

        patient.payBill(50.0);
        assertEquals(150.0, patient.getOutstandingBalance());

        patient.payBill(500.0);
        assertEquals(150.0, patient.getOutstandingBalance());
    }

    // tests that assigning a doctor updates both the patient and doctor sides of the relationship
    @Test
    void assignDoctorSyncsPatientAndDoctor() {
        patient.assignDoctor(doctor);

        assertEquals(doctor, patient.getAssignedDoctor());
        assertTrue(doctor.hasPatient(patient));
        assertEquals(1, doctor.getPatientCount());
    }

    // tests the edge case of switching doctors and making sure the old doctor no longer keeps the patient
    @Test
    void reassignDoctorMovesPatientToNewDoctor() {
        Doctor firstDoctor = makeDoctor("D001", "Robinavitch");
        Doctor secondDoctor = makeDoctor("D002", "Newton");

        patient.assignDoctor(firstDoctor);
        patient.assignDoctor(secondDoctor);

        assertEquals(secondDoctor, patient.getAssignedDoctor());
        assertFalse(firstDoctor.hasPatient(patient));
        assertTrue(secondDoctor.hasPatient(patient));
        assertEquals(0, firstDoctor.getPatientCount());
        assertEquals(1, secondDoctor.getPatientCount());
    }
}
