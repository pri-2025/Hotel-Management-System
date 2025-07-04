package com.hms.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomeFrame extends JFrame {

    public WelcomeFrame() {
        setTitle("Welcome");
        setSize(500, 300);
        setLocationRelativeTo(null); // center the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Hotel name label
        JLabel titleLabel = new JLabel("Welcome to Neelam's The Grand Hotel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Button to proceed
        JButton startButton = new JButton("Start Booking");
        startButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        startButton.setPreferredSize(new Dimension(160, 40));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.CENTER);

        // Action Listener
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // close welcome frame
                new DateSelectionFrame(); // open next frame (we'll create this next)
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new WelcomeFrame();
    }
}
