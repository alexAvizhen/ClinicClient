package com.bsuir.lagunovskaya.clinic.client.ui.tables.models;

import com.bsuir.lagunovskaya.clinic.communication.entity.Appointment;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class AppointmentsTableModel extends AbstractTableModel {

    private String[] columnNames = new String[]{"Фамилия врача", "Имя врача", "Телефон врача", "Фамилия пациента", "Имя пациента",
            "Телефон пациента", "Дата приёма", "Комментарий к приёму"};

    private List<Appointment> appointments = new ArrayList<>();

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }
    
    @Override
    public int getRowCount() {
        return appointments.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Appointment appointment = appointments.get(rowIndex);
        if (columnIndex == 0) {
            return appointment.getDoctor().getSurname();
        } else if (columnIndex == 1) {
            return appointment.getDoctor().getName();
        } else if (columnIndex == 2) {
            return appointment.getDoctor().getPhoneNumber();
        } else if (columnIndex == 3) {
            return appointment.getPatient().getSurname();
        } else if (columnIndex == 4) {
            return appointment.getPatient().getName();
        } else if (columnIndex == 5) {
            return appointment.getPatient().getPhoneNumber();
        } else if (columnIndex == 6) {
            return appointment.getAppointmentDate();
        } else if (columnIndex == 7) {
            return appointment.getCommentToAppointment();
        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void clearAppointments() {
        appointments.clear();
    }


    public Appointment getAppointmentAtRow(int selectedRow) {
        return appointments.get(selectedRow);
    }
}
