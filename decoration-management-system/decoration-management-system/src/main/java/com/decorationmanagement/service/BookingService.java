package com.decorationmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decorationmanagement.dao.BookingDao;
import com.decorationmanagement.entity.Booking;

@Service
public class BookingService {

	@Autowired
	private BookingDao bookingDao;
	
	public Booking addBooking(Booking booking) {
		return bookingDao.save(booking);
	}
	
	public Booking getBookById(int id) {
		return bookingDao.findById(id).get();
	}
	
	public List<Booking> getAllBooking() {
		return bookingDao.findAll();
	}
	
	public List<Booking> getBookingsByUserId(int userId) {
		return bookingDao.findByUserId(userId);
	}
	
	public List<Booking> getBookingsByDecorationId(int photoshootId) {
		return bookingDao.findByDecorationId(photoshootId);
	}
	
}
