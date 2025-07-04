package com.hms.gui;

import com.hms.dao.GuestDAO;
import com.hms.dao.impl.GuestDAOImpl;
import com.hms.entity.Guest;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.List;

public class GuestDetailsFrame extends JFrame {

	private JTextField nameField, emailField, mobField, addressField;
	private JRadioButton adultRadio, childRadio;
	private JRadioButton maleRadio, femaleRadio;
	private JButton addButton, updateButton, deleteButton, proceedButton;
	private JTable guestTable;
	private DefaultTableModel tableModel;

	private GuestDAO guestDAO = new GuestDAOImpl();

	public GuestDetailsFrame() {
		setTitle("Guest Management");
		setSize(880, 630);
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
		ButtonGroup typeGroup = new ButtonGroup();
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
		ButtonGroup genderGroup = new ButtonGroup();
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

		addButton = new JButton("Add");
		addButton.setBounds(30, 280, 100, 30);
		add(addButton);

		updateButton = new JButton("Update");
		updateButton.setBounds(140, 280, 100, 30);
		add(updateButton);

		deleteButton = new JButton("Delete");
		deleteButton.setBounds(250, 280, 100, 30);
		add(deleteButton);

		proceedButton = new JButton("Proceed to Reservation");
		proceedButton.setBounds(30, 330, 320, 30);
		add(proceedButton);

		tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(new String[] { "Name", "Type", "Gender", "Email", "Mobile", "Address" });

		guestTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(guestTable);
		scrollPane.setBounds(370, 30, 480, 470);
		add(scrollPane);

		loadGuests();

		addButton.addActionListener(e -> addGuest());
		updateButton.addActionListener(e -> updateGuest());
		deleteButton.addActionListener(e -> deleteGuest());
		proceedButton.addActionListener(e -> proceedToReservation());

		guestTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = guestTable.getSelectedRow();
				nameField.setText(tableModel.getValueAt(row, 0).toString());
				String type = tableModel.getValueAt(row, 1).toString();
				if (type.equalsIgnoreCase("Adult"))
					adultRadio.setSelected(true);
				else
					childRadio.setSelected(true);
				String gender = tableModel.getValueAt(row, 2).toString();
				if (gender.equalsIgnoreCase("Male"))
					maleRadio.setSelected(true);
				else
					femaleRadio.setSelected(true);
				emailField.setText(tableModel.getValueAt(row, 3).toString());
				mobField.setText(tableModel.getValueAt(row, 4).toString());
				addressField.setText(tableModel.getValueAt(row, 5).toString());
			}
		});

		setVisible(true);
	}

	private void loadGuests() {
		tableModel.setRowCount(0);
		List<Guest> list = guestDAO.getAllGuests();
		for (Guest g : list) {
			String gender = g.getType().contains("Female") ? "Female" : "Male";
			String type = g.getType().contains("Adult") ? "Adult" : "Child";
			tableModel.addRow(new Object[] { g.getName(), type, gender, g.getEmail(), g.getMobno(), g.getAddress() });
		}
	}

	private boolean validateFields() {
		String name = nameField.getText().trim();
		String email = emailField.getText().trim();
		String mob = mobField.getText().trim();
		String address = addressField.getText().trim();

		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter name.");
			return false;
		}
		if (!adultRadio.isSelected() && !childRadio.isSelected()) {
			JOptionPane.showMessageDialog(this, "Please select guest type.");
			return false;
		}
		if (!maleRadio.isSelected() && !femaleRadio.isSelected()) {
			JOptionPane.showMessageDialog(this, "Please select gender.");
			return false;
		}
		if (email.isEmpty() || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
			JOptionPane.showMessageDialog(this, "Enter valid email address.");
			return false;
		}
		if (mob.isEmpty() || !mob.matches("\\d{10}")) {
			JOptionPane.showMessageDialog(this, "Enter valid 10-digit mobile number.");
			return false;
		}
		if (address.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter address.");
			return false;
		}
		return true;
	}

	private void addGuest() {
		if (!validateFields())
			return;

		String name = nameField.getText();
		String type = adultRadio.isSelected() ? "Adult" : "Child";
		String gender = maleRadio.isSelected() ? "Male" : "Female";
		String email = emailField.getText();
		String mob = mobField.getText();
		String address = addressField.getText();

		Guest g = new Guest(name, type + " - " + gender, email, mob, address);
		guestDAO.addGuest(g);
		loadGuests();
		clearFields();
	}

	private void updateGuest() {
		if (!validateFields())
			return;

		String name = nameField.getText();
		String type = adultRadio.isSelected() ? "Adult" : "Child";
		String gender = maleRadio.isSelected() ? "Male" : "Female";
		String email = emailField.getText();
		String mob = mobField.getText();
		String address = addressField.getText();

		Guest g = new Guest(name, type + " - " + gender, email, mob, address);
		guestDAO.updateGuest(g);
		loadGuests();
		clearFields();
	}

	private void deleteGuest() {
		String email = emailField.getText().trim();
		if (email.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Enter email to delete guest.");
			return;
		}
		guestDAO.deleteGuest(email);
		loadGuests();
		clearFields();
	}

	private void proceedToReservation() {
		String email = emailField.getText().trim();
		if (email.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter guest details before proceeding.");
			return;
		}
		Guest g = guestDAO.getGuestByEmail(email);
		if (g == null) {
			JOptionPane.showMessageDialog(this, "Guest not found. Please add guest first.");
			return;
		}

		new ReservationPanel(email);
		this.dispose();
	}

	private void clearFields() {
		nameField.setText("");
		emailField.setText("");
		mobField.setText("");
		addressField.setText("");
		adultRadio.setSelected(false);
		childRadio.setSelected(false);
		maleRadio.setSelected(false);
		femaleRadio.setSelected(false);
	}

	public static void main(String[] args) {
		new GuestDetailsFrame();
	}
}
