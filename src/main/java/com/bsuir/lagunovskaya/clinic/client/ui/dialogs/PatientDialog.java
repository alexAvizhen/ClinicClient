package com.bsuir.lagunovskaya.clinic.client.ui.dialogs;

import com.bsuir.lagunovskaya.clinic.client.service.ClientCommandSender;
import com.bsuir.lagunovskaya.clinic.client.ui.DoctorFrame;
import com.bsuir.lagunovskaya.clinic.communication.ClientCommand;
import com.bsuir.lagunovskaya.clinic.communication.ClinicDepartmentServerResponse;
import com.bsuir.lagunovskaya.clinic.communication.CreateOrUpdatePatientClientCommand;
import com.bsuir.lagunovskaya.clinic.communication.entity.ClinicDepartment;
import com.bsuir.lagunovskaya.clinic.communication.entity.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class PatientDialog extends JDialog {

    private JTextField loginField = new JTextField(20);
    private JTextField passwordField = new JTextField(20);
    private JComboBox<String> departmentsComboBox = new JComboBox<>();

    public PatientDialog(final DoctorFrame doctorFrame, final Patient patient) {
        super(doctorFrame);

        setTitle("Отображение пациента");
        setLayout(new BorderLayout());
        setSize(600, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        for (String possibleDepartmentName : doctorFrame.getPossibleDepartmentNames()) {
            departmentsComboBox.addItem(possibleDepartmentName);
        }
        if (patient != null) {
            loginField.setText(patient.getLogin());
            passwordField.setText(patient.getPassword());
            departmentsComboBox.setSelectedItem(patient.getClinicDepartment().getName());
        }
        JPanel patientDialogMainPanel = new JPanel(new GridLayout(3, 1));

        JPanel tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Логин пациента"));
        tempRowPanel.add(loginField);
        patientDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Пароль пациента"));
        tempRowPanel.add(passwordField);
        patientDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new BorderLayout());
        tempRowPanel.add(new JLabel("Отделение пациента"), BorderLayout.WEST);
        tempRowPanel.add(departmentsComboBox, BorderLayout.CENTER);
        patientDialogMainPanel.add(tempRowPanel);
        add(patientDialogMainPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton createOrUpdatePatientBtn;
        if (patient == null) {
            createOrUpdatePatientBtn = new JButton("Создать пациента");
        } else {
            createOrUpdatePatientBtn = new JButton("Обновить пациента");
        }
        buttonsPanel.add(createOrUpdatePatientBtn);
        createOrUpdatePatientBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDepartmentName = (String) departmentsComboBox.getSelectedItem();
                ClientCommand getClinicDepartmentByNameCommand =
                        new ClientCommand("getClinicDepartmentByName", Arrays.asList(selectedDepartmentName));
                ClinicDepartmentServerResponse clinicDepartmentServerResponse
                        = (ClinicDepartmentServerResponse) ClientCommandSender.sendClientCommand(getClinicDepartmentByNameCommand);
                ClinicDepartment selectedClinicDepartment = clinicDepartmentServerResponse.getClinicDepartment();
                Patient patientToUpdateOrCreate;
                if (patient == null) {
                    patientToUpdateOrCreate = new Patient(loginField.getText(), passwordField.getText(), selectedClinicDepartment);
                } else {
                    patientToUpdateOrCreate = patient;
                    patientToUpdateOrCreate.setLogin(loginField.getText());
                    patientToUpdateOrCreate.setPassword(passwordField.getText());
                    patientToUpdateOrCreate.setClinicDepartment(selectedClinicDepartment);
                }
                CreateOrUpdatePatientClientCommand createOrUpdatePatientCommand =
                        new CreateOrUpdatePatientClientCommand("createOrUpdatePatient", patientToUpdateOrCreate);
                ClientCommandSender.sendClientCommand(createOrUpdatePatientCommand);
                JOptionPane.showMessageDialog(PatientDialog.this, "Операция выполнена успешно");
                doctorFrame.loadClinicDepartments();
                dispose();
            }
        });

        add(buttonsPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}
