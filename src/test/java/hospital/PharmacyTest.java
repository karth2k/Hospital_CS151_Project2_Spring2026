package hospital;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PharmacyTest {
    /*
     * Qingheng Fang
     * ID: 015323763
     */

    // -------------------------------------------------------------------------
    // Pharmacy constructor and basic getters
    // -------------------------------------------------------------------------

    @Test
    public void testConstructor() {
        Pharmacy pharmacy = new Pharmacy("P-001");
        assertEquals("P-001", pharmacy.getPharmacyId());
        assertEquals(0, pharmacy.getMedicineCount());
    }

    // -------------------------------------------------------------------------
    // restockMedicine
    // -------------------------------------------------------------------------

    @Test
    public void testRestockNewMedicine() {
        Pharmacy pharmacy = new Pharmacy("P-001");
        pharmacy.restockMedicine("Aspirin", 10, 5.00);
        assertEquals(1, pharmacy.getMedicineCount());
    }

    @Test
    public void testRestockExistingMedicineIncreasesQuantity() {
        Pharmacy pharmacy = new Pharmacy("P-001");
        pharmacy.restockMedicine("Aspirin", 10, 5.00);
        pharmacy.restockMedicine("Aspirin", 5, 5.00);
        assertEquals(1, pharmacy.getMedicineCount());
    }

    @Test
    public void testRestockInvalidQuantityThrows() {
        Pharmacy pharmacy = new Pharmacy("P-001");
        assertThrows(IllegalArgumentException.class, () ->
                pharmacy.restockMedicine("Aspirin", 0, 5.00));
    }

    @Test
    public void testRestockNullMedicineThrows() {
        Pharmacy pharmacy = new Pharmacy("P-001");
        assertThrows(IllegalArgumentException.class, () ->
                pharmacy.restockMedicine(null, 5, 5.00));
    }

    // -------------------------------------------------------------------------
    // checkAvailability
    // -------------------------------------------------------------------------

    @Test
    public void testCheckAvailabilityTrue() {
        Pharmacy pharmacy = new Pharmacy("P-001");
        pharmacy.restockMedicine("Aspirin", 10, 5.00);
        assertTrue(pharmacy.checkAvailability("Aspirin"));
    }

    @Test
    public void testCheckAvailabilityNotFound() {
        Pharmacy pharmacy = new Pharmacy("P-001");
        assertFalse(pharmacy.checkAvailability("Aspirin"));
    }

    // -------------------------------------------------------------------------
    // findMedicinePrice
    // -------------------------------------------------------------------------

    @Test
    public void testFindMedicinePriceFound() {
        Pharmacy pharmacy = new Pharmacy("P-001");
        pharmacy.restockMedicine("Aspirin", 10, 5.00);
        assertEquals(5.00, pharmacy.findMedicinePrice("Aspirin"));
    }

    @Test
    public void testFindMedicinePriceNotFound() {
        Pharmacy pharmacy = new Pharmacy("P-001");
        assertEquals(-1, pharmacy.findMedicinePrice("Aspirin"));
    }

    // -------------------------------------------------------------------------
    // removeMedicine
    // -------------------------------------------------------------------------

    @Test
    public void testRemoveMedicine() {
        Pharmacy pharmacy = new Pharmacy("P-001");
        pharmacy.restockMedicine("Aspirin", 10, 5.00);
        assertTrue(pharmacy.removeMedicine("Aspirin"));
        assertEquals(0, pharmacy.getMedicineCount());
    }

    @Test
    public void testRemoveMedicineNotFound() {
        Pharmacy pharmacy = new Pharmacy("P-001");
        assertFalse(pharmacy.removeMedicine("Aspirin"));
    }

    // -------------------------------------------------------------------------
    // Billable interface
    // -------------------------------------------------------------------------

    @Test
    public void testGetOutstandingBalance() {
        Pharmacy pharmacy = new Pharmacy("P-001");
        pharmacy.restockMedicine("Aspirin", 10, 5.00);
        // restocking 10 units at $5.00 = $50.00 expense
        assertEquals(-50.00, pharmacy.getOutstandingBalance(), 0.01);
    }

    // -------------------------------------------------------------------------
    // OutOfStockException
    // -------------------------------------------------------------------------

    @Test
    public void testOutOfStockExceptionMessage() {
        OutOfStockException e = new OutOfStockException("Aspirin", true);
        assertEquals("Aspirin", e.getMedicineName());
        assertTrue(e.getMessage().contains("Aspirin"));
    }

    @Test
    public void testOutOfStockExceptionDefaultConstructor() {
        OutOfStockException e = new OutOfStockException("out of stock");
        assertEquals("Unknown", e.getMedicineName());
    }

    // -------------------------------------------------------------------------
    // MaxCapacityException
    // -------------------------------------------------------------------------

    @Test
    public void testMaxCapacityExceptionFields() {
        MaxCapacityException e = new MaxCapacityException("Medicine", 100);
        assertEquals(100, e.getMaxCapacity());
        assertEquals("Medicine", e.getResourceType());
        assertTrue(e.hasCapacityInfo());
    }

    @Test
    public void testMaxCapacityExceptionNoInfo() {
        MaxCapacityException e = new MaxCapacityException("capacity exceeded");
        assertFalse(e.hasCapacityInfo());
    }

    // -------------------------------------------------------------------------
    // PatientNotFoundException
    // -------------------------------------------------------------------------

    @Test
    public void testPatientNotFoundExceptionId() {
        PatientNotFoundException e = new PatientNotFoundException("P-999", true);
        assertEquals("P-999", e.getPatientId());
        assertTrue(e.getMessage().contains("P-999"));
    }

    @Test
    public void testPatientNotFoundExceptionDefault() {
        PatientNotFoundException e = new PatientNotFoundException("not found");
        assertEquals("Unknown", e.getPatientId());
    }
}
