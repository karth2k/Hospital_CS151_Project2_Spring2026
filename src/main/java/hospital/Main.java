package hospital;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Scanner;

public class Main {
    /*********************************************
     * author: Sean Tadina
     * id: 018950802
     * username: @seantadina
     *********************************************/

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

    // this reminds the user what special input commands they can use
    private static final String INPUT_HELP_MESSAGE = "(type back to go back or exit to close program)";

    // this is the date format used for appointments
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd/uuuu").withResolverStyle(ResolverStyle.STRICT);

    // this is used to stop the program immediately from any prompt
    private static class ProgramExitException extends RuntimeException {
    }

    // this is used to go back from an input prompt to the previous menu
    private static class BackInputException extends RuntimeException {
    }

    public static void main(String[] args) {
        try {
            setupHospital();
            if (programRunning) {
                runMenu();
            }
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

            // this creates one sample appointment for quick testing
            hospital.scheduleAppointment(karthik, telvin, "04/15/2026");
        });
    }

    // keeps showing the main menu until the user exits
    private static void runMenu() {
        while (programRunning) {
            printMainMenu();
            int choice = readMenuChoice("Choose an option: ", false);
            System.out.println();

            switch (choice) {
                case 1:
                    patientMenu();
                    break;
                case 2:
                    doctorMenu();
                    break;
                case 3:
                    nurseMenu();
                    break;
                case 4:
                    appointmentMenu();
                    break;
                case 5:
                    pharmacyMenu();
                    break;
                case 6:
                    viewHospitalSummary();
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }

            System.out.println();
        }
    }

    // prints the main menu choices
    private static void printMainMenu() {
        System.out.println("=== Main Menu ===");
        System.out.println("1. Patient Menu");
        System.out.println("2. Doctor Menu");
        System.out.println("3. Nurse Menu");
        System.out.println("4. Appointment Menu");
        System.out.println("5. Pharmacy Menu");
        System.out.println("6. View Hospital Summary");
        System.out.println(MENU_EXIT_MESSAGE);
    }

    // creates a new patient and adds them to the hospital
    private static void registerPatient() {
        try {
            String patientId = readRequiredString("Enter patient id: ");
            String name = readRequiredString("Enter patient name: ");
            int age = readPositiveInt("Enter patient age: ");
            String condition = readRequiredString("Enter patient condition: ");

            Patient patient = new Patient(patientId, name, age, condition);
            hospital.addPatient(patient);
        } catch (BackInputException e) {
            System.out.println("Going back.");
        } catch (MaxCapacityException e) {
            System.out.println(e.getMessage());
        }
    }

    // removes a patient by id
    private static void removePatient() {
        try {
            String patientId = readRequiredString("Enter patient id to remove: ");
            hospital.removePatient(patientId);
        } catch (BackInputException e) {
            System.out.println("Going back.");
        } catch (PatientNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // creates a doctor and adds them to the hospital
    private static void addDoctor() {
        try {
            String employeeId = readRequiredString("Enter doctor employee id: ");
            String name = readRequiredString("Enter doctor name: ");
            String department = readRequiredString("Enter department: ");
            double salary = readMoneyAmount("Enter salary with two decimal places (example 1000.00): ");
            String specialization = readRequiredString("Enter specialization: ");
            String licenseNumber = readRequiredString("Enter license number: ");
            String roomNumber = readRequiredString("Enter room number: ");

            Doctor doctor = new Doctor(employeeId, name, department, salary,
                    specialization, licenseNumber, roomNumber);
            hospital.addDoctor(doctor);
        } catch (BackInputException e) {
            System.out.println("Going back.");
        } catch (MaxCapacityException e) {
            System.out.println(e.getMessage());
        }
    }

    // removes a doctor by id
    private static void removeDoctor() {
        try {
            String doctorId = readRequiredString("Enter doctor id to remove: ");
            hospital.removeDoctor(doctorId);
        } catch (BackInputException e) {
            System.out.println("Going back.");
        }
    }

    // creates a nurse and adds them to the hospital
    private static void addNurse() {
        try {
            String employeeId = readRequiredString("Enter nurse employee id: ");
            String name = readRequiredString("Enter nurse name: ");
            String department = readRequiredString("Enter department: ");
            double salary = readMoneyAmount("Enter salary with two decimal places (example 1000.00): ");
            String shiftStart = readShiftTime("Enter shift start time in 24-Hour time (XX:XX): ");
            String shiftEnd = readShiftTime("Enter shift end time in 24-Hour time (XX:XX): ");
            String ward = readRequiredString("Enter ward: ");

            Nurse nurse = new Nurse(employeeId, name, department, salary,
                    shiftStart + " - " + shiftEnd, ward);
            hospital.addNurse(nurse);
        } catch (BackInputException e) {
            System.out.println("Going back.");
        } catch (MaxCapacityException e) {
            System.out.println(e.getMessage());
        }
    }

    // removes a nurse by id
    private static void removeNurse() {
        try {
            String nurseId = readRequiredString("Enter nurse id to remove: ");
            hospital.removeNurse(nurseId);
        } catch (BackInputException e) {
            System.out.println("Going back.");
        }
    }

    // schedules a new appointment for a patient and doctor
    private static void scheduleAppointment() {
        try {
            Patient patient = promptForPatient();
            Doctor doctor = promptForDoctor();
            String date = readDate("Enter appointment date (MM/DD/YYYY): ");
            int previousAppointmentCount = hospital.getAppointmentCount();

            runSilently(() -> hospital.scheduleAppointment(patient, doctor, date));

            if (hospital.getAppointmentCount() > previousAppointmentCount) {
                Appointment latestAppointment = hospital.getAppointments()[hospital.getAppointmentCount() - 1];
                System.out.println("Appointment " + latestAppointment.getAppointmentId() + " scheduled for "
                        + patient.getName() + " with Dr. " + doctor.getName() + " on " + date + ".");
            }
        } catch (BackInputException e) {
            System.out.println("Going back.");
        } catch (PatientNotFoundException | DoctorUnavailableException | MaxCapacityException e) {
            System.out.println(e.getMessage());
        }
    }

    // shows the appointment submenu
    private static void appointmentMenu() {
        boolean back = false;

        while (programRunning && !back) {
            printAppointmentMenu();
            int choice = readMenuChoice("Choose an option: ", true);
            System.out.println();

            if (choice == -1) {
                back = true;
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        scheduleAppointment();
                        break;
                    case 2:
                        hospital.displayAllAppointments();
                        break;
                    case 3:
                        hospital.cancelAppointment(readPositiveInt("Enter appointment id: "));
                        break;
                    case 4:
                        int rescheduleId = readPositiveInt("Enter appointment id: ");
                        String newDate = readDate("Enter new date (MM/DD/YYYY): ");
                        hospital.rescheduleAppointment(rescheduleId, newDate);
                        break;
                    case 5:
                        hospital.completeAppointment(readPositiveInt("Enter appointment id: "));
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (BackInputException e) {
                System.out.println("Going back.");
            }

            System.out.println();
        }
    }

    // prints the appointment menu
    private static void printAppointmentMenu() {
        System.out.println("=== Appointment Menu ===");
        System.out.println("1. Schedule Appointment");
        System.out.println("2. View All Appointments");
        System.out.println("3. Cancel Appointment");
        System.out.println("4. Reschedule Appointment");
        System.out.println("5. Complete Appointment");
        System.out.println(MENU_BACK_MESSAGE);
        System.out.println(MENU_EXIT_MESSAGE);
    }

    // shows the patient submenu
    private static void patientMenu() {
        boolean back = false;

        while (programRunning && !back) {
            printPatientMenu();
            int choice = readMenuChoice("Choose an option: ", true);
            System.out.println();

            if (choice == -1) {
                back = true;
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        registerPatient();
                        break;
                    case 2:
                        removePatient();
                        break;
                    case 3:
                        scheduleAppointment();
                        break;
                    case 4:
                        hospital.admitPatient(readRequiredString("Enter patient id: "));
                        break;
                    case 5:
                        hospital.dischargePatient(readRequiredString("Enter patient id: "));
                        break;
                    case 6:
                        String patientId = readRequiredString("Enter patient id: ");
                        String doctorId = readRequiredString("Enter doctor id: ");
                        hospital.assignDoctorToPatient(patientId, doctorId);
                        break;
                    case 7:
                        String payPatientId = readRequiredString("Enter patient id: ");
                        double amount = readMoneyAmount("Enter payment amount with two decimal places (example 100.00): ");
                        hospital.payPatientBill(payPatientId, amount);
                        break;
                    case 8:
                        Patient patient = promptForPatient();
                        patient.viewPatientInfo();
                        break;
                    case 9:
                        hospital.displayAllPatients();
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (BackInputException e) {
                System.out.println("Going back.");
            } catch (PatientNotFoundException e) {
                System.out.println(e.getMessage());
            }

            System.out.println();
        }
    }

    // prints the patient menu
    private static void printPatientMenu() {
        System.out.println("=== Patient Menu ===");
        System.out.println("1. Register Patient");
        System.out.println("2. Remove Patient");
        System.out.println("3. Schedule Appointment");
        System.out.println("4. Admit Patient");
        System.out.println("5. Discharge Patient");
        System.out.println("6. Assign Doctor To Patient");
        System.out.println("7. Pay Bill");
        System.out.println("8. View One Patient");
        System.out.println("9. View All Patients");
        System.out.println(MENU_BACK_MESSAGE);
        System.out.println(MENU_EXIT_MESSAGE);
    }

    // shows the doctor submenu
    private static void doctorMenu() {
        boolean back = false;

        while (programRunning && !back) {
            printDoctorMenu();
            int choice = readMenuChoice("Choose an option: ", true);
            System.out.println();

            if (choice == -1) {
                back = true;
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        addDoctor();
                        break;
                    case 2:
                        removeDoctor();
                        break;
                    case 3:
                        scheduleAppointment();
                        break;
                    case 4:
                        Doctor viewDoctor = promptForDoctor();
                        viewDoctor.viewStaffInfo();
                        break;
                    case 5:
                        hospital.displayAllDoctors();
                        break;
                    case 6:
                        handleDoctorDiagnosis();
                        break;
                    case 7:
                        handleDoctorPrescription();
                        break;
                    case 8:
                        handleDoctorDischargeApproval();
                        break;
                    case 9:
                        Doctor clockInDoctor = promptForDoctor();
                        clockInDoctor.clockIn();
                        break;
                    case 10:
                        Doctor clockOutDoctor = promptForDoctor();
                        clockOutDoctor.clockOut();
                        break;
                    case 11:
                        Doctor salaryDoctor = promptForDoctor();
                        salaryDoctor.updateSalary(readMoneyAmount("Enter new salary with two decimal places (example 1000.00): "));
                        break;
                    case 12:
                        Doctor assignDeptDoctor = promptForDoctor();
                        assignDeptDoctor.assignDepartment(readRequiredString("Enter department: "));
                        break;
                    case 13:
                        Doctor transferDoctor = promptForDoctor();
                        transferDoctor.transferDepartment(readRequiredString("Enter new department: "));
                        break;
                    case 14:
                        Doctor dutiesDoctor = promptForDoctor();
                        dutiesDoctor.performDuties();
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (BackInputException e) {
                System.out.println("Going back.");
            } catch (PatientNotFoundException e) {
                System.out.println(e.getMessage());
            }

            System.out.println();
        }
    }

    // prints the doctor menu
    private static void printDoctorMenu() {
        System.out.println("=== Doctor Menu ===");
        System.out.println("1. Add Doctor");
        System.out.println("2. Remove Doctor");
        System.out.println("3. Schedule Appointment");
        System.out.println("4. View One Doctor");
        System.out.println("5. View All Doctors");
        System.out.println("6. Diagnose Patient");
        System.out.println("7. Prescribe Medicine");
        System.out.println("8. Approve Discharge");
        System.out.println("9. Clock In");
        System.out.println("10. Clock Out");
        System.out.println("11. Update Salary");
        System.out.println("12. Assign Department");
        System.out.println("13. Transfer Department");
        System.out.println("14. Perform Duties");
        System.out.println(MENU_BACK_MESSAGE);
        System.out.println(MENU_EXIT_MESSAGE);
    }

    // shows the nurse submenu
    private static void nurseMenu() {
        boolean back = false;

        while (programRunning && !back) {
            printNurseMenu();
            int choice = readMenuChoice("Choose an option: ", true);
            System.out.println();

            if (choice == -1) {
                back = true;
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        addNurse();
                        break;
                    case 2:
                        removeNurse();
                        break;
                    case 3:
                        Nurse viewNurse = promptForNurse();
                        viewNurse.viewStaffInfo();
                        break;
                    case 4:
                        hospital.displayAllNurses();
                        break;
                    case 5:
                        Nurse vitalsNurse = promptForNurse();
                        Patient vitalsPatient = promptForPatient();
                        vitalsNurse.checkVitals(vitalsPatient);
                        break;
                    case 6:
                        Nurse assistNurse = promptForNurse();
                        Doctor assistDoctor = promptForDoctor();
                        assistNurse.assistDoctor(assistDoctor);
                        break;
                    case 7:
                        handleNurseMedicine();
                        break;
                    case 8:
                        Nurse wardNurse = promptForNurse();
                        wardNurse.assignWard(readRequiredString("Enter new ward: "));
                        break;
                    case 9:
                        Nurse clockInNurse = promptForNurse();
                        clockInNurse.clockIn();
                        break;
                    case 10:
                        Nurse clockOutNurse = promptForNurse();
                        clockOutNurse.clockOut();
                        break;
                    case 11:
                        Nurse salaryNurse = promptForNurse();
                        salaryNurse.updateSalary(readMoneyAmount("Enter new salary with two decimal places (example 1000.00): "));
                        break;
                    case 12:
                        Nurse assignDeptNurse = promptForNurse();
                        assignDeptNurse.assignDepartment(readRequiredString("Enter department: "));
                        break;
                    case 13:
                        Nurse transferNurse = promptForNurse();
                        transferNurse.transferDepartment(readRequiredString("Enter new department: "));
                        break;
                    case 14:
                        Nurse dutiesNurse = promptForNurse();
                        dutiesNurse.performDuties();
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (BackInputException e) {
                System.out.println("Going back.");
            }

            System.out.println();
        }
    }

    // prints the nurse menu
    private static void printNurseMenu() {
        System.out.println("=== Nurse Menu ===");
        System.out.println("1. Add Nurse");
        System.out.println("2. Remove Nurse");
        System.out.println("3. View One Nurse");
        System.out.println("4. View All Nurses");
        System.out.println("5. Check Patient Vitals");
        System.out.println("6. Assist Doctor");
        System.out.println("7. Administer Medicine");
        System.out.println("8. Assign Ward");
        System.out.println("9. Clock In");
        System.out.println("10. Clock Out");
        System.out.println("11. Update Salary");
        System.out.println("12. Assign Department");
        System.out.println("13. Transfer Department");
        System.out.println("14. Perform Duties");
        System.out.println(MENU_BACK_MESSAGE);
        System.out.println(MENU_EXIT_MESSAGE);
    }

    // shows the pharmacy submenu
    private static void pharmacyMenu() {
        boolean back = false;

        while (programRunning && !back) {
            printPharmacyMenu();
            int choice = readMenuChoice("Choose an option: ", true);
            System.out.println();

            if (choice == -1) {
                back = true;
                continue;
            }

            try {
                switch (choice) {
                    case 1:
                        hospital.getPharmacy().viewInventory();
                        break;
                    case 2:
                        String medicine = readRequiredString("Enter medicine name: ");
                        int quantity = readPositiveInt("Enter quantity: ");
                        double price = readMoneyAmount("Enter unit price with two decimal places (example 10.00): ");
                        hospital.getPharmacy().restockMedicine(medicine, quantity, price);
                        break;
                    case 3:
                        Patient patient = promptForPatient();
                        String medicineToDispense = readRequiredString("Enter medicine name: ");
                        hospital.getPharmacy().dispenseMedicine(patient, medicineToDispense);
                        break;
                    case 4:
                        String medicineToCheck = readRequiredString("Enter medicine name: ");
                        boolean available = hospital.getPharmacy().checkAvailability(medicineToCheck);
                        System.out.println("Available: " + available);
                        break;
                    case 5:
                        String medicineToRemove = readRequiredString("Enter medicine name: ");
                        hospital.getPharmacy().removeMedicine(medicineToRemove);
                        break;
                    case 6:
                        System.out.println(hospital.getPharmacy());
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            } catch (BackInputException e) {
                System.out.println("Going back.");
            } catch (MaxCapacityException | OutOfStockException | IllegalArgumentException | PatientNotFoundException e) {
                System.out.println(e.getMessage());
            }

            System.out.println();
        }
    }

    // prints the pharmacy menu
    private static void printPharmacyMenu() {
        System.out.println("=== Pharmacy Menu ===");
        System.out.println("1. View Inventory");
        System.out.println("2. Restock Medicine");
        System.out.println("3. Dispense Medicine");
        System.out.println("4. Check Availability");
        System.out.println("5. Remove Medicine");
        System.out.println("6. View Pharmacy Summary");
        System.out.println(MENU_BACK_MESSAGE);
        System.out.println(MENU_EXIT_MESSAGE);
    }

    // lets a doctor enter a diagnosis for a patient
    private static void handleDoctorDiagnosis() {
        Doctor doctor = promptForDoctor();
        Patient patient = promptForPatient();
        String diagnosis = readRequiredString("Enter diagnosis: ");
        doctor.diagnosePatient(patient, diagnosis);
    }

    // lets a doctor prescribe medicine and optionally dispense it
    private static void handleDoctorPrescription() {
        Doctor doctor = promptForDoctor();
        Patient patient = promptForPatient();
        String medicine = readRequiredString("Enter medicine name: ");

        doctor.prescribeMedicine(patient, medicine);

        String choice = readRequiredString("Dispense medicine from pharmacy now? (yes/no): ");
        if (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y")) {
            try {
                hospital.getPharmacy().dispenseMedicine(patient, medicine);
            } catch (OutOfStockException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // lets a doctor approve a patient discharge
    private static void handleDoctorDischargeApproval() {
        Doctor doctor = promptForDoctor();
        Patient patient = promptForPatient();
        doctor.approveDischarge(patient);
    }

    // lets a nurse administer medicine if it exists in the pharmacy
    private static void handleNurseMedicine() {
        Nurse nurse = promptForNurse();
        Patient patient = promptForPatient();
        String medicine = readRequiredString("Enter medicine name: ");

        try {
            hospital.getPharmacy().dispenseMedicine(patient, medicine);
            nurse.administerMedicine(patient, medicine);
        } catch (OutOfStockException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // shows a quick summary of the hospital system
    private static void viewHospitalSummary() {
        System.out.println(hospital);
        System.out.println("Patient count: " + hospital.getPatientCount());
        System.out.println("Doctor count: " + hospital.getDoctorCount());
        System.out.println("Nurse count: " + hospital.getNurseCount());
        System.out.println("Appointment count: " + hospital.getAppointmentCount());
    }

    // keeps asking for a patient id until a valid patient is found or the user goes back
    private static Patient promptForPatient() {
        while (true) {
            String patientId = readRequiredString("Enter patient id: ");
            try {
                return hospital.findPatient(patientId);
            } catch (PatientNotFoundException e) {
                System.out.println(e.getMessage());
                System.out.println("Try again.");
            }
        }
    }

    // keeps asking for a doctor id until a valid doctor is found or the user goes back
    private static Doctor promptForDoctor() {
        while (true) {
            String doctorId = readRequiredString("Enter doctor id: ");
            Doctor doctor = hospital.findDoctor(doctorId);
            if (doctor != null) {
                return doctor;
            }
            System.out.println("Try again.");
        }
    }

    // keeps asking for a nurse id until a valid nurse is found or the user goes back
    private static Nurse promptForNurse() {
        while (true) {
            String nurseId = readRequiredString("Enter nurse id: ");
            Nurse nurse = hospital.findNurse(nurseId);
            if (nurse != null) {
                return nurse;
            }
            System.out.println("Try again.");
        }
    }

    // prints a consistent reminder under every input prompt
    private static void printInputPrompt(String prompt) {
        System.out.println(prompt);
        System.out.print(INPUT_HELP_MESSAGE + " ");
    }

    // reads a numbered menu choice and allows global menu commands
    private static int readMenuChoice(String prompt, boolean allowBack) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase(EXIT_COMMAND)) {
                shutdownProgram();
            }

            if (input.equalsIgnoreCase(BACK_COMMAND)) {
                if (allowBack) {
                    return -1;
                }
                System.out.println("You are already at the main menu.");
                continue;
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Please enter an integer.");
            }
        }
    }

    // keeps asking until the user enters a non-empty string
    private static String readRequiredString(String prompt) {
        while (true) {
            printInputPrompt(prompt);
            String input = scanner.nextLine().trim();
            handleSpecialCommands(input);

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Input cannot be empty. Try again.");
        }
    }

    // keeps asking until the user enters a valid positive integer
    private static int readPositiveInt(String prompt) {
        while (true) {
            printInputPrompt(prompt);
            String input = scanner.nextLine().trim();
            handleSpecialCommands(input);

            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("Please enter a whole number greater than 0. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number. Try again.");
            }
        }
    }

    // keeps asking until the user enters a valid money amount with two decimals
    private static double readMoneyAmount(String prompt) {
        while (true) {
            printInputPrompt(prompt);
            String input = scanner.nextLine().trim();
            handleSpecialCommands(input);

            if (!input.matches("^\\$?(\\d{1,3}(,\\d{3})*|\\d+)\\.\\d{2}$")) {
                System.out.println("Please enter a valid amount with exactly two decimal places. Try again.");
                continue;
            }

            String cleanedInput = input.replace("$", "").replace(",", "");

            try {
                double value = Double.parseDouble(cleanedInput);
                if (value >= 0) {
                    return value;
                }
                System.out.println("Amount cannot be negative. Try again.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount with exactly two decimal places. Try again.");
            }
        }
    }

    // keeps asking until the user enters a valid date in mm/dd/yyyy format
    private static String readDate(String prompt) {
        while (true) {
            printInputPrompt(prompt);
            String input = scanner.nextLine().trim();
            handleSpecialCommands(input);

            try {
                LocalDate.parse(input, DATE_FORMATTER);
                return input;
            } catch (DateTimeParseException e) {
                System.out.println("Please enter the date in MM/DD/YYYY format. Try again.");
            }
        }
    }

    // keeps asking until the user enters a valid 24-hour time like 06:00 or 18:30
    private static String readShiftTime(String prompt) {
        while (true) {
            printInputPrompt(prompt);
            String input = scanner.nextLine().trim();
            handleSpecialCommands(input);

            if (input.matches("^([01]\\d|2[0-3]):[0-5]\\d$")) {
                return input;
            }

            System.out.println("Please enter time in 24-hour format like XX:XX. Try again.");
        }
    }

    // checks for the shared exit and back commands
    private static void handleSpecialCommands(String input) {
        if (input.equalsIgnoreCase(EXIT_COMMAND)) {
            shutdownProgram();
        }

        if (input.equalsIgnoreCase(BACK_COMMAND)) {
            throw new BackInputException();
        }
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
