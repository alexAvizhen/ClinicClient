package com.bsuir.lagunovskaya.clinic.client.ui.dialogs;

import com.bsuir.lagunovskaya.clinic.client.service.ClientCommandSender;
import com.bsuir.lagunovskaya.clinic.client.service.ClinicClientOperations;
import com.bsuir.lagunovskaya.clinic.client.ui.frames.UserFrame;
import com.bsuir.lagunovskaya.clinic.communication.command.ClientCommand;
import com.bsuir.lagunovskaya.clinic.communication.response.ClinicDepartmentServerResponse;
import com.bsuir.lagunovskaya.clinic.communication.command.CreateOrUpdateDoctorClientCommand;
import com.bsuir.lagunovskaya.clinic.communication.entity.ClinicDepartment;
import com.bsuir.lagunovskaya.clinic.communication.entity.Doctor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;

public class DoctorDialog extends JDialog {

    private JTextField loginField = new JTextField(20);
    private JTextField passwordField = new JTextField(20);
    private JTextField surnameField = new JTextField(20);
    private JTextField nameField = new JTextField(20);
    private JTextField phoneNumberField = new JTextField(20);
    private JSpinner birthDateSpinner = new JSpinner(new SpinnerDateModel());
    final String birthDateFormatPattern = "yyyy-MM-dd";

    private JComboBox<String> departmentsComboBox = new JComboBox<>();

    public DoctorDialog(final UserFrame userFrame, final Doctor doctor, boolean isWriteMode) {
        super(userFrame);

        setTitle("Врач");
        setLayout(new BorderLayout());
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel doctorDialogMainPanel = new JPanel(new GridLayout(7, 1));

        JPanel tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Логин врача"));
        tempRowPanel.add(loginField);
        doctorDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Пароль врача"));
        tempRowPanel.add(passwordField);
        doctorDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Фамилия врача"));
        tempRowPanel.add(surnameField);
        doctorDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Имя врача"));
        tempRowPanel.add(nameField);
        doctorDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Мобильный номер врача"));
        tempRowPanel.add(phoneNumberField);
        doctorDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(birthDateSpinner, birthDateFormatPattern);
        birthDateSpinner.setEditor(timeEditor);
        birthDateSpinner.setValue(new Date());
        tempRowPanel.add(new JLabel("Дата рождения врача"));
        tempRowPanel.add(birthDateSpinner);
        doctorDialogMainPanel.add(tempRowPanel);


        tempRowPanel = new JPanel(new BorderLayout());
        tempRowPanel.add(new JLabel("Отделение врача"), BorderLayout.WEST);
        tempRowPanel.add(departmentsComboBox, BorderLayout.CENTER);
        doctorDialogMainPanel.add(tempRowPanel);
        add(doctorDialogMainPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton createOrUpdateDoctorBtn;
        if (doctor == null) {
            createOrUpdateDoctorBtn = new JButton("Создать врача");
        } else {
            createOrUpdateDoctorBtn = new JButton("Обновить врача");
        }
        buttonsPanel.add(createOrUpdateDoctorBtn);
        createOrUpdateDoctorBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDepartmentName = (String) departmentsComboBox.getSelectedItem();
                ClientCommand getClinicDepartmentByNameCommand =
                        new ClientCommand("getClinicDepartmentByName", Arrays.asList(selectedDepartmentName));
                ClinicDepartmentServerResponse clinicDepartmentServerResponse
                        = (ClinicDepartmentServerResponse) ClientCommandSender.sendClientCommand(getClinicDepartmentByNameCommand);
                ClinicDepartment selectedClinicDepartment = clinicDepartmentServerResponse.getClinicDepartment();
                Doctor doctorToUpdateOrCreate;
                if (doctor == null) {
                    doctorToUpdateOrCreate = new Doctor(loginField.getText(), passwordField.getText(), selectedClinicDepartment.getId());
                } else {
                    doctorToUpdateOrCreate = doctor;
                    doctorToUpdateOrCreate.setLogin(loginField.getText());
                    doctorToUpdateOrCreate.setPassword(passwordField.getText());
                    doctorToUpdateOrCreate.setClinicDepartmentId(selectedClinicDepartment.getId());
                }
                doctorToUpdateOrCreate.setSurname(surnameField.getText());
                doctorToUpdateOrCreate.setName(nameField.getText());
                doctorToUpdateOrCreate.setBirthDate((Date) birthDateSpinner.getValue());
                doctorToUpdateOrCreate.setPhoneNumber(phoneNumberField.getText());

                CreateOrUpdateDoctorClientCommand createOrUpdateDoctorCommand =
                        new CreateOrUpdateDoctorClientCommand("createOrUpdateDoctor", doctorToUpdateOrCreate);
                ClientCommandSender.sendClientCommand(createOrUpdateDoctorCommand);
                JOptionPane.showMessageDialog(DoctorDialog.this, "Операция выполнена успешно");
                userFrame.loadClinicDepartments();
                dispose();
            }
        });
        if (doctor != null) {
            JButton removeDoctorBtn = new JButton("Удалить врача");
            buttonsPanel.add(removeDoctorBtn);
            removeDoctorBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ClientCommand removeDoctorByIdCommand = new ClientCommand("removeDoctorById", Arrays.asList(doctor.getId().toString()));
                    ClientCommandSender.sendClientCommand(removeDoctorByIdCommand);
                    JOptionPane.showMessageDialog(DoctorDialog.this, "Операция выполнена успешно");
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

        if (doctor != null) {
            loginField.setText(doctor.getLogin());
            passwordField.setText(doctor.getPassword());
            ClinicDepartment clinicDepartmentById = ClinicClientOperations.getClinicDepartmentById(doctor.getClinicDepartmentId());
            departmentsComboBox.setSelectedItem(clinicDepartmentById.getName());
            surnameField.setText(doctor.getSurname());
            nameField.setText(doctor.getName());
            phoneNumberField.setText(doctor.getPhoneNumber());
            if (doctor.getBirthDate() != null) {
                birthDateSpinner.setValue(doctor.getBirthDate());
            }
        }

        setVisible(true);
    }
}
