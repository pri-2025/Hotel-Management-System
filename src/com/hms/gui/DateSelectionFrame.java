package com.hms.gui;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.Date;

public class DateSelectionFrame extends JFrame {

    private JDateChooser checkInChooser, checkOutChooser;
    private JButton proceedButton;

    public DateSelectionFrame() {
        setTitle("Select Dates");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Font labelFont = new Font("Arial", Font.PLAIN, 18);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        JLabel checkInLabel = new JLabel("Check-In Date:");
        checkInLabel.setBounds(300, 150, 150, 30);
        checkInLabel.setFont(labelFont);
        add(checkInLabel);

        checkInChooser = new JDateChooser();
        checkInChooser.setBounds(460, 150, 200, 30);
        checkInChooser.setFont(fieldFont);
        add(checkInChooser);

        JLabel checkOutLabel = new JLabel("Check-Out Date:");
        checkOutLabel.setBounds(300, 210, 150, 30);
        checkOutLabel.setFont(labelFont);
        add(checkOutLabel);

        checkOutChooser = new JDateChooser();
        checkOutChooser.setBounds(460, 210, 200, 30);
        checkOutChooser.setFont(fieldFont);
        add(checkOutChooser);

        proceedButton = new JButton("Proceed");
        proceedButton.setBounds(370, 300, 140, 40);
        proceedButton.setFont(new Font("Arial", Font.BOLD, 18));
        add(proceedButton);

        disableManualTyping(checkInChooser);
        disableManualTyping(checkOutChooser);

        Date today = new Date();
        checkInChooser.setMinSelectableDate(today);
        checkOutChooser.setMinSelectableDate(today);

        checkInChooser.getDateEditor().addPropertyChangeListener("date", evt -> {
            Date checkInDate = checkInChooser.getDate();
            if (checkInDate != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(checkInDate);
                cal.add(Calendar.DATE, 1);
                checkOutChooser.setMinSelectableDate(cal.getTime());
            }
        });

        proceedButton.addActionListener(this::proceedAction);

        setVisible(true);
    }

    private void disableManualTyping(JDateChooser chooser) {
        ((JTextFieldDateEditor) chooser.getDateEditor()).setEditable(false);
    }

    private void proceedAction(ActionEvent e) {
        Date checkIn = checkInChooser.getDate();
        Date checkOut = checkOutChooser.getDate();

        if (checkIn == null || checkOut == null) {
            JOptionPane.showMessageDialog(this, "Please select both Check-In and Check-Out dates.");
            return;
        }

        if (!checkOut.after(checkIn)) {
            JOptionPane.showMessageDialog(this, "Check-Out must be after Check-In.");
            return;
        }

        int result = JOptionPane.showConfirmDialog(this,
                "Dates selected:\nCheck-in: " + checkIn + "\nCheck-out: " + checkOut,
                "Proceed to Room Selection", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            new AvailableRoomsFrame(checkIn, checkOut);
            dispose();
        }
    }

    public static void main(String[] args) {
        new DateSelectionFrame();
    }
}
