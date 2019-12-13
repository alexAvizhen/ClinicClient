package com.bsuir.lagunovskaya.clinic.communication.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Appointment implements Serializable {
    private Integer id;
    private Integer doctorId;
    private Integer patientId;
    private Date appointmentDate;
    private String commentToAppointment;

    public Appointment(Integer doctorId, Integer patientId, Date appointmentDate, String commentToAppointment) {
        this.id = null;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentDate = appointmentDate;
        this.commentToAppointment = commentToAppointment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
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
