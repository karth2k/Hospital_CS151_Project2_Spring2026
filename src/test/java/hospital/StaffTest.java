package hospital;

import org.junit.jupiter.api.Test;

public class StaffTest {

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

}
