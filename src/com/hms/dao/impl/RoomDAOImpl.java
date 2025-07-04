package com.hms.dao.impl;

import com.hms.entity.Room;
import com.hms.db.jdbcConnect;
import com.hms.dao.RoomDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {

	private Connection conn;

	public RoomDAOImpl() {
		conn = jdbcConnect.getConnection();
	}

	@Override
	public Room getRoomByNumber(int roomNum) {
		Room room = null;
		try {
			String query = "SELECT * FROM room WHERE room_num = ?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, roomNum);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				room = new Room(rs.getInt("room_num"), rs.getDouble("price"), rs.getString("ac_type"),
						rs.getString("room_type"), rs.getBoolean("status"));
			}
			ps.close();
		} catch (Exception e) {
			System.out.println("Error while fetching room details by room ID : " + e);
		}
		return room;
	}

	@Override
	public List<Room> getAllRooms() {
		List<Room> rooms = new ArrayList<>();
		try {
			String query = "SELECT * FROM room";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				Room room = new Room(rs.getInt("room_num"), rs.getDouble("price"), rs.getString("ac_type"),
						rs.getString("room_type"), rs.getBoolean("status"));
				rooms.add(room);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println("Error while fetching all room details : " + e);
		}
		return rooms;
	}

	@Override
	public List<Room> getAvailableRooms(Date checkIn, Date checkOut) {
		List<Room> rooms = new ArrayList<>();

		try {
			String query = "SELECT * FROM room WHERE room_num NOT IN (\r\n" + "  SELECT room_num FROM reservation \r\n"
					+ "  WHERE (checkIn < ? AND checkOut > ?)\r\n" + ")\r\n" + "";

			PreparedStatement ps = conn.prepareStatement(query);
			ps.setDate(1, checkOut);
			ps.setDate(2, checkIn);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Room room = new Room(rs.getInt("room_num"), rs.getDouble("price"), rs.getString("ac_type"),
						rs.getString("room_type"), rs.getBoolean("status"));
				rooms.add(room);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			System.out.println("Error while fetching available room details : " + e);
		}
		return rooms;
	}
}
