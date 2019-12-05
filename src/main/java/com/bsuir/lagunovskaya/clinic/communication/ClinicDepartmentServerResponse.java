package com.bsuir.lagunovskaya.clinic.communication;


import com.bsuir.lagunovskaya.clinic.communication.entity.ClinicDepartment;

public class ClinicDepartmentServerResponse extends ServerResponse {

    private static final long serialVersionUID = -5810329889528687736L;

    private ClinicDepartment clinicDepartment;

    public ClinicDepartmentServerResponse(ClinicDepartment clinicDepartment) {
        this.clinicDepartment = clinicDepartment;
    }

    public ClinicDepartment getClinicDepartment() {
        return clinicDepartment;
    }
}
