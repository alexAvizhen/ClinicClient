package com.bsuir.lagunovskaya.clinic.client.ui.dialogs;

import com.bsuir.lagunovskaya.clinic.client.service.ClientCommandSender;
import com.bsuir.lagunovskaya.clinic.client.ui.DoctorFrame;
import com.bsuir.lagunovskaya.clinic.communication.ClientCommand;
import com.bsuir.lagunovskaya.clinic.communication.ClinicDepartmentServerResponse;
import com.bsuir.lagunovskaya.clinic.communication.CreateOrUpdateDepartmentClientCommand;
import com.bsuir.lagunovskaya.clinic.communication.CreateOrUpdateDoctorClientCommand;
import com.bsuir.lagunovskaya.clinic.communication.entity.ClinicDepartment;
import com.bsuir.lagunovskaya.clinic.communication.entity.Doctor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class DoctorDialog extends JDialog {

    private JTextField loginField = new JTextField(20);
    private JTextField passwordField = new JTextField(20);
    private JComboBox<String> departmentsComboBox = new JComboBox<>();

    public DoctorDialog(final DoctorFrame doctorFrame, final Doctor doctor) {
        super(doctorFrame);

        setTitle("Отображение доктора");
        setLayout(new BorderLayout());
        setSize(600, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        for (String possibleDepartmentName : doctorFrame.getPossibleDepartmentNames()) {
            departmentsComboBox.addItem(possibleDepartmentName);
        }
        if (doctor != null) {
            loginField.setText(doctor.getLogin());
            passwordField.setText(doctor.getPassword());
            departmentsComboBox.setSelectedItem(doctor.getClinicDepartment().getName());
        }
        JPanel doctorDialogMainPanel = new JPanel(new GridLayout(3, 1));

        JPanel tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Логин врача"));
        tempRowPanel.add(loginField);
        doctorDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Пароль врача"));
        tempRowPanel.add(passwordField);
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
                    doctorToUpdateOrCreate = new Doctor(loginField.getText(), passwordField.getText(), selectedClinicDepartment);
                } else {
                    doctorToUpdateOrCreate = doctor;
                    doctorToUpdateOrCreate.setLogin(loginField.getText());
                    doctorToUpdateOrCreate.setPassword(passwordField.getText());
                    doctorToUpdateOrCreate.setClinicDepartment(selectedClinicDepartment);
                }
                CreateOrUpdateDoctorClientCommand createOrUpdateDoctorCommand =
                        new CreateOrUpdateDoctorClientCommand("createOrUpdateDoctor", doctorToUpdateOrCreate);
                ClientCommandSender.sendClientCommand(createOrUpdateDoctorCommand);
                JOptionPane.showMessageDialog(DoctorDialog.this, "Операция выполнена успешно");
                doctorFrame.loadClinicDepartments();
                dispose();
            }
        });

        add(buttonsPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}
