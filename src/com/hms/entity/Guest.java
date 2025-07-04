package com.hms.entity;

public class Guest {
    private String name;
    private String type;
    private String email;
    private String mobNo;
    private String address;

    public Guest(String name, String type, String email, String mobNo, String address) {
        this.name = name;
        this.type = type;
        this.email = email;
        this.mobNo = mobNo;
        this.address = address;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobno() {
		return mobNo;
	}

	public void setMobno(String mobNo) {
		this.mobNo = mobNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
