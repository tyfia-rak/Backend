package com.backend.patrimoine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Patrimoine {
    private String possesseur;
    private LocalDateTime derniereModification;
}
