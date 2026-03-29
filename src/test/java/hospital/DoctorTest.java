package hospital;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class DoctorTest {
    /*********************************************
     * author: Sean Tadina
     * id: 018950802
     * username: @seantadina
     *********************************************/

    private Doctor doctor;
    private Patient patient;

    // resets test data before each test runs
    @BeforeEach
    void setUp() {
        doctor = makeDoctor("D001", "Robinavitch");
        patient = makePatient("P001", "Sean");
    }

    // helper method to quickly create a doctor
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

    // helper method to quickly create a patient
    private Patient makePatient(String id, String name) {
        return new Patient(id, name, 22, "Cold");
    }

    // helper method to quickly create an appointment
    private Appointment makeAppointment(Patient patient, Doctor doctor, String date) {
        return new Appointment(patient, doctor, date);
    }

    // tests that the constructor sets all doctor fields correctly
    @Test
    void constructorInitializesFieldsCorrectly() {
        assertEquals("D001", doctor.getEmployeeID());
        assertEquals("Robinavitch", doctor.getName());
        assertEquals("Cardiology", doctor.getDepartment());
        assertEquals(150000.0, doctor.getSalary());
        assertEquals("Cardiologist", doctor.getSpecialization());
        assertEquals("LIC-D001", doctor.getLicenseNumber());
        assertEquals("ROOM-D001", doctor.getRoomNumber());
        assertTrue(doctor.isAvailable());
        assertEquals(0, doctor.getPatientCount());
        assertEquals(0, doctor.getAppointmentCount());
        assertNotNull(doctor.getAssignedPatients());
        assertNotNull(doctor.getAppointments());
    }

    // tests that blank doctor fields fall back to default values
    @Test
    void constructorUsesFallbackValuesForBlankDoctorFields() {
        Doctor blankDoctor = new Doctor(
                "D002",
                "Gray",
                "General",
                120000,
                "   ",
                null,
                ""
        );

        assertEquals("General", blankDoctor.getSpecialization());
        assertEquals("Unknown", blankDoctor.getLicenseNumber());
        assertEquals("Unassigned", blankDoctor.getRoomNumber());
        assertTrue(blankDoctor.isAvailable());
    }

    // tests that valid setters update the doctor fields
    @Test
    void validSettersUpdateDoctorFields() {
        doctor.setSpecialization("Neurology");
        doctor.setLicenseNumber("LIC-NEW");
        doctor.setRoomNumber("402B");
        doctor.setAvailable(false);

        assertEquals("Neurology", doctor.getSpecialization());
        assertEquals("LIC-NEW", doctor.getLicenseNumber());
        assertEquals("402B", doctor.getRoomNumber());
        assertFalse(doctor.isAvailable());
    }

    // tests that assigning a patient adds them and updates the patient's doctor
    @Test
    void assignPatientAddsPatientAndSyncsDoctorOnPatient() {
        doctor.assignPatient(patient);

        assertEquals(1, doctor.getPatientCount());
        assertTrue(doctor.hasPatient(patient));
        assertEquals(patient, doctor.getAssignedPatients()[0]);
        assertEquals(doctor, patient.getAssignedDoctor());
    }

    // tests that assigning the same patient twice does not create a duplicate
    @Test
    void assigningSamePatientTwiceDoesNotDuplicatePatient() {
        doctor.assignPatient(patient);
        doctor.assignPatient(patient);

        assertEquals(1, doctor.getPatientCount());
        assertTrue(doctor.hasPatient(patient));
    }

    // tests that removing a patient lowers the count and clears the slot
    @Test
    void removePatientDecreasesPatientCount() {
        doctor.assignPatient(patient);
        doctor.removePatient(patient);

        assertEquals(0, doctor.getPatientCount());
        assertFalse(doctor.hasPatient(patient));
        assertNull(doctor.getAssignedPatients()[0]);
    }

    // tests that diagnosing an assigned patient updates their diagnosis
    @Test
    void diagnoseAssignedPatientUpdatesDiagnosis() {
        doctor.assignPatient(patient);
        doctor.diagnosePatient(patient, "Flu");

        assertEquals("Flu", patient.getDiagnosis());
    }

    // tests that diagnosing an unassigned patient does not change anything
    @Test
    void diagnoseUnassignedPatientDoesNotChangeDiagnosis() {
        doctor.diagnosePatient(patient, "Flu");

        assertEquals("Not diagnosed", patient.getDiagnosis());
    }

    // tests that prescribing medicine updates the assigned patient's prescription
    @Test
    void prescribeMedicineForAssignedPatientUpdatesPrescription() {
        doctor.assignPatient(patient);
        doctor.prescribeMedicine(patient, "Ibuprofen");

        assertEquals("Ibuprofen", patient.getPrescribedMedicine());
    }

    // tests that approving discharge releases an assigned admitted patient
    @Test
    void approveDischargeForAssignedPatientDischargesPatient() {
        doctor.assignPatient(patient);
        patient.admitPatient();

        doctor.approveDischarge(patient);

        assertFalse(patient.isAdmitted());
    }

    // tests that taking an appointment stores it and makes the doctor unavailable
    @Test
    void takeAppointmentStoresAppointmentMarksDoctorUnavailableAndAutoAssignsPatient() {
        Appointment appointment = makeAppointment(patient, doctor, "03/28/2026");

        doctor.takeAppointment(appointment);

        assertEquals(1, doctor.getAppointmentCount());
        assertTrue(doctor.hasAppointment(appointment));
        assertEquals(appointment, doctor.getAppointments()[0]);
        assertFalse(doctor.isAvailable());
        assertEquals("Scheduled!", appointment.getStatus());
        assertEquals(doctor, patient.getAssignedDoctor());
        assertTrue(doctor.hasPatient(patient));
    }

    // tests that taking another appointment while unavailable throws an exception
    @Test
    void takeAppointmentWhenDoctorIsUnavailableThrowsException() {
        Appointment firstAppointment = makeAppointment(patient, doctor, "03/28/2026");
        doctor.takeAppointment(firstAppointment);

        Patient secondPatient = makePatient("P002", "Alex");
        Appointment secondAppointment = makeAppointment(secondPatient, doctor, "03/29/2026");

        assertThrows(DoctorUnavailableException.class, () -> doctor.takeAppointment(secondAppointment));
    }

    // tests that removing an appointment clears it and makes the doctor available again
    @Test
    void removeAppointmentClearsScheduleAndMakesDoctorAvailableAgain() {
        Appointment appointment = makeAppointment(patient, doctor, "03/28/2026");
        doctor.takeAppointment(appointment);

        doctor.removeAppointment(appointment);

        assertEquals(0, doctor.getAppointmentCount());
        assertFalse(doctor.hasAppointment(appointment));
        assertTrue(doctor.isAvailable());
        assertNull(doctor.getAppointments()[0]);
    }

    // tests that performDuties prints the expected doctor task list
    @Test
    void performDutiesPrintsExpectedDoctorResponsibilities() {
        String output = captureOutput(() -> doctor.performDuties());

        assertTrue(output.contains("Doctor Robinavitch is performing duties."));
        assertTrue(output.contains("diagnosing patients"));
        assertTrue(output.contains("prescribing medicine"));
        assertTrue(output.contains("reviewing appointments"));
        assertTrue(output.contains("approving discharges"));
    }

    // tests that the doctor role description matches the specialization
    @Test
    void getRoleDescriptionReturnsDoctorSpecialization() {
        assertEquals("Doctor specializing in Cardiologist.", doctor.getRoleDescription());
    }

    // tests that toString includes the important doctor details
    @Test
    void toStringIncludesDoctorSpecificDetails() {
        String doctorText = doctor.toString();

        assertTrue(doctorText.contains("Employee ID: D001"));
        assertTrue(doctorText.contains("Name: Robinavitch"));
        assertTrue(doctorText.contains("Specialization: Cardiologist"));
        assertTrue(doctorText.contains("License Number: LIC-D001"));
        assertTrue(doctorText.contains("Room Number: ROOM-D001"));
        assertTrue(doctorText.contains("Available: true"));
    }

    // tests that adding more than the max number of patients throws an exception
    @Test
    void assigningMoreThanMaxPatientsThrowsException() {
        for (int i = 0; i < Hospital.MAX_OBJECTS; i++) {
            doctor.assignPatient(makePatient("P" + i, "Patient" + i));
        }

        assertEquals(Hospital.MAX_OBJECTS, doctor.getPatientCount());

        Patient extraPatient = makePatient("P101", "Overflow");
        assertThrows(MaxCapacityException.class, () -> doctor.assignPatient(extraPatient));
    }

    // helper method that captures printed output from console methods
    private String captureOutput(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            action.run();
        } finally {
            System.setOut(originalOut);
        }

        return outputStream.toString();
    }
}
