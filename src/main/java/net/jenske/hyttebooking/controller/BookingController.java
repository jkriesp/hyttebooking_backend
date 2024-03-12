package net.jenske.hyttebooking.controller;

import net.jenske.hyttebooking.model.Booking;
import net.jenske.hyttebooking.model.Cabin;
import net.jenske.hyttebooking.model.User;
import net.jenske.hyttebooking.repository.BookingRepository;
import net.jenske.hyttebooking.repository.CabinRepository;
import net.jenske.hyttebooking.repository.UserRepository;
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
    @Autowired
    private CabinRepository cabinRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves all bookings optionally filtered by title.
     *
     * @param title the title to filter the bookings, if provided
     * @return a list of bookings that match the title filter or all bookings if no filter is provided
     */
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

    /**
     * Retrieves a single booking by its unique identifier.
     * @param id the unique identifier of the booking to be retrieved
     * @return the booking with the specified ID or a not found response if it doesn't exist
     */
    @GetMapping("/bookings/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable("id") long id) {
        try {
            Optional<Booking> booking = bookingRepository.findById(id);

            return booking.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a new booking in the system.
     *
     * @param booking the booking object to be created
     * @return the newly created booking with a 201 status code, or an error message if the creation fails
     */
    @PostMapping("/bookings")
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            Cabin cabin = cabinRepository.findById(booking.getCabin().getCabinId())
                    .orElseThrow(() -> new Exception("Cabin not found with id: " + booking.getCabin().getCabinId()));

            User user = userRepository.findById(booking.getUser().getUserId())
                    .orElseThrow(() -> new Exception("User not found with id: " + booking.getUser().getUserId()));

            Booking newBooking = new Booking(
                    booking.getStartDate(),
                    booking.getEndDate(),
                    booking.getStatus(),
                    booking.getTitle(),
                    cabin,  // Use the fetched cabin
                    user    // Use the fetched user
            );

            Booking savedBooking = bookingRepository.save(newBooking);
            return new ResponseEntity<>(savedBooking, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates an existing booking.
     *
     * @param id the unique identifier of the booking to update
     * @param bookingDetails the updated booking information
     * @return the updated booking, or a 404 status if the booking with the specified ID is not found
     */
    @PutMapping("/bookings/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable("id") long id, @RequestBody Booking bookingDetails) {
        return bookingRepository.findById(id)
                .map(existingBooking -> {
                    // Fetch and set the cabin and user from the database to ensure they exist
                    cabinRepository.findById(bookingDetails.getCabin().getCabinId())
                            .ifPresent(existingBooking::setCabin);
                    userRepository.findById(bookingDetails.getUser().getUserId())
                            .ifPresent(existingBooking::setUser);

                    // Update the existing booking with details from the request
                    existingBooking.setStartDate(bookingDetails.getStartDate());
                    existingBooking.setEndDate(bookingDetails.getEndDate());
                    existingBooking.setStatus(bookingDetails.getStatus());
                    existingBooking.setTitle(bookingDetails.getTitle());

                    // Save the updated booking
                    return new ResponseEntity<>(bookingRepository.save(existingBooking), HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Deletes a booking from the system.
     *
     * @param id the unique identifier of the booking to be deleted
     * @return a no content response if the deletion is successful, or a not found response if the booking doesn't exist
     */
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
