package com.bsuir.lagunovskaya.clinic.communication.entity;

import java.util.Objects;

public class Doctor extends User {

    private ClinicDepartment clinicDepartment;

    public Doctor(String login, String password, ClinicDepartment clinicDepartment) {
        super(null, login, password);
        this.clinicDepartment = clinicDepartment;
    }

    public ClinicDepartment getClinicDepartment() {
        return clinicDepartment;
    }

    public void setClinicDepartment(ClinicDepartment clinicDepartment) {
        this.clinicDepartment = clinicDepartment;
    }

    @Override
    public boolean isAdmin() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getClinicDepartment());
    }
}
