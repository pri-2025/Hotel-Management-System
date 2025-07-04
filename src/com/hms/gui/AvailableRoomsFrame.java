package com.hms.gui;

import com.hms.dao.RoomDAO;
import com.hms.dao.impl.RoomDAOImpl;
import com.hms.entity.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.List;

public class AvailableRoomsFrame extends JFrame {

    private JTable roomTable;
    private JButton nextButton;
    private Date checkInDate, checkOutDate;

    public AvailableRoomsFrame(Date checkIn, Date checkOut) {
        this.checkInDate = checkIn;
        this.checkOutDate = checkOut;

        setTitle("Available Rooms");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel heading = new JLabel("Available Rooms", SwingConstants.CENTER);
        heading.setFont(new Font("Serif", Font.BOLD, 22));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        // Table setup
        String[] columns = {"Room Number", "Room Type", "AC Type", "Bed Count", "Price"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        roomTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(roomTable);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch and add room data
        RoomDAO roomDAO = new RoomDAOImpl();
        List<Room> availableRooms = roomDAO.getAvailableRooms(checkInDate, checkOutDate);

        for (Room room : availableRooms) {
            Object[] row = {
                room.getRoom_num(),
                room.getType_2(),
                room.getType_1(),
                room.getPrice()
            };
            tableModel.addRow(row);
        }

        // Next button
        nextButton = new JButton("Proceed to Guest Details");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(nextButton);
        add(bottomPanel, BorderLayout.SOUTH);

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = roomTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a room.");
                    return;
                }

                int roomNumber = (int) tableModel.getValueAt(selectedRow, 0);
                dispose();
                new GuestDetailsFrame();
            }
        });

        setVisible(true);
    }
}
