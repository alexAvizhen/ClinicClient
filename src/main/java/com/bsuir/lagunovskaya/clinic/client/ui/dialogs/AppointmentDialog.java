package com.bsuir.lagunovskaya.clinic.client.ui.dialogs;

import com.bsuir.lagunovskaya.clinic.client.ui.DoctorFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentDialog extends JDialog {

    private JTextField doctorLoginField = new JTextField(20);
    private JTextField patientLoginField = new JTextField(20);
    private JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
    private JTextArea commentToAppointment = new JTextArea(30, 20);

    public AppointmentDialog(final DoctorFrame doctorFrame, String doctorLogin, String patientLogin) {
        super(doctorFrame);

        setTitle("Оформление приёма");
        setLayout(new BorderLayout());
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        JPanel appointmentDialogMainPanel = new JPanel(new GridLayout(4, 1));

        JPanel tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Логин врача"));
        doctorLoginField.setText(doctorLogin);
        doctorLoginField.setEditable(false);
        tempRowPanel.add(doctorLoginField);
        appointmentDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Логин пациента"));
        patientLoginField.setText(patientLogin);
        patientLoginField.setEditable(false);
        tempRowPanel.add(patientLoginField);
        appointmentDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new FlowLayout());
        tempRowPanel.add(new JLabel("Дата приёма"));
        final String dateFormatPattern = "yyyy-MM-dd HH:mm:ss";
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, dateFormatPattern);
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date());
        tempRowPanel.add(timeSpinner);
        appointmentDialogMainPanel.add(tempRowPanel);

        tempRowPanel = new JPanel(new BorderLayout());
        tempRowPanel.add(new JLabel("Коментарий к приёму"), BorderLayout.WEST);
        tempRowPanel.add(commentToAppointment, BorderLayout.CENTER);
        appointmentDialogMainPanel.add(tempRowPanel);

        add(appointmentDialogMainPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton makeAnAppointment = new JButton("Назначить приём");
        buttonsPanel.add(makeAnAppointment);
        makeAnAppointment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = (Date) timeSpinner.getValue();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatPattern);
                String message = "Создан приём для доктора " + doctorLoginField.getText() +
                        " и пациента " + patientLoginField.getText() + " на " + simpleDateFormat.format(date) + " с комментарием " +
                        commentToAppointment.getText();
                JOptionPane.showMessageDialog(null, message);
                doctorFrame.loadClinicDepartments();
                dispose();
            }
        });

        add(buttonsPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
}
