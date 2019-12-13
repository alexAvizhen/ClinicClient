package com.bsuir.lagunovskaya.clinic.communication.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Clinic implements Serializable {
    private Integer id;
    private String address;
    private String description;
    private List<Integer> clinicDepartmentIds;

    public Clinic(String address, String description) {
        this.id = null;
        this.address = address;
        this.description = description;
        this.clinicDepartmentIds = new ArrayList<>();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getClinicDepartmentIds() {
        return clinicDepartmentIds;
    }

    public void setClinicDepartmentIds(List<Integer> clinicDepartmentIds) {
        this.clinicDepartmentIds = clinicDepartmentIds;
    }

    public Integer getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Clinic)) return false;
        Clinic clinic = (Clinic) o;
        return Objects.equals(getId(), clinic.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAddress(), getDescription(), getClinicDepartmentIds());
    }
}
