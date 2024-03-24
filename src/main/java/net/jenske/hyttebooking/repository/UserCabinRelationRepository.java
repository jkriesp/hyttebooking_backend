package net.jenske.hyttebooking.repository;

import net.jenske.hyttebooking.model.UserCabinRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCabinRelationRepository extends JpaRepository<UserCabinRelation, Long> {
}
