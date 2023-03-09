package com.decorationmanagement.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.decorationmanagement.dto.BookingResponseDto;
import com.decorationmanagement.dto.CommanApiResponse;
import com.decorationmanagement.dto.UpdateBookingStatusRequestDto;
import com.decorationmanagement.entity.Booking;
import com.decorationmanagement.entity.Decoration;
import com.decorationmanagement.entity.User;
import com.decorationmanagement.service.BookingService;
import com.decorationmanagement.service.DecorationService;
import com.decorationmanagement.service.UserService;
import com.decorationmanagement.utility.Constants.BookingStatus;
import com.decorationmanagement.utility.Constants.ResponseCode;
import com.decorationmanagement.utility.Helper;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/book/decoration")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

	Logger LOG = LoggerFactory.getLogger(BookingController.class);

	@Autowired
	private BookingService bookingService;

	@Autowired
	private UserService userService;

	@Autowired
	private DecorationService decorationService;

	@PostMapping("/")
	@ApiOperation(value = "Api to book decoration")
	public ResponseEntity<?> bookPhotoshoot(Booking booking) {
		LOG.info("Recieved request for booking hotel");

		System.out.println(booking);

		CommanApiResponse response = new CommanApiResponse();

		if (booking == null) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Photoshoot Booking Failed");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		if (booking.getUserId() == 0) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("User is not not looged in");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		if (booking.getDecorationId() == 0) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Photoshoot not found to Book");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		Decoration decoration = decorationService.getDecorationById(booking.getDecorationId());

		if (decoration == null) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("No decoration present with this Id");
		}

		
		booking.setBookingDate(LocalDate.now().toString());
		booking.setStatus(BookingStatus.PENDING.value());
		booking.setBookingId(Helper.getAlphaNumericId());

		Booking bookedHotel = this.bookingService.addBooking(booking);

		if (bookedHotel != null) {
			response.setResponseCode(ResponseCode.SUCCESS.value());
			response.setResponseMessage("Photoshoot Booked Successfully, Please Check Approval Status on Booking Option");
			return new ResponseEntity(response, HttpStatus.OK);
		}

		else {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Failed to Book Hotel");
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/fetch/all")
	@ApiOperation(value = "Api to fetch all booked hotel")
	public ResponseEntity<?> fetchAllHotelBooking() {
		LOG.info("Recieved request for fetch all booking");

		List<BookingResponseDto> bookings = new ArrayList<>();

		List<Booking> allBookings = this.bookingService.getAllBooking();

		for (Booking booking : allBookings) {

			BookingResponseDto b = new BookingResponseDto();

			User customer = this.userService.getUserById(booking.getUserId());
			Decoration decoration = this.decorationService.getDecorationById(booking.getDecorationId());

			b.setBookingId(booking.getBookingId());
			b.setCustomerContact(customer.getContact());
			b.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
			
			b.setDecorationId(booking.getDecorationId());
			b.setDecorationImage(decoration.getImage());
			b.setDecorationName(decoration.getName());
			b.setId(booking.getId());
			b.setStatus(booking.getStatus());
			b.setDecorationId(decoration.getId());
			b.setUserId(customer.getId());
			b.setPrice(String.valueOf(decoration.getPrice()));
			b.setBookDate(booking.getBookedDate());
			b.setBookingDate(booking.getBookingDate());

			bookings.add(b);
		}

		return new ResponseEntity(bookings, HttpStatus.OK);

	}

	@GetMapping("/fetch")
	@ApiOperation(value = "Api to fetch my booked decoration")
	public ResponseEntity<?> fetchMyBooking(@RequestParam("userId") int userId) {
		LOG.info("Recieved request for fetch all booking by using userId");

		List<BookingResponseDto> bookings = new ArrayList<>();

		List<Booking> allBookings = this.bookingService.getBookingsByUserId(userId);

		for (Booking booking : allBookings) {

			BookingResponseDto b = new BookingResponseDto();

			User customer = this.userService.getUserById(booking.getUserId());
			Decoration decoration = this.decorationService.getDecorationById(booking.getDecorationId());

			b.setBookingId(booking.getBookingId());
			b.setCustomerContact(customer.getContact());
			b.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
			
			b.setDecorationId(booking.getDecorationId());
			b.setDecorationImage(decoration.getImage());
			b.setDecorationName(decoration.getName());
			b.setId(booking.getId());
			b.setStatus(booking.getStatus());
			b.setDecorationId(decoration.getId());
			b.setUserId(customer.getId());
			b.setPrice(String.valueOf(decoration.getPrice()));
			b.setBookDate(booking.getBookedDate());
			b.setBookingDate(booking.getBookingDate());

			bookings.add(b);
		}

		return new ResponseEntity(bookings, HttpStatus.OK);

	}

	@GetMapping("/fetch/bookingId")
	@ApiOperation(value = "Api to fetch my booked decoration")
	public ResponseEntity<?> fetchBooking(@RequestParam("id") int bookingId) {
		LOG.info("Recieved request for fetch all booking by booking Id ");

		Booking booking = this.bookingService.getBookById(bookingId);

		BookingResponseDto b = new BookingResponseDto();

		User customer = this.userService.getUserById(booking.getUserId());
		Decoration decoration = this.decorationService.getDecorationById(booking.getDecorationId());

		b.setBookingId(booking.getBookingId());
		b.setCustomerContact(customer.getContact());
		b.setCustomerName(customer.getFirstName() + " " + customer.getLastName());
		
		b.setDecorationId(booking.getDecorationId());
		b.setDecorationImage(decoration.getImage());
		b.setDecorationName(decoration.getName());
		b.setId(booking.getId());
		b.setStatus(booking.getStatus());
		b.setDecorationId(decoration.getId());
		b.setUserId(customer.getId());
		b.setPrice(String.valueOf(decoration.getPrice()));
		b.setBookDate(booking.getBookedDate());
		b.setBookingDate(booking.getBookingDate());

		return new ResponseEntity(b, HttpStatus.OK);

	}

	@GetMapping("/fetch/status")
	@ApiOperation(value = "Api to fetch all booking status")
	public ResponseEntity<?> fetchAllBookingStatus() {
		LOG.info("Recieved request for fetch all booking status");

		List<String> response = new ArrayList<>();

		for (BookingStatus status : BookingStatus.values()) {
			response.add(status.value());
		}

		return new ResponseEntity(response, HttpStatus.OK);

	}

	@PostMapping("/update/status")
	@ApiOperation(value = "Api to update decoration booking")
	public ResponseEntity<?> updatePhotoshootBookingStatus(@RequestBody UpdateBookingStatusRequestDto request) {

		LOG.info("Recieved request for updating Booking Status");

		
		if (request == null) {
			return new ResponseEntity("Request found NULL", HttpStatus.BAD_REQUEST);
		}

		Booking book = this.bookingService.getBookById(request.getBookingId());

		if (BookingStatus.PENDING.value().equals(request.getStatus())) {
			return new ResponseEntity("Can't update Booking status to Pending", HttpStatus.BAD_REQUEST);
		}

		// cancel status
		book.setStatus(request.getStatus());
		this.bookingService.addBooking(book);

		return new ResponseEntity("Booking " + book.getStatus() + "Successfully", HttpStatus.OK);

	}

	
}
