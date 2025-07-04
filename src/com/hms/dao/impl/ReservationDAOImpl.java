package com.hms.dao.impl;

import com.hms.dao.ReservationDAO;
import com.hms.db.jdbcConnect;
import com.hms.entity.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {

    private Connection conn;

    public ReservationDAOImpl() {
        conn = jdbcConnect.getConnection();
    }

    @Override
    public void addReservation(Reservation reservation) {
        try {
            String query = "INSERT INTO reservation (guest_email, room_num, checkIn, checkOut, hotelId) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, reservation.getGuestEmail());
            ps.setInt(2, reservation.getRoomNum());
            ps.setDate(3, reservation.getCheckInDate());
            ps.setDate(4, reservation.getCheckOutDate());
            ps.setInt(5, reservation.getHotelId());
            ps.executeUpdate();
            ps.close();

            PreparedStatement updateRoom = conn.prepareStatement("UPDATE room SET status = false WHERE room_num = ?");
            updateRoom.setInt(1, reservation.getRoomNum());
            updateRoom.executeUpdate();
            updateRoom.close();

            PreparedStatement updateGuest = conn.prepareStatement("UPDATE guest SET room_num = ? WHERE email = ?");
            updateGuest.setInt(1, reservation.getRoomNum());
            updateGuest.setString(2, reservation.getGuestEmail());
            updateGuest.executeUpdate();
            updateGuest.close();

        } catch (Exception e) {
            System.out.println("Error while adding reservation: " + e);
        }
    }

    @Override
    public void updateReservation(Reservation reservation) {
        try {
            String query = "UPDATE reservation SET guest_email=?, room_num=?, checkIn=?, checkOut=?, hotelId=? WHERE reservationId=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, reservation.getGuestEmail());
            ps.setInt(2, reservation.getRoomNum());
            ps.setDate(3, reservation.getCheckInDate());
            ps.setDate(4, reservation.getCheckOutDate());
            ps.setInt(5, reservation.getHotelId());
            ps.setInt(6, reservation.getReservationId());
            ps.executeUpdate();
            ps.close();

            PreparedStatement updateRoom = conn.prepareStatement("UPDATE room SET status = false WHERE room_num = ?");
            updateRoom.setInt(1, reservation.getRoomNum());
            updateRoom.executeUpdate();
            updateRoom.close();
        } catch (Exception e) {
            System.out.println("Error while updating reservation: " + e);
        }
    }

    @Override
    public void cancelReservation(int reservationId) {
        try {
            String getRoom = "SELECT room_num FROM reservation WHERE reservationId = ?";
            PreparedStatement getRoomStmt = conn.prepareStatement(getRoom);
            getRoomStmt.setInt(1, reservationId);
            ResultSet rs = getRoomStmt.executeQuery();

            int roomNum = -1;
            if (rs.next()) {
                roomNum = rs.getInt("room_num");
            }
            rs.close();
            getRoomStmt.close();

            String deleteQuery = "DELETE FROM reservation WHERE reservationId = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
            deleteStmt.setInt(1, reservationId);
            deleteStmt.executeUpdate();
            deleteStmt.close();

            if (roomNum != -1) {
                PreparedStatement updateRoom = conn.prepareStatement("UPDATE room SET status = true WHERE room_num = ?");
                updateRoom.setInt(1, roomNum);
                updateRoom.executeUpdate();
                updateRoom.close();

                PreparedStatement clearGuest = conn.prepareStatement("UPDATE guest SET room_num = NULL WHERE room_num = ?");
                clearGuest.setInt(1, roomNum);
                clearGuest.executeUpdate();
                clearGuest.close();
            }

        } catch (Exception e) {
            System.out.println("Error while canceling reservation: " + e);
        }
    }

    @Override
    public Reservation getReservationById(int reservationId) {
        Reservation res = null;
        try {
            String query = "SELECT * FROM reservation WHERE reservationId=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, reservationId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                res = new Reservation(
                        rs.getInt("room_num"),
                        rs.getString("guest_email"),
                        rs.getDate("checkIn"),
                        rs.getDate("checkOut"),
                        rs.getInt("reservationId"),
                        rs.getInt("hotelId")
                );
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error while fetching reservation by ID: " + e);
        }

        return res;
    }

    @Override
    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        try {
            String query = "SELECT * FROM reservation";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Reservation res = new Reservation(
                        rs.getInt("room_num"),
                        rs.getString("guest_email"),
                        rs.getDate("checkIn"),
                        rs.getDate("checkOut"),
                        rs.getInt("reservationId"),
                        rs.getInt("hotelId")
                );
                reservations.add(res);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error while fetching all reservations: " + e);
        }

        return reservations;
    }

    @Override
    public List<Reservation> getReservationsByEmail(String guestEmail) {
        List<Reservation> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM reservation WHERE guest_email=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, guestEmail);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reservation res = new Reservation(
                        rs.getInt("room_num"),
                        rs.getString("guest_email"),
                        rs.getDate("checkIn"),
                        rs.getDate("checkOut"),
                        rs.getInt("reservationId"),
                        rs.getInt("hotelId")
                );
                list.add(res);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error while fetching reservations by email: " + e);
        }

        return list;
    }

    @Override
    public List<Reservation> getReservationsByRoomNum(int roomNum) {
        List<Reservation> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM reservation WHERE room_num=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, roomNum);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reservation res = new Reservation(
                        rs.getInt("room_num"),
                        rs.getString("guest_email"),
                        rs.getDate("checkIn"),
                        rs.getDate("checkOut"),
                        rs.getInt("reservationId"),
                        rs.getInt("hotelId")
                );
                list.add(res);
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error while fetching reservations by room number: " + e);
        }

        return list;
    }
}
