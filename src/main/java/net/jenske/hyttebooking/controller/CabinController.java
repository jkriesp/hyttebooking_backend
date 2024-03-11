package net.jenske.hyttebooking.controller;

import net.jenske.hyttebooking.model.Cabin;
import net.jenske.hyttebooking.repository.CabinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api")
public class CabinController {

    @Autowired
    CabinRepository cabinRepository;

    @GetMapping("/cabins")
    public ResponseEntity<List<Cabin>> getAllCabins(@RequestParam(required = false) String title) {
        try {
            List<Cabin> cabins = new ArrayList<Cabin>();

            if (title == null)
                cabins.addAll(cabinRepository.findAll());
            else
                cabins.addAll(cabinRepository.findByTitleContaining(title));

            if (cabins.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(cabins, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cabins/{id}")
    public ResponseEntity<Cabin> getCabinById(@PathVariable("id") long id) {
        try {
            Cabin cabin = cabinRepository.findById(id);

            if (cabin != null) {
                return new ResponseEntity<>(cabin, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/cabins")
    public ResponseEntity<Cabin> createCabin(@RequestBody Cabin cabin) {
        try {
            Cabin _cabin = cabinRepository
                    .save(new Cabin(cabin.getName(), cabin.getLocation(), cabin.getDescription(), cabin.isVisible(), cabin.getNumberOfBeds()));
            return new ResponseEntity<>(_cabin, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/cabins/{id}")
    public ResponseEntity<Cabin> updateCabin(@PathVariable("id") long id, @RequestBody Cabin cabin) {
        try {
            Cabin _cabin = cabinRepository.findById(id);
            _cabin.setName(cabin.getName());
            _cabin.setDescription(cabin.getDescription());
            _cabin.setNumberOfBeds(cabin.getNumberOfBeds());
            _cabin.setLocation(cabin.getLocation());
            _cabin.setVisible(cabin.isVisible());
            return new ResponseEntity<>(cabinRepository.save(_cabin), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cabins/{id}")
    public ResponseEntity<HttpStatus> deleteCabin(@PathVariable("id") long id) {
        try {
            cabinRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
