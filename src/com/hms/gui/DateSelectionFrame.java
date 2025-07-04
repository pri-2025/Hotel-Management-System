package com.hms.gui;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.*;
import java.sql.Date;

public class DateSelectionFrame extends JFrame {

    private JDateChooser checkInChooser, checkOutChooser;
    private JButton nextButton;

    public DateSelectionFrame() {
        setTitle("Select Dates");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 20, 10));
        setResizable(false);

        // Labels
        JLabel checkInLabel = new JLabel("Check-In Date:");
        JLabel checkOutLabel = new JLabel("Check-Out Date:");

        // Date Choosers
        checkInChooser = new JDateChooser();
        checkOutChooser = new JDateChooser();

        // Next Button
        nextButton = new JButton("Search Available Rooms");

        // Add components
        add(new JLabel()); // empty cell
        add(new JLabel("Select Your Stay", SwingConstants.CENTER)).setFont(new Font("Arial", Font.BOLD, 18));
        add(checkInLabel);
        add(checkInChooser);
        add(checkOutLabel);
        add(checkOutChooser);
        add(new JLabel());
        add(nextButton);

        // Action
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                java.util.Date checkIn = checkInChooser.getDate();
                java.util.Date checkOut = checkOutChooser.getDate();

                if (checkIn == null || checkOut == null) {
                    JOptionPane.showMessageDialog(null, "Please select both dates.");
                } else if (!checkOut.after(checkIn)) {
                    JOptionPane.showMessageDialog(null, "Check-out must be after check-in.");
                } else {
                    dispose();
                    new AvailableRoomsFrame(new Date(checkIn.getTime()), new Date(checkOut.getTime()));
                }
            }
        });

        setVisible(true);
    }
}
