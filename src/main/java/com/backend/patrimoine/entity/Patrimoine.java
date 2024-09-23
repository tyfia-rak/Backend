package com.backend.patrimoine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class Patrimoine {
    private String possesseur;
    private LocalDateTime derniereModification;
}
