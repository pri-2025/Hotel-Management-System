package com.hms.entity;

public class Room {
	private double price;
	private int room_num; // 3 digit pin
	private String type_1; // Ac/Non-Ac
	private String type_2; // Single/Double/Deluxe
	private boolean status; // Occupies/Not-Occupied

	public Room(int room_num, double price, String type_1, String type_2, boolean status) {
		this.price = price;
		this.room_num = room_num;
		this.type_1 = type_1;
		this.type_2 = type_2;
		this.status = status;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getRoom_num() {
		return room_num;
	}

	public void setRoom_num(int room_num) {
		this.room_num = room_num;
	}

	public String getType_1() {
		return type_1;
	}

	public void setType_1(String type_1) {
		this.type_1 = type_1;
	}

	public String getType_2() {
		return type_2;
	}

	public void setType_2(String type_2) {
		this.type_2 = type_2;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
