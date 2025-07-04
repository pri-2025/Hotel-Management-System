package com.hms.dao;

import com.hms.entity.Room;

import java.sql.Date;
import java.util.List;

public interface RoomDAO {

	Room getRoomByNumber(int roomNum);

	List<Room> getAllRooms();

	List<Room> getAvailableRooms(Date checkIn, Date checkOut);
}
