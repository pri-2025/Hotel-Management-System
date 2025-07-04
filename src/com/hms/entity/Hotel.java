package com.hms.entity;

public class Hotel {
	private String name;
	private String location;
	private int id;
	private String contact_num;
	private String contact_email;

	// Constructor with correct parameter order for DAOImpl
	public Hotel(String name, String location, int id, String contact_num, String contact_email) {
		this.name = name;
		this.location = location;
		this.id = id;
		this.contact_num = contact_num;
		this.contact_email = contact_email;
	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContact_num() {
		return contact_num;
	}

	public void setContact_num(String contact_num) {
		this.contact_num = contact_num;
	}

	public String getContact_email() {
		return contact_email;
	}

	public void setContact_email(String contact_email) {
		this.contact_email = contact_email;
	}
}
