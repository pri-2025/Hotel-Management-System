package com.hms.gui;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class ConfirmationFrame extends JFrame {

    public ConfirmationFrame(String guestName, int roomNumber, Date checkIn, Date checkOut) {
        setTitle("Booking Confirmation");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1, 10, 10));

        JLabel title = new JLabel("Reservation Confirmed", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 22));
        add(title);

        JLabel nameLabel = new JLabel("Guest Name: " + guestName, SwingConstants.CENTER);
        JLabel roomLabel = new JLabel("Room Number: " + roomNumber, SwingConstants.CENTER);
        JLabel checkInLabel = new JLabel("Check-In Date: " + checkIn.toString(), SwingConstants.CENTER);
        JLabel checkOutLabel = new JLabel("Check-Out Date: " + checkOut.toString(), SwingConstants.CENTER);
        JLabel thankYouLabel = new JLabel("Thank you for booking with us!", SwingConstants.CENTER);

        add(nameLabel);
        add(roomLabel);
        add(checkInLabel);
        add(checkOutLabel);
        add(thankYouLabel);

        setVisible(true);
    }
}
