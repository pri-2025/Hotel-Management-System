package com.hms.gui;

import com.hms.dao.RoomDAO;
import com.hms.dao.impl.RoomDAOImpl;
import com.hms.entity.Room;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;

public class AvailableRoomsFrame extends JFrame {

    private JTable roomTable;
    private DefaultTableModel tableModel;
    private JButton bookButton;

    private Date checkInDate, checkOutDate;

    public AvailableRoomsFrame(Date checkInDate, Date checkOutDate) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;

        setTitle("Available Rooms");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel heading = new JLabel("Available Rooms");
        heading.setBounds(320, 20, 200, 30);
        add(heading);

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Room No", "Price", "AC Type", "Room Type"});

        roomTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(roomTable);
        scrollPane.setBounds(50, 70, 680, 200);
        add(scrollPane);

        bookButton = new JButton("Book Selected Room");
        bookButton.setBounds(300, 290, 200, 30);
        add(bookButton);

        loadAvailableRooms();

        bookButton.addActionListener(this::bookSelectedRoom);

        setVisible(true);
    }

    private void loadAvailableRooms() {
        tableModel.setRowCount(0);
        RoomDAO roomDAO = new RoomDAOImpl();
        List<Room> rooms = roomDAO.getAvailableRooms(new java.sql.Date(checkInDate.getTime()),
                new java.sql.Date(checkOutDate.getTime()));

        for (Room room : rooms) {
            tableModel.addRow(new Object[]{
                    room.getRoom_num(),
                    room.getPrice(),
                    room.getType_2(),
                    room.getType_1()
            });
        }
    }

    private void bookSelectedRoom(ActionEvent e) {
        int selectedRow = roomTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a room to book.");
            return;
        }

        int roomNum = (int) tableModel.getValueAt(selectedRow, 0);
        new GuestDetailsFrame(roomNum, checkInDate, checkOutDate);
        dispose();
    }
}
