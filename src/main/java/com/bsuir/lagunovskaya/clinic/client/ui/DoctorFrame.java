package com.bsuir.lagunovskaya.clinic.client.ui;

import com.bsuir.lagunovskaya.clinic.client.service.ClientCommandSender;
import com.bsuir.lagunovskaya.clinic.client.ui.dialogs.AppointmentDialog;
import com.bsuir.lagunovskaya.clinic.client.ui.dialogs.DepartmentDialog;
import com.bsuir.lagunovskaya.clinic.client.ui.dialogs.DoctorDialog;
import com.bsuir.lagunovskaya.clinic.client.ui.dialogs.PatientDialog;
import com.bsuir.lagunovskaya.clinic.client.utils.StringUtils;
import com.bsuir.lagunovskaya.clinic.communication.AllClinicDepartmentsServerResponse;
import com.bsuir.lagunovskaya.clinic.communication.ClientCommand;
import com.bsuir.lagunovskaya.clinic.communication.ClinicDepartmentServerResponse;
import com.bsuir.lagunovskaya.clinic.communication.UserServerResponse;
import com.bsuir.lagunovskaya.clinic.communication.entity.ClinicDepartment;
import com.bsuir.lagunovskaya.clinic.communication.entity.Doctor;
import com.bsuir.lagunovskaya.clinic.communication.entity.Patient;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoctorFrame extends JFrame {

    private LoginFrame loginFrame;
    private Doctor activeDoctor;

    private DefaultListModel<String> clinicDepartmentsListModel;
    private JList<String> clinicDepartmentsList;
    private JScrollPane scrolledClinicDepartmentsList;

    private DefaultListModel<String> doctorsListModel;
    private JList<String> doctorsList;
    private JScrollPane scrolledDoctorsList;

    private DefaultListModel<String> patientsListModel;
    private JList<String> patientsList;
    private JScrollPane scrolledPatientsList;

    private JTextField doctorLoginForAppointmentField = new JTextField(20);
    private JTextField patientLoginForAppointmentField = new JTextField(20);


    public DoctorFrame(final LoginFrame loginFrame, Doctor doctor) throws HeadlessException {
        this.loginFrame = loginFrame;
        this.activeDoctor = doctor;
        setTitle("Doctor Clinic Application Client"); //название окна
        setSize(new Dimension(800, 600)); //устанавливаем размер окна
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); //выход из приложения при нажатии  на крестит в окне
        setLocationRelativeTo(null);
        addListenerOnCloseDoctorFrame();

        JPanel northPanel = buildNorthPanel();
        add(northPanel, BorderLayout.NORTH);

        JPanel centerPanel = buildCenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        JPanel eastPanel = buildEastPanel();
        add(eastPanel, BorderLayout.EAST);


        loadClinicDepartments();
        setVisible(true);
    }

    private JPanel buildCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 3));

        clinicDepartmentsListModel = new DefaultListModel<>();
        clinicDepartmentsList = new JList<>(clinicDepartmentsListModel);
        clinicDepartmentsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addClinicListListeners();
        scrolledClinicDepartmentsList = new JScrollPane();
        scrolledClinicDepartmentsList.setViewportView(clinicDepartmentsList);
        scrolledClinicDepartmentsList.setColumnHeaderView(new JLabel("Отделения поликлинники"));
        centerPanel.add(scrolledClinicDepartmentsList);

        doctorsListModel = new DefaultListModel<>();
        doctorsList = new JList<>(doctorsListModel);
        doctorsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addDoctorListListeners();
        scrolledDoctorsList = new JScrollPane();
        scrolledDoctorsList.setViewportView(doctorsList);
        scrolledDoctorsList.setColumnHeaderView(new JLabel("Врачи выбранного отделения"));
        centerPanel.add(scrolledDoctorsList);

        patientsListModel = new DefaultListModel<>();
        patientsList = new JList<>(patientsListModel);
        patientsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addPatientsListListeners();
        scrolledPatientsList = new JScrollPane();
        scrolledPatientsList.setViewportView(patientsList);
        scrolledPatientsList.setColumnHeaderView(new JLabel("Пациенты выбранного отделения"));
        centerPanel.add(scrolledPatientsList);

        return centerPanel;
    }

    private void addPatientsListListeners() {
        patientsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String selectedValue = patientsList.getSelectedValue();
                if (selectedValue != null && !selectedValue.isEmpty()) {
                    patientLoginForAppointmentField.setText(patientsList.getSelectedValue());
                }
            }
        });
        patientsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    String selectedPatientLogin = patientsList.getSelectedValue();
                    ClientCommand getUserByLoginCommand
                            = new ClientCommand("getUserByLogin", Arrays.asList(selectedPatientLogin));
                    UserServerResponse userServerResponse = (UserServerResponse) ClientCommandSender.sendClientCommand(getUserByLoginCommand);
                    Patient selectedPatient = (Patient) userServerResponse.getUser();
                    new PatientDialog(DoctorFrame.this, selectedPatient);
                }
            }
        });
    }

    private void addDoctorListListeners() {
        doctorsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String selectedValue = doctorsList.getSelectedValue();
                if (selectedValue != null && !selectedValue.isEmpty()) {
                    doctorLoginForAppointmentField.setText(doctorsList.getSelectedValue());
                }
            }
        });
        doctorsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    String selectedDoctorLogin = doctorsList.getSelectedValue();
                    ClientCommand getUserByLoginCommand
                            = new ClientCommand("getUserByLogin", Arrays.asList(selectedDoctorLogin));
                    UserServerResponse userServerResponse = (UserServerResponse) ClientCommandSender.sendClientCommand(getUserByLoginCommand);
                    Doctor selectedDoctor = (Doctor) userServerResponse.getUser();
                    new DoctorDialog(DoctorFrame.this, selectedDoctor);
                }
            }
        });

    }

    private void addClinicListListeners() {
        clinicDepartmentsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String selectedClinicDepartmentName = clinicDepartmentsList.getSelectedValue();
                ClientCommand getClinicDepartmentByNameCommand =
                        new ClientCommand("getClinicDepartmentByName", Arrays.asList(selectedClinicDepartmentName));
                ClinicDepartmentServerResponse clinicDepartmentServerResponse
                        = (ClinicDepartmentServerResponse) ClientCommandSender.sendClientCommand(getClinicDepartmentByNameCommand);
                ClinicDepartment clinicDepartment = clinicDepartmentServerResponse.getClinicDepartment();
                doctorsListModel.clear();
                patientsListModel.clear();
                if (clinicDepartment != null) {
                    for (Doctor departmentDoctor : clinicDepartment.getDoctors()) {
                        doctorsListModel.addElement(departmentDoctor.getLogin());
                    }
                    for (Patient departmentPatient : clinicDepartment.getPatients()) {
                        patientsListModel.addElement(departmentPatient.getLogin());
                    }
                }
            }
        });

        clinicDepartmentsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    String selectedDepartmentName = clinicDepartmentsList.getSelectedValue();
                    ClientCommand getClinicDepartmentByNameCommand =
                            new ClientCommand("getClinicDepartmentByName", Arrays.asList(selectedDepartmentName));
                    ClinicDepartmentServerResponse clinicDepartmentServerResponse
                            = (ClinicDepartmentServerResponse) ClientCommandSender.sendClientCommand(getClinicDepartmentByNameCommand);
                    ClinicDepartment clinicDepartment = clinicDepartmentServerResponse.getClinicDepartment();
                    new DepartmentDialog(DoctorFrame.this, clinicDepartment.getClinic(), clinicDepartment);
                }
            }
        });
    }

    private JPanel buildNorthPanel() {
        JPanel northPanel = new JPanel(new FlowLayout());

        JLabel activeDoctorInfoLabel = new JLabel("Ваш Логин: " + activeDoctor.getLogin() +
                ", Ваше отделение: " + activeDoctor.getClinicDepartment().getName());
        northPanel.add(activeDoctorInfoLabel);

        return northPanel;
    }

    public void loadClinicDepartments() {
        ClientCommand getAllClinicDepartmentsClientCommand = new ClientCommand("getAllClinicDepartments");
        AllClinicDepartmentsServerResponse allClinicDepartmentsServerResponse =
                (AllClinicDepartmentsServerResponse) ClientCommandSender.sendClientCommand(getAllClinicDepartmentsClientCommand);
        clinicDepartmentsListModel.clear();
        for (ClinicDepartment clinicDepartment : allClinicDepartmentsServerResponse.getClinicDepartments()) {
            clinicDepartmentsListModel.addElement(clinicDepartment.getName());
        }
        scrolledClinicDepartmentsList.setVisible(true);
    }

    private void addListenerOnCloseDoctorFrame() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                DoctorFrame.this.loginFrame.activateLoginFrame();
            }
        });
    }

    private JPanel buildEastPanel() {
        JPanel eastPanel = new JPanel(new GridLayout(4, 1));
        JPanel tempRowPanel = new JPanel(new FlowLayout());
        JButton createDepartment = new JButton("Создать отделение");
        tempRowPanel.add(createDepartment);
        createDepartment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DepartmentDialog(DoctorFrame.this, activeDoctor.getClinicDepartment().getClinic(), null);
            }
        });
        eastPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        JButton createDoctor = new JButton("Создать врача");
        tempRowPanel.add(createDoctor);
        createDoctor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DoctorDialog(DoctorFrame.this, null);
            }
        });
        eastPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        JButton createPatient = new JButton("Создать пациента");
        tempRowPanel.add(createPatient);
        createPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PatientDialog(DoctorFrame.this, null);
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
                    JOptionPane.showMessageDialog(DoctorFrame.this,
                            "Выберите доктора и пациента, чтобы назначить приём");
                } else {
                    new AppointmentDialog(DoctorFrame.this, doctorLogin, patientLogin);
                }
            }
        });
        eastPanel.add(appointmentPanel);

        return eastPanel;
    }

    public java.util.List<String> getPossibleDepartmentNames() {
        List<String> departmentNames = new ArrayList<>();
        for (int i = 0; i < clinicDepartmentsListModel.getSize(); i++) {
            departmentNames.add(clinicDepartmentsListModel.getElementAt(i));
        }
        return departmentNames;
    }

}
