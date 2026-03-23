package hospital;

public interface Billable {

    //Adds charge to patients bill
    void addCharge(double amount);

    //Allows patient to pay the balance (as a part or whole) on their bill
    void payBill(double amount);

    //Returns the unpaid remaining balance
    double getOutstandingBalance();
}
