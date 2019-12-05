package com.bsuir.lagunovskaya.clinic.communication.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClinicDepartment implements Serializable {

    private Integer id;
    private Clinic clinic;
    private String name;
    private List<String> streets;

    private List<Patient> patients;
    private List<Doctor> doctors;

    public ClinicDepartment(Clinic clinic, String name, List<String> streets) {
        this.id = null;
        this.clinic = clinic;
        this.name = name;
        this.streets = streets;
        this.patients = new ArrayList<>();
        this.doctors = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public String getName() {
        return name;
    }

    public List<String> getStreets() {
        return streets;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreets(List<String> streets) {
        this.streets = streets;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }
}
