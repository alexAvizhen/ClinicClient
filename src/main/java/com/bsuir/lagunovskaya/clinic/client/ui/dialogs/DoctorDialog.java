package com.bsuir.lagunovskaya.clinic.client.ui.dialogs;

import com.bsuir.lagunovskaya.clinic.communication.entity.Doctor;

import javax.swing.*;
import java.awt.*;

public class DoctorDialog extends JDialog {

    private Doctor doctor;



    public DoctorDialog(Frame owner, Doctor doctor) {
        super(owner);
        this.doctor = doctor;
    }
}
