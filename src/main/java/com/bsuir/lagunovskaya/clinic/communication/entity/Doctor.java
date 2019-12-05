package com.bsuir.lagunovskaya.clinic.communication.entity;

public class Doctor extends User {

    private ClinicDepartment clinicDepartment;

    public Doctor(String login, String password, ClinicDepartment clinicDepartment) {
        super(null, login, password);
        this.clinicDepartment = clinicDepartment;
    }

    public ClinicDepartment getClinicDepartment() {
        return clinicDepartment;
    }

    @Override
    public boolean isAdmin() {
        return true;
    }
}
