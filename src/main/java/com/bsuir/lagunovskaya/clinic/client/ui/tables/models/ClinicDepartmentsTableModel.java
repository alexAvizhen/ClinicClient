package com.bsuir.lagunovskaya.clinic.client.ui.tables.models;

import com.bsuir.lagunovskaya.clinic.communication.entity.ClinicDepartment;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ClinicDepartmentsTableModel extends AbstractTableModel {

    private String[] columnNames = new String[]{"Имя отделения", "Входящие улицы"};
    private List<ClinicDepartment> clinicDepartments = new ArrayList<>();

    public void addClinicDepartment(ClinicDepartment clinicDepartment) {
        clinicDepartments.add(clinicDepartment);
    }

    @Override
    public int getRowCount() {
        return clinicDepartments.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClinicDepartment clinicDepartment = clinicDepartments.get(rowIndex);
        if (columnIndex == 0) {
            return clinicDepartment.getName();
        } else if (columnIndex == 1) {
            return clinicDepartment.getStreets();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void clearClinicDepartments() {
        clinicDepartments.clear();
    }

    public List<String> getPossibleDepartmentNames() {
        List<String> possibleClinicDepartmentNames = new ArrayList<>();
        for (ClinicDepartment clinicDepartment : clinicDepartments) {
            possibleClinicDepartmentNames.add(clinicDepartment.getName());
        }
        return possibleClinicDepartmentNames;
    }

    public ClinicDepartment getClinicDepartmentAtRow(int selectedRow) {
        return clinicDepartments.get(selectedRow);
    }
}
