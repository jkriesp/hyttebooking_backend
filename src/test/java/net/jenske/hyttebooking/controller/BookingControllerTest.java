package net.jenske.hyttebooking.controller;

import net.jenske.hyttebooking.model.Booking;
import net.jenske.hyttebooking.model.Cabin;
import net.jenske.hyttebooking.model.User;
import net.jenske.hyttebooking.repository.BookingRepository;
import net.jenske.hyttebooking.repository.CabinRepository;
import net.jenske.hyttebooking.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    // Testing out GitHub Copilot for generating test cases

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private CabinRepository cabinRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testGetAllBookings() throws Exception {
        when(bookingRepository.findAll()).thenReturn(Arrays.asList(new Booking()));
        mockMvc.perform(get("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    public void testGetBookingById() throws Exception {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(new Booking()));
        mockMvc.perform(get("/api/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bookingRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testCreateBooking() throws Exception {
        when(bookingRepository.save(any(Booking.class))).thenReturn(new Booking());
        when(cabinRepository.findById(anyLong())).thenReturn(Optional.of(new Cabin()));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"startDate\": \"2022-01-01\", \"endDate\": \"2022-01-02\", \"status\": \"BOOKED\", \"title\": \"Test Booking\", \"cabin\": {\"cabinId\": 1}, \"user\": {\"userId\": 1}}"))
                .andExpect(status().isCreated());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    public void testUpdateBooking() throws Exception {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(new Booking()));
        when(bookingRepository.save(any(Booking.class))).thenReturn(new Booking());
        when(cabinRepository.findById(anyLong())).thenReturn(Optional.of(new Cabin()));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        mockMvc.perform(put("/api/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"startDate\": \"2022-01-01\", \"endDate\": \"2022-01-02\", \"status\": \"BOOKED\", \"title\": \"Test Booking\", \"cabin\": {\"cabinId\": 1}, \"user\": {\"userId\": 1}}"))
                .andExpect(status().isOk());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    public void testDeleteBooking() throws Exception {
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(new Booking()));
        mockMvc.perform(delete("/api/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(bookingRepository, times(1)).delete(any(Booking.class));
    }
}