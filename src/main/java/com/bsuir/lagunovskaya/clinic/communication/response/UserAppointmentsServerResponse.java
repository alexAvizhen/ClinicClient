package com.bsuir.lagunovskaya.clinic.communication.response;

import com.bsuir.lagunovskaya.clinic.communication.entity.Appointment;

import java.util.Collection;

public class UserAppointmentsServerResponse extends ServerResponse {

    private static final long serialVersionUID = 4702412197894627864L;

    private Collection<Appointment> appointments;

    public UserAppointmentsServerResponse(Collection<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Collection<Appointment> getAppointments() {
        return appointments;
    }
}
