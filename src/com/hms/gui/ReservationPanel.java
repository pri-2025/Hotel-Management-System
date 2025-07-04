package com.hms.gui;

import com.hms.dao.ReservationDAO;
import com.hms.dao.RoomDAO;
import com.hms.dao.impl.ReservationDAOImpl;
import com.hms.dao.impl.RoomDAOImpl;
import com.hms.entity.Reservation;
import com.hms.entity.Room;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class ReservationPanel extends JFrame {

    private JDateChooser checkInChooser, checkOutChooser;
    private JComboBox<String> roomDropdown;
    private JTextField emailField;
    private JButton showButton, reserveButton;

    private RoomDAO roomDAO = new RoomDAOImpl();
    private ReservationDAO reservationDAO = new ReservationDAOImpl();

    private List<Room> availableRooms;

    public ReservationPanel() {
        setTitle("Make a Reservation");
        setSize(500, 400);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel checkInLabel = new JLabel("Check-In Date:");
        checkInLabel.setBounds(40, 40, 100, 25);
        add(checkInLabel);

        checkInChooser = new JDateChooser();
        checkInChooser.setBounds(160, 40, 200, 25);
        add(checkInChooser);

        JLabel checkOutLabel = new JLabel("Check-Out Date:");
        checkOutLabel.setBounds(40, 80, 100, 25);
        add(checkOutLabel);

        checkOutChooser = new JDateChooser();
        checkOutChooser.setBounds(160, 80, 200, 25);
        add(checkOutChooser);

        showButton = new JButton("Show Available Rooms");
        showButton.setBounds(160, 120, 200, 30);
        add(showButton);

        JLabel roomLabel = new JLabel("Select Room:");
        roomLabel.setBounds(40, 170, 100, 25);
        add(roomLabel);

        roomDropdown = new JComboBox<>();
        roomDropdown.setBounds(160, 170, 200, 25);
        add(roomDropdown);

        JLabel emailLabel = new JLabel("Guest Email:");
        emailLabel.setBounds(40, 210, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(160, 210, 200, 25);
        add(emailField);

        reserveButton = new JButton("Reserve");
        reserveButton.setBounds(160, 260, 200, 30);
        add(reserveButton);

        showButton.addActionListener(e -> fetchAvailableRooms());
        reserveButton.addActionListener(e -> makeReservation());

        setVisible(true);
    }

    public ReservationPanel(String email) {
		// TODO Auto-generated constructor stub
	}

	private void fetchAvailableRooms() {
        java.util.Date in = checkInChooser.getDate();
        java.util.Date out = checkOutChooser.getDate();

        if (in == null || out == null || out.before(in)) {
            JOptionPane.showMessageDialog(this, "Please select valid check-in and check-out dates.");
            return;
        }

        availableRooms = roomDAO.getAvailableRooms(new Date(in.getTime()), new Date(out.getTime()));
        roomDropdown.removeAllItems();

        if (availableRooms.isEmpty()) {
            roomDropdown.addItem("No rooms available");
        } else {
            for (Room r : availableRooms) {
                roomDropdown.addItem("Room " + r.getRoom_num() + " - ₹" + r.getPrice());
            }
        }
    }

    private void makeReservation() {
        if (availableRooms == null || availableRooms.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No available rooms to book.");
            return;
        }

        int index = roomDropdown.getSelectedIndex();
        if (index == -1 || index >= availableRooms.size()) {
            JOptionPane.showMessageDialog(this, "Please select a valid room.");
            return;
        }

        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Guest email cannot be empty.");
            return;
        }

        java.util.Date in = checkInChooser.getDate();
        java.util.Date out = checkOutChooser.getDate();

        Room selectedRoom = availableRooms.get(index);

        Reservation reservation = new Reservation(
                selectedRoom.getRoom_num(),
                email,
                new Date(in.getTime()),
                new Date(out.getTime()),
                1 // Fixed hotel ID for now
        );

        reservationDAO.addReservation(reservation);
        JOptionPane.showMessageDialog(this, "Reservation successful!");

        roomDropdown.removeAllItems();
        emailField.setText("");
    }

    public static void main(String[] args) {
        new ReservationPanel();
    }
}
