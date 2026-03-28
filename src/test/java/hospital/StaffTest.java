package hospital;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StaffTest {

    /*
    * Karthik Muthukumar
    * ID: 016281915
    * */
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




}
