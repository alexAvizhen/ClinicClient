package com.bsuir.lagunovskaya.clinic.communication.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClinicDepartment implements Serializable {

    private Integer id;
    private Integer clinicId;
    private String name;
    private List<String> streets;

    private List<Integer> patientIds;
    private List<Integer> doctorIds;

    public ClinicDepartment(Integer clinicId, String name, List<String> streets) {
        this.id = null;
        this.clinicId = clinicId;
        this.name = name;
        this.streets = streets;
        this.patientIds = new ArrayList<>();
        this.doctorIds = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public String getName() {
        return name;
    }

    public List<String> getStreets() {
        return streets;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreets(List<String> streets) {
        this.streets = streets;
    }

    public List<Integer> getPatientIds() {
        return patientIds;
    }

    public void setPatientIds(List<Integer> patientIds) {
        this.patientIds = patientIds;
    }

    public List<Integer> getDoctorIds() {
        return doctorIds;
    }

    public void setDoctorIds(List<Integer> doctorIds) {
        this.doctorIds = doctorIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClinicDepartment)) return false;
        ClinicDepartment that = (ClinicDepartment) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getClinicId(), getName(), getStreets(), getPatientIds(), getDoctorIds());
    }
}
