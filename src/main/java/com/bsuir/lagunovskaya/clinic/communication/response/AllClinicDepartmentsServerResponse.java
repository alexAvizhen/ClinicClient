package com.bsuir.lagunovskaya.clinic.communication.response;

import com.bsuir.lagunovskaya.clinic.communication.entity.ClinicDepartment;

import java.util.List;

public class AllClinicDepartmentsServerResponse extends ServerResponse {

    private static final long serialVersionUID = 378021352695090678L;

    private List<ClinicDepartment> clinicDepartments;

    public AllClinicDepartmentsServerResponse(List<ClinicDepartment> clinicDepartments) {
        this.clinicDepartments = clinicDepartments;
    }

    public List<ClinicDepartment> getClinicDepartments() {
        return clinicDepartments;
    }
}