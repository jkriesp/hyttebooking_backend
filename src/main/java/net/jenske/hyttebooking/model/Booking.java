package net.jenske.hyttebooking.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookingId;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @Column
    private String status;

    @ManyToOne
    @JoinColumn(name = "cabinId") // This is the foreign key
    private Cabin cabin;

    @ManyToOne
    @JoinColumn(name = "userId") // This is the foreign key
    private User user;

    public Booking() {
    }

    public Booking(LocalDate startDate, LocalDate endDate, String status, Cabin cabin, User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.cabin = cabin;
        this.user = user;
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