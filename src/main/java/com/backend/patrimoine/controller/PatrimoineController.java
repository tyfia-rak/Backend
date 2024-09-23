package com.backend.patrimoine.controller;

import com.backend.patrimoine.entity.Patrimoine;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/patrimoines")
public class PatrimoineController {

    private static final Path STORAGE_PATH = Paths.get("patrimoines");

    @PutMapping("/{id}")
    public ResponseEntity<Void> createOrUpdatePatrimoine(@PathVariable String id, @RequestBody Patrimoine patrimoine) throws IOException {
        patrimoine.setDerniereModification(LocalDateTime.now());
        ObjectMapper mapper = new ObjectMapper();
        Files.createDirectories(STORAGE_PATH);
        Files.write(STORAGE_PATH.resolve(id + ".json"), mapper.writeValueAsBytes(patrimoine));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patrimoine> getPatrimoine(@PathVariable String id) throws IOException {
        Path filePath = STORAGE_PATH.resolve(id + ".json");
        if (Files.exists(filePath)) {
            ObjectMapper mapper = new ObjectMapper();
            Patrimoine patrimoine = mapper.readValue(Files.readAllBytes(filePath), Patrimoine.class);
            return ResponseEntity.ok(patrimoine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
