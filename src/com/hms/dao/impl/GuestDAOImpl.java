package com.hms.dao.impl;

import com.hms.dao.GuestDAO;
import com.hms.db.jdbcConnect;
import com.hms.entity.Guest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestDAOImpl implements GuestDAO {

    private Connection conn;

    public GuestDAOImpl() {
        conn = jdbcConnect.getConnection();
    }

    @Override
    public void addGuest(Guest guest) {
        try {
            String sql = "INSERT INTO guest (name, guest_type, email, mob_no, address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, guest.getName());
            ps.setString(2, guest.getType());
            ps.setString(3, guest.getEmail());
            ps.setString(4, guest.getMobno());
            ps.setString(5, guest.getAddress());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error while adding guest: " + e);
        }
    }

    @Override
    public void updateGuest(Guest guest) {
        try {
            String sql = "UPDATE guest SET name=?, guest_type=?, mob_no=?, address=? WHERE email=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, guest.getName());
            ps.setString(2, guest.getType());
            ps.setString(3, guest.getMobno());
            ps.setString(4, guest.getAddress());
            ps.setString(5, guest.getEmail());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error while updating guest: " + e);
        }
    }

    public void deleteGuest(String email) {
        try {
            String query = "DELETE FROM guest WHERE email=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error while deleting guest: " + e);
        }
    }

    @Override
    public Guest getGuestByEmail(String email) {
        Guest guest = null;
        try {
            String query = "SELECT * FROM guest WHERE email=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                guest = new Guest(
                    rs.getString("name"),
                    rs.getString("guest_type"),
                    rs.getString("email"),
                    rs.getString("mob_no"),
                    rs.getString("address")
                );
            }

            rs.close();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error while fetching guest by email: " + e);
        }

        return guest;
    }

    @Override
    public List<Guest> getAllGuests() {
        List<Guest> guests = new ArrayList<>();
        try {
            String query = "SELECT * FROM guest";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Guest guest = new Guest(
                    rs.getString("name"),
                    rs.getString("guest_type"),
                    rs.getString("email"),
                    rs.getString("mob_no"),
                    rs.getString("address")
                );
                guests.add(guest);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error while fetching all guests: " + e);
        }

        return guests;
    }
}
