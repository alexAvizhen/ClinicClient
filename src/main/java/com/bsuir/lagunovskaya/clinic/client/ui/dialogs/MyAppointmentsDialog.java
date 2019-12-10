package com.bsuir.lagunovskaya.clinic.client.ui.dialogs;

import com.bsuir.lagunovskaya.clinic.client.service.ClientCommandSender;
import com.bsuir.lagunovskaya.clinic.client.ui.frames.UserFrame;
import com.bsuir.lagunovskaya.clinic.client.ui.tables.models.AppointmentsTableModel;
import com.bsuir.lagunovskaya.clinic.communication.command.ClientCommand;
import com.bsuir.lagunovskaya.clinic.communication.entity.Appointment;
import com.bsuir.lagunovskaya.clinic.communication.entity.User;
import com.bsuir.lagunovskaya.clinic.communication.response.UserAppointmentsServerResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class MyAppointmentsDialog extends JDialog {

    private AppointmentsTableModel appointmentsTableModel;
    private JTable appointmentsTable;
    private JScrollPane scrolledAppointmentsTable;

    private User activeUser;

    public MyAppointmentsDialog(final UserFrame userFrame, User activeUser) {
        super(userFrame);
        this.activeUser = activeUser;
        setTitle("Мои приёмы");
        setLayout(new BorderLayout());
        setSize(1000, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        JPanel appointmentsCenterPanel = new JPanel(new BorderLayout());
        appointmentsCenterPanel.add(new JLabel("Мои приёмы"), BorderLayout.CENTER);
        appointmentsTableModel = new AppointmentsTableModel();
        appointmentsTable = new JTable(appointmentsTableModel);
        scrolledAppointmentsTable = new JScrollPane(appointmentsTable);
        appointmentsCenterPanel.add(scrolledAppointmentsTable, BorderLayout.CENTER);

        JPanel appointmentsButtonPanel = new JPanel(new FlowLayout());
        JButton saveMyAppointmentsInFileBtn = new JButton("Сохранить мои приёмы в файл");
        appointmentsButtonPanel.add(saveMyAppointmentsInFileBtn);
        saveMyAppointmentsInFileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MyAppointmentsDialog.this, "Сохраняю в файл");
            }
        });
        appointmentsCenterPanel.add(appointmentsButtonPanel, BorderLayout.SOUTH);
        loadAppointmentsForActiveUser();
        add(appointmentsCenterPanel);
        setVisible(true);
    }

    private void loadAppointmentsForActiveUser() {
        ClientCommand getAppointmentsByUserLoginCommand = new ClientCommand("getAppointmentsByUserLogin", Arrays.asList(activeUser.getLogin()));
        UserAppointmentsServerResponse serverResponse = ((UserAppointmentsServerResponse) ClientCommandSender.sendClientCommand(getAppointmentsByUserLoginCommand));
        for (Appointment activeUserAppointment : serverResponse.getAppointments()) {
            appointmentsTableModel.addAppointment(activeUserAppointment);
        }
        appointmentsTableModel.fireTableDataChanged();
    }
}
