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
		Patrimoine patrimoine = new Patrimoine();
		patrimoine.setPossesseur("John Doe");

		ObjectMapper mapper = new ObjectMapper();
		mockMvc.perform(put("/patrimoines/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(mapper.writeValueAsString(patrimoine)))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetPatrimoine() throws Exception {
		Patrimoine patrimoine = new Patrimoine();
		patrimoine.setPossesseur("John Doe");
		patrimoine.setDerniereModification(LocalDateTime.now());

		ObjectMapper mapper = new ObjectMapper();
		Files.write(STORAGE_PATH.resolve("1.json"), mapper.writeValueAsBytes(patrimoine));

		mockMvc.perform(get("/patrimoines/1"))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetPatrimoineNotFound() throws Exception {
		mockMvc.perform(get("/patrimoines/999"))
				.andExpect(status().isNotFound());
	}
}
