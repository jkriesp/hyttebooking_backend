package net.jenske.hyttebooking.repository;

import net.jenske.hyttebooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Collection<? extends User> findByEmailContaining(String email);
    Optional<User> findById(long userId);
    Optional<User> findByEmail(String email);
}
