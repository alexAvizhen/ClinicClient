package com.bsuir.lagunovskaya.clinic.communication;

import com.bsuir.lagunovskaya.clinic.communication.entity.Doctor;

public class CreateOrUpdateDoctorClientCommand extends ClientCommand {

    private Doctor doctor;

    public CreateOrUpdateDoctorClientCommand(String commandName, Doctor doctor) {
        super(commandName);
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return doctor;
    }
}
