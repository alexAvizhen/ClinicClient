package com.bsuir.lagunovskaya.clinic.communication.command;

import com.bsuir.lagunovskaya.clinic.communication.entity.ClinicDepartment;

public class CreateOrUpdateDepartmentClientCommand extends ClientCommand {

    private ClinicDepartment clinicDepartment;

    public CreateOrUpdateDepartmentClientCommand(String commandName, ClinicDepartment clinicDepartment) {
        super(commandName);
        this.clinicDepartment = clinicDepartment;
    }

    public ClinicDepartment getClinicDepartment() {
        return clinicDepartment;
    }
}
