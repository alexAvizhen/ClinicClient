package com.bsuir.lagunovskaya.clinic.client.ui;

import com.bsuir.lagunovskaya.clinic.client.service.ClientCommandSender;
import com.bsuir.lagunovskaya.clinic.communication.ClientCommand;
import com.bsuir.lagunovskaya.clinic.communication.LoginServerResponse;
import com.bsuir.lagunovskaya.clinic.communication.entity.Doctor;
import com.bsuir.lagunovskaya.clinic.communication.entity.User;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class LoginFrame extends JFrame {

    private JPanel loginPanel;

    private JTextField loginField;
    private JTextField passwordField;
    private JButton loginButton;


    public LoginFrame() throws HeadlessException {
        setTitle("Clinic Application"); //название окна

        setSize(new Dimension(400, 200)); //устанавливаем размер окна
        setDefaultCloseOperation(EXIT_ON_CLOSE); //выход из приложения при нажатии  на крестит в окне
        setVisible(false); //видно окно или нет. меняется в мейне
        setLocationRelativeTo(null);

        loginPanel = buildLoginPanel();
        add(loginPanel, BorderLayout.CENTER);
    }

    private JPanel buildLoginPanel() {
        final JPanel loginPanel = new JPanel(new GridLayout(3, 1));

        JPanel loginLabelAndFieldPanel = new JPanel(new FlowLayout());
        loginLabelAndFieldPanel.add(new JLabel("Логин   "));
        loginField = new JTextField(10);
        loginLabelAndFieldPanel.add(loginField);
        loginPanel.add(loginLabelAndFieldPanel);

        JPanel passwordLabelAndFieldPanel = new JPanel(new FlowLayout());
        passwordLabelAndFieldPanel.add(new JLabel("Пароль"));
        passwordField = new JTextField(10);
        passwordLabelAndFieldPanel.add(passwordField);
        loginPanel.add(passwordLabelAndFieldPanel);

        JPanel loginButtonPanel = new JPanel(new FlowLayout());
        loginButton = new JButton("Войти");
        loginButtonPanel.add(loginButton);
        loginPanel.add(loginButtonPanel);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginField.getText();
                String password = passwordField.getText();
                ClientCommand loginCommand = new ClientCommand("login", Arrays.asList(login, password));
                LoginServerResponse loginServerResponse = (LoginServerResponse) ClientCommandSender.sendClientCommand(loginCommand);
                if ("success".equals(loginServerResponse.getLoginStatus())) {
                    LoginFrame.this.setVisible(false);
                    User user = loginServerResponse.getUser();
                    UserFrame userFrame = new UserFrame(LoginFrame.this, user);

                } else {
                    JOptionPane.showMessageDialog(null, "Cannot login. Please check login and password");
                }
            }
        });
        return loginPanel;
    }

    public void activateLoginFrame() {
        loginField.setText("");
        passwordField.setText("");
        setVisible(true);
    }
}
