package hospital;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    /*********************************************
     * author: Sean Tadina
     * id: 018950802
     * username: @seantadina
     *********************************************/

    // runs the real Main program in a separate process so each test gets a fresh scanner and fresh state
    private String runProgram(String input) throws IOException, InterruptedException {
        String javaCommand = Paths.get(
                System.getProperty("java.home"),
                "bin",
                System.getProperty("os.name").toLowerCase().contains("win") ? "java.exe" : "java"
        ).toString();

        ProcessBuilder builder = new ProcessBuilder(
                javaCommand,
                "-cp",
                System.getProperty("java.class.path"),
                "hospital.Main"
        );

        builder.redirectErrorStream(true);
        Process process = builder.start();

        try (OutputStream outputStream = process.getOutputStream()) {
            outputStream.write(input.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }

        boolean finished = process.waitFor(10, TimeUnit.SECONDS);
        if (!finished) {
            process.destroyForcibly();
            fail("Main program did not finish in time.");
        }

        String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8)
                .replace("\r\n", "\n");

        assertEquals(0, process.exitValue());
        return output;
    }

    // tests that pressing enter for both startup prompts uses the default names and loads sample data
    @Test
    void startupWithDefaultsLoadsSampleDataAndSummary() throws Exception {
        String output = runProgram("\n\n6\nexit\n");

        assertTrue(output.contains("Hospital system started for CS151 General Hospital."));
        assertTrue(output.contains("Patient count: 4"));
        assertTrue(output.contains("Doctor count: 1"));
        assertTrue(output.contains("Nurse count: 1"));
        assertTrue(output.contains("Appointment count: 1"));
    }

    // tests that custom startup names are kept in the running system
    @Test
    void startupWithCustomNamesUsesProvidedHospitalAndPharmacy() throws Exception {
        String output = runProgram("Pittsburgh Hospital\nRX-321\n6\nexit\n");

        assertTrue(output.contains("Hospital system started for Pittsburgh Hospital."));
        assertTrue(output.contains("Hospital Name: Pittsburgh Hospital"));
        assertTrue(output.contains("Pharmacy{id='RX-321'"));
    }

    // tests that typing back at the very first prompt does not crash and shows the correct message
    @Test
    void backAtBeginningShowsRootMessage() throws Exception {
        String output = runProgram("back\n\n\nexit\n");

        assertTrue(output.contains("You are already at the beginning."));
        assertTrue(output.contains("Hospital system started for CS151 General Hospital."));
    }

    // tests that typing back at the pharmacy prompt returns to the hospital name prompt
    @Test
    void backAtPharmacyPromptReturnsToHospitalNamePrompt() throws Exception {
        String output = runProgram("Wrong Hospital\nback\nPittsburgh Hospital\nCVS-777\nexit\n");

        assertTrue(output.contains("Going back."));
        assertTrue(output.contains("Hospital system started for Pittsburgh Hospital."));
    }

    // tests that a non integer main menu input prints the integer validation message
    @Test
    void nonIntegerMainMenuChoiceShowsValidationMessage() throws Exception {
        String output = runProgram("\n\nabc\nexit\n");

        assertTrue(output.contains("Invalid option. Please enter an integer."));
    }

    // tests that an out of range main menu number reaches the default invalid option branch
    @Test
    void outOfRangeMainMenuChoiceShowsInvalidOptionMessage() throws Exception {
        String output = runProgram("\n\n99\nexit\n");

        assertTrue(output.contains("Invalid option. Try again."));
    }

    // tests that patient registration retries after a bad age and then succeeds once valid input is given
    @Test
    void registerPatientRetriesOnInvalidAgeThenAddsPatient() throws Exception {
        String output = runProgram("\n\n1\n1\np123\nJohn Doe\n0\n30\nFlu\nback\n6\nexit\n");

        assertTrue(output.contains("Please enter a whole number greater than 0. Try again."));
        assertTrue(output.contains("Patient John Doe added to CS151 General Hospital."));
        assertTrue(output.contains("Patient count: 5"));
    }

    // tests that using back during patient registration cancels the action and keeps sample counts unchanged
    @Test
    void backDuringPatientRegistrationCancelsAdd() throws Exception {
        String output = runProgram("\n\n1\n1\nback\nback\n6\nexit\n");

        assertTrue(output.contains("Going back."));
        assertTrue(output.contains("Patient count: 4"));
    }

    // tests that the sample doctor cannot be removed because the doctor already has patients and an appointment
    @Test
    void removeDoctorFailsWhenDoctorStillHasAssignedWork() throws Exception {
        String output = runProgram("\n\n2\n2\ntelvin.zhong\nback\nexit\n");

        assertTrue(output.contains("Cannot remove Dr. Telvin Zhong because they still have assigned patients or scheduled appointments."));
    }

    // tests that typing exit from a nested input prompt shuts the whole program down immediately
    @Test
    void exitCommandWorksFromNestedInputPrompt() throws Exception {
        String output = runProgram("\n\n1\n1\nexit\n");

        assertTrue(output.contains("Enter patient id:"));
        assertTrue(output.trim().endsWith("program shut down."));
    }
}
