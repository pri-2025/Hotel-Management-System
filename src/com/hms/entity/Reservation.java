package com.hms.entity;

import java.sql.Date;

public class Reservation {

    private int reservationId;
    private String guestEmail;
    private int roomNum;
    private Date checkInDate;
    private Date checkOutDate;
    private int hotelId;

    public Reservation(int roomNum, String guestEmail, Date checkInDate, Date checkOutDate, int hotelId) {
        this.roomNum = roomNum;
        this.guestEmail = guestEmail;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.hotelId = hotelId;
    }

    public Reservation(int roomNum, String guestEmail, Date checkInDate, Date checkOutDate, int reservationId, int hotelId) {
        this.roomNum = roomNum;
        this.guestEmail = guestEmail;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.reservationId = reservationId;
        this.hotelId = hotelId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public int getHotelId() {
        return hotelId;
    }
}
