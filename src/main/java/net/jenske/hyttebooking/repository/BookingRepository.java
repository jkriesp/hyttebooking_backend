package net.jenske.hyttebooking.repository;

import net.jenske.hyttebooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{
    List<Booking> findByTitleContaining(String title);
    List<Booking> findByStatus(String status);
    Optional<Booking> findById(long cabinId);
}
