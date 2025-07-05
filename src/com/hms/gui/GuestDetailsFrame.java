package com.hms.gui;

import com.hms.dao.GuestDAO;
import com.hms.dao.ReservationDAO;
import com.hms.dao.impl.GuestDAOImpl;
import com.hms.dao.impl.ReservationDAOImpl;
import com.hms.entity.Guest;
import com.hms.entity.Reservation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class GuestDetailsFrame extends JFrame {

	private JTextField nameField, emailField, mobField, addressField;
	private JRadioButton adultRadio, childRadio;
	private JRadioButton maleRadio, femaleRadio;
	private JButton proceedButton, addGuestButton, deleteGuestButton;
	private JTable guestTable;
	private DefaultTableModel tableModel;

	private ButtonGroup typeGroup, genderGroup;

	private GuestDAO guestDAO = new GuestDAOImpl();
	private ReservationDAO reservationDAO = new ReservationDAOImpl();

	private int roomNum;
	private Date checkInDate, checkOutDate;

	public GuestDetailsFrame(int roomNum, Date checkInDate, Date checkOutDate) {
		this.roomNum = roomNum;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;

		setTitle("Guest Details");
		setSize(850, 600);
		setLayout(null);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setBounds(30, 30, 100, 25);
		add(nameLabel);

		nameField = new JTextField();
		nameField.setBounds(140, 30, 200, 25);
		add(nameField);

		JLabel typeLabel = new JLabel("Type:");
		typeLabel.setBounds(30, 70, 100, 25);
		add(typeLabel);

		adultRadio = new JRadioButton("Adult");
		adultRadio.setBounds(140, 70, 80, 25);
		childRadio = new JRadioButton("Child");
		childRadio.setBounds(220, 70, 80, 25);
		typeGroup = new ButtonGroup(); // ✅ class-level variable
		typeGroup.add(adultRadio);
		typeGroup.add(childRadio);
		add(adultRadio);
		add(childRadio);

		JLabel genderLabel = new JLabel("Gender:");
		genderLabel.setBounds(30, 110, 100, 25);
		add(genderLabel);

		maleRadio = new JRadioButton("Male");
		maleRadio.setBounds(140, 110, 80, 25);
		femaleRadio = new JRadioButton("Female");
		femaleRadio.setBounds(220, 110, 80, 25);
		genderGroup = new ButtonGroup(); // ✅ class-level variable
		genderGroup.add(maleRadio);
		genderGroup.add(femaleRadio);
		add(maleRadio);
		add(femaleRadio);

		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setBounds(30, 150, 100, 25);
		add(emailLabel);

		emailField = new JTextField();
		emailField.setBounds(140, 150, 200, 25);
		add(emailField);

		JLabel mobLabel = new JLabel("Mobile No:");
		mobLabel.setBounds(30, 190, 100, 25);
		add(mobLabel);

		mobField = new JTextField();
		mobField.setBounds(140, 190, 200, 25);
		add(mobField);

		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setBounds(30, 230, 100, 25);
		add(addressLabel);

		addressField = new JTextField();
		addressField.setBounds(140, 230, 200, 25);
		add(addressField);

		addGuestButton = new JButton("Add Guest");
		addGuestButton.setBounds(30, 280, 140, 30);
		add(addGuestButton);

		deleteGuestButton = new JButton("Delete Selected Guest");
		deleteGuestButton.setBounds(180, 280, 180, 30);
		add(deleteGuestButton);

		proceedButton = new JButton("Proceed");
		proceedButton.setBounds(140, 330, 120, 30);
		add(proceedButton);

		tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(new String[] { "Name", "Type", "Gender", "Email", "Mobile", "Address" });

		guestTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(guestTable);
		scrollPane.setBounds(370, 30, 440, 400);
		add(scrollPane);

		addGuestButton.addActionListener(e -> addGuestToTable());
		deleteGuestButton.addActionListener(e -> deleteSelectedGuest());
		proceedButton.addActionListener(e -> proceedReservation());

		setVisible(true);
	}

	private void addGuestToTable() {
		String name = nameField.getText().trim();
		String type = adultRadio.isSelected() ? "Adult" : childRadio.isSelected() ? "Child" : "";
		String gender = maleRadio.isSelected() ? "Male" : femaleRadio.isSelected() ? "Female" : "";
		String email = emailField.getText().trim();
		String mob = mobField.getText().trim();
		String address = addressField.getText().trim();

		if (name.isEmpty() || type.isEmpty() || gender.isEmpty() || email.isEmpty() || mob.isEmpty()
				|| address.isEmpty()) {
			JOptionPane.showMessageDialog(this, "All fields are mandatory.");
			return;
		}

		if (!Pattern.matches("^\\d{10}$", mob)) {
			JOptionPane.showMessageDialog(this, "Mobile number must be exactly 10 digits.");
			return;
		}

		if (!Pattern.matches("^[\\w-\\.]+@[\\w-]+(\\.[\\w-]{2,4})+$", email)) {
			JOptionPane.showMessageDialog(this, "Invalid email format.");
			return;
		}

		tableModel.addRow(new Object[] { name, type, gender, email, mob, address });
		clearForm();
	}

	private void deleteSelectedGuest() {
		int selectedRow = guestTable.getSelectedRow();
		if (selectedRow == -1) {
			JOptionPane.showMessageDialog(this, "Please select a guest to delete.");
			return;
		}
		tableModel.removeRow(selectedRow);
	}

	private void proceedReservation() {
		if (tableModel.getRowCount() == 0) {
			JOptionPane.showMessageDialog(this, "Please add at least one guest.");
			return;
		}

		for (int i = 0; i < tableModel.getRowCount(); i++) {
			String name = (String) tableModel.getValueAt(i, 0);
			String type = (String) tableModel.getValueAt(i, 1);
			String gender = (String) tableModel.getValueAt(i, 2);
			String email = (String) tableModel.getValueAt(i, 3);
			String mob = (String) tableModel.getValueAt(i, 4);
			String address = (String) tableModel.getValueAt(i, 5);

			Guest guest = new Guest(name, type + " - " + gender, email, mob, address);
			guestDAO.addGuest(guest);
			Reservation reservation = new Reservation(roomNum, email, new java.sql.Date(checkInDate.getTime()),
					new java.sql.Date(checkOutDate.getTime()));
			reservationDAO.addReservation(reservation);
		}

		JOptionPane.showMessageDialog(this, "Reservation successful!");
		new ReservationPanel(); // You may pass email if filtering
		dispose();
	}

	private void clearForm() {
		nameField.setText("");
		emailField.setText("");
		mobField.setText("");
		addressField.setText("");
		typeGroup.clearSelection();
		genderGroup.clearSelection();
	}
}
