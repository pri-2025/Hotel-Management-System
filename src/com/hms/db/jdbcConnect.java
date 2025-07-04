package com.hms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class jdbcConnect {

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelMgmt", "root", "Pri@1234");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Database Error: " + e.getMessage());
			return null;
		}
	}

	public static void main(String[] args) {
		try (Connection con = getConnection(); Statement stmt = con.createStatement()) {

//			stmt.executeUpdate("DROP TABLE IF EXISTS reservation");
//			stmt.executeUpdate("DROP TABLE IF EXISTS guest");
//			stmt.executeUpdate("DROP TABLE IF EXISTS room");
//			stmt.executeUpdate("DROP TABLE IF EXISTS hotel");

			// Create hotel table (NO AUTO_INCREMENT)
			stmt.executeUpdate("""
					    CREATE TABLE hotel (
					        hotelId INT PRIMARY KEY,
					        h_name VARCHAR(100),
					        h_locat VARCHAR(100),
					        contact_num VARCHAR(10),
					        contact_email VARCHAR(100)
					    )

					""");
			System.out.println("Hotel table created successfully!");

			stmt.executeUpdate("""
					    CREATE TABLE room (
					        room_num INT PRIMARY KEY,
					        price DOUBLE,
					        ac_type VARCHAR(50),
					        room_type VARCHAR(50),
					        status BOOLEAN,
					        hotelId INT,
					        FOREIGN KEY (hotelId) REFERENCES hotel(hotelId)
					    )
					""");
			System.out.println("Room table created successfully!");

			stmt.executeUpdate("""
					CREATE TABLE guest (
					    name VARCHAR(100),
					    guest_type VARCHAR(50),
					    email VARCHAR(100) PRIMARY KEY,
					    mob_no VARCHAR(20),
					    address VARCHAR(200),
					    room_num INT
					);
										""");
			System.out.println("Guest table created successfully!");

			stmt.executeUpdate("""
					CREATE TABLE reservation (
					    reservationId INT AUTO_INCREMENT PRIMARY KEY,
					    guest_email VARCHAR(100),
					    room_num INT,
					    checkIn DATE,
					    checkOut DATE,
					    hotelId INT
					);

										""");
			System.out.println("Reservation table created successfully!");

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
