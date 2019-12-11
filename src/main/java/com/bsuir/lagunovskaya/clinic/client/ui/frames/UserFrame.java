package com.bsuir.lagunovskaya.clinic.client.ui.frames;

import com.bsuir.lagunovskaya.clinic.client.service.ClientCommandSender;
import com.bsuir.lagunovskaya.clinic.client.ui.dialogs.AppointmentDialog;
import com.bsuir.lagunovskaya.clinic.client.ui.dialogs.AppointmentsStatsDialog;
import com.bsuir.lagunovskaya.clinic.client.ui.dialogs.DepartmentDialog;
import com.bsuir.lagunovskaya.clinic.client.ui.dialogs.DoctorDialog;
import com.bsuir.lagunovskaya.clinic.client.ui.dialogs.MyAppointmentsDialog;
import com.bsuir.lagunovskaya.clinic.client.ui.dialogs.PatientDialog;
import com.bsuir.lagunovskaya.clinic.client.ui.tables.models.ClinicDepartmentsTableModel;
import com.bsuir.lagunovskaya.clinic.client.ui.tables.models.DoctorsTableModel;
import com.bsuir.lagunovskaya.clinic.client.ui.tables.models.PatientTableModel;
import com.bsuir.lagunovskaya.clinic.client.utils.StringUtils;
import com.bsuir.lagunovskaya.clinic.communication.response.AllClinicDepartmentsServerResponse;
import com.bsuir.lagunovskaya.clinic.communication.command.ClientCommand;
import com.bsuir.lagunovskaya.clinic.communication.entity.ClinicDepartment;
import com.bsuir.lagunovskaya.clinic.communication.entity.Doctor;
import com.bsuir.lagunovskaya.clinic.communication.entity.Patient;
import com.bsuir.lagunovskaya.clinic.communication.entity.User;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserFrame extends JFrame {

    private LoginFrame loginFrame;
    private User activeUser;

    private ClinicDepartmentsTableModel clinicDepartmentsTableModel;
    private JTable clinicDepartmentsTable;
    private JScrollPane scrolledClinicDepartmentsTable;

    private DoctorsTableModel doctorsTableModel;
    private JTable doctorsTable;
    private JScrollPane scrolledDoctorsTable;

    private PatientTableModel patientsTableModel;
    private JTable patientsTable;
    private JScrollPane scrolledPatientsTable;

    private JTextField doctorLoginForAppointmentField = new JTextField(20);
    private JTextField patientLoginForAppointmentField = new JTextField(20);

    private boolean isReadOnlyMode = false;


    public UserFrame(final LoginFrame loginFrame, User activeUser) throws HeadlessException {
        this.loginFrame = loginFrame;
        this.activeUser = activeUser;
        if (activeUser.isAdmin()) {
            isReadOnlyMode = false;
        } else {
            isReadOnlyMode = true;
        }
        setTitle("Doctor Clinic Application Client"); //название окна
        setSize(new Dimension(1200, 600)); //устанавливаем размер окна
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //выход из приложения при нажатии  на крестит в окне
        setLocationRelativeTo(null);
        addListenerOnCloseDoctorFrame();

        JPanel northPanel = buildNorthPanel();
        add(northPanel, BorderLayout.NORTH);

        JPanel centerPanel = buildCenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        if (activeUser.isAdmin()) {
            JPanel eastPanel = buildEastPanel();
            add(eastPanel, BorderLayout.EAST);
        }

        loadClinicDepartments();
        setVisible(true);
    }

    private JPanel buildCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(3, 1));

        clinicDepartmentsTableModel = new ClinicDepartmentsTableModel();
        clinicDepartmentsTable = new JTable(clinicDepartmentsTableModel);
        scrolledClinicDepartmentsTable = new JScrollPane(clinicDepartmentsTable);
        clinicDepartmentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addClinicTableListeners();
        JPanel tempRowPanel = new JPanel(new BorderLayout());
        tempRowPanel.add(new JLabel("Отделения поликлинники"), BorderLayout.NORTH);
        tempRowPanel.add(scrolledClinicDepartmentsTable, BorderLayout.CENTER);
        centerPanel.add(tempRowPanel);

        doctorsTableModel = new DoctorsTableModel();
        doctorsTable = new JTable(doctorsTableModel);
        doctorsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addDoctorsTablesListeners();
        scrolledDoctorsTable = new JScrollPane(doctorsTable);
        tempRowPanel = new JPanel(new BorderLayout());
        tempRowPanel.add(new JLabel("Врачи выбранного отделения"), BorderLayout.NORTH);
        tempRowPanel.add(scrolledDoctorsTable, BorderLayout.CENTER);
        centerPanel.add(tempRowPanel);

        patientsTableModel = new PatientTableModel();
        patientsTable = new JTable(patientsTableModel);
        patientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addPatientsTableListeners();
        scrolledPatientsTable = new JScrollPane(patientsTable);
        tempRowPanel = new JPanel(new BorderLayout());
        tempRowPanel.add(new JLabel("Пациенты выбранного отделения"), BorderLayout.NORTH);
        tempRowPanel.add(scrolledPatientsTable, BorderLayout.CENTER);
        centerPanel.add(tempRowPanel);

        return centerPanel;
    }

    private void addPatientsTableListeners() {
        patientsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = patientsTable.getSelectedRow();
                Patient selectedPatient = patientsTableModel.getPatientAtRow(selectedRow);
                if (e.getClickCount() == 1) {
                    //single click
                    patientLoginForAppointmentField.setText(selectedPatient.getLogin());
                } else {
                    //double click
                    new PatientDialog(UserFrame.this, selectedPatient, activeUser.isAdmin());
                }
            }
        });
    }

    private void addDoctorsTablesListeners() {
        doctorsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = doctorsTable.getSelectedRow();
                Doctor selectedDoctor = doctorsTableModel.getDoctorAtRow(selectedRow);
                if (e.getClickCount() == 1) {
                    //single click
                    doctorLoginForAppointmentField.setText(selectedDoctor.getLogin());
                } else {
                    //double click
                    new DoctorDialog(UserFrame.this, selectedDoctor, activeUser.isAdmin());
                }
            }
        });

    }

    private void addClinicTableListeners() {
        clinicDepartmentsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int selectedRow = clinicDepartmentsTable.getSelectedRow();
                ClinicDepartment clinicDepartment = clinicDepartmentsTableModel.getClinicDepartmentAtRow(selectedRow);
                if (e.getClickCount() == 1) {
                    //single click
                    fillDoctorsAndPatientsTablesForDepartment(clinicDepartment);
                } else {

                    new DepartmentDialog(UserFrame.this, clinicDepartment.getClinic(), clinicDepartment, activeUser.isAdmin());
                }
            }
        });
    }

    private void fillDoctorsAndPatientsTablesForDepartment(ClinicDepartment clinicDepartment) {
        doctorsTableModel.clearDoctors();
        patientsTableModel.clearPatients();
        if (clinicDepartment != null) {
            for (Doctor departmentDoctor : clinicDepartment.getDoctors()) {
                doctorsTableModel.addDoctor(departmentDoctor);
            }
            for (Patient departmentPatient : clinicDepartment.getPatients()) {
                patientsTableModel.addPatient(departmentPatient);
            }
        }
        doctorsTableModel.fireTableDataChanged();
        patientsTableModel.fireTableDataChanged();
    }

    private JPanel buildNorthPanel() {
        JPanel northPanel = new JPanel(new FlowLayout());

        JLabel activeUserInfoLabel = new JLabel();
        if (activeUser.isAdmin()) {
            Doctor activeDoctor = (Doctor) this.activeUser;
            activeUserInfoLabel.setText("Добро пожаловать, Ваш Логин: " + activeUser.getLogin() +
                    ", Ваше отделение: " + activeDoctor.getClinicDepartment().getName());
        } else {
            Patient activePatient = (Patient) this.activeUser;
            activeUserInfoLabel.setText("Добро пожаловать, Ваш Логин: " + activeUser.getLogin() +
                    ", Ваше отделение: " + activePatient.getClinicDepartment().getName());
        }
        northPanel.add(activeUserInfoLabel);

        return northPanel;
    }

    public void loadClinicDepartments() {
        ClientCommand getAllClinicDepartmentsClientCommand = new ClientCommand("getAllClinicDepartments");
        AllClinicDepartmentsServerResponse allClinicDepartmentsServerResponse =
                (AllClinicDepartmentsServerResponse) ClientCommandSender.sendClientCommand(getAllClinicDepartmentsClientCommand);
        int selectedRow = clinicDepartmentsTable.getSelectedRow();
        clinicDepartmentsTableModel.clearClinicDepartments();
        for (ClinicDepartment clinicDepartment : allClinicDepartmentsServerResponse.getClinicDepartments()) {
            clinicDepartmentsTableModel.addClinicDepartment(clinicDepartment);
        }
        clinicDepartmentsTableModel.fireTableDataChanged();
        if (selectedRow >= 0) {
            ClinicDepartment selectedClinicDepartment = clinicDepartmentsTableModel.getClinicDepartmentAtRow(selectedRow);
            clinicDepartmentsTable.setRowSelectionInterval(selectedRow, selectedRow);
            fillDoctorsAndPatientsTablesForDepartment(selectedClinicDepartment);
        }

    }

    private void addListenerOnCloseDoctorFrame() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                UserFrame.this.loginFrame.activateLoginFrame();
            }
        });
    }

    private JPanel buildEastPanel() {
        JPanel eastPanel = new JPanel(new GridLayout(3, 1));
        JPanel tempRowPanel = new JPanel(new GridLayout(3, 1));
        JPanel tempCellPanel = new JPanel(new FlowLayout());
        JButton createDepartment = new JButton("Создать отделение");
        tempCellPanel.add(createDepartment);
        tempRowPanel.add(tempCellPanel);
        final Doctor activeDoctor;
        if (activeUser.isAdmin()) {
            activeDoctor = ((Doctor) activeUser);
        } else {
            throw new IllegalArgumentException("This panel is available only for admins(doctors) ");
        }

        createDepartment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DepartmentDialog(UserFrame.this, activeDoctor.getClinicDepartment().getClinic(), null, activeUser.isAdmin());
            }
        });
        eastPanel.add(tempRowPanel);

//        tempRowPanel = new JPanel(new FlowLayout());
        tempCellPanel = new JPanel(new FlowLayout());
        JButton createDoctor = new JButton("Создать врача");
        tempCellPanel.add(createDoctor);
        tempRowPanel.add(tempCellPanel);
        createDoctor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DoctorDialog(UserFrame.this, null, activeUser.isAdmin());
            }
        });
        eastPanel.add(tempRowPanel);

//        tempRowPanel = new JPanel(new FlowLayout());
        tempCellPanel = new JPanel(new FlowLayout());
        JButton createPatient = new JButton("Создать пациента");
        tempCellPanel.add(createPatient);
        tempRowPanel.add(tempCellPanel);
        createPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PatientDialog(UserFrame.this, null, activeUser.isAdmin());
            }
        });
        eastPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new GridLayout(2, 1));
        tempCellPanel = new JPanel(new FlowLayout());
        JButton showMyAppointments = new JButton("Показать мои приёмы");
        tempCellPanel.add(showMyAppointments);
        tempRowPanel.add(tempCellPanel);
        showMyAppointments.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MyAppointmentsDialog(UserFrame.this, activeUser);
            }
        });
        eastPanel.add(tempRowPanel);

//        tempRowPanel = new JPanel(new FlowLayout());
        tempCellPanel = new JPanel(new FlowLayout());
        JButton showAppointmentsStatsBtn = new JButton("Показать статистику приёмов");
        tempCellPanel.add(showAppointmentsStatsBtn);
        tempRowPanel.add(tempCellPanel);
        showAppointmentsStatsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AppointmentsStatsDialog(UserFrame.this);
            }
        });
        eastPanel.add(tempRowPanel);

        JPanel appointmentPanel = new JPanel(new GridLayout(3, 1));
        tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Врач для приёма"));
        doctorLoginForAppointmentField.setEditable(false);
        tempRowPanel.add(doctorLoginForAppointmentField);
        appointmentPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Пациент для приёма"));
        patientLoginForAppointmentField.setEditable(false);
        tempRowPanel.add(patientLoginForAppointmentField);
        appointmentPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        JButton makeAndAppointmentBtn = new JButton("Назначить приём");
        tempRowPanel.add(makeAndAppointmentBtn);
        appointmentPanel.add(tempRowPanel);
        makeAndAppointmentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String doctorLogin = doctorLoginForAppointmentField.getText();
                String patientLogin = patientLoginForAppointmentField.getText();
                if (StringUtils.isEmpty(doctorLogin) || StringUtils.isEmpty(patientLogin)) {
                    JOptionPane.showMessageDialog(UserFrame.this,
                            "Выберите доктора и пациента, чтобы назначить приём");
                } else {
                    new AppointmentDialog(UserFrame.this, doctorLogin, patientLogin);
                }
            }
        });
        eastPanel.add(appointmentPanel);

        return eastPanel;
    }

    public java.util.List<String> getPossibleDepartmentNames() {
        return clinicDepartmentsTableModel.getPossibleDepartmentNames();
    }

    public void setReadOnlyMode(boolean isReadOnlyMode) {
        this.isReadOnlyMode = isReadOnlyMode;
    }

}
