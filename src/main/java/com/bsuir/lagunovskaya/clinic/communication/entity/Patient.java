package com.bsuir.lagunovskaya.clinic.communication.entity;

import java.util.Objects;

public class Patient extends User {

    private ClinicDepartment clinicDepartment;

    public Patient(String login, String password, ClinicDepartment clinicDepartment) {
        super(null, login, password);
        this.clinicDepartment = clinicDepartment;
    }

    public ClinicDepartment getClinicDepartment() {
        return clinicDepartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        if (!super.equals(o)) return false;
        Patient patient = (Patient) o;
        return Objects.equals(getClinicDepartment(), patient.getClinicDepartment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getClinicDepartment());
    }
}
