package hospital;

public interface Billable {

    void addCharge(double amount);

    void payBill(double amount);

    double getOutstandingBalance(double amount);
}
