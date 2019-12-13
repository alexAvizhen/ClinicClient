package com.bsuir.lagunovskaya.clinic.communication.response;

import com.bsuir.lagunovskaya.clinic.communication.entity.Clinic;

public class ClinicServerResponse extends ServerResponse {

    private static final long serialVersionUID = 4530571438163647886L;

    private Clinic clinic;

    public ClinicServerResponse(Clinic clinic) {
        this.clinic = clinic;
    }

    public Clinic getClinic() {
        return clinic;
    }
}
