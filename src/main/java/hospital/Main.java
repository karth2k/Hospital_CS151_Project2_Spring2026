package hospital;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    // one scanner is used for the whole program
    private static final Scanner scanner = new Scanner(System.in);

    // this stores the hospital system the user will interact with
    private static Hospital hospital;

    // this keeps track of whether the whole program should stay open
    private static boolean programRunning = true;

    // these are the special commands the user can type at any prompt
    private static final String EXIT_COMMAND = "exit";
    private static final String BACK_COMMAND = "back";

    // this keeps the menu footer message consistent
    private static final String MENU_EXIT_MESSAGE = "TYPE EXIT TO LEAVE AT ANY POINT";
    private static final String MENU_BACK_MESSAGE = "TYPE BACK TO GO BACK";

    // this is used to stop the program immediately from any prompt
    private static class ProgramExitException extends RuntimeException {
    }

    // this is used to go back from an input prompt to the previous menu
    private static class BackInputException extends RuntimeException {
    }

    public static void main(String[] args) {
        try {
            setupHospital();
        } catch (ProgramExitException e) {
            // the shutdown already happens when the exception is thrown
        } finally {
            scanner.close();
            System.out.println("program shut down.");
        }
    }

    // asks the user for startup information and creates the hospital system
    private static void setupHospital() {
        System.out.println("=== Hospital Management System ===");
        System.out.println(MENU_EXIT_MESSAGE);
        System.out.println(MENU_BACK_MESSAGE);

        while (true) {
            System.out.print("Enter hospital name (or press enter for default): ");
            String hospitalInput = scanner.nextLine().trim();

            if (hospitalInput.equalsIgnoreCase(EXIT_COMMAND)) {
                shutdownProgram();
            }
            if (hospitalInput.equalsIgnoreCase(BACK_COMMAND)) {
                System.out.println("You are already at the beginning.");
                continue;
            }

            String hospitalName = hospitalInput.isEmpty() ? "CS151 General Hospital" : hospitalInput;

            while (true) {
                System.out.print("Enter pharmacy id (or press enter for default): ");
                String pharmacyInput = scanner.nextLine().trim();

                if (pharmacyInput.equalsIgnoreCase(EXIT_COMMAND)) {
                    shutdownProgram();
                }
                if (pharmacyInput.equalsIgnoreCase(BACK_COMMAND)) {
                    System.out.println("Going back.");
                    break;
                }

                String pharmacyId = pharmacyInput.isEmpty() ? "CVS-001" : pharmacyInput;
                hospital = new Hospital(hospitalName, new Pharmacy(pharmacyId));
                preloadSampleData();

                System.out.println();
                System.out.println("Hospital system started for " + hospital.getHospitalName() + ".");
                System.out.println("sample data has been loaded for quick testing.");
                System.out.println();
                System.out.println("main menu is the next part to add.");
                return;
            }
        }
    }

    // adds sample patients, staff, bills, medicine, and one sample appointment
    private static void preloadSampleData() {
        runSilently(() -> {
            Doctor telvin = new Doctor(
                    "telvin.zhong",
                    "Telvin Zhong",
                    "Emergency Room",
                    1000000.00,
                    "Emergency Medicine",
                    "2D1234A",
                    "151"
            );

            Nurse dana = new Nurse(
                    "dana.evans",
                    "Dana Evans",
                    "Emergency Room",
                    250000.00,
                    "06:00 - 18:00",
                    "Surgical floor"
            );

            Patient karthik = new Patient("karthik.muthukumar", "Karthik Muthukumar", 21, "critical");
            Patient david = new Patient("david.ayala", "David Ayala", 21, "Good");
            Patient qingheng = new Patient("qingheng.fang", "Qingheng Fang", 21, "Undetermined");
            Patient sean = new Patient("sean.tadina", "Sean Tadina", 21, "stable");

            hospital.addDoctor(telvin);
            hospital.addNurse(dana);
            hospital.addPatient(karthik);
            hospital.addPatient(david);
            hospital.addPatient(qingheng);
            hospital.addPatient(sean);

            hospital.assignDoctorToPatient("karthik.muthukumar", "telvin.zhong");
            hospital.assignDoctorToPatient("david.ayala", "telvin.zhong");
            hospital.assignDoctorToPatient("qingheng.fang", "telvin.zhong");
            hospital.assignDoctorToPatient("sean.tadina", "telvin.zhong");

            karthik.addCharge(1250.00);
            david.addCharge(300.00);
            qingheng.addCharge(450.00);
            sean.addCharge(175.00);

            hospital.getPharmacy().restockMedicine("Ibuprofen", 25, 14.99);
            hospital.getPharmacy().restockMedicine("Amoxicillin", 20, 24.99);
            hospital.getPharmacy().restockMedicine("Acetaminophen", 30, 9.99);
            hospital.getPharmacy().restockMedicine("Bandages", 40, 4.50);

            hospital.scheduleAppointment(karthik, telvin, "04/15/2026");
        });
    }

    // shuts down the whole program
    private static void shutdownProgram() {
        programRunning = false;
        throw new ProgramExitException();
    }

    // runs noisy methods quietly when they print ugly startup messages
    private static void runSilently(Runnable action) {
        PrintStream originalOut = System.out;
        try {
            System.setOut(new PrintStream(new ByteArrayOutputStream()));
            action.run();
        } finally {
            System.setOut(originalOut);
        }
    }
}
