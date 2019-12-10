package com.bsuir.lagunovskaya.clinic.client.ui.tables.models;

import com.bsuir.lagunovskaya.clinic.communication.entity.Patient;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class PatientTableModel extends AbstractTableModel {

    private String[] columnNames = new String[]{"Логин", "Фамилия", "Имя", "Дата рождения", "Номер телефона"};

    private List<Patient> patients = new ArrayList<>();

    public void addPatient(Patient patient) {
        patients.add(patient);
    }
    
    @Override
    public int getRowCount() {
        return patients.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Patient patient = patients.get(rowIndex);
        if (columnIndex == 0) {
            return patient.getLogin();
        } else if (columnIndex == 1) {
            return patient.getSurname();
        } else if (columnIndex == 2) {
            return patient.getName();
        } else if (columnIndex == 3) {
            return patient.getBirthDate();
        } else if (columnIndex == 4) {
            return patient.getPhoneNumber();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void clearPatients() {
        patients.clear();
    }


    public Patient getPatientAtRow(int selectedRow) {
        return patients.get(selectedRow);
    }
}
