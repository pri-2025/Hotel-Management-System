package com.hms.gui;

import com.hms.dao.ReservationDAO;
import com.hms.dao.impl.ReservationDAOImpl;
import com.hms.entity.Reservation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReservationPanel extends JFrame {

    private JTable reservationTable;
    private DefaultTableModel tableModel;
    private JButton cancelButton, proceedButton;

    private ReservationDAO reservationDAO = new ReservationDAOImpl();
    private String guestEmail; // Logged-in user's email

    public ReservationPanel(String guestEmail) {
        this.guestEmail = guestEmail;

        setTitle("My Reservations");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel heading = new JLabel("Your Reservation Records");
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setBounds(300, 20, 350, 30);
        add(heading);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{
                "Reservation ID", "Room No", "Guest Email", "Check-In", "Check-Out", "Hotel ID"
        });

        reservationTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(reservationTable);
        scrollPane.setBounds(50, 70, 780, 300);
        add(scrollPane);

        cancelButton = new JButton("Cancel Selected Reservation");
        cancelButton.setBounds(320, 390, 220, 35);
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        add(cancelButton);

        proceedButton = new JButton("Proceed to Summary");
        proceedButton.setBounds(570, 390, 180, 35);
        proceedButton.setFont(new Font("Arial", Font.PLAIN, 14));
        add(proceedButton);

        cancelButton.addActionListener(e -> cancelSelectedReservation());
        proceedButton.addActionListener(e -> proceedToSummary());

        loadReservations();
        setVisible(true);
    }

    public ReservationPanel() {
		// TODO Auto-generated constructor stub
	}

	private void loadReservations() {
        tableModel.setRowCount(0); // Clear existing rows
        List<Reservation> list = reservationDAO.getReservationsByEmail(guestEmail);
        for (Reservation r : list) {
            tableModel.addRow(new Object[]{
                    r.getReservationId(),
                    r.getRoomNum(),
                    r.getGuestEmail(),
                    r.getCheckInDate(),
                    r.getCheckOutDate(),
                    r.getHotelId()
            });
        }
    }

    private void cancelSelectedReservation() {
        int row = reservationTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a reservation to cancel.");
            return;
        }

        int resId = (int) tableModel.getValueAt(row, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel this reservation?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            reservationDAO.cancelReservation(resId);
            JOptionPane.showMessageDialog(this, "Reservation canceled successfully.");
            loadReservations(); // Refresh table
        }
    }

    private void proceedToSummary() {
        int selectedRow = reservationTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a reservation to proceed.");
            return;
        }

        int reservationId = (int) tableModel.getValueAt(selectedRow, 0);
    }
}
