package com.hms.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomeFrame extends JFrame {

    public WelcomeFrame() {
        setTitle("Welcome");
        setSize(700, 500);
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JLabel titleLabel = new JLabel("Welcome to Neelam's The Grand Hotel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 35));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 30, 0));
        add(titleLabel, BorderLayout.NORTH);


        JButton startButton = new JButton("Start Booking");
        startButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        startButton.setPreferredSize(new Dimension(200, 60));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        add(buttonPanel, BorderLayout.CENTER);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); 
                new DateSelectionFrame();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new WelcomeFrame();
    }
}
