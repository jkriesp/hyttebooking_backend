package net.jenske.hyttebooking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookingId;

    @NotNull(message = "Start date is required: yyyy-mm-dd")
    private LocalDate startDate;
    @NotNull(message = "End date is required: yyyy-mm-dd")
    private LocalDate endDate;
    private String status;
    @NotBlank(message = "Title is required: `Family vacation` or `Hiking trip` or `Skiing trip` or `Other")
    private String title;

    @ManyToOne
    @JoinColumn(name = "cabinId") // This is the foreign key
    @JsonBackReference("cabin-booking")
    private Cabin cabin;

    @ManyToOne
    @JoinColumn(name = "userId") // This is the foreign key
    @JsonBackReference("user-booking")
    private User user;

    public Booking() {
    }

    public Booking(LocalDate startDate, LocalDate endDate, String status, String title , Cabin cabin, User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.cabin = cabin;
        this.user = user;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getBookingId() {
        return bookingId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cabin getCabin() {
        return cabin;
    }

    public void setCabin(Cabin cabin) {
        this.cabin = cabin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Booking [bookingId=" + bookingId + ", startDate=" + startDate + ", endDate=" + endDate + ", status=" + status + ", cabin=" + cabin + ", user=" + user + "]";
    }
}