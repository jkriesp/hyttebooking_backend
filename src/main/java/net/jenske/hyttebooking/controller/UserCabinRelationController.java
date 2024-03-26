package net.jenske.hyttebooking.controller;

import net.jenske.hyttebooking.model.UserCabinRelation;
import net.jenske.hyttebooking.service.UserCabinRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping ("/api/userCabinRelations")
public class UserCabinRelationController {

    @Autowired
    private UserCabinRelationService userCabinRelationService;

    @PostMapping
    public ResponseEntity<UserCabinRelation> createUserCabinRelation(@RequestBody UserCabinRelation userCabinRelation) {
        return new ResponseEntity<>(userCabinRelationService.createUserCabinRelation(userCabinRelation), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserCabinRelation> getUserCabinRelationById(@PathVariable Long id) {
        return userCabinRelationService.getUserCabinRelationById(id)
                .map(userCabinRelation -> new ResponseEntity<>(userCabinRelation, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<UserCabinRelation>> getAllUserCabinRelations() {
        return new ResponseEntity<>(userCabinRelationService.getAllUserCabinRelations(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserCabinRelation> updateUserCabinRelation(@PathVariable Long id, @RequestBody UserCabinRelation userCabinRelationDetails) {
        UserCabinRelation updatedUserCabinRelation = userCabinRelationService.updateUserCabinRelation(id, userCabinRelationDetails);
        return new ResponseEntity<>(updatedUserCabinRelation, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserCabinRelation(@PathVariable Long id) {
        userCabinRelationService.deleteUserCabinRelation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
