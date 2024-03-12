package net.jenske.hyttebooking.repository;

import net.jenske.hyttebooking.model.Cabin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CabinRepository extends JpaRepository<Cabin, Long> {
    Collection<Cabin> findByNameContaining(String name);
    Optional<Cabin> findById(long cabinId);
}
