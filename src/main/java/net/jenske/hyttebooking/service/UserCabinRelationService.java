package net.jenske.hyttebooking.service;

import net.jenske.hyttebooking.controller.exception.ResourceNotFoundException;
import net.jenske.hyttebooking.model.UserCabinRelation;
import net.jenske.hyttebooking.repository.UserCabinRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserCabinRelationService {

    @Autowired
    private UserCabinRelationRepository userCabinRelationRepository;

    public UserCabinRelation createUserCabinRelation(UserCabinRelation userCabinRelation) {
        return userCabinRelationRepository.save(userCabinRelation);
    }

    public Optional<UserCabinRelation> getUserCabinRelationById(long id) {
        return userCabinRelationRepository.findById(id);
    }

    public List<UserCabinRelation> getAllUserCabinRelations() {
        return userCabinRelationRepository.findAll();
    }

    public UserCabinRelation updateUserCabinRelation(long id, UserCabinRelation userCabinRelationDetails) {
        UserCabinRelation userCabinRelation = userCabinRelationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserCabinRelation not found with id: " + id));

        userCabinRelation.setUser(userCabinRelationDetails.getUser());
        userCabinRelation.setCabin(userCabinRelationDetails.getCabin());

        return userCabinRelationRepository.save(userCabinRelation);
    }

    public void deleteUserCabinRelation(long id) {
        UserCabinRelation userCabinRelation = userCabinRelationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserCabinRelation not found with id: " + id));

        userCabinRelationRepository.delete(userCabinRelation);
    }
}
