package com.bsuir.lagunovskaya.clinic.client.ui.tables.models;

import com.bsuir.lagunovskaya.clinic.communication.entity.Doctor;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class DoctorsTableModel extends AbstractTableModel {

    private String[] columnNames = new String[]{"Логин", "Фамилия", "Имя", "Дата рождения", "Номер телефона"};

    private List<Doctor> doctors = new ArrayList<>();

    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }
    
    @Override
    public int getRowCount() {
        return doctors.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Doctor doctor = doctors.get(rowIndex);
        if (columnIndex == 0) {
            return doctor.getLogin();
        } else if (columnIndex == 1) {
            return doctor.getSurname();
        } else if (columnIndex == 2) {
            return doctor.getName();
        } else if (columnIndex == 3) {
            return doctor.getBirthDate();
        } else if (columnIndex == 4) {
            return doctor.getPhoneNumber();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void clearDoctors() {
        doctors.clear();
    }


    public Doctor getDoctorAtRow(int selectedRow) {
        return doctors.get(selectedRow);
    }
}
