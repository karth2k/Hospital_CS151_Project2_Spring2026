package hospital;

public class Staff {
    /*
    * Class Implemented by Karthik Muthukumar
    * ID: 016281915
    */

    //Attributes
    private String employeeID;                   //ID of employee
    private String name;                         //Name of employee
    private String department;                   //Name of department that the staff works in
    private double salary;                       //Staff member salary


    //Constructor
    public Staff(String employeeID, String name, String department, double salary){
        //Assigns parameters to instance variables
        this.employeeID = employeeID;
        this.name = name;
        this.department = department;
        this.salary = salary;
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




}
