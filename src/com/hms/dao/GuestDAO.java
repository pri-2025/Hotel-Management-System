package com.hms.dao;

import com.hms.entity.Guest;
import java.util.List;

public interface GuestDAO {
	void addGuest(Guest guest);

	void updateGuest(Guest guest);

	Guest getGuestByEmail(String email);

	List<Guest> getAllGuests();

	void deleteGuest(String email);
}
