package com.hms.dao;

import com.hms.entity.Reservation;
import java.util.List;

public interface ReservationDAO {
	void addReservation(Reservation reservation);

	void updateReservation(Reservation reservation);

	void cancelReservation(int reservationId);

	Reservation getReservationById(int reservationId);

	List<Reservation> getAllReservations();

	List<Reservation> getReservationsByEmail(String guestEmail);

	List<Reservation> getReservationsByRoomNum(int roomNum);
}
