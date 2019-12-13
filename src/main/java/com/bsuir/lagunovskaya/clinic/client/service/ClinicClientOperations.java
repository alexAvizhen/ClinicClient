package com.bsuir.lagunovskaya.clinic.client.service;

import com.bsuir.lagunovskaya.clinic.communication.command.ClientCommand;
import com.bsuir.lagunovskaya.clinic.communication.entity.Clinic;
import com.bsuir.lagunovskaya.clinic.communication.entity.ClinicDepartment;
import com.bsuir.lagunovskaya.clinic.communication.entity.Doctor;
import com.bsuir.lagunovskaya.clinic.communication.entity.Patient;
import com.bsuir.lagunovskaya.clinic.communication.response.ClinicDepartmentServerResponse;
import com.bsuir.lagunovskaya.clinic.communication.response.ClinicServerResponse;
import com.bsuir.lagunovskaya.clinic.communication.response.UserServerResponse;

import java.util.Arrays;

public class ClinicClientOperations {

    public static Patient getPatientById(Integer id) {
        ClientCommand getUserByIdCommand = new ClientCommand("getPatientById", Arrays.asList(id.toString()));
        UserServerResponse serverResponse = (UserServerResponse) ClientCommandSender.sendClientCommand(getUserByIdCommand);
        return (Patient) serverResponse.getUser();
    }

    public static Doctor getDoctorById(Integer id) {
        ClientCommand getUserByIdCommand = new ClientCommand("getDoctorById", Arrays.asList(id.toString()));
        UserServerResponse serverResponse = (UserServerResponse) ClientCommandSender.sendClientCommand(getUserByIdCommand);
        return (Doctor) serverResponse.getUser();
    }

    public static Clinic getClinicById(Integer id) {
        ClientCommand getClinicByIdCommand = new ClientCommand("getClinicById", Arrays.asList(id.toString()));
        ClinicServerResponse serverResponse = (ClinicServerResponse) ClientCommandSender.sendClientCommand(getClinicByIdCommand);
        return serverResponse.getClinic();
    }

    public static ClinicDepartment getClinicDepartmentById(Integer id) {
        ClientCommand getClinicByIdCommand = new ClientCommand("getClinicDepartmentById", Arrays.asList(id.toString()));
        ClinicDepartmentServerResponse serverResponse = (ClinicDepartmentServerResponse) ClientCommandSender.sendClientCommand(getClinicByIdCommand);
        return serverResponse.getClinicDepartment();
    }

}
