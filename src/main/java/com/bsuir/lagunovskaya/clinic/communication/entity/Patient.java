package com.bsuir.lagunovskaya.clinic.communication.entity;

import java.util.Objects;

public class Patient extends User {

    private ClinicDepartment clinicDepartment;
    private String address;

    public Patient(String login, String password, ClinicDepartment clinicDepartment) {
        super(null, login, password);
        this.clinicDepartment = clinicDepartment;
    }

    public ClinicDepartment getClinicDepartment() {
        return clinicDepartment;
    }

    public void setClinicDepartment(ClinicDepartment clinicDepartment) {
        this.clinicDepartment = clinicDepartment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getClinicDepartment(), getAddress());
    }
}
