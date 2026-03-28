package hospital;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HospitalTest {
    /*
     * Karthik Muthukumar
     * ID: 016281915
     */
    private Hospital hospital;
    private Pharmacy pharmacy;

    @BeforeEach
    void setUp(){
        //makes sure to create initial state before each function is executed so state changes are independent of each other
        pharmacy = new Pharmacy("CVS-TEST");
        hospital = new Hospital("Dublin Hospital", pharmacy);
    }
    private Patient makePatient(String id, String name) {
        return new Patient(id, "karthik", 22, "Cold");
    }
    private Doctor makeDoctor(String id, String name) {
        return new Doctor(
                id, name, "Cardiology", 150000,
                "Cardiologist", "LIC-" + id, "ROOM-" + id
        );
    }
    private Nurse makeNurse(String id, String name) {
        return new Nurse(
                id, name, "General", 80000,
                "Day", "Ward A"
        );
    }





}
