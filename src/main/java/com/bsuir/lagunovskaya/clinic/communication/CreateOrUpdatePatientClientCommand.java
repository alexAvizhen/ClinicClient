package com.bsuir.lagunovskaya.clinic.communication;

import com.bsuir.lagunovskaya.clinic.communication.entity.Patient;

public class CreateOrUpdatePatientClientCommand extends ClientCommand {

    private Patient patient;

    public CreateOrUpdatePatientClientCommand(String commandName, Patient patient) {
        super(commandName);
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }
}
