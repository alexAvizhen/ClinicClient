package com.bsuir.lagunovskaya.clinic.communication.command;

import java.util.Date;

public class CreateAppointmentClientCommand extends ClientCommand {

    private String doctorLogin;
    private String patientLogin;
    private Date appointmentDate;
    private String commentToAppointment;


    public CreateAppointmentClientCommand(String commandName, String doctorLogin, String patientLogin, Date appointmentDate, String commentToAppointment) {
        super(commandName);
        this.doctorLogin = doctorLogin;
        this.patientLogin = patientLogin;
        this.appointmentDate = appointmentDate;
        this.commentToAppointment = commentToAppointment;
    }

    public String getDoctorLogin() {
        return doctorLogin;
    }

    public String getPatientLogin() {
        return patientLogin;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public String getCommentToAppointment() {
        return commentToAppointment;
    }
}
