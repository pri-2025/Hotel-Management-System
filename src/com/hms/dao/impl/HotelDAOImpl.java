package com.hms.dao.impl;

import com.hms.dao.HotelDAO;
import com.hms.db.jdbcConnect;
import com.hms.entity.Hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HotelDAOImpl implements HotelDAO {

    private Connection conn;

    public HotelDAOImpl() {
        conn = jdbcConnect.getConnection();
    }

    @Override
    public Hotel getHotel() {
        Hotel hotel = null;
        try {
            String query = "SELECT * FROM hotel LIMIT 1"; 
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                hotel = new Hotel(
                    rs.getString("h_name"),
                    rs.getString("h_locat"),
                    rs.getInt("hotelId"),
                    rs.getString("contact_num"),
                    rs.getString("contact_email")
                );
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.out.println("Error while fetching hotel: " + e);
        }

        return hotel;
    }
}
