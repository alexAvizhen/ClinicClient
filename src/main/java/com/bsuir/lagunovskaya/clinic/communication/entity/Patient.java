package com.bsuir.lagunovskaya.clinic.communication.entity;

public class Patient extends User {

    private ClinicDepartment clinicDepartment;

    public Patient(String login, String password, ClinicDepartment clinicDepartment) {
        super(null, login, password);
        this.clinicDepartment = clinicDepartment;
    }

    public ClinicDepartment getClinicDepartment() {
        return clinicDepartment;
    }
}
