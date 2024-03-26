package net.jenske.hyttebooking.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @NotBlank(message = "First Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String lastName;
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    private String sub; // Field to store the Auth0 sub value

    @OneToMany(mappedBy = "user")
    @JsonManagedReference("user-booking")
    private List<Booking> bookings; // A user can have multiple bookings

    @OneToMany(mappedBy = "user")
    @JsonManagedReference("user-cabin-relation")
    private List<UserCabinRelation> userCabinRelations;

    // Constructors, getters, and setters

    public User() {
    }

    public User(String firstName, String lastName, String email, String sub) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.sub = sub;
    }

    // Getters and setters for all fields

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<UserCabinRelation> getUserCabinRelations() {
        return userCabinRelations;
    }

    public void setUserCabinRelations(List<UserCabinRelation> userCabinRelations) {
        this.userCabinRelations = userCabinRelations;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", sub=" + sub + "]";
    }
}
