package com.bsuir.lagunovskaya.clinic.communication.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Clinic implements Serializable {
    private Integer id;
    private String address;
    private String description;
    private List<ClinicDepartment> clinicDepartments;

    public Clinic(String address, String description) {
        this.id = null;
        this.address = address;
        this.description = description;
        this.clinicDepartments = new ArrayList<>();
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

    public void setClinicDepartments(List<ClinicDepartment> clinicDepartments) {
        this.clinicDepartments = clinicDepartments;
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

    public List<ClinicDepartment> getClinicDepartments() {
        return clinicDepartments;
    }
}
