package net.jenske.hyttebooking.controller;

import net.jenske.hyttebooking.controller.exception.ResourceNotFoundException;
import net.jenske.hyttebooking.model.Cabin;
import net.jenske.hyttebooking.repository.CabinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api")
public class CabinController {

    @Autowired
    CabinRepository cabinRepository;
    /**
     * Retrieves all cabins available in the system.
     *
     * @return a list of cabins or an empty list if no cabin is found
     */
    @GetMapping("/cabins")
    public ResponseEntity<List<Cabin>> getAllCabins(@RequestParam(required = false) String name) {
        try {
            List<Cabin> cabins = new ArrayList<Cabin>();

            if (name == null)
                cabins.addAll(cabinRepository.findAll());
            else
                cabins.addAll(cabinRepository.findByNameContaining(name));

            if (cabins.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(cabins, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves a single cabin by its unique identifier.
     *
     * @param id the unique identifier of the cabin to be retrieved
     * @return the cabin with the specified ID or a not found response if it doesn't exist
     */
    @GetMapping("/cabins/{id}")
    public ResponseEntity<Cabin> getCabinById(@PathVariable("id") long id) {
        Cabin cabin = cabinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cabin not found with id: " + id));
        return ResponseEntity.ok(cabin);
    }

    /**
     * Creates a new Cabin in the system.
     *
     * @param cabin the booking object to be created
     * @return the created Cabin with a 201 status code, or an error message if creation fails
     */
    @PostMapping("/cabins")
    public ResponseEntity<Cabin> createCabin(@RequestBody Cabin cabin) {
        try {
            Cabin _cabin = cabinRepository
                    .save(new Cabin(cabin.getName(), cabin.getLocation(), cabin.getDescription(), cabin.isVisible(), cabin.getNumberOfBeds()));
            return new ResponseEntity<>(_cabin, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    /**
     * Updates an existing cabin.
     *
     * @param id the unique identifier of the cabin to update
     * @param cabinDetails the updated cabin information
     * @return the updated cabin, or a 404 status if the cabin with the specified ID is not found
     */
    @PutMapping("/cabins/{id}")
    public ResponseEntity<Cabin> updateCabin(@PathVariable("id") long id, @RequestBody Cabin cabinDetails) {
        Optional<Cabin> cabinData = cabinRepository.findById(id);

        if (cabinData.isPresent()) {
            try {
                Cabin _cabin = cabinData.get();
                _cabin.setName(cabinDetails.getName());
                _cabin.setDescription(cabinDetails.getDescription());
                _cabin.setNumberOfBeds(cabinDetails.getNumberOfBeds());
                _cabin.setLocation(cabinDetails.getLocation());
                _cabin.setVisible(cabinDetails.isVisible());

                Cabin updatedCabin = cabinRepository.save(_cabin);
                return new ResponseEntity<>(updatedCabin, HttpStatus.OK);
            } catch (Exception e) {
                throw new ResourceNotFoundException(e.getMessage());
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a cabin from the system.
     *
     * @param id the unique identifier of the cabin to be deleted
     * @return a 204 status code if the deletion is successful, or an error message if the deletion fails
     */
    @DeleteMapping("/cabins/{id}")
    public ResponseEntity<HttpStatus> deleteCabin(@PathVariable("id") long id) {
        Cabin cabin = cabinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cabin not found with id: " + id));
        cabinRepository.delete(cabin);
        return ResponseEntity.noContent().build();
    }
}
