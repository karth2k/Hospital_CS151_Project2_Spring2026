package hospital;

public interface Billable {
    /*
     * Class Implemented by Karthik Muthukumar
     * ID: 016281915
     */

    //Adds charge to patients bill
    void addCharge(double amount);

    //Allows patient to pay the balance (as a part or whole) on their bill
    void payBill(double amount);

    //Returns the unpaid remaining balance
    double getOutstandingBalance();
}
