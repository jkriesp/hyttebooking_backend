package net.jenske.hyttebooking.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name= "cabins")
public class Cabin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cabinId;
    private String name;
    private String location;
    private String description;
    private boolean visible;
    private int numberOfBeds;

    @OneToMany(mappedBy = "cabin")
    private List<Booking> bookings;

    public Cabin() {

    }


    public Cabin(String name, String location, String description, boolean visible, int numberOfBeds) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.visible = visible;
        this.numberOfBeds = numberOfBeds;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public long getCabinId() {
        return cabinId;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    @Override
    public String toString() {
        return "Cabin [id=" + cabinId + ", name=" + name + "numberOfBeds=" + numberOfBeds + ", location=" + location + ", description=" + description + ", visible=" + visible + "]";
    }
}
