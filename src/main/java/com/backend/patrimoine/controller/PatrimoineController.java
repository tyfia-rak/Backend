package com.backend.patrimoine.controller;

import com.backend.patrimoine.entity.Patrimoine;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/patrimoines")
public class PatrimoineController {

    private static final Path STORAGE_PATH = Paths.get("patrimoines");

    @Autowired
    private ObjectMapper objectMapper;

    @PutMapping("/{id}")
    public ResponseEntity<Void> createOrUpdatePatrimoine(@PathVariable String id, @RequestBody Patrimoine patrimoine) throws IOException {
        patrimoine.setDerniereModification(LocalDateTime.now());
        Files.createDirectories(STORAGE_PATH);
        Path filePath = STORAGE_PATH.resolve(id + ".json");
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            objectMapper.writeValue(writer, patrimoine);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patrimoine> getPatrimoine(@PathVariable String id) throws IOException {
        Path filePath = STORAGE_PATH.resolve(id + ".json");
        if (Files.exists(filePath)) {
            Patrimoine patrimoine = objectMapper.readValue(Files.newBufferedReader(filePath), Patrimoine.class);
            return ResponseEntity.ok(patrimoine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}