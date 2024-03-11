package net.jenske.hyttebooking.controller;

import net.jenske.hyttebooking.model.Booking;
import net.jenske.hyttebooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api")
public class BookingController {

    @Autowired
    BookingRepository bookingRepository;

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getAllBookings(@RequestParam(required = false) String title) {
        try {
            List<Booking> bookings = new ArrayList<Booking>();

            if (title == null)
                bookings.addAll(bookingRepository.findAll());
            else
                bookings.addAll(bookingRepository.findByTitleContaining(title));

            if (bookings.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable("id") long id) {
        try {
            Booking booking = bookingRepository.findById(id);

            if (booking != null) {
                return new ResponseEntity<>(booking, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/bookings")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        try {
            Booking _booking = bookingRepository
                    .save(new Booking(booking.getStartDate(), booking.getEndDate(), booking.getStatus(), booking.getTitle(), booking.getCabin(), booking.getUser()));
            return new ResponseEntity<>(_booking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/bookings/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable("id") long id, @RequestBody Booking booking) {
        Optional<Booking> bookingData = Optional.ofNullable(bookingRepository.findById(id));

        if (bookingData.isPresent()) {
            try {
                Booking _booking = bookingRepository.findById(id);
                _booking.setStartDate(booking.getStartDate());
                _booking.setEndDate(booking.getEndDate());
                _booking.setStatus(booking.getStatus());
                _booking.setTitle(booking.getTitle());
                _booking.setCabin(booking.getCabin());
                _booking.setUser(booking.getUser());
                return new ResponseEntity<>(bookingRepository.save(_booking), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<HttpStatus> deleteBooking(@PathVariable("id") long id) {
        try {
            bookingRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
