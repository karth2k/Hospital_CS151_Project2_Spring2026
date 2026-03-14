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
    private int hoursWorked;                     //How many hours the staff member has worked


    //Constructor
    public Staff(String employeeID, String name, String department, double salary, boolean clockedIn, int hoursWorked){
        //Assigns parameters to instance variables
        this.employeeID = employeeID;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.clockedIn = clockedIn;
        this.hoursWorked = hoursWorked;
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
    public int getHoursWorked(){
        return hoursWorked;
    }


    //Setters and validation for setters
    public void setEmployeeID(String employeeID){
        //Makes sure employee ID isnt null or invalid
        if(employeeID != null && !employeeID.isEmpty()){
            this.employeeID = employeeID;
        }
        else{
            System.out.println("Invalid Entry");
        }
    }
    public void setName(String name){
        //Makes sure name is a valid string
        if(name != null && !name.isEmpty()){
            this.name = name;
        }
        else{
            System.out.println("Invalid Entry");
        }
    }
    public void setDepartment(String department){
        //Makes sure department is a valid string
        if(department != null && !department.isEmpty()){
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
    public void setHoursWorked(int hoursWorked){
        if(hoursWorked >= 0){
            this.hoursWorked = hoursWorked;
        }
        else{
            System.out.println("Hours worked cannot be negative.");
        }
    }

    //Staff methods
    public double updateSalary(double salary){
        //Already implemented the logic here
        setSalary(salary);
    }

}
