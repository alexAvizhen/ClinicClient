package com.bsuir.lagunovskaya.clinic.client.ui.dialogs;

import com.bsuir.lagunovskaya.clinic.client.service.ClientCommandSender;
import com.bsuir.lagunovskaya.clinic.client.ui.UserFrame;
import com.bsuir.lagunovskaya.clinic.communication.CreateOrUpdateDepartmentClientCommand;
import com.bsuir.lagunovskaya.clinic.communication.entity.Clinic;
import com.bsuir.lagunovskaya.clinic.communication.entity.ClinicDepartment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDialog extends JDialog {

    private JTextField departmentNameField = new JTextField(20);
    private DefaultListModel<String> streetsListModel = new DefaultListModel<>();
    private JList<String> streetsList = new JList<>(streetsListModel);

    private JTextField newStreetField = new JTextField(20);

    public DepartmentDialog(final UserFrame owner, final Clinic clinic, final ClinicDepartment clinicDepartment, boolean isWriteMode) {
        super(owner);
        setTitle("Отображение отделения");

        setLayout(new BorderLayout());
        setSize(600, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        if (clinicDepartment != null) {
            departmentNameField.setText(clinicDepartment.getName());
            for (String street : clinicDepartment.getStreets()) {
                streetsListModel.addElement(street);
            }
        }

        JPanel departmentDialogMainPanel = new JPanel(new GridLayout(4, 1));
        JPanel tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Имя отделения:"));
        tempRowPanel.add(departmentNameField);
        departmentDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new BorderLayout());
        tempRowPanel.add(new JLabel("Входящие улицы"), BorderLayout.WEST);
        tempRowPanel.add(new JScrollPane(streetsList), BorderLayout.CENTER);
        departmentDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Новая улица"));
        tempRowPanel.add(newStreetField);
        departmentDialogMainPanel.add(tempRowPanel);

        if (clinicDepartment != null) {
            tempRowPanel = new JPanel(new FlowLayout());
            tempRowPanel.add(new JLabel("Врачей в отделении: " + clinicDepartment.getDoctors().size()));
            tempRowPanel.add(new JLabel("Пациентов в отделении: " + clinicDepartment.getPatients().size()));
            departmentDialogMainPanel.add(tempRowPanel);

        }

        add(departmentDialogMainPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton addStreetBtn = new JButton("Добавить улицу");
        buttonsPanel.add(addStreetBtn);
        addStreetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newStreet = newStreetField.getText();
                if (newStreet == null || newStreet.isEmpty()) {
                    JOptionPane.showMessageDialog(DepartmentDialog.this, "Укажите улицу в поле \"Новая улица\"");
                } else {
                    streetsListModel.addElement(newStreet);
                    newStreetField.setText("");
                }
            }
        });
        JButton removeStreetBtn = new JButton("Удалить выделенную улицу");
        buttonsPanel.add(removeStreetBtn);
        removeStreetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedStreet = streetsList.getSelectedValue();
                streetsListModel.removeElement(selectedStreet);
            }
        });
        JButton createOrUpdateButton;
        if (clinicDepartment == null) {
            createOrUpdateButton = new JButton("Создать отделение");
        } else {
            createOrUpdateButton = new JButton("Обновить отделение");
        }
        createOrUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> streets = new ArrayList<>();
                for (int i = 0; i < streetsListModel.getSize(); i++) {
                    streets.add(streetsListModel.getElementAt(i));
                }
                ClinicDepartment clinicDepartmentToUpdateOrCreate;
                if (clinicDepartment == null) {
                    clinicDepartmentToUpdateOrCreate = new ClinicDepartment(clinic, departmentNameField.getText(), streets);
                } else {
                    clinicDepartmentToUpdateOrCreate = clinicDepartment;
                    clinicDepartmentToUpdateOrCreate.setName(departmentNameField.getText());
                    clinicDepartmentToUpdateOrCreate.setStreets(streets);
                }
                CreateOrUpdateDepartmentClientCommand createOrUpdateDepartmentCommand =
                        new CreateOrUpdateDepartmentClientCommand("createOrUpdateDepartment", clinicDepartmentToUpdateOrCreate);
                ClientCommandSender.sendClientCommand(createOrUpdateDepartmentCommand);
                JOptionPane.showMessageDialog(DepartmentDialog.this, "Операция выполнена успешно");
                owner.loadClinicDepartments();
                dispose();
            }
        });
        buttonsPanel.add(createOrUpdateButton);
        if (isWriteMode) {
            add(buttonsPanel, BorderLayout.SOUTH);
        }
        setVisible(true);
    }
}
