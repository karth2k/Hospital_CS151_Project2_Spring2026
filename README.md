# Hospital Management System — CS151 Spring 2026

## Overview
A Java program that simulates a hospital management system. Users can manage patients,
doctors, nurses, appointments, and a pharmacy through a text-based menu. The pharmacy
tracks medicine inventory, dispenses prescriptions to patients, and monitors revenue
and expenses via the Billable interface.

## Design
- `Hospital` — manages all hospital resources (patients, staff, appointments, pharmacy)
- `Staff` (abstract) — base class for Doctor and Nurse
- `Doctor`, `Nurse` — hospital employees
- `Patient` — represents a patient, implements Billable for billing tracking
- `Appointment` — tracks patient-doctor meetings
- `Pharmacy` — manages medicine inventory, dispenses prescriptions, implements Billable
- `Billable` (interface) — billing behavior: addCharge(), payBill(), getOutstandingBalance()
- `MaxCapacityException` — thrown when an array reaches 100 objects
- `PatientNotFoundException` — thrown when a patient ID cannot be found
- `OutOfStockException` — thrown when a medicine is out of stock
- `DoctorUnavailableException` — thrown when a doctor is unavailable

## Installation
1. Clone the repo and open in IntelliJ or Eclipse
2. Run `Main.java`

## Usage
Follow the on-screen menu to create and manage patients, staff, appointments, and pharmacy inventory.

```
10. Pharmacy: View Inventory
11. Pharmacy: Restock Medicine
12. Pharmacy: Dispense Medicine
```

## Contributions
- Karthik Muthukumar: Hospital.java, Staff.java, Billable interface
- Sean Tadina: Doctor.java, Patient.java, Main.java
- David: Nurse.java, Appointment.java, DoctorUnavailableException.java
- Qingheng Fang: Pharmacy.java, MaxCapacityException.java, PatientNotFoundException.java, OutOfStockException.java, README.md
