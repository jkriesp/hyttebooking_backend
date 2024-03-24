package net.jenske.hyttebooking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "user_cabin_relation")
public class UserCabinRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    @JsonBackReference("user-cabin-relation")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cabinId", nullable = false)
    @JsonBackReference("cabin-user-relation")
    private Cabin cabin;

    public UserCabinRelation() {
    }

    public UserCabinRelation(User user, Cabin cabin) {
        this.user = user;
        this.cabin = cabin;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cabin getCabin() {
        return cabin;
    }

    public void setCabin(Cabin cabin) {
        this.cabin = cabin;
    }
}
