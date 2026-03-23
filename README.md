# Hospital Management System — CS151 Spring 2026

## Overview
A Java program that simulates a hospital management system. Users can manage patients,
doctors, nurses, and appointments through a text-based menu.

## Design
- `Hospital` — manages all hospital resources
- `Staff` (abstract) — base class for Doctor and Nurse
- `Doctor`, `Nurse` — hospital employees
- `Patient` — represents a patient
- `Appointment` — tracks patient-doctor meetings
- `Billable` (interface) — billing behavior

## Installation
1. Clone the repo and open in IntelliJ or Eclipse
2. Run `Main.java`

## Usage
Follow the on-screen menu to create and manage patients, staff, and appointments.
Type `EXIT` at any time to quit.

## Contributions
- Karthik Muthukumar: Hospital.java, Staff.java, PayBill interface
- Sean Tadina: Doctor.java, Patient.java, Main.java
- David: Nurse.java, Appointment.java, DoctorUnavailableException.java
- Qingheng Fang: MaxCapacityException.java, PatientNotFoundException.java, README.md
