package hospital;

public abstract class Staff {
    /*
    * Class Implemented by Karthik Muthukumar
    * ID: 016281915
    */



    //Attributes
    private String employeeID;                   //ID of employee
    private String name;                         //Name of employee
    private String department;                   //Name of department that the staff works in
    private double salary;                       //Staff member salary
    private boolean clockedIn;                   //If the staff member has clocked into work
    private int shiftsWorked;                    //How many shifts the staff member has worked


    //Constructor
    public Staff(String employeeID, String name, String department, double salary){
        //Assigns parameters to instance variables
        this.employeeID = employeeID;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.clockedIn = false;
        this.shiftsWorked = 0;
    }

    //Getters
    public String getEmployeeID(){
        return employeeID;
    }
    public String getName(){
        return name;
    }
    public String getDepartment(){
        return department;
    }
    public double getSalary(){
        return salary;
    }
    public boolean isClockedIn(){
        return clockedIn;
    }
    public int getShiftsWorked(){
        return shiftsWorked;
    }


    //Setters and validation for setters
    public void setEmployeeID(String employeeID){
        //Makes sure employee ID isnt null or invalid or empty
        if(employeeID != null && !employeeID.trim().isEmpty()){
            this.employeeID = employeeID;
        }
        else{
            System.out.println("Invalid Entry");
        }
    }
    public void setName(String name){
        //Makes sure name is a valid nonempty string
        if(name != null && !name.trim().isEmpty()){
            this.name = name;
        }
        else{
            System.out.println("Invalid Entry");
        }
    }
    public void setDepartment(String department){
        //Makes sure department is a valid non empty string
        if(department != null && !department.trim().isEmpty()){
            this.department = department;
        }
        else{
            System.out.println("Invalid Entry");
        }
    }
    public void setSalary(double salary){
        //Makes sure salary is positive
        if(salary >= 0){
            this.salary = salary;
        }
        else{
            System.out.println("Salary cannot be negative.");
        }
    }
    public void setClockedIn(boolean clockedIn){
        this.clockedIn = clockedIn;
    }
    public void setShiftsWorked(int shiftsWorked){
        if(shiftsWorked >= 0){
            this.shiftsWorked = shiftsWorked;
        }
        else{
            System.out.println("Shifts worked cannot be negative.");
        }
    }

    //Staff methods
    public void updateSalary(double newSalary){
        //New salary must be positive otherwise method should not work
        if (newSalary < 0) {
            System.out.println("Salary cannot be negative.");
            return;
        }

        double oldSalary = this.salary;
        this.salary = newSalary;
        System.out.println(name + "'s salary was updated from $" + oldSalary + " to $" + newSalary + ".");
    }
    public void assignDepartment(String dept) {
        //If the staff member is assigned to invalid department or none
        if (dept == null || dept.trim().isEmpty()) {
            System.out.println("Department cannot be empty.");
            return;
        }

        //Case where they are assigned to the same department
        if (this.department != null && this.department.equalsIgnoreCase(dept.trim())) {
            System.out.println(name + " is already assigned to " + this.department + ".");
            return;
        }

        //Assigns the staff member to a valid department entry
        this.department = dept.trim();
        System.out.println(name + " was assigned to the " + this.department + " department.");
    }

    public void viewStaffInfo() {
        //Prints out staff information
        System.out.println("----- Staff Information -----");
        System.out.println("Employee ID: " + employeeID);
        System.out.println("Name: " + name);
        System.out.println("Department: " + department);
        System.out.println("Salary: $" + salary);
        System.out.println("Clocked In: " + clockedIn);
        System.out.println("Shifts Worked: " + shiftsWorked);
        System.out.println("Role: " + getRoleDescription());       //role description comes from overrided method in child classes
    }

    public void clockIn(){
        //Checks if clocked in and if not then clocks in the staff member
        if(clockedIn){
            System.out.println(name + " is already clocked in.");
            return;
        }
        clockedIn = true;
        System.out.println(name + " clocked in successfully.");
    }

    public void clockOut(){
        //Checks if clocked out and if not then clocks out the staff member and adds to shifts worked
        if(!clockedIn){
            System.out.println(name + " is not currently clocked in.");
            return;
        }
        clockedIn = false;
        shiftsWorked++;
        System.out.println(name + " clocked out successfully. Total shifts worked: " + shiftsWorked);
    }

    public void transferDepartment(String newDept) {
        //if the new department is empty or null doesnt do anything
        if (newDept == null || newDept.trim().isEmpty()) {
            System.out.println("New department cannot be empty.");
            return;
        }

        //if the staff member is assigned to the same department
        if (this.department != null && this.department.equalsIgnoreCase(newDept.trim())) {
            System.out.println(name + " is already in the " + this.department + " department.");
            return;
        }

        //if the staff member is assigned to a valid new departmnt
        String oldDepartment = this.department;
        this.department = newDept.trim();
        System.out.println(name + " was transferred from " + oldDepartment + " to " + this.department + ".");
    }

    //Abstract methods
    public abstract void performDuties();

    //Child classes that inherit and override this method will provide role description like for example if doctor is created they
    //can be something like a cardiologist or a pediatrician
    public abstract String getRoleDescription();


    @Override
    public String toString(){
        //Overrides toString to print out the staff details
        return "Employee ID: " + employeeID + ", Name: " + name + ", Department: " + department + ", Salary: $" + salary
                + ", Clocked In: " + clockedIn + ", Shifts Worked: " + shiftsWorked;
    }


}
