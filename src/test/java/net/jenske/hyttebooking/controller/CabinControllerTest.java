package net.jenske.hyttebooking.controller;

import net.jenske.hyttebooking.model.Cabin;
import net.jenske.hyttebooking.repository.CabinRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CabinController.class)
public class CabinControllerTest {

    // Testing out GitHub Copilot for generating test cases

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CabinRepository cabinRepository;

    @Test
    public void testGetAllCabins() throws Exception {
        when(cabinRepository.findAll()).thenReturn(Arrays.asList(new Cabin()));
        mockMvc.perform(get("/api/cabins")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCabinById() throws Exception {
        when(cabinRepository.findById(anyLong())).thenReturn(Optional.of(new Cabin()));
        mockMvc.perform(get("/api/cabins/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateCabin() throws Exception {
        when(cabinRepository.save(any(Cabin.class))).thenReturn(new Cabin());
        mockMvc.perform(post("/api/cabins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test Cabin\", \"location\": \"Test Location\", \"description\": \"Test Description\", \"isVisible\": true, \"numberOfBeds\": 2}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateCabin() throws Exception {
        when(cabinRepository.findById(anyLong())).thenReturn(Optional.of(new Cabin()));
        when(cabinRepository.save(any(Cabin.class))).thenReturn(new Cabin());
        mockMvc.perform(put("/api/cabins/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Cabin\", \"location\": \"Updated Location\", \"description\": \"Updated Description\", \"isVisible\": true, \"numberOfBeds\": 3}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteCabin() throws Exception {
        when(cabinRepository.findById(anyLong())).thenReturn(Optional.of(new Cabin()));
        mockMvc.perform(delete("/api/cabins/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}