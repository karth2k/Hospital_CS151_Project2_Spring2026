package hospital;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StaffTest {
    /*
    * Karthik Muthukumar
    * ID: 016281915
    */

    //Since staff is abstract we need to extend a subclass to test it
    private static class TestStaff extends Staff{
        public TestStaff(String employeeID, String name, String department, double salary) {
            super(employeeID, name, department, salary);
        }

        @Override
        public void performDuties() {
            System.out.println("Staff did their job");
        }

        @Override
        public String getRoleDescription() {
            return "Test Staff";
        }
    }

    //Initializes the staff member after calling constructor
    TestStaff staff = new TestStaff("S001", "Karthik", "Admin", 100000);
    @Test
    void constructorInitializesFieldsCorrectly() {


        //Asserts the ID, name, role/department, salary, status, and shiftsworked were initialized properly
        assertEquals("S001", staff.getEmployeeID());
        assertEquals("Karthik", staff.getName());
        assertEquals("Admin", staff.getDepartment());
        assertEquals(100000.0, staff.getSalary());
        assertFalse(staff.isClockedIn());
        assertEquals(0, staff.getShiftsWorked());
    }

    @Test
    void clockInSetsClockedInToTrue() {
        //Checks if clockedIn function is true and works
        staff.clockIn();
        assertTrue(staff.isClockedIn());
    }

    @Test
    void clockOutAfterClockInIncrementsShiftCount() {
        //Checks if shiftsworked is incrememnted after one clock in clock out cycle

        staff.clockIn();
        staff.clockOut();

        assertFalse(staff.isClockedIn());
        assertEquals(1, staff.getShiftsWorked());
    }

    @Test
    void clockOutWithoutClockInDoesNotChangeShiftCount() {
        //Checks if clockout without clockin doesnt increment the counter shifts worked (it shouldnt)

        staff.clockOut();

        assertFalse(staff.isClockedIn());
        assertEquals(0, staff.getShiftsWorked());
    }

    @Test
    void updateSalaryTest(){
        staff.updateSalary(125000);

        assertEquals(125000.0, staff.getSalary());
    }

    @Test
    void updateSalaryWithNegativeValue(){
        //Checks if negative salary works (it shouldnt) and makes sure salary remains the same if updated with incorrect values
        staff.updateSalary(-125000);

        assertEquals(100000.0, staff.getSalary());
    }

    @Test
    void assignDepartmentChangesDepartment() {
        //checks assigndepartment function if it changes the assigned department

        staff.assignDepartment("HR");

        assertEquals("HR", staff.getDepartment());
    }

    @Test
    void assignDepartmentInvalidInput(){
        //checks if empty string input would change department to random empty string (it shouldnt)
        staff.assignDepartment("    ");
        assertEquals("Admin", staff.getDepartment());
    }

    @Test
    void toStringContainsImportantFields() {

        //Quick test for to string
        TestStaff staff = new TestStaff("S001", "Karthik", "Admin", 50000);

        String result = staff.toString();

        assertTrue(result.contains("S001"));
        assertTrue(result.contains("Karthik"));
        assertTrue(result.contains("Admin"));
    }




}
