package com.bsuir.lagunovskaya.clinic.client.ui;

import com.bsuir.lagunovskaya.clinic.client.service.ClientCommandSender;
import com.bsuir.lagunovskaya.clinic.communication.AllClinicDepartmentsServerResponse;
import com.bsuir.lagunovskaya.clinic.communication.ClientCommand;
import com.bsuir.lagunovskaya.clinic.communication.ClinicDepartmentServerResponse;
import com.bsuir.lagunovskaya.clinic.communication.ServerResponse;
import com.bsuir.lagunovskaya.clinic.communication.UserServerResponse;
import com.bsuir.lagunovskaya.clinic.communication.entity.ClinicDepartment;
import com.bsuir.lagunovskaya.clinic.communication.entity.Doctor;
import com.bsuir.lagunovskaya.clinic.communication.entity.Patient;
import com.bsuir.lagunovskaya.clinic.communication.entity.User;

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
import java.util.Arrays;

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

        loadClinicDepartmentsOnStart();
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
        patientsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    String selectedPatientLogin = patientsList.getSelectedValue();
                    ClientCommand getUserByLoginCommand
                            = new ClientCommand("getUserByLogin", Arrays.asList(selectedPatientLogin));
                    UserServerResponse userServerResponse = (UserServerResponse) ClientCommandSender.sendClientCommand(getUserByLoginCommand);
                    Patient selectedPatient = (Patient) userServerResponse.getUser();
                    String departmentInfo = "Относится к отеделению: " + selectedPatient.getClinicDepartment().getName() +
                            ", логин: " + selectedPatient.getLogin();
                    JOptionPane.showMessageDialog(DoctorFrame.this, departmentInfo,
                            "Информация о пациенте", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void addDoctorListListeners() {
        doctorsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1) {
                    String selectedDoctorLogin = doctorsList.getSelectedValue();
                    ClientCommand getUserByLoginCommand
                            = new ClientCommand("getUserByLogin", Arrays.asList(selectedDoctorLogin));
                    UserServerResponse userServerResponse = (UserServerResponse) ClientCommandSender.sendClientCommand(getUserByLoginCommand);
                    Doctor selectedDoctor = (Doctor) userServerResponse.getUser();
                    String departmentInfo = "Относится к отеделению: " + selectedDoctor.getClinicDepartment().getName() +
                            ", логин: " + selectedDoctor.getLogin();
                    JOptionPane.showMessageDialog(DoctorFrame.this, departmentInfo,
                            "Информация о докторе", JOptionPane.INFORMATION_MESSAGE);
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
                for (Doctor departmentDoctor : clinicDepartment.getDoctors()) {
                    doctorsListModel.addElement(departmentDoctor.getLogin());
                }
                patientsListModel.clear();
                for (Patient departmentPatient : clinicDepartment.getPatients()) {
                    patientsListModel.addElement(departmentPatient.getLogin());
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
                    if (clinicDepartment != null) {
                        String departmentInfo = "Название отделения: " + clinicDepartment.getName() +
                                ", входящие улицы: " + clinicDepartment.getStreets();
                        JOptionPane.showMessageDialog(DoctorFrame.this, departmentInfo,
                                "Информация об отделении", JOptionPane.INFORMATION_MESSAGE);
                    }
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

    private void loadClinicDepartmentsOnStart() {
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

}
