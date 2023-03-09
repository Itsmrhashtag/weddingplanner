package com.decorationmanagement.dto;

import lombok.Data;

public class BookingResponseDto {
	
    private int id;
	
	private String bookingId;
	
	private String bookingDate;
	
	private String bookDate;
	
	private int userId;
	
	private int decorationId;
	
	private String status;
	
	private String customerName;
	
	private String customerContact;
	
	private String decorationName;
	
	private String decorationImage;
	
	private String price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getBookDate() {
		return bookDate;
	}

	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDecorationId() {
		return decorationId;
	}

	public void setDecorationId(int decorationId) {
		this.decorationId = decorationId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerContact() {
		return customerContact;
	}

	public void setCustomerContact(String customerContact) {
		this.customerContact = customerContact;
	}

	public String getDecorationName() {
		return decorationName;
	}

	public void setDecorationName(String decorationName) {
		this.decorationName = decorationName;
	}

	public String getDecorationImage() {
		return decorationImage;
	}

	public void setDecorationImage(String decorationImage) {
		this.decorationImage = decorationImage;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	
	
}
