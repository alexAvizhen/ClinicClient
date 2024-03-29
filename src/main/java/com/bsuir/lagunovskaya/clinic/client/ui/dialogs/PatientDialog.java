package com.bsuir.lagunovskaya.clinic.client.ui.dialogs;

import com.bsuir.lagunovskaya.clinic.client.service.ClientCommandSender;
import com.bsuir.lagunovskaya.clinic.client.service.ClinicClientOperations;
import com.bsuir.lagunovskaya.clinic.client.ui.frames.UserFrame;
import com.bsuir.lagunovskaya.clinic.communication.command.ClientCommand;
import com.bsuir.lagunovskaya.clinic.communication.response.ClinicDepartmentServerResponse;
import com.bsuir.lagunovskaya.clinic.communication.command.CreateOrUpdatePatientClientCommand;
import com.bsuir.lagunovskaya.clinic.communication.entity.ClinicDepartment;
import com.bsuir.lagunovskaya.clinic.communication.entity.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;

public class PatientDialog extends JDialog {

    private JTextField loginField = new JTextField(20);
    private JTextField passwordField = new JTextField(20);
    private JTextField surnameField = new JTextField(20);
    private JTextField nameField = new JTextField(20);
    private JTextField phoneNumberField = new JTextField(20);
    private JTextField addressField = new JTextField(20);
    private JSpinner birthDateSpinner = new JSpinner(new SpinnerDateModel());
    final String birthDateFormatPattern = "yyyy-MM-dd";
    private JComboBox<String> departmentsComboBox = new JComboBox<>();

    public PatientDialog(final UserFrame userFrame, final Patient patient, boolean isWriteMode) {
        super(userFrame);

        setTitle("Пациент");
        setLayout(new BorderLayout());
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel patientDialogMainPanel = new JPanel(new GridLayout(8, 1));

        JPanel tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Логин пациента"));
        tempRowPanel.add(loginField);
        patientDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Пароль пациента"));
        tempRowPanel.add(passwordField);
        patientDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Фамилия пациента"));
        tempRowPanel.add(surnameField);
        patientDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Имя пациента"));
        tempRowPanel.add(nameField);
        patientDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Мобильный номер пациента"));
        tempRowPanel.add(phoneNumberField);
        patientDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Адрес пациента"));
        tempRowPanel.add(addressField);
        patientDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(birthDateSpinner, birthDateFormatPattern);
        birthDateSpinner.setEditor(timeEditor);
        birthDateSpinner.setValue(new Date());
        tempRowPanel.add(new JLabel("Дата рождения пациента"));
        tempRowPanel.add(birthDateSpinner);
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
                    patientToUpdateOrCreate = new Patient(loginField.getText(), passwordField.getText(), selectedClinicDepartment.getId());
                } else {
                    patientToUpdateOrCreate = patient;
                    patientToUpdateOrCreate.setLogin(loginField.getText());
                    patientToUpdateOrCreate.setPassword(passwordField.getText());
                    patientToUpdateOrCreate.setClinicDepartmentId(selectedClinicDepartment.getId());
                }
                patientToUpdateOrCreate.setSurname(surnameField.getText());
                patientToUpdateOrCreate.setName(nameField.getText());
                patientToUpdateOrCreate.setBirthDate((Date) birthDateSpinner.getValue());
                patientToUpdateOrCreate.setPhoneNumber(phoneNumberField.getText());
                patientToUpdateOrCreate.setAddress(addressField.getText());

                CreateOrUpdatePatientClientCommand createOrUpdatePatientCommand =
                        new CreateOrUpdatePatientClientCommand("createOrUpdatePatient", patientToUpdateOrCreate);
                ClientCommandSender.sendClientCommand(createOrUpdatePatientCommand);
                JOptionPane.showMessageDialog(PatientDialog.this, "Операция выполнена успешно");
                userFrame.loadClinicDepartments();
                dispose();
            }
        });
        if (patient != null) {
            JButton removePatientBtn = new JButton("Удалить пациента");
            buttonsPanel.add(removePatientBtn);
            removePatientBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ClientCommand removePatientByIdCommand = new ClientCommand("removePatientById", Arrays.asList(patient.getId().toString()));
                    ClientCommandSender.sendClientCommand(removePatientByIdCommand);
                    JOptionPane.showMessageDialog(PatientDialog.this, "Операция выполнена успешно");
                    userFrame.loadClinicDepartments();
                    dispose();
                }
            });
        }
        if (isWriteMode) {
            add(buttonsPanel, BorderLayout.SOUTH);
        }


        for (String possibleDepartmentName : userFrame.getPossibleDepartmentNames()) {
            departmentsComboBox.addItem(possibleDepartmentName);
        }
        if (patient != null) {
            loginField.setText(patient.getLogin());
            passwordField.setText(patient.getPassword());
            ClinicDepartment clinicDepartmentById = ClinicClientOperations.getClinicDepartmentById(patient.getClinicDepartmentId());
            departmentsComboBox.setSelectedItem(clinicDepartmentById.getName());
            surnameField.setText(patient.getSurname());
            nameField.setText(patient.getName());
            phoneNumberField.setText(patient.getPhoneNumber());
            addressField.setText(patient.getAddress());
            if (patient.getBirthDate() != null) {
                birthDateSpinner.setValue(patient.getBirthDate());
            }
        }

        setVisible(true);
    }
}
