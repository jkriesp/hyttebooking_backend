package net.jenske.hyttebooking.repository;

import net.jenske.hyttebooking.model.UserCabinRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCabinRelationRepository extends JpaRepository<UserCabinRelation, Long> {
    Optional<UserCabinRelation> findByUser_UserIdAndCabin_CabinId(Long userId, Long cabinId);
}
