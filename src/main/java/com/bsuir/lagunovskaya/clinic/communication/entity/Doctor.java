package com.bsuir.lagunovskaya.clinic.communication.entity;

import java.util.Objects;

public class Doctor extends User {

    private Integer clinicDepartmentId;

    public Doctor(String login, String password, Integer clinicDepartmentId) {
        super(null, login, password);
        this.clinicDepartmentId = clinicDepartmentId;
    }

    public Integer getClinicDepartmentId() {
        return clinicDepartmentId;
    }

    public void setClinicDepartmentId(Integer clinicDepartmentId) {
        this.clinicDepartmentId = clinicDepartmentId;
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
        return Objects.hash(super.hashCode(), getClinicDepartmentId());
    }
}
