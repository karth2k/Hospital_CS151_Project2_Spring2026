package hospital;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class NurseTest {

    private Patient makePatient() {
        return new Patient("P001", "John Doe", 25, "Flu");
    }

    private Doctor makeDoctor() {
        return new Doctor(
                "D001",
                "Smith",
                "Cardiology",
                120000.0,
                "Cardiology",
                "LIC123",
                "101A"
        );
    }

    private Nurse makeNurse() {
        return new Nurse(
                "N001",
                "Alice",
                "Emergency",
                85000.0,
                "Night",
                "Ward A"
        );
    }

    @Test
    public void testConstructorAndGetters() {
        Nurse nurse = makeNurse();

        assertEquals("Night", nurse.getShift());
        assertEquals("Ward A", nurse.getWard());
        assertEquals("Alice", nurse.getName());
        assertEquals("Emergency", nurse.getDepartment());
    }

    @Test
    public void testSetShiftWithValidValue() {
        Nurse nurse = makeNurse();

        nurse.setShift("Day");

        assertEquals("Day", nurse.getShift());
    }

    @Test
    public void testSetShiftWithNullDoesNotChangeValue() {
        Nurse nurse = makeNurse();

        nurse.setShift(null);

        assertEquals("Night", nurse.getShift());
    }

    @Test
    public void testSetShiftWithBlankDoesNotChangeValue() {
        Nurse nurse = makeNurse();

        nurse.setShift("   ");

        assertEquals("Night", nurse.getShift());
    }

    @Test
    public void testAssignWardWithValidValue() {
        Nurse nurse = makeNurse();

        nurse.assignWard("Ward B");

        assertEquals("Ward B", nurse.getWard());
    }

    @Test
    public void testAssignWardWithNullDoesNotChangeValue() {
        Nurse nurse = makeNurse();

        nurse.assignWard(null);

        assertEquals("Ward A", nurse.getWard());
    }

    @Test
    public void testAssignWardWithBlankDoesNotChangeValue() {
        Nurse nurse = makeNurse();

        nurse.assignWard("   ");

        assertEquals("Ward A", nurse.getWard());
    }

    @Test
    public void testCheckVitalsPrintsExpectedOutput() {
        Nurse nurse = makeNurse();
        Patient patient = makePatient();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            nurse.checkVitals(patient);
        } finally {
            System.setOut(originalOut);
        }

        String printed = output.toString();
        assertTrue(printed.contains("Nurse Alice is checking vitals for patient John Doe"));
        assertTrue(printed.contains("Vitals are stable."));
    }

    @Test
    public void testAssistDoctorPrintsExpectedOutput() {
        Nurse nurse = makeNurse();
        Doctor doctor = makeDoctor();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            nurse.assistDoctor(doctor);
        } finally {
            System.setOut(originalOut);
        }

        String printed = output.toString();
        assertTrue(printed.contains("Nurse Alice assists Doctor Smith with procedure"));
        assertTrue(printed.contains("Doctor successfully assisted!"));
    }

    @Test
    public void testAdministerMedicinePrintsExpectedOutput() {
        Nurse nurse = makeNurse();
        Patient patient = makePatient();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            nurse.administerMedicine(patient, "Ibuprofen");
        } finally {
            System.setOut(originalOut);
        }

        String printed = output.toString();
        assertTrue(printed.contains("Nurse Alice administers Ibuprofen for patient John Doe"));
    }

    @Test
    public void testViewStaffInfoPrintsShiftAndWard() {
        Nurse nurse = makeNurse();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            nurse.viewStaffInfo();
        } finally {
            System.setOut(originalOut);
        }

        String printed = output.toString();
        assertTrue(printed.contains("Shift: Night"));
        assertTrue(printed.contains("Ward: Ward A"));
    }

    @Test
    public void testPerformDutiesPrintsExpectedLines() {
        Nurse nurse = makeNurse();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            nurse.performDuties();
        } finally {
            System.setOut(originalOut);
        }

        String printed = output.toString();
        assertTrue(printed.contains("Nurse Alice is performing duties:"));
        assertTrue(printed.contains("Checking patient vitals"));
        assertTrue(printed.contains("Administering medication"));
        assertTrue(printed.contains("Assisting doctor with procedures"));
        assertTrue(printed.contains("Monitoring patient conditions"));
    }

    @Test
    public void testGetRoleDescription() {
        Nurse nurse = makeNurse();

        assertEquals(
                "Nurse responsible for monitoring patients, administering medication, and assisting doctors.",
                nurse.getRoleDescription()
        );
    }

    @Test
    public void testSetShiftWithTrimmedInputIsNotTrimmedInCurrentCode() {
        Nurse nurse = makeNurse();

        nurse.setShift("  Day  ");

        assertEquals("  Day  ", nurse.getShift());
    }

    @Test
    public void testAssignWardWithTrimmedInputIsNotTrimmedInCurrentCode() {
        Nurse nurse = makeNurse();

        nurse.assignWard("  Ward C  ");

        assertEquals("  Ward C  ", nurse.getWard());
    }

    @Test
    public void testCheckVitalsWithNullPatientThrowsException() {
        Nurse nurse = makeNurse();

        assertThrows(NullPointerException.class, () -> nurse.checkVitals(null));
    }

    @Test
    public void testAssistDoctorWithNullDoctorThrowsException() {
        Nurse nurse = makeNurse();

        assertThrows(NullPointerException.class, () -> nurse.assistDoctor(null));
    }

    @Test
    public void testAdministerMedicineWithNullPatientThrowsException() {
        Nurse nurse = makeNurse();

        assertThrows(NullPointerException.class, () -> nurse.administerMedicine(null, "Ibuprofen"));
    }
}
