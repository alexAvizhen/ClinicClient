package com.bsuir.lagunovskaya.clinic.communication.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Appointment implements Serializable {
    private Integer id;
    private Doctor doctor;
    private Patient patient;
    private Date appointmentDate;
    private String commentToAppointment;

    public Appointment(Doctor doctor, Patient patient, Date appointmentDate, String commentToAppointment) {
        this.id = null;
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.commentToAppointment = commentToAppointment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getCommentToAppointment() {
        return commentToAppointment;
    }

    public void setCommentToAppointment(String commentToAppointment) {
        this.commentToAppointment = commentToAppointment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
