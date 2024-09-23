package com.backend.patrimoine;

import com.backend.patrimoine.controller.PatrimoineController;
import com.backend.patrimoine.entity.Patrimoine;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatrimoineController.class)
public class PatrimoineControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper; // Injecter l'ObjectMapper configuré

	private static final Path STORAGE_PATH = Paths.get("patrimoines");

	@BeforeEach
	public void setup() throws Exception {
		Files.createDirectories(STORAGE_PATH);
		Files.list(STORAGE_PATH).forEach(file -> {
			try {
				Files.delete(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	public void testCreateOrUpdatePatrimoine() throws Exception {
		Patrimoine patrimoine = new Patrimoine("John Doe", LocalDateTime.now());

		mockMvc.perform(put("/patrimoines/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(patrimoine))) // Utiliser l'ObjectMapper injecté
				.andExpect(status().isOk());
	}

	@Test
	public void testGetPatrimoine() throws Exception {
		Patrimoine patrimoine = new Patrimoine("John Doe", LocalDateTime.now());

		// Écrire le fichier JSON avec l'ObjectMapper injecté
		Files.write(STORAGE_PATH.resolve("1.json"), objectMapper.writeValueAsBytes(patrimoine));

		mockMvc.perform(get("/patrimoines/1"))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetPatrimoineNotFound() throws Exception {
		mockMvc.perform(get("/patrimoines/999"))
				.andExpect(status().isNotFound());
	}
}