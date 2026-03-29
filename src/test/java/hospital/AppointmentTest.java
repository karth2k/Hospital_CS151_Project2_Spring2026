package hospital;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentTest {

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

    @Test
    public void testConstructorInitialValues() {
        Patient patient = makePatient();
        Doctor doctor = makeDoctor();

        Appointment appointment = new Appointment(patient, doctor, "2026-03-29");

        assertEquals(patient, appointment.getPatient());
        assertEquals(doctor, appointment.getDoctor());
        assertEquals("2026-03-29", appointment.getDate());
        assertEquals("Pending", appointment.getStatus());
        assertTrue(appointment.getAppointmentId() > 0);
    }

    @Test
    public void testAppointmentIdsIncrease() {
        Appointment a1 = new Appointment(makePatient(), makeDoctor(), "2026-03-29");
        Appointment a2 = new Appointment(makePatient(), makeDoctor(), "2026-03-30");

        assertTrue(a2.getAppointmentId() > a1.getAppointmentId());
    }

    @Test
    public void testScheduleChangesStatus() {
        Appointment appointment = new Appointment(makePatient(), makeDoctor(), "2026-03-29");

        appointment.schedule();

        assertEquals("Scheduled!", appointment.getStatus());
    }

    @Test
    public void testCancelChangesStatus() {
        Appointment appointment = new Appointment(makePatient(), makeDoctor(), "2026-03-29");

        appointment.cancel();

        assertEquals("Canceled", appointment.getStatus());
    }

    @Test
    public void testCompleteChangesStatus() {
        Appointment appointment = new Appointment(makePatient(), makeDoctor(), "2026-03-29");

        appointment.complete();

        assertEquals("Completed", appointment.getStatus());
    }

    @Test
    public void testRescheduleChangesDate() {
        Appointment appointment = new Appointment(makePatient(), makeDoctor(), "2026-03-29");

        appointment.reschedule("2026-04-02");

        assertEquals("2026-04-02", appointment.getDate());
    }

    @Test
    public void testRescheduleAllowsEmptyStringBecauseNoValidationExists() {
        Appointment appointment = new Appointment(makePatient(), makeDoctor(), "2026-03-29");

        appointment.reschedule("");

        assertEquals("", appointment.getDate());
    }

    @Test
    public void testRescheduleAllowsNullBecauseNoValidationExists() {
        Appointment appointment = new Appointment(makePatient(), makeDoctor(), "2026-03-29");

        appointment.reschedule(null);

        assertNull(appointment.getDate());
    }

    @Test
    public void testToStringContainsImportantFields() {
        Appointment appointment = new Appointment(makePatient(), makeDoctor(), "2026-03-29");

        String result = appointment.toString();

        assertTrue(result.contains("Appointment ID:"));
        assertTrue(result.contains("Date: 2026-03-29"));
        assertTrue(result.contains("Status: Pending"));
    }

    @Test
    public void testViewDetailsPrintsExpectedInfo() {
        Appointment appointment = new Appointment(makePatient(), makeDoctor(), "2026-03-29");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            appointment.viewDetails();
        } finally {
            System.setOut(originalOut);
        }

        String printed = output.toString();
        assertTrue(printed.contains("Appointment Id:"));
        assertTrue(printed.contains("Date: 2026-03-29"));
        assertTrue(printed.contains("Status: Pending"));
        assertTrue(printed.contains("Patient:"));
        assertTrue(printed.contains("Doctor:"));
    }

    @Test
    public void testSchedulePrintsExpectedMessage() {
        Appointment appointment = new Appointment(makePatient(), makeDoctor(), "2026-03-29");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            appointment.schedule();
        } finally {
            System.setOut(originalOut);
        }

        String printed = output.toString();
        assertTrue(printed.contains("Appointment"));
        assertTrue(printed.contains("scheduled for John Doe"));
        assertTrue(printed.contains("with Dr. Smith"));
        assertTrue(printed.contains("on 2026-03-29"));
    }

    @Test
    public void testCancelPrintsExpectedMessage() {
        Appointment appointment = new Appointment(makePatient(), makeDoctor(), "2026-03-29");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            appointment.cancel();
        } finally {
            System.setOut(originalOut);
        }

        String printed = output.toString();
        assertTrue(printed.contains("has been canceled!"));
        assertTrue(printed.contains("John Doe"));
        assertTrue(printed.contains("Smith"));
    }

    @Test
    public void testCompletePrintsExpectedMessage() {
        Appointment appointment = new Appointment(makePatient(), makeDoctor(), "2026-03-29");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            appointment.complete();
        } finally {
            System.setOut(originalOut);
        }

        String printed = output.toString();
        assertTrue(printed.contains("has been completed"));
    }
}
